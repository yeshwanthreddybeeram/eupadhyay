package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.StudyMaterials;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the StudyMaterials entity.
 */
@Repository
public interface StudyMaterialsRepository extends JpaRepository<StudyMaterials, Long> {

    @Query(value = "select distinct studyMaterials from StudyMaterials studyMaterials left join fetch studyMaterials.videoLinks",
        countQuery = "select count(distinct studyMaterials) from StudyMaterials studyMaterials")
    Page<StudyMaterials> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct studyMaterials from StudyMaterials studyMaterials left join fetch studyMaterials.videoLinks")
    List<StudyMaterials> findAllWithEagerRelationships();

    @Query("select studyMaterials from StudyMaterials studyMaterials left join fetch studyMaterials.videoLinks where studyMaterials.id =:id")
    Optional<StudyMaterials> findOneWithEagerRelationships(@Param("id") Long id);
}
