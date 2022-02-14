package com.cellulant.iprs.service;

import com.cellulant.iprs.exception.ResourceExistsException;
import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.exception.UnprocessedResourceException;
import com.cellulant.iprs.model.ChangeLog;
import com.cellulant.iprs.model.RequestType;
import com.cellulant.iprs.repository.RequestTypeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RequestTypeServiceImplTest {

    @Autowired
    private IRequestTypeService requestTypeService;

    @MockBean
    private RequestTypeRepository requestTypeRepository;

    @MockBean
    private IChangeLogService changeLogService;

    protected MockMvc mockMvc;

    private static RequestType requestType1, requestType2;
    private static ChangeLog changeLog;

    @BeforeAll
    public static void setupModel() {
        requestType1 = RequestType.builder()
                .requestTypeID(1L)
                .requestTypeName("Alias")
                .active(1)
                .createdBy(1L)
                .updatedBy(1L)
                .build();
        requestType2 = RequestType.builder()
                .requestTypeID(2L)
                .requestTypeName("Passport")
                .active(1)
                .createdBy(1L)
                .updatedBy(1L)
                .build();

        changeLog = ChangeLog.builder()
                .narration("hello world 2")
                .insertedBy(2L)
                .build();
    }

    @Test
    @DisplayName("shouldCreateRequestType")
    public void shouldCreateRequestType() {

        when(requestTypeRepository.findByRequestTypeNameIgnoreCase(anyString()))
                .thenReturn(Optional.ofNullable(null));

        when(changeLogService.create(anyLong(), anyString()))
                .thenReturn(changeLog);

        // when
        requestTypeService.create(2L, requestType1);

        // then
        // capture user value inserted
        ArgumentCaptor<RequestType> userArgumentCaptor =
                ArgumentCaptor.forClass(RequestType.class);

        verify(requestTypeRepository).save(userArgumentCaptor.capture());
        RequestType capturedRequestType = userArgumentCaptor.getValue();
        assertThat(capturedRequestType).isEqualTo(requestType1);
    }

    @Test
    @DisplayName("shouldCreateRequestTypeThrowResourceFoundIfEntityExists")
    public void shouldCreateRequestTypeThrowResourceFoundIfEntityExists() {

        when(requestTypeRepository.findByRequestTypeNameIgnoreCase(anyString()))
                .thenReturn(Optional.ofNullable(requestType1));

        assertThatThrownBy(() -> requestTypeService.create(1, requestType1))
                .isInstanceOf(ResourceExistsException.class)
                .hasMessageContaining("Request Type exists " + requestType1.getRequestTypeName());

        // mock never saves any user, mock never executed
        verify(requestTypeRepository, never()).save(any(RequestType.class));
    }

    @Test
    @DisplayName("shouldUpdateRequestType")
    public void shouldUpdateRequestType() {
        // create mock behaviour
        when(requestTypeRepository.findByRequestTypeID(anyLong()))
                .thenReturn(Optional.ofNullable(requestType1));

        // execute service call
        requestTypeService.update(requestType1.getRequestTypeID(), requestType1.getUpdatedBy(), requestType1);

        // verify
        verify(requestTypeRepository).save(any(RequestType.class));
    }

    @Test
    @DisplayName("shouldUpdateRequestTypeThrowResourceNotFoundIfEntityNotFound")
    public void shouldUpdateRequestTypeThrowResourceNotFoundIfEntityNotFound() {
        // create mock behaviour
        when(requestTypeRepository.findByRequestTypeID(anyLong())).thenReturn(Optional.ofNullable(null));
        assertThatThrownBy(() -> requestTypeService.update(1, 1, requestType1))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Request Type not found");

        // mock never saves any user, mock never executed
        verify(requestTypeRepository, never()).save(any(RequestType.class));
    }

    @Test
    @DisplayName("shouldUpdateRequestTypeThrowResourceNotFoundIfEntityNameConflicts")
    public void shouldUpdateRequestTypeThrowResourceNotFoundIfEntityNameConflicts() {
        // create mock behaviour
        when(requestTypeRepository.findByRequestTypeID(1L)).thenReturn(Optional.ofNullable(requestType2));

        when(requestTypeRepository.findByRequestTypeNameIgnoreCase(requestType1.getRequestTypeName()))
                .thenReturn(Optional.ofNullable(requestType1));

        // execute service call
        assertThatThrownBy(() -> requestTypeService.update(1, 1, requestType1))
                .isInstanceOf(UnprocessedResourceException.class)
                .hasMessageContaining("Request Type exists " + requestType1.getRequestTypeName());

        // mock never saves any user, mock never executed
        verify(requestTypeRepository, never()).save(any(RequestType.class));
    }

    @Test
    @DisplayName("shouldDeleteRequestType")
    public void shouldDeleteRequestType() {
        // create mock behaviour
        when(requestTypeRepository.findByRequestTypeID(anyLong())).thenReturn(Optional.ofNullable(requestType1));
        // execute service call
        requestTypeService.delete(requestType1.getRequestTypeID(), requestType1.getUpdatedBy());
        // verify
        verify(requestTypeRepository).deleteByRequestTypeID(anyLong());
    }

    @Test
    @DisplayName("shouldDeleteRequestTypeThrowResourceNotFoundExceptionIfEntityNotFound")
    public void shouldDeleteRequestTypeThrowResourceNotFoundExceptionIfEntityNotFound() {
        // create mock behaviour
        when(requestTypeRepository.findByRequestTypeID(1L)).thenReturn(Optional.ofNullable(null));
        // execute service call
        assertThatThrownBy(() -> requestTypeService.delete(requestType1.getRequestTypeID(),
                requestType1.getUpdatedBy()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Request Type not found");
        // verify
        verify(requestTypeRepository, never()).deleteById(anyLong());
    }
    @Test
    @DisplayName("shouldFindAllRequestTypes")
    public void shouldFindAllRequestTypes()  {
        // create mock behaviour
        when(requestTypeRepository.findAll()).thenReturn(Arrays.asList(requestType1, requestType2));

        // Execute service call
        List<RequestType> requestTypes = requestTypeService.findAll();

        // assert
        assertEquals(2, requestTypes.size());
        verify(requestTypeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("shouldFindAllActiveRequestTypes")
    public void shouldFindAllActiveRequestTypes()  {
        // create mock behaviour
        when(requestTypeRepository.findAllActive()).thenReturn(Arrays.asList(requestType1, requestType2));

        // Execute service call
        List<RequestType> requestTypes = requestTypeService.findAllActive();

        // assert
        assertEquals(2, requestTypes.size());
        verify(requestTypeRepository, times(1)).findAllActive();
    }
}
