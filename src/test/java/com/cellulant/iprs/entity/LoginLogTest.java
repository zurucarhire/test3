package com.cellulant.iprs.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class LoginLogTest {

    private Date date = new Date();
    private static LoginLog loginLog, loginLog2;

    @BeforeEach
    public void setup(){
        loginLog = LoginLog.builder()
                .userLogID(1L)
                .userID(1L)
                .loginTime(date)
                .logoutTime(date)
                .attemptsBeforeLogin(1)
                .loginIP("0.0.0.0")
                .build();

        loginLog2 = LoginLog.builder()
                .userLogID(2L)
                .userID(2L)
                .loginTime(date)
                .logoutTime(date)
                .attemptsBeforeLogin(2)
                .loginIP("0.0.0.0")
                .build();
    }

    @Test
    void testToString() {
        assertEquals(loginLog.toString(), "LoginLog(userLogID=1, userID=1, loginTime="+date+", logoutTime="+date+", loginIP=0.0.0.0, attemptsBeforeLogin=1, user=null)");
    }

    @Test
    void testEquals() {
        LoginLog loginLog1 = new LoginLog();
        LoginLog loginLog2 = new LoginLog();
        assertEquals(loginLog1, loginLog2);
        //assertEquals(role, this.role1);

        assertEquals(loginLog.getUserLogID(), 1L);
        assertEquals(loginLog.getUserID(), 1L);
        assertEquals(loginLog.getLoginTime(), date);
        assertEquals(loginLog.getLoginIP(), "0.0.0.0");
        assertEquals(loginLog.getAttemptsBeforeLogin(), 1);

    }

    @Test
    void testNotEquals() {
        LoginLog loginLog = new LoginLog();
        LoginLog loginLog1 = LoginLog.builder().userLogID(1L).build();
        assertNotEquals(loginLog, loginLog1);
    }
}
