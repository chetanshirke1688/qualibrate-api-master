package com.qualibrate.api.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author <a href="mailto:chetan.shirke1688@gmail.com">Chetan Shirke</a>
 *
 * Data Access Layer for Project
 */
public interface ProjectRepository extends JpaRepository<ProjectRecord, Long> {
    Page<ProjectRecord> findAll(Pageable pageable);
}
