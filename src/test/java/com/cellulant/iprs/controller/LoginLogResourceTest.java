package com.cellulant.iprs.controller;

import com.cellulant.iprs.model.LoginLog;
import com.cellulant.iprs.service.ILoginLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginLogResourceTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ILoginLogService loginLogService;

    @Autowired
    private ObjectMapper objectMapper;

    protected MockMvc mockMvc;

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

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("shouldFindAllLoginLogsIfRoleUser")
    public void shouldFindAllLoginLogsIfRoleUser() throws Exception{
        // create test behaviour
        Mockito.when(loginLogService.findAll()).thenReturn(Arrays.asList(loginLog1, loginLog2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/loginlog/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("USER"))
                        .with(csrf()))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].userID", Matchers.is(1)))
                .andExpect(jsonPath("$[1].userID", Matchers.is(2)))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(loginLog1, loginLog2))));
    }

    @Test
    @DisplayName("shouldFindAllLoginLogsIfRoleCreator")
    public void shouldFindAllLoginLogsIfRoleCreator() throws Exception{
        // create test behaviour
        Mockito.when(loginLogService.findAll()).thenReturn(Arrays.asList(loginLog1, loginLog2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/loginlog/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("CREATOR"))
                        .with(csrf()))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].userID", Matchers.is(1)))
                .andExpect(jsonPath("$[1].userID", Matchers.is(2)))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(loginLog1, loginLog2))));
    }

    @Test
    @DisplayName("shouldFindAllLoginLogsIfRoleEditor")
    public void shouldFindAllLoginLogsIfRoleEditor() throws Exception{
        // create test behaviour
        Mockito.when(loginLogService.findAll()).thenReturn(Arrays.asList(loginLog1, loginLog2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/loginlog/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("EDITOR"))
                        .with(csrf()))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].userID", Matchers.is(1)))
                .andExpect(jsonPath("$[1].userID", Matchers.is(2)))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(loginLog1, loginLog2))));
    }

    @Test
    @DisplayName("shouldFindAllLoginLogsIfRoleAdmin")
    public void shouldFindAllLoginLogsIfRoleAdmin() throws Exception{
        // create test behaviour
        Mockito.when(loginLogService.findAll()).thenReturn(Arrays.asList(loginLog1, loginLog2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/loginlog/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].userID", Matchers.is(1)))
                .andExpect(jsonPath("$[1].userID", Matchers.is(2)))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(loginLog1, loginLog2))));
    }
}
