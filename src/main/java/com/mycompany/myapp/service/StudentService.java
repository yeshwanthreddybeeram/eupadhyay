package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Student}.
 */
public interface StudentService {

    /**
     * Save a student.
     *
     * @param student the entity to save.
     * @return the persisted entity.
     */
    Student save(Student student);

    /**
     * Get all the students.
     *
     * @return the list of entities.
     */
    List<Student> findAll();

    /**
     * Get all the students with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Student> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" student.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Student> findOne(Long id);

    /**
     * Delete the "id" student.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
