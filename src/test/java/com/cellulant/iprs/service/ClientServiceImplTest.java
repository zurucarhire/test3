package com.cellulant.iprs.service;

import com.cellulant.iprs.exception.ResourceExistsException;
import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.model.ChangeLog;
import com.cellulant.iprs.model.Client;
import com.cellulant.iprs.repository.ClientRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Autowired
    private IClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private IChangeLogService changeLogService;

    private static Client client1, client2;
    private static ChangeLog changeLog;

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

        changeLog = ChangeLog.builder()
                .narration("hello world 2")
                .insertedBy(2L)
                .build();
    }

    @Test
    @DisplayName("shouldCreateRequestType")
    public void shouldCreateClient() {

        when(clientRepository.findByClientNameIgnoreCase(anyString()))
                .thenReturn(Optional.ofNullable(null));

        when(changeLogService.create(anyLong(), anyString()))
                .thenReturn(changeLog);

        // when
        clientService.create(1L, client1);

        // then
        // capture user value inserted
        ArgumentCaptor<Client> userArgumentCaptor =
                ArgumentCaptor.forClass(Client.class);

        verify(clientRepository).save(userArgumentCaptor.capture());
        Client capturedClient = userArgumentCaptor.getValue();
        assertThat(capturedClient).isEqualTo(client1);
    }

    @Test
    @DisplayName("shouldCreateClientThrowResourceExistsExceptionIfEntityExists")
    public void shouldCreateClientThrowResourceExistsExceptionIfEntityExists() {

        when(clientRepository.findByClientNameIgnoreCase(anyString()))
                .thenReturn(Optional.ofNullable(client1));

        assertThatThrownBy(() -> clientService.create(1, client1))
                .isInstanceOf(ResourceExistsException.class)
                .hasMessageContaining("Resource Already Exists");

        // mock never saves any user, mock never executed
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    @DisplayName("shouldUpdateClient")
    public void shouldUpdateClient() {
        // create mock behaviour
        when(clientRepository.findByClientID(anyLong()))
                .thenReturn(Optional.ofNullable(client1));

        when(changeLogService.create(anyLong(), anyString()))
                .thenReturn(changeLog);

        // execute service call
        clientService.update(client1.getClientID(), client1.getUpdatedBy(), client1);

        // verify
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    @DisplayName("shouldUpdateClientThrowResourceNotFoundExceptionIfEntityNotFound")
    public void shouldUpdateClientThrowResourceNotFoundExceptionIfEntityNotFound() {
        // create mock behaviour
        when(clientRepository.findByClientID(anyLong())).thenReturn(Optional.ofNullable(null));
        assertThatThrownBy(() -> clientService.update(1, 1, client1))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Resource Not Found");

        // mock never saves any user, mock never executed
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    @DisplayName("shouldUpdateClientThrowResourceExistsExceptionIfEntityNameConflicts")
    public void shouldUpdateClientThrowResourceExistsExceptionIfEntityNameConflicts() {
        // create mock behaviour
        when(clientRepository.findByClientID(1L)).thenReturn(Optional.ofNullable(client2));

        when(clientRepository.findByClientNameIgnoreCase(client1.getClientName()))
                .thenReturn(Optional.ofNullable(client1));

        // execute service call
        assertThatThrownBy(() -> clientService.update(1, 1, client1))
                .isInstanceOf(ResourceExistsException.class)
                .hasMessageContaining("Resource Already Exists");

        // mock never saves any user, mock never executed
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    @DisplayName("shouldDeleteClient")
    public void shouldDeleteClient() {
        // create mock behaviour
        when(clientRepository.findByClientID(anyLong())).thenReturn(Optional.ofNullable(client1));
        // execute service call
        clientService.delete(client1.getClientID(), client1.getUpdatedBy());
        // verify
        verify(clientRepository).deleteByClientID(anyLong());
    }

    @Test
    @DisplayName("shouldDeleteRequestTypeThrowResourceNotFoundExceptionIfEntityNotFound")
    public void shouldDeleteClientThrowResourceNotFoundExceptionIfEntityNotFound() {
        // create mock behaviour
        when(clientRepository.findById(1L)).thenReturn(Optional.ofNullable(null));
        // execute service call
        assertThatThrownBy(() -> clientService.delete(client1.getClientID(),
                client1.getUpdatedBy()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Resource Not Found");
        // verify
        verify(clientRepository, never()).deleteByClientID(anyLong());
    }

    @Test
    @DisplayName("shouldFindAllClients")
    public void shouldFindAllClients()  {
        // create mock behaviour
        when(clientRepository.findAll()).thenReturn(Arrays.asList(client1, client2));

        // Execute service call
        List<Client> requestTypes = clientRepository.findAll();

        // assert
        assertEquals(2, requestTypes.size());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("shouldFindAllActiveClients")
    public void shouldFindAllActiveClients()  {
        // create mock behaviour
        when(clientRepository.findAllActive()).thenReturn(Arrays.asList(client1, client2));

        // Execute service call
        List<Client> requestTypes = clientRepository.findAllActive();

        // assert
        assertEquals(2, requestTypes.size());
        verify(clientRepository, times(1)).findAllActive();
    }
}
