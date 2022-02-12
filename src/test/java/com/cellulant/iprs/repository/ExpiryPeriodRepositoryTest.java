package com.cellulant.iprs.repository;

import com.cellulant.iprs.model.ExpiryPeriod;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ExpiryPeriod repository tests
 */
@Testcontainers
@DataJpaTest
@ContextConfiguration(initializers = ExpiryPeriodRepositoryTest.Initializer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ExpiryPeriodRepositoryTest {
    // create Postgres container definition
    @Container
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("testrepo")
            .withPassword("testrepo")
            .withInitScript("init.sql")
            .withDatabaseName("testrepo");

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

    /**
     * Establish connection to postgres instance
     */
    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues values = TestPropertyValues.of(
                    "spring.datasource.url=" + container.getJdbcUrl(),
                    "spring.datasource.password=" + container.getPassword(),
                    "spring.datasource.username=" + container.getUsername()
            );
            values.applyTo(configurableApplicationContext);
        }
    }

    @Test
    @DisplayName("shouldFindByExpiryPeriodID")
    void findByExpiryPeriodID() {
        expiryPeriodRepository.save(expiryPeriod1);
        Optional<ExpiryPeriod> expiryPeriod = expiryPeriodRepository.findByExpiryPeriodID(expiryPeriod1.getExpiryPeriodID());
        assertThat(expiryPeriod).isNotEmpty();
    }
}
