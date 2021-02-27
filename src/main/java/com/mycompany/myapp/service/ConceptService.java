package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Concept;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Concept}.
 */
public interface ConceptService {

    /**
     * Save a concept.
     *
     * @param concept the entity to save.
     * @return the persisted entity.
     */
    Concept save(Concept concept);

    /**
     * Get all the concepts.
     *
     * @return the list of entities.
     */
    List<Concept> findAll();

    /**
     * Get all the concepts with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Concept> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" concept.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Concept> findOne(Long id);

    /**
     * Delete the "id" concept.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
