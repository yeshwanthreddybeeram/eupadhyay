package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ClassIdentity;
import com.mycompany.myapp.service.ClassIdentityService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ClassIdentity}.
 */
@RestController
@RequestMapping("/api")
public class ClassIdentityResource {

    private final Logger log = LoggerFactory.getLogger(ClassIdentityResource.class);

    private static final String ENTITY_NAME = "classIdentity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassIdentityService classIdentityService;

    public ClassIdentityResource(ClassIdentityService classIdentityService) {
        this.classIdentityService = classIdentityService;
    }

    /**
     * {@code POST  /class-identities} : Create a new classIdentity.
     *
     * @param classIdentity the classIdentity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classIdentity, or with status {@code 400 (Bad Request)} if the classIdentity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/class-identities")
    public ResponseEntity<ClassIdentity> createClassIdentity(@RequestBody ClassIdentity classIdentity) throws URISyntaxException {
        log.debug("REST request to save ClassIdentity : {}", classIdentity);
        if (classIdentity.getId() != null) {
            throw new BadRequestAlertException("A new classIdentity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassIdentity result = classIdentityService.save(classIdentity);
        return ResponseEntity.created(new URI("/api/class-identities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /class-identities} : Updates an existing classIdentity.
     *
     * @param classIdentity the classIdentity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classIdentity,
     * or with status {@code 400 (Bad Request)} if the classIdentity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classIdentity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/class-identities")
    public ResponseEntity<ClassIdentity> updateClassIdentity(@RequestBody ClassIdentity classIdentity) throws URISyntaxException {
        log.debug("REST request to update ClassIdentity : {}", classIdentity);
        if (classIdentity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClassIdentity result = classIdentityService.save(classIdentity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, classIdentity.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /class-identities} : get all the classIdentities.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classIdentities in body.
     */
    @GetMapping("/class-identities")
    public ResponseEntity<List<ClassIdentity>> getAllClassIdentities(Pageable pageable) {
        log.debug("REST request to get a page of ClassIdentities");
        Page<ClassIdentity> page = classIdentityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /class-identities/:id} : get the "id" classIdentity.
     *
     * @param id the id of the classIdentity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classIdentity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/class-identities/{id}")
    public ResponseEntity<ClassIdentity> getClassIdentity(@PathVariable Long id) {
        log.debug("REST request to get ClassIdentity : {}", id);
        Optional<ClassIdentity> classIdentity = classIdentityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classIdentity);
    }

    /**
     * {@code DELETE  /class-identities/:id} : delete the "id" classIdentity.
     *
     * @param id the id of the classIdentity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/class-identities/{id}")
    public ResponseEntity<Void> deleteClassIdentity(@PathVariable Long id) {
        log.debug("REST request to delete ClassIdentity : {}", id);
        classIdentityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
