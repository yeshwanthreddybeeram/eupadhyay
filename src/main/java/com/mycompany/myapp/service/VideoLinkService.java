package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.VideoLink;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link VideoLink}.
 */
public interface VideoLinkService {

    /**
     * Save a videoLink.
     *
     * @param videoLink the entity to save.
     * @return the persisted entity.
     */
    VideoLink save(VideoLink videoLink);

    /**
     * Get all the videoLinks.
     *
     * @return the list of entities.
     */
    List<VideoLink> findAll();


    /**
     * Get the "id" videoLink.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VideoLink> findOne(Long id);

    /**
     * Delete the "id" videoLink.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
