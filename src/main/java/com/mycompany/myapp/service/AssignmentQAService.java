package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.AssignmentQA;
import com.mycompany.myapp.repository.AssignmentQARepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AssignmentQA}.
 */
@Service
@Transactional
public class AssignmentQAService {

    private final Logger log = LoggerFactory.getLogger(AssignmentQAService.class);

    private final AssignmentQARepository assignmentQARepository;

    public AssignmentQAService(AssignmentQARepository assignmentQARepository) {
        this.assignmentQARepository = assignmentQARepository;
    }

    /**
     * Save a assignmentQA.
     *
     * @param assignmentQA the entity to save.
     * @return the persisted entity.
     */
    public AssignmentQA save(AssignmentQA assignmentQA) {
        log.debug("Request to save AssignmentQA : {}", assignmentQA);
        return assignmentQARepository.save(assignmentQA);
    }

    /**
     * Get all the assignmentQAS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AssignmentQA> findAll(Pageable pageable) {
        log.debug("Request to get all AssignmentQAS");
        return assignmentQARepository.findAll(pageable);
    }


    /**
     * Get one assignmentQA by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AssignmentQA> findOne(Long id) {
        log.debug("Request to get AssignmentQA : {}", id);
        return assignmentQARepository.findById(id);
    }

    /**
     * Delete the assignmentQA by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AssignmentQA : {}", id);
        assignmentQARepository.deleteById(id);
    }
}
