package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AssignmentQA;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AssignmentQA entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssignmentQARepository extends JpaRepository<AssignmentQA, Long> {
}
