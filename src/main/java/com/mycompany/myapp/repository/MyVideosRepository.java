package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MyVideos;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MyVideos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MyVideosRepository extends JpaRepository<MyVideos, Long> {
}
