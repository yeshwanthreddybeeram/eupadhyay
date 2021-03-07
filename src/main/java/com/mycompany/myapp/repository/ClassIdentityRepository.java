package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ClassIdentity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ClassIdentity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassIdentityRepository extends JpaRepository<ClassIdentity, Long> {
}
