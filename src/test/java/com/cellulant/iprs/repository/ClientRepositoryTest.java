package com.cellulant.iprs.repository;

import com.cellulant.iprs.model.Client;
import com.cellulant.iprs.model.RequestType;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Client repository tests
 */
@Testcontainers
@DataJpaTest
@ContextConfiguration(initializers = ClientRepositoryTest.Initializer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientRepositoryTest {
    // create Postgres container definition
    @Container
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("testrepo")
            .withPassword("testrepo")
            .withInitScript("init.sql")
            .withDatabaseName("testrepo");

    @Autowired
    ClientRepository clientRepository;

    private Client client1, client2, client3;

    /**
     * Execute code before running each test
     */
    @BeforeEach
    public void setupModel() {
        client1 = Client.builder()
                .clientName("Cellulant")
                .clientDescription("cellulant")
                .active(1)
                .createdBy(1L)
                .updatedBy(1L)
                .build();
        client2 = Client.builder()
                .clientName("KCB")
                .clientDescription("kcb")
                .active(1)
                .createdBy(1L)
                .updatedBy(1L)
                .build();
        client3 = Client.builder()
                .clientName("Stanchart")
                .clientDescription("stanchart")
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
        client1 = null;
        client2 = null;
        client3 = null;

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
    @DisplayName("shouldFindByClientID")
    void findByClientID() {
        clientRepository.save(client1);
        Optional<Client> requestType = clientRepository.findByClientID(client1.getClientID());
        assertThat(requestType).isNotEmpty();
    }

    @Test
    @DisplayName("shouldFindByClientNameIgnoreCase")
    void findByClientNameIgnoreCase() {
        clientRepository.save(client1);
        Optional<Client> requestType = clientRepository.findByClientNameIgnoreCase(client1.getClientName());
        assertThat(requestType).isNotEmpty();
    }

    @Test
    @DisplayName("shouldFindAllActive")
    void findAllActive() {
        clientRepository.saveAll(Arrays.asList(client1, client2, client3));
        List<Client> requestType = clientRepository.findAllActive();
        assertThat(requestType).hasSize(2);
    }

    @Test
    @DisplayName("shouldDeleteByClientID")
    void deleteByClientID() {
        clientRepository.saveAll(Arrays.asList(client1, client2, client3));
        clientRepository.deleteByClientID(client1.getClientID());
        List<Client> requestType = clientRepository.findAllActive();
        assertThat(requestType).hasSize(1);
    }
}
