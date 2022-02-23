package com.cellulant.iprs.repository;

import com.cellulant.iprs.entity.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Client repository tests
 */
@DataJpaTest
public class ClientRepositoryTest {
    // create Postgres container definition

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

    @Test
    @DisplayName("shouldFindByClientID")
    void findByClientID() {
        clientRepository.save(client1);
        Optional<Client> client = clientRepository.findByClientID(client1.getClientID());
        assertThat(client).isNotEmpty();
        assertThat(client.get()).isEqualTo(client1);
    }

    @Test
    @DisplayName("shouldFindByClientNameIgnoreCase")
    void findByClientNameIgnoreCase() {
        clientRepository.save(client1);
        Optional<Client> client = clientRepository.findByClientNameIgnoreCase(client1.getClientName());
        assertThat(client).isNotEmpty();
        assertThat(client.get()).isEqualTo(client1);
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
