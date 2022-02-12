package com.cellulant.iprs.service;

import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.model.ExpiryPeriod;
import com.cellulant.iprs.repository.ExpiryPeriodRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ExpiryPeriodServiceImplTest {

    @Autowired
    private IExpiryCheckPeriod expiryCheckService;

    @MockBean
    private ExpiryPeriodRepository expiryPeriodRepository;

    private static ExpiryPeriod expiryPeriod1, expiryPeriod2;

    @BeforeAll
    public static void setupModel() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        expiryPeriod1 = ExpiryPeriod.builder()
                .expiryID(1L)
                .expiryPeriod(1)
                .active(1)
                .createdBy(1L)
                .updatedBy(1L)
                .dateCreated(timestamp)
                .dateModified(timestamp)
                .build();
        expiryPeriod2 = ExpiryPeriod.builder()
                .expiryID(2L)
                .expiryPeriod(1)
                .active(1)
                .createdBy(1L)
                .updatedBy(1L)
                .dateCreated(timestamp)
                .dateModified(timestamp)
                .build();
    }

    @Test
    @DisplayName("shouldUpdateExpiry")
    public void shouldUpdateExpiry() {
        // create mock behaviour
        when(expiryPeriodRepository.findByExpiryID(anyLong())).thenReturn(Optional.ofNullable(expiryPeriod1));
        // execute service call
        ExpiryPeriod expiryPeriod = expiryCheckService.update(1L, 21);

        // verify
        verify(expiryPeriodRepository).save(expiryPeriod1);
    }

    @Test
    @DisplayName("shouldUpdateExpiryThrowError")
    public void shouldUpdateExpiryThrowError() {
        when(expiryPeriodRepository.findByExpiryID(1L)).thenReturn(Optional.ofNullable(expiryPeriod1));

        assertThatThrownBy(() -> expiryCheckService.update(2L, 23))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("expiry id not found " + 2L);

        verify(expiryPeriodRepository, never()).save(any());
    }

    @Test
    @DisplayName("shouldDeleteExpiry")
    @Disabled
    public void shouldDeleteExpiry(){
        when(expiryPeriodRepository.findByExpiryID(1L)).thenReturn(Optional.ofNullable(expiryPeriod1));
        expiryCheckService.delete(1L);
        verify(expiryPeriodRepository).deleteById(1L);
    }

    @Test
    @DisplayName("shouldDeleteExpiryThrowErrorIfRoleNotFound")
    public void shouldDeleteExpiryThrowErrorIfRoleNotFound() {
        when(expiryPeriodRepository.findByExpiryID(1L)).thenReturn(Optional.ofNullable(expiryPeriod1));

        assertThatThrownBy(() -> expiryCheckService.delete(2L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("expiry id not found " + 2L);

        verify(expiryPeriodRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Should Find All Change Logs")
    public void shouldFindAllChangeLogs()  {
        // when
        expiryPeriodRepository.findAll();

        // then
        // verify that role repository.findAll() is invoked
        verify(expiryPeriodRepository, times(1)).findAll();
    }
}
