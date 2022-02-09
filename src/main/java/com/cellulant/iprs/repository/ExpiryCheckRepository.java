package com.cellulant.iprs.repository;

import com.cellulant.iprs.model.ExpiryCheck;
import com.cellulant.iprs.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpiryCheckRepository extends JpaRepository<ExpiryCheck, Long> {
    Optional<ExpiryCheck> findByExpiryID(long id);

}
