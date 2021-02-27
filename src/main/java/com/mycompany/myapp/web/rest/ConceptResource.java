package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Concept;
import com.mycompany.myapp.service.ConceptService;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Concept}.
 */
@RestController
@RequestMapping("/api")
public class ConceptResource {

    private final Logger log = LoggerFactory.getLogger(ConceptResource.class);

    private static final String ENTITY_NAME = "concept";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConceptService conceptService;

    public ConceptResource(ConceptService conceptService) {
        this.conceptService = conceptService;
    }

    /**
     * {@code POST  /concepts} : Create a new concept.
     *
     * @param concept the concept to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new concept, or with status {@code 400 (Bad Request)} if the concept has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concepts")
    public ResponseEntity<Concept> createConcept(@RequestBody Concept concept) throws URISyntaxException {
        log.debug("REST request to save Concept : {}", concept);
        if (concept.getId() != null) {
            throw new BadRequestAlertException("A new concept cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Concept result = conceptService.save(concept);
        return ResponseEntity.created(new URI("/api/concepts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concepts} : Updates an existing concept.
     *
     * @param concept the concept to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated concept,
     * or with status {@code 400 (Bad Request)} if the concept is not valid,
     * or with status {@code 500 (Internal Server Error)} if the concept couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concepts")
    public ResponseEntity<Concept> updateConcept(@RequestBody Concept concept) throws URISyntaxException {
        log.debug("REST request to update Concept : {}", concept);
        if (concept.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Concept result = conceptService.save(concept);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, concept.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /concepts} : get all the concepts.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of concepts in body.
     */
    @GetMapping("/concepts")
    public List<Concept> getAllConcepts(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Concepts");
        return conceptService.findAll();
    }

    /**
     * {@code GET  /concepts/:id} : get the "id" concept.
     *
     * @param id the id of the concept to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the concept, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concepts/{id}")
    public ResponseEntity<Concept> getConcept(@PathVariable Long id) {
        log.debug("REST request to get Concept : {}", id);
        Optional<Concept> concept = conceptService.findOne(id);
        return ResponseUtil.wrapOrNotFound(concept);
    }

    /**
     * {@code DELETE  /concepts/:id} : delete the "id" concept.
     *
     * @param id the id of the concept to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concepts/{id}")
    public ResponseEntity<Void> deleteConcept(@PathVariable Long id) {
        log.debug("REST request to delete Concept : {}", id);
        conceptService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
