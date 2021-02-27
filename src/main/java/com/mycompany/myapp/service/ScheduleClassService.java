package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ScheduleClass;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ScheduleClass}.
 */
public interface ScheduleClassService {

    /**
     * Save a scheduleClass.
     *
     * @param scheduleClass the entity to save.
     * @return the persisted entity.
     */
    ScheduleClass save(ScheduleClass scheduleClass);

    /**
     * Get all the scheduleClasses.
     *
     * @return the list of entities.
     */
    List<ScheduleClass> findAll();

    /**
     * Get all the scheduleClasses with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ScheduleClass> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" scheduleClass.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ScheduleClass> findOne(Long id);

    /**
     * Delete the "id" scheduleClass.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
