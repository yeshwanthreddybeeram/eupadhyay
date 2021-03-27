package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.StudyMaterials;
import com.mycompany.myapp.repository.StudyMaterialsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StudyMaterials}.
 */
@Service
@Transactional
public class StudyMaterialsService {

    private final Logger log = LoggerFactory.getLogger(StudyMaterialsService.class);

    private final StudyMaterialsRepository studyMaterialsRepository;

    public StudyMaterialsService(StudyMaterialsRepository studyMaterialsRepository) {
        this.studyMaterialsRepository = studyMaterialsRepository;
    }

    /**
     * Save a studyMaterials.
     *
     * @param studyMaterials the entity to save.
     * @return the persisted entity.
     */
    public StudyMaterials save(StudyMaterials studyMaterials) {
        log.debug("Request to save StudyMaterials : {}", studyMaterials);
        return studyMaterialsRepository.save(studyMaterials);
    }

    /**
     * Get all the studyMaterials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StudyMaterials> findAll(Pageable pageable) {
        log.debug("Request to get all StudyMaterials");
        return studyMaterialsRepository.findAll(pageable);
    }


    /**
     * Get all the studyMaterials with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<StudyMaterials> findAllWithEagerRelationships(Pageable pageable) {
        return studyMaterialsRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one studyMaterials by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StudyMaterials> findOne(Long id) {
        log.debug("Request to get StudyMaterials : {}", id);
        return studyMaterialsRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the studyMaterials by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StudyMaterials : {}", id);
        studyMaterialsRepository.deleteById(id);
    }
}
