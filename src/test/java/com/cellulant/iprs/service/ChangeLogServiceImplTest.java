package com.cellulant.iprs.service;

import com.cellulant.iprs.model.ChangeLog;
import com.cellulant.iprs.model.User;
import com.cellulant.iprs.model.UserRole;
import com.cellulant.iprs.repository.ChangeLogRepository;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ChangeLogServiceImplTest {

    @Autowired
    private IChangeLogService changeLogService;

    @MockBean
    private ChangeLogRepository changeLogRepository;

    private static ChangeLog changeLog1, changeLog2, changeLog3;

    @BeforeAll
    public static void setupModel() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        changeLog1 = ChangeLog.builder().changeLogID(1L)
                .narration("hello world")
                .insertedBy(1L)
                .dateCreated(timestamp)
                .build();
        changeLog2 = ChangeLog.builder().changeLogID(2L)
                .narration("hello world")
                .insertedBy(2L)
                .dateCreated(timestamp)
                .build();
        changeLog3 = ChangeLog.builder()
                .narration("hello world 2")
                .insertedBy(2L)
                .build();
    }

    @Test
    @DisplayName("Should Find All Change Logs")
    public void shouldFindAllChangeLogs()  {
        // create mock behaviour
        when(changeLogRepository.findAll()).thenReturn(Arrays.asList(changeLog1, changeLog2));

        // Execute service call
        List<ChangeLog> usersList = changeLogService.findAll();

        // assert
        assertEquals(2, usersList.size());
        verify(changeLogRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("shouldCreateUser")
    public void shouldCreateUser() {
        // when
        changeLogService.create(2L, "hello world 2");

        // then
        // capture user value inserted
        ArgumentCaptor<ChangeLog> userArgumentCaptor =
                ArgumentCaptor.forClass(ChangeLog.class);

        verify(changeLogRepository).save(userArgumentCaptor.capture());
        ChangeLog capturedChangeLog = userArgumentCaptor.getValue();
        assertThat(capturedChangeLog).isEqualTo(changeLog3);
    }
}
