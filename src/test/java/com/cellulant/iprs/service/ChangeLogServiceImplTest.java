package com.cellulant.iprs.service;

import com.cellulant.iprs.model.ChangeLog;
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
import java.util.Date;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ChangeLogServiceImplTest {

    @Autowired
    private IChangeLogService changeLogService;

    @MockBean
    private ChangeLogRepository changeLogRepository;

    private static ChangeLog changeLog1, changeLog2;

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
                .narration("hello world 2")
                .insertedBy(2L)
                .dateCreated(timestamp)
                .build();
    }

    @Test
    @DisplayName("Should Find All Change Logs")
    public void shouldFindAllChangeLogs()  {
        // when
        changeLogRepository.findAll();

        // then
        // verify that role repository.findAll() is invoked
        verify(changeLogRepository, times(1)).findAll();
    }
}
