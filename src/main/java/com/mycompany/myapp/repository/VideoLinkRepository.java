package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.VideoLink;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the VideoLink entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VideoLinkRepository extends JpaRepository<VideoLink, Long> {
}
