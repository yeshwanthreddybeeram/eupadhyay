package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.StudyMaterials;
import com.mycompany.myapp.service.StudyMaterialsService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.StudyMaterials}.
 */
@RestController
@RequestMapping("/api")
public class StudyMaterialsResource {

    private final Logger log = LoggerFactory.getLogger(StudyMaterialsResource.class);

    private static final String ENTITY_NAME = "studyMaterials";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudyMaterialsService studyMaterialsService;

    public StudyMaterialsResource(StudyMaterialsService studyMaterialsService) {
        this.studyMaterialsService = studyMaterialsService;
    }

    /**
     * {@code POST  /study-materials} : Create a new studyMaterials.
     *
     * @param studyMaterials the studyMaterials to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studyMaterials, or with status {@code 400 (Bad Request)} if the studyMaterials has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/study-materials")
    public ResponseEntity<StudyMaterials> createStudyMaterials(@RequestBody StudyMaterials studyMaterials) throws URISyntaxException {
        log.debug("REST request to save StudyMaterials : {}", studyMaterials);
        if (studyMaterials.getId() != null) {
            throw new BadRequestAlertException("A new studyMaterials cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudyMaterials result = studyMaterialsService.save(studyMaterials);
        return ResponseEntity.created(new URI("/api/study-materials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /study-materials} : Updates an existing studyMaterials.
     *
     * @param studyMaterials the studyMaterials to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studyMaterials,
     * or with status {@code 400 (Bad Request)} if the studyMaterials is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studyMaterials couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/study-materials")
    public ResponseEntity<StudyMaterials> updateStudyMaterials(@RequestBody StudyMaterials studyMaterials) throws URISyntaxException {
        log.debug("REST request to update StudyMaterials : {}", studyMaterials);
        if (studyMaterials.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudyMaterials result = studyMaterialsService.save(studyMaterials);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, studyMaterials.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /study-materials} : get all the studyMaterials.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of studyMaterials in body.
     */
    @GetMapping("/study-materials")
    public ResponseEntity<List<StudyMaterials>> getAllStudyMaterials(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of StudyMaterials");
        Page<StudyMaterials> page;
        if (eagerload) {
            page = studyMaterialsService.findAllWithEagerRelationships(pageable);
        } else {
            page = studyMaterialsService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /study-materials/:id} : get the "id" studyMaterials.
     *
     * @param id the id of the studyMaterials to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the studyMaterials, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/study-materials/{id}")
    public ResponseEntity<StudyMaterials> getStudyMaterials(@PathVariable Long id) {
        log.debug("REST request to get StudyMaterials : {}", id);
        Optional<StudyMaterials> studyMaterials = studyMaterialsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studyMaterials);
    }

    /**
     * {@code DELETE  /study-materials/:id} : delete the "id" studyMaterials.
     *
     * @param id the id of the studyMaterials to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/study-materials/{id}")
    public ResponseEntity<Void> deleteStudyMaterials(@PathVariable Long id) {
        log.debug("REST request to delete StudyMaterials : {}", id);
        studyMaterialsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
