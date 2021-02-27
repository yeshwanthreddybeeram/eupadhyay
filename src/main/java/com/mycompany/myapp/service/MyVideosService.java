package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.MyVideos;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MyVideos}.
 */
public interface MyVideosService {

    /**
     * Save a myVideos.
     *
     * @param myVideos the entity to save.
     * @return the persisted entity.
     */
    MyVideos save(MyVideos myVideos);

    /**
     * Get all the myVideos.
     *
     * @return the list of entities.
     */
    List<MyVideos> findAll();


    /**
     * Get the "id" myVideos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MyVideos> findOne(Long id);

    /**
     * Delete the "id" myVideos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
