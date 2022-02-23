package com.cellulant.iprs.repository;

import com.cellulant.iprs.entity.ExpiryPeriod;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ExpiryPeriod repository tests
 */
@DataJpaTest
public class ExpiryPeriodRepositoryTest {
    // create Postgres container definition

    @Autowired
    ExpiryPeriodRepository expiryPeriodRepository;

    private ExpiryPeriod expiryPeriod1;

    /**
     * Execute code before running each test
     */
    @BeforeEach
    public void setupModel() {
        expiryPeriod1 = ExpiryPeriod.builder()
                .expiryPeriod(1)
                .active(1)
                .createdBy(1L)
                .updatedBy(1L)
                .build();
    }

    /**
     * Execute code after running each test
     */
    @AfterEach
    public void tearDown() {
        expiryPeriod1 = null;
    }

    @Test
    @DisplayName("shouldFindByExpiryPeriodID")
    void findByExpiryPeriodID() {
        expiryPeriodRepository.save(expiryPeriod1);
        Optional<ExpiryPeriod> expiryPeriod = expiryPeriodRepository.findByExpiryPeriodID(expiryPeriod1.getExpiryPeriodID());
        assertThat(expiryPeriod).isNotEmpty();
        assertThat(expiryPeriod.get()).isEqualTo(expiryPeriod1);
    }
}
