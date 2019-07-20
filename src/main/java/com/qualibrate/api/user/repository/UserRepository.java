package com.qualibrate.api.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author <a href="mailto:chetan.shirke1688@gmail.com">Chetan Shirke</a>
 *
 * Data Access Layer for User
 */
public interface UserRepository extends JpaRepository<UserRecord, Long> {
    Page<UserRecord> findAll(Pageable pageable);

}
