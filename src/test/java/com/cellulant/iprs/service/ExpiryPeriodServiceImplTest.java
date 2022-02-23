package com.cellulant.iprs.service;

import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.entity.ExpiryPeriod;
import com.cellulant.iprs.repository.ExpiryPeriodRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ExpiryPeriodServiceImplTest {

    @Autowired
    private IExpiryCheckPeriodService expiryCheckService;

    @MockBean
    private ExpiryPeriodRepository expiryPeriodRepository;

    private static ExpiryPeriod expiryPeriod1, expiryPeriod2;

    @BeforeAll
    public static void setupModel() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        expiryPeriod1 = ExpiryPeriod.builder()
                .expiryPeriodID(1L)
                .expiryPeriod(1)
                .active(1)
                .createdBy(1L)
                .updatedBy(1L)
                .dateCreated(timestamp)
                .dateModified(timestamp)
                .build();
        expiryPeriod2 = ExpiryPeriod.builder()
                .expiryPeriodID(2L)
                .expiryPeriod(1)
                .active(1)
                .createdBy(1L)
                .updatedBy(1L)
                .dateCreated(timestamp)
                .dateModified(timestamp)
                .build();
    }

    @Test
    @DisplayName("shouldCreateExpiryPeriod")
    public void shouldCreateExpiryPeriod() {

        expiryCheckService.create(1L, expiryPeriod1);

        ArgumentCaptor<ExpiryPeriod> userArgumentCaptor =
                ArgumentCaptor.forClass(ExpiryPeriod.class);

        verify(expiryPeriodRepository).save(userArgumentCaptor.capture());
        ExpiryPeriod capturedExpiryPeriod = userArgumentCaptor.getValue();
        assertThat(capturedExpiryPeriod).isEqualTo(expiryPeriod1);
    }

    @Test
    @DisplayName("shouldUpdateExpiryPeriod")
    public void shouldUpdateExpiryPeriod() {
        // create mock behaviour
        when(expiryPeriodRepository.findByExpiryPeriodID(anyLong())).thenReturn(Optional.ofNullable(expiryPeriod1));
        // execute service call
        expiryCheckService.update(expiryPeriod1.getExpiryPeriodID(),
                expiryPeriod1.getExpiryPeriod(), expiryPeriod1.getUpdatedBy());
        // verify
        verify(expiryPeriodRepository).save(expiryPeriod1);
    }

    @Test
    @DisplayName("shouldUpdateExpiryPeriodThrowResourceNotFoundIfEntityNotFound")
    public void shouldUpdateExpiryPeriodThrowResourceNotFoundIfEntityNotFound() {
        when(expiryPeriodRepository.findByExpiryPeriodID(1L)).thenReturn(Optional.ofNullable(expiryPeriod1));

        assertThatThrownBy(() -> expiryCheckService.update(2L, 23, 1))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Resource Not Found");

        verify(expiryPeriodRepository, never()).save(any());
    }

    @Test
    @DisplayName("shouldFindAllExpiryPeriod")
    public void shouldFindAllExpiryPeriod()  {
        when(expiryPeriodRepository.findAll()).thenReturn(Arrays.asList(expiryPeriod1));

        // Execute service call
        List<ExpiryPeriod> expiryPeriodList = expiryCheckService.findAll();

        // assert
        assertEquals(1, expiryPeriodList.size());
        verify(expiryPeriodRepository, times(1)).findAll();
    }
}
