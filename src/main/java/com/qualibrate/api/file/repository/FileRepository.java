package com.qualibrate.api.file.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author <a href="mailto:chetan.shirke1688@gmail.com">Chetan Shirke</a>
 *
 * Data Access Layer for Files
 */
public interface FileRepository extends JpaRepository<FileRecord, Long> {
    Page<FileRecord> findAll(Pageable pageable);
}
