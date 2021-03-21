package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Assignment;
import com.mycompany.myapp.domain.Employee;
import com.mycompany.myapp.domain.Student;
import com.mycompany.myapp.repository.EmployeeRepository;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.AssignmentService;
import com.mycompany.myapp.service.MailService;
import com.mycompany.myapp.service.StudentService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Assignment}.
 */
@RestController
@RequestMapping("/api")
public class AssignmentResource {

    private final Logger log = LoggerFactory.getLogger(AssignmentResource.class);

    private static final String ENTITY_NAME = "assignment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssignmentService assignmentService;
    private final EmployeeRepository employeeRepository;
    private final StudentService studentService;
    private final MailService mailService;

    public AssignmentResource(AssignmentService assignmentService, EmployeeRepository employeeRepository,
            MailService mailService, StudentService studentService) {
        this.assignmentService = assignmentService;
        this.employeeRepository = employeeRepository;
        this.mailService = mailService;
        this.studentService = studentService;

    }

    /**
     * {@code POST  /assignments} : Create a new assignment.
     *
     * @param assignment the assignment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new assignment, or with status {@code 400 (Bad Request)} if
     *         the assignment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/assignments")
    public ResponseEntity<Assignment> createAssignment(@RequestBody Assignment assignment) throws URISyntaxException {
        log.debug("REST request to save Assignment : {}", assignment);
        if (assignment.getId() != null) {
            throw new BadRequestAlertException("A new assignment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Assignment result = assignmentService.save(assignment);
        sendEmailToUsers(assignment);
        return ResponseEntity
                .created(new URI("/api/assignments/" + result.getId())).headers(HeaderUtil
                        .createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /assignments} : Updates an existing assignment.
     *
     * @param assignment the assignment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated assignment, or with status {@code 400 (Bad Request)} if
     *         the assignment is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the assignment couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/assignments")
    public ResponseEntity<Assignment> updateAssignment(@RequestBody Assignment assignment) throws URISyntaxException {
        log.debug("REST request to update Assignment : {}", assignment);
        if (assignment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Assignment result = assignmentService.save(assignment);
        sendEmailToUsers(assignment);
        return ResponseEntity.ok().headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, assignment.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /assignments} : get all the assignments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of assignments in body.
     */
    @GetMapping("/assignments")
    public List<Assignment> getAllAssignments() {
        log.debug("REST request to get all Assignments");
        return assignmentService.findAll();
    }

    /**
     * {@code GET  /assignments/:id} : get the "id" assignment.
     *
     * @param id the id of the assignment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the assignment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/assignments/{id}")
    public ResponseEntity<Assignment> getAssignment(@PathVariable Long id) {
        log.debug("REST request to get Assignment : {}", id);
        Optional<Assignment> assignment = assignmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assignment);
    }

    /**
     * {@code DELETE  /assignments/:id} : delete the "id" assignment.
     *
     * @param id the id of the assignment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/assignments/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        log.debug("REST request to delete Assignment : {}", id);
        assignmentService.delete(id);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }

    // Custom defined Get dunction

    /**
     * {@code GET  /schedule-classes} : get all the scheduleClasses.
     *
     * @param eagerload flag to eager load entities from relationships (This is
     *                  applicable for many-to-many).
     * 
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of scheduleClasses in body.
     */
    @GetMapping("/assignments/student")
    public List<Assignment> getStudentsAssignments() {
        log.debug("REST request to get Student Spefic Assignments");
        String loginname = SecurityUtils.getCurrentUserLogin().orElse("undefined");
        List<Assignment> studentAssignments = new ArrayList<>();
        List<Assignment> allAssignments = assignmentService.findAll();
        for (Assignment assignment : allAssignments) {
            if (assignment.getStudentloginname() != null && !assignment.getStudentloginname().isEmpty()) {

                if (assignment.getStudentloginname().equals(loginname)) {
                    studentAssignments.add(assignment);
                }
            }
        }
        return studentAssignments;

    }

    /**
     * {@code GET  /schedule-classes} : get all the scheduleClasses.
     *
     * @param eagerload flag to eager load entities from relationships (This is
     *                  applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of scheduleClasses in body.
     */
    @GetMapping("/sassignments/employee")
    public List<Assignment> getEmployeeAssignments() {
        log.debug("REST request to get Student Spefic Assignments");
        String loginname = SecurityUtils.getCurrentUserLogin().orElse("undefined");
        List<Assignment> employeeivenAssignments = new ArrayList<>();
        List<Assignment> allAssignments = assignmentService.findAll();
        for (Assignment assignment : allAssignments) {
            if (assignment.getStudentloginname() != null && !assignment.getStudentloginname().isEmpty()) {
            }
            if (assignment.getEmployeeloginname().equals(loginname)) {
                employeeivenAssignments.add(assignment);
            }
        }
        return employeeivenAssignments;
    }

    private void sendEmailToUsers(Assignment assignment) {
        String subject = "E Uphadaya Assignment Given";
        String content = "Assignment Schedulued : " + assignment.getAssignmentlink() + "\n " + assignment.getSubject()
                + "\n " + assignment.getDescription() + "\n " + "Submit Date" + "\n " + assignment.getSubmitdate();
        Optional<Employee> employee = employeeRepository.findOneWithLoginName(assignment.getEmployeeloginname());
        employee.ifPresent(emp -> {
            mailService.sendEmail(emp.getEmail(), subject, content, false, false);
        });

        for (Student std : studentService.findAll()) {
            if (std.getUserName().equals(assignment.getStudentloginname())) {
                mailService.sendEmail(std.getEmail(), subject, content, false, false);
            }
        }
    }
}
