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
@DataJpaTest
public class RequestTypeRepositoryTest {
    // create Postgres container definition

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

    @Test
    @DisplayName("shouldFindByRequestTypeID")
    void findByRequestTypeID() {
        requestTypeRepository.save(requestType1);
        Optional<RequestType> requestType = requestTypeRepository.findByRequestTypeID(requestType1.getRequestTypeID());
        assertThat(requestType).isNotEmpty();
        assertThat(requestType.get()).isEqualTo(requestType1);
    }

    @Test
    @DisplayName("shouldFindByRequestTypeNameIgnoreCase")
    void findByRequestTypeNameIgnoreCase() {
        requestTypeRepository.save(requestType1);
        Optional<RequestType> requestType = requestTypeRepository.findByRequestTypeNameIgnoreCase(requestType1.getRequestTypeName());
        assertThat(requestType).isNotEmpty();
        assertThat(requestType.get()).isEqualTo(requestType1);
    }

    @Test
    @DisplayName("shouldFindAllActiveRequestTypes")
    void findAllActiveRequestTypes() {
        requestTypeRepository.saveAll(Arrays.asList(requestType1, requestType2, requestType3));
        List<RequestType> requestType = requestTypeRepository.findAllActive();
        assertThat(requestType).hasSize(2);
    }

    @Test
    @DisplayName("shouldDeleteByRequestTypeID")
    void deleteByRequestTypeID() {
        requestTypeRepository.saveAll(Arrays.asList(requestType1, requestType2, requestType3));
        requestTypeRepository.deleteByRequestTypeID(requestType1.getRequestTypeID());
        List<RequestType> requestType = requestTypeRepository.findAll();
        assertThat(requestType).hasSize(2);
    }
}
