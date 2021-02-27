package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ConceptService;
import com.mycompany.myapp.domain.Concept;
import com.mycompany.myapp.repository.ConceptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Concept}.
 */
@Service
@Transactional
public class ConceptServiceImpl implements ConceptService {

    private final Logger log = LoggerFactory.getLogger(ConceptServiceImpl.class);

    private final ConceptRepository conceptRepository;

    public ConceptServiceImpl(ConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }

    @Override
    public Concept save(Concept concept) {
        log.debug("Request to save Concept : {}", concept);
        return conceptRepository.save(concept);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Concept> findAll() {
        log.debug("Request to get all Concepts");
        return conceptRepository.findAllWithEagerRelationships();
    }


    public Page<Concept> findAllWithEagerRelationships(Pageable pageable) {
        return conceptRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Concept> findOne(Long id) {
        log.debug("Request to get Concept : {}", id);
        return conceptRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Concept : {}", id);
        conceptRepository.deleteById(id);
    }
}
