package com.cellulant.iprs.service;

import com.cellulant.iprs.model.Client;
import com.cellulant.iprs.repository.ClientRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.util.Date;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Autowired
    private IClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    private static Client client1, client2;

    @BeforeAll
    public static void setupModel() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        client1 = Client.builder().clientID(1L)
                .clientName("Cellulant")
                .clientDescription("cellulant")
                .active(1)
                .createdBy(1L)
                .updatedBy(1L)
                .dateCreated(timestamp)
                .dateModified(timestamp)
                .build();
        client2 = Client.builder().clientID(2L)
                .clientName("KCB")
                .clientDescription("kcb")
                .active(1)
                .createdBy(1L)
                .updatedBy(1L)
                .dateCreated(timestamp)
                .dateModified(timestamp)
                .build();
    }

    @Test
    @DisplayName("Should Find All Change Logs")
    public void shouldFindAllChangeLogs()  {
        // when
        clientRepository.findAll();

        // then
        // verify that role repository.findAll() is invoked
        verify(clientRepository, times(1)).findAll();
    }
}
