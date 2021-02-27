package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Concept;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Concept entity.
 */
@Repository
public interface ConceptRepository extends JpaRepository<Concept, Long> {

    @Query(value = "select distinct concept from Concept concept left join fetch concept.videoLinks",
        countQuery = "select count(distinct concept) from Concept concept")
    Page<Concept> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct concept from Concept concept left join fetch concept.videoLinks")
    List<Concept> findAllWithEagerRelationships();

    @Query("select concept from Concept concept left join fetch concept.videoLinks where concept.id =:id")
    Optional<Concept> findOneWithEagerRelationships(@Param("id") Long id);
}
