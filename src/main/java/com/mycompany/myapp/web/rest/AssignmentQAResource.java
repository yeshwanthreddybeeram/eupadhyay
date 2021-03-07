package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.AssignmentQA;
import com.mycompany.myapp.service.AssignmentQAService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.AssignmentQA}.
 */
@RestController
@RequestMapping("/api")
public class AssignmentQAResource {

    private final Logger log = LoggerFactory.getLogger(AssignmentQAResource.class);

    private static final String ENTITY_NAME = "assignmentQA";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssignmentQAService assignmentQAService;

    public AssignmentQAResource(AssignmentQAService assignmentQAService) {
        this.assignmentQAService = assignmentQAService;
    }

    /**
     * {@code POST  /assignment-qas} : Create a new assignmentQA.
     *
     * @param assignmentQA the assignmentQA to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assignmentQA, or with status {@code 400 (Bad Request)} if the assignmentQA has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/assignment-qas")
    public ResponseEntity<AssignmentQA> createAssignmentQA(@RequestBody AssignmentQA assignmentQA) throws URISyntaxException {
        log.debug("REST request to save AssignmentQA : {}", assignmentQA);
        if (assignmentQA.getId() != null) {
            throw new BadRequestAlertException("A new assignmentQA cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssignmentQA result = assignmentQAService.save(assignmentQA);
        return ResponseEntity.created(new URI("/api/assignment-qas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /assignment-qas} : Updates an existing assignmentQA.
     *
     * @param assignmentQA the assignmentQA to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assignmentQA,
     * or with status {@code 400 (Bad Request)} if the assignmentQA is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assignmentQA couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/assignment-qas")
    public ResponseEntity<AssignmentQA> updateAssignmentQA(@RequestBody AssignmentQA assignmentQA) throws URISyntaxException {
        log.debug("REST request to update AssignmentQA : {}", assignmentQA);
        if (assignmentQA.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AssignmentQA result = assignmentQAService.save(assignmentQA);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, assignmentQA.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /assignment-qas} : get all the assignmentQAS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assignmentQAS in body.
     */
    @GetMapping("/assignment-qas")
    public ResponseEntity<List<AssignmentQA>> getAllAssignmentQAS(Pageable pageable) {
        log.debug("REST request to get a page of AssignmentQAS");
        Page<AssignmentQA> page = assignmentQAService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /assignment-qas/:id} : get the "id" assignmentQA.
     *
     * @param id the id of the assignmentQA to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assignmentQA, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/assignment-qas/{id}")
    public ResponseEntity<AssignmentQA> getAssignmentQA(@PathVariable Long id) {
        log.debug("REST request to get AssignmentQA : {}", id);
        Optional<AssignmentQA> assignmentQA = assignmentQAService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assignmentQA);
    }

    /**
     * {@code DELETE  /assignment-qas/:id} : delete the "id" assignmentQA.
     *
     * @param id the id of the assignmentQA to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/assignment-qas/{id}")
    public ResponseEntity<Void> deleteAssignmentQA(@PathVariable Long id) {
        log.debug("REST request to delete AssignmentQA : {}", id);
        assignmentQAService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
