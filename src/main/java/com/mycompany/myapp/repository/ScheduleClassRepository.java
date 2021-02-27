package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ScheduleClass;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ScheduleClass entity.
 */
@Repository
public interface ScheduleClassRepository extends JpaRepository<ScheduleClass, Long> {

    @Query(value = "select distinct scheduleClass from ScheduleClass scheduleClass left join fetch scheduleClass.students left join fetch scheduleClass.employees",
        countQuery = "select count(distinct scheduleClass) from ScheduleClass scheduleClass")
    Page<ScheduleClass> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct scheduleClass from ScheduleClass scheduleClass left join fetch scheduleClass.students left join fetch scheduleClass.employees")
    List<ScheduleClass> findAllWithEagerRelationships();

    @Query("select scheduleClass from ScheduleClass scheduleClass left join fetch scheduleClass.students left join fetch scheduleClass.employees where scheduleClass.id =:id")
    Optional<ScheduleClass> findOneWithEagerRelationships(@Param("id") Long id);
}
