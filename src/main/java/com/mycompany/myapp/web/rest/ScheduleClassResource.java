package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Employee;
import com.mycompany.myapp.domain.ScheduleClass;
import com.mycompany.myapp.domain.Student;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.MailService;
import com.mycompany.myapp.service.ScheduleClassService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing
 * {@link com.mycompany.myapp.domain.ScheduleClass}.
 */
@RestController
@RequestMapping("/api")
public class ScheduleClassResource {

    private final Logger log = LoggerFactory.getLogger(ScheduleClassResource.class);

    private static final String ENTITY_NAME = "scheduleClass";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScheduleClassService scheduleClassService;
    private final MailService mailService;

    public ScheduleClassResource(ScheduleClassService scheduleClassService, MailService mailService) {
        this.scheduleClassService = scheduleClassService;
        this.mailService = mailService;
    }

    /**
     * {@code POST  /schedule-classes} : Create a new scheduleClass.
     *
     * @param scheduleClass the scheduleClass to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new scheduleClass, or with status {@code 400 (Bad Request)}
     *         if the scheduleClass has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/schedule-classes")
    public ResponseEntity<ScheduleClass> createScheduleClass(@Valid @RequestBody ScheduleClass scheduleClass)
            throws URISyntaxException {
        log.debug("REST request to save ScheduleClass : {}", scheduleClass);
        if (scheduleClass.getId() != null) {
            throw new BadRequestAlertException("A new scheduleClass cannot already have an ID", ENTITY_NAME,
                    "idexists");
        }
        ScheduleClass result = scheduleClassService.save(scheduleClass);
        this.sendEmailToUsers(scheduleClass);
        return ResponseEntity
                .created(new URI("/api/schedule-classes/" + result.getId())).headers(HeaderUtil
                        .createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /schedule-classes} : Updates an existing scheduleClass.
     *
     * @param scheduleClass the scheduleClass to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated scheduleClass, or with status {@code 400 (Bad Request)}
     *         if the scheduleClass is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the scheduleClass couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/schedule-classes")
    public ResponseEntity<ScheduleClass> updateScheduleClass(@Valid @RequestBody ScheduleClass scheduleClass)
            throws URISyntaxException {
        log.debug("REST request to update ScheduleClass : {}", scheduleClass);
        if (scheduleClass.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScheduleClass result = scheduleClassService.save(scheduleClass);
        this.sendEmailToUsers(scheduleClass);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                scheduleClass.getId().toString())).body(result);
    }

    /**
     * {@code GET  /schedule-classes} : get all the scheduleClasses.
     *
     * @param eagerload flag to eager load entities from relationships (This is
     *                  applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of scheduleClasses in body.
     */
    @GetMapping("/schedule-classes")
    public List<ScheduleClass> getAllScheduleClasses(
            @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all ScheduleClasses");
        return scheduleClassService.findAll();
    }

    /**
     * {@code GET  /schedule-classes/:id} : get the "id" scheduleClass.
     *
     * @param id the id of the scheduleClass to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the scheduleClass, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/schedule-classes/{id}")
    public ResponseEntity<ScheduleClass> getScheduleClass(@PathVariable Long id) {
        log.debug("REST request to get ScheduleClass : {}", id);
        Optional<ScheduleClass> scheduleClass = scheduleClassService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scheduleClass);
    }

    /**
     * {@code DELETE  /schedule-classes/:id} : delete the "id" scheduleClass.
     *
     * @param id the id of the scheduleClass to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/schedule-classes/{id}")
    public ResponseEntity<Void> deleteScheduleClass(@PathVariable Long id) {
        log.debug("REST request to delete ScheduleClass : {}", id);
        scheduleClassService.delete(id);
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
    @GetMapping("/schedule-classes/student")
    public List<ScheduleClass> getStudentScheduleClasses(
            @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all ScheduleClasses");
        String loginname = SecurityUtils.getCurrentUserLogin().orElse("undefined");
        List<ScheduleClass> studentClasses = new ArrayList<>();
        List<ScheduleClass> allAchedules = scheduleClassService.findAll();
        for (ScheduleClass eachSchedule : allAchedules) {
            for (Student student : eachSchedule.getStudents()) {
                if (student.getUserName().equals(loginname)) {
                    studentClasses.add(eachSchedule);
                }
            }
        }
        Collections.sort(studentClasses);
        return studentClasses;

    }

    /**
     * {@code GET  /schedule-classes} : get all the scheduleClasses.
     *
     * @param eagerload flag to eager load entities from relationships (This is
     *                  applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of scheduleClasses in body.
     */
    @GetMapping("/schedule-classes/employee")
    public List<ScheduleClass> getEmployeeScheduleClasses(
            @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        String loginname = SecurityUtils.getCurrentUserLogin().orElse("undefined");

        List<ScheduleClass> employeeClasses = new ArrayList<>();
        List<ScheduleClass> allAchedules = scheduleClassService.findAll();
        for (ScheduleClass eachSchedule : allAchedules) {
            for (Employee employee : eachSchedule.getEmployees()) {
                if (employee.getUsername().equals(loginname)) {
                    employeeClasses.add(eachSchedule);
                }
            }
        }
        Collections.sort(employeeClasses);
        return employeeClasses;
    }

    private void sendEmailToUsers(ScheduleClass scheduleClass) {
        String subject = "E Uphadaya Class Scheduled";
        String content = "Class Scheduled join : " + scheduleClass.getSchedulelink() + "\n "
                + "Concept : "+scheduleClass.getConcept() + "\n " 
                + "Overview : "+scheduleClass.getOverview() + "\n " 
                + "Please login to https://eupadhyay.com  for more information.";
        for (Employee employee : scheduleClass.getEmployees()) {
            mailService.sendEmail(employee.getEmail(), subject, content, false, false);
        }
        for (Student student : scheduleClass.getStudents()) {
            mailService.sendEmail(student.getEmail(), subject, content, false, false);
        }
    }

}
