package com.mycompany.myapp.service;

import com.mycompany.myapp.config.Constants;
import com.mycompany.myapp.domain.Authority;
import com.mycompany.myapp.domain.Employee;
import com.mycompany.myapp.domain.Student;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.AuthorityRepository;
import com.mycompany.myapp.repository.EmployeeRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.security.AuthoritiesConstants;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.dto.UserDTO;

import io.github.jhipster.security.RandomUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    private final CacheManager cacheManager;
    private final StudentService studentService;

    private EmployeeRepository employeeRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            AuthorityRepository authorityRepository, CacheManager cacheManager, StudentService studentService,
            EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
        this.studentService = studentService;
        this.employeeRepository = employeeRepository;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key).map(user -> {
            // activate given user for the registration key.
            user.setActivated(true);
            user.setActivationKey(null);
            this.clearUserCaches(user);
            log.debug("Activated user: {}", user);
            return user;
        });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return userRepository.findOneByResetKey(key)
                .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400))).map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setResetKey(null);
                    user.setResetDate(null);
                    this.clearUserCaches(user);
                    return user;
                });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmailIgnoreCase(mail).filter(User::getActivated).map(user -> {
            user.setResetKey(RandomUtil.generateResetKey());
            user.setResetDate(Instant.now());
            this.clearUserCaches(user);
            return user;
        });
    }

    public User registerUser(UserDTO userDTO, String password) {
        userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new UsernameAlreadyUsedException();
            }
        });
        userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new EmailAlreadyUsedException();
            }
        });
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(userDTO.getLogin().toLowerCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            newUser.setEmail(userDTO.getEmail().toLowerCase());
        }
        newUser.setImageUrl(userDTO.getImageUrl());
        newUser.setLangKey(userDTO.getLangKey());
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        authorityRepository.findById(AuthoritiesConstants.STUDENT).ifPresent(authorities::add);

        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        this.createStudent(userDTO);
        this.clearUserCaches(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.getActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        this.clearUserCaches(existingUser);
        return true;
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin().toLowerCase());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail().toLowerCase());
        }
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = userDTO.getAuthorities().stream().map(authorityRepository::findById)
                    .filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
            user.setAuthorities(authorities);
            Authority roleEmployee = new Authority();
            // ADD the User if employee to the Employee list
            roleEmployee.setName("ROLE_EMPLOYEE");
            if (authorities.contains(roleEmployee)) {
                this.createEmployee(userDTO);
            }
            Authority roleStudent = new Authority();
            roleStudent.setName("ROLE_STUDENT");
            Authority roleUser = new Authority();
            roleUser.setName("ROLE_USER");
            if (authorities.contains(roleStudent) || authorities.contains(roleUser)) {
                this.createStudent(userDTO);
            }
        }
        userRepository.save(user);
        this.clearUserCaches(user);
        log.debug("Created Information for User: {}", user);
        // Add the User To either Student or Employee based on the Role of the user

        return user;
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update.
     * @return updated user.
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository.findById(userDTO.getId())).filter(Optional::isPresent).map(Optional::get)
                .map(user -> {
                    this.clearUserCaches(user);
                    user.setLogin(userDTO.getLogin().toLowerCase());
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
                    if (userDTO.getEmail() != null) {
                        user.setEmail(userDTO.getEmail().toLowerCase());
                    }
                    user.setImageUrl(userDTO.getImageUrl());
                    user.setActivated(userDTO.isActivated());
                    user.setLangKey(userDTO.getLangKey());
                    Set<Authority> managedAuthorities = user.getAuthorities();
                    managedAuthorities.clear();
                    userDTO.getAuthorities().stream().map(authorityRepository::findById).filter(Optional::isPresent)
                            .map(Optional::get).forEach(managedAuthorities::add);
                    this.clearUserCaches(user);
                    Authority roleEmployee = new Authority();
                    Authority roleStudent = new Authority();
                    roleStudent.setName("ROLE_STUDENT");
                    Authority roleUser = new Authority();
                    roleUser.setName("ROLE_USER");
                    // ADD the User if employee to the Employee list
                    roleEmployee.setName("ROLE_EMPLOYEE");
                    if (managedAuthorities.contains(roleEmployee)) {
                        this.createOrUpdateEmployee(userDTO);
                    }
                    if(managedAuthorities.contains(roleStudent))
                    {
                        System.out.println("hello world");
                        this.createOrUpdateStudent(userDTO);
                    }
                    log.debug("Changed Information for User: {}", user);
                    return user;
                }).map(UserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            userRepository.delete(user);
            this.clearUserCaches(user);
            log.debug("Deleted User: {}", user);
        });
    }

    /**
     * Update basic information (first name, last name, email, language) for the
     * current user.
     *
     * @param firstName first name of user.
     * @param lastName  last name of user.
     * @param email     email id of user.
     * @param langKey   language key.
     * @param imageUrl  image URL of user.
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin).ifPresent(user -> {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            if (email != null) {
                user.setEmail(email.toLowerCase());
            }
            user.setLangKey(langKey);
            user.setImageUrl(imageUrl);
            this.clearUserCaches(user);
            log.debug("Changed Information for User: {}", user);
        });
    }

    @Transactional
    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin).ifPresent(user -> {
            String currentEncryptedPassword = user.getPassword();
            if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                throw new InvalidPasswordException();
            }
            String encryptedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encryptedPassword);
            this.clearUserCaches(user);
            log.debug("Changed password for User: {}", user);
        });
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository.findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(
                Instant.now().minus(3, ChronoUnit.DAYS)).forEach(user -> {
                    log.debug("Deleting not activated user {}", user.getLogin());
                    userRepository.delete(user);
                    for (Student st : studentService.findAll()) {
                        if (st.getUserName().equals(user.getLogin())) {
                            studentService.delete(st.getId());
                        }
                    }
                    this.clearUserCaches(user);
                });
    }

    /**
     * Gets a list of all the authorities.
     * 
     * @return a list of all the authorities.
     */
    @Transactional(readOnly = true)
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        if (user.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
        }
    }

    // Customization functions

    private void createEmployee(UserDTO userDto) {
        Employee employee = new Employee();
        employee.setFirstName(userDto.getFirstName());
        employee.setUsername(userDto.getLogin());
        employee.setEmail(userDto.getEmail());
        employee.setLastName(userDto.getLastName());
        employee.setPhoneNumber("9 Digit number");
        employeeRepository.save(employee);
    }

    // Customization functions

    private void createStudent(UserDTO userDto) {
        Student student = new Student();
        if (userDto.getFirstName() == null || userDto.getFirstName().isEmpty()) {
            student.setFullName(userDto.getLogin());
        } else {
            student.setFullName(userDto.getFirstName() +" "+userDto.getLastName());
        }
        student.setUserName(userDto.getLogin());
        student.setEmail(userDto.getEmail());
        student.setPhoneNumber("9 Digit number");
        studentService.save(student);
    }

    private void createOrUpdateEmployee(UserDTO userDto) {
        List<Employee> existingEmployees = employeeRepository.findAll();
        boolean emloyeeExist = false;
        if (existingEmployees != null) {
            for (Employee employee : existingEmployees) {
                if (employee.getUsername().equals(userDto.getLogin())) {
                    emloyeeExist = true;
                    employee.setFirstName(userDto.getFirstName());
                    employee.setUsername(userDto.getLogin());
                    employee.setEmail(userDto.getEmail());
                    employee.setLastName(userDto.getLastName());
                    employee.setPhoneNumber("9 Digit number");
                    employeeRepository.save(employee);
                }
            }
        }
        if (emloyeeExist == false) {
            this.createEmployee(userDto);
        }
    }

    
    private void createOrUpdateStudent(UserDTO userDto) {
        List<Student> existingStudents = studentService.findAll();
        boolean studentExist = false;
        if (existingStudents != null) {
            for (Student student : existingStudents) {
                if (student.getUserName().equals(userDto.getLogin())) {
                    studentExist = true;
                    student.setFullName(userDto.getFirstName() + " "+ userDto.getLastName());
                    student.setUserName(userDto.getLogin());
                    student.setEmail(userDto.getEmail());
                    student.setPhoneNumber("9 Digit number");
                    studentService.save(student);
                }
            }
        }
        if (studentExist == false) {
          this.createStudent(userDto);
        }
    }

}
