package com.cellulant.iprs.repository;
import com.cellulant.iprs.model.*;
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

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * RequestType repository tests
 */
@Testcontainers
@DataJpaTest
@ContextConfiguration(initializers = RequestTypeRepositoryTest.Initializer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RequestTypeRepositoryTest {
    // create Postgres container definition
    @Container
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("testrepo")
            .withPassword("testrepo")
            .withInitScript("init.sql")
            .withDatabaseName("testrepo");

    @Autowired
    RequestTypeRepository requestTypeRepository;

    private RequestType requestType1, requestType2, requestType3;

    /**
     * Execute code before running each test
     */
    @BeforeEach
    public void setupModel() {
        requestType1 = RequestType.builder()
                .requestTypeName("Alias")
                .active(1)
                .createdBy(1L)
                .updatedBy(1L)
                .build();
        requestType2 = RequestType.builder()
                .requestTypeName("Passport")
                .active(1)
                .createdBy(1L)
                .updatedBy(1L)
                .build();
        requestType3 = RequestType.builder()
                .requestTypeName("ID Number")
                .active(0)
                .createdBy(1L)
                .updatedBy(1L)
                .build();
    }

    /**
     * Execute code after running each test
     */
    @AfterEach
    public void tearDown() {
        requestType1 = null;
        requestType2 = null;

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
    @DisplayName("shouldFindByRequestTypeID")
    void findByRequestTypeID() {
        requestTypeRepository.save(requestType1);
        Optional<RequestType> requestType = requestTypeRepository.findByRequestTypeID(requestType1.getRequestTypeID());
        System.out.println(">>> "+requestType);
        assertThat(requestType).isNotEmpty();
    }

    @Test
    @DisplayName("shouldFindByRequestTypeNameIgnoreCase")
    void findByRequestTypeNameIgnoreCase() {
        requestTypeRepository.save(requestType1);
        Optional<RequestType> requestType = requestTypeRepository.findByRequestTypeNameIgnoreCase(requestType1.getRequestTypeName());
        assertThat(requestType).isNotEmpty();
    }

    @Test
    @DisplayName("shouldFindAllActiveRequestTypes")
    void findAllActiveRequestTypes() {
        requestTypeRepository.saveAll(Arrays.asList(requestType1, requestType2, requestType3));
        List<RequestType> requestType = requestTypeRepository.findAllActiveRequestTypes();
        assertThat(requestType).hasSize(2);
    }

    @Test
    @DisplayName("shouldDeleteByRequestTypeID")
    void deleteByRequestTypeID() {
        requestTypeRepository.saveAll(Arrays.asList(requestType1, requestType2, requestType3));
        requestTypeRepository.deleteByRequestTypeID(requestType1.getRequestTypeID());
        List<RequestType> requestType = requestTypeRepository.findAllActiveRequestTypes();
        assertThat(requestType).hasSize(1);
    }
}
