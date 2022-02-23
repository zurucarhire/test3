package com.cellulant.iprs.repository;

import com.cellulant.iprs.entity.ExpiryPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpiryPeriodRepository extends JpaRepository<ExpiryPeriod, Long> {
    Optional<ExpiryPeriod> findByExpiryPeriodID(long expiryPeriodId);
    @Query("SELECT e FROM ExpiryPeriod e")
    Optional<ExpiryPeriod> findExpiryPeriod();
}
