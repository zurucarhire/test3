package com.cellulant.iprs.service;

import com.cellulant.iprs.model.LoginLog;
import com.cellulant.iprs.repository.LoginLogRepository;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LoginLogServiceImplTest {

    @Autowired
    private ILoginLogService loginLogService;

    @MockBean
    private LoginLogRepository loginLogRepository;

    private static LoginLog loginLog1, loginLog2;

    @BeforeAll
    public static void setupModel() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        loginLog1 = LoginLog.builder().userID(1L)
                .loginTime(timestamp)
                .logoutTime(timestamp)
                .attemptsBeforeLogin(1)
                .loginIP("0.0.0.0")
                .build();
        loginLog2 =  LoginLog.builder().userID(2L)
                .loginTime(timestamp)
                .logoutTime(timestamp)
                .attemptsBeforeLogin(2)
                .loginIP("0.0.0.0")
                .build();
    }

    @Test
    @DisplayName("shouldCreateLoginLog")
    public void shouldCreateLoginLog() {
        // when
        loginLogService.create(loginLog1);

        // then
        // capture student value inserted
        ArgumentCaptor<LoginLog> roleArgumentCaptor =
                ArgumentCaptor.forClass(LoginLog.class);

        verify(loginLogRepository).save(roleArgumentCaptor.capture());
        LoginLog capturedRole = roleArgumentCaptor.getValue();
        assertThat(capturedRole).isEqualTo(loginLog1);
    }

    @Test
    @DisplayName("shouldFindAllLoginLogs")
    public void shouldFindAllLoginLogs()  {

        when(loginLogRepository.findAll()).thenReturn(Arrays.asList(loginLog1, loginLog2));

        // when
        List<LoginLog> loginLogList = loginLogService.findAll();

        // then
        // assert
        assertEquals(2, loginLogList.size());
        // verify that role repository.findAll() is invoked
        verify(loginLogRepository, times(1)).findAll();
    }
}
