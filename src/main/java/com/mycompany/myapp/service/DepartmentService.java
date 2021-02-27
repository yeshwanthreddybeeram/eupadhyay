package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Department;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Department}.
 */
public interface DepartmentService {

    /**
     * Save a department.
     *
     * @param department the entity to save.
     * @return the persisted entity.
     */
    Department save(Department department);

    /**
     * Get all the departments.
     *
     * @return the list of entities.
     */
    List<Department> findAll();

    /**
     * Get all the departments with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Department> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" department.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Department> findOne(Long id);

    /**
     * Delete the "id" department.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
