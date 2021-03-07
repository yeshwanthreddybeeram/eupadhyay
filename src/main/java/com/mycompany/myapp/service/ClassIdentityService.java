package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ClassIdentity;
import com.mycompany.myapp.repository.ClassIdentityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ClassIdentity}.
 */
@Service
@Transactional
public class ClassIdentityService {

    private final Logger log = LoggerFactory.getLogger(ClassIdentityService.class);

    private final ClassIdentityRepository classIdentityRepository;

    public ClassIdentityService(ClassIdentityRepository classIdentityRepository) {
        this.classIdentityRepository = classIdentityRepository;
    }

    /**
     * Save a classIdentity.
     *
     * @param classIdentity the entity to save.
     * @return the persisted entity.
     */
    public ClassIdentity save(ClassIdentity classIdentity) {
        log.debug("Request to save ClassIdentity : {}", classIdentity);
        return classIdentityRepository.save(classIdentity);
    }

    /**
     * Get all the classIdentities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassIdentity> findAll(Pageable pageable) {
        log.debug("Request to get all ClassIdentities");
        return classIdentityRepository.findAll(pageable);
    }


    /**
     * Get one classIdentity by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClassIdentity> findOne(Long id) {
        log.debug("Request to get ClassIdentity : {}", id);
        return classIdentityRepository.findById(id);
    }

    /**
     * Delete the classIdentity by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClassIdentity : {}", id);
        classIdentityRepository.deleteById(id);
    }
}
