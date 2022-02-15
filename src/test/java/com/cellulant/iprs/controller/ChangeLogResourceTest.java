package com.cellulant.iprs.controller;

import com.cellulant.iprs.model.ChangeLog;
import com.cellulant.iprs.security.CustomAuthorizationFilter;
import com.cellulant.iprs.service.IChangeLogService;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ChangeLogResourceTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private IChangeLogService changeLogService;

    @Autowired
    private ObjectMapper objectMapper;

    protected MockMvc mockMvc;

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

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(new CustomAuthorizationFilter(), "/*")
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("shouldFindAllUsersIfUserHasRoleUser")
    public void shouldFindAllLoginLogsIfUserHasRoleUser() throws Exception{
        // create test behaviour
        Mockito.when(changeLogService.findAll()).thenReturn(Arrays.asList(changeLog1, changeLog2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/changelog/findall")
                .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].changeLogID", Matchers.is(1)))
                .andExpect(jsonPath("$[1].changeLogID", Matchers.is(2)))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(changeLog1, changeLog2))));
    }

    @Test
    @DisplayName("shouldFindAllUsersIfUserHasRoleCreator")
    public void shouldFindAllUsersIfUserHasRoleCreator() throws Exception{
        // create test behaviour
        Mockito.when(changeLogService.findAll()).thenReturn(Arrays.asList(changeLog1, changeLog2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/changelog/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("CREATOR")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].changeLogID", Matchers.is(1)))
                .andExpect(jsonPath("$[1].changeLogID", Matchers.is(2)))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(changeLog1, changeLog2))));
    }

    @Test
    @DisplayName("shouldFindAllUsersIfUserHasRoleEditor")
    public void shouldFindAllLoginLogsIfUserHasRoleEditor() throws Exception{
        // create test behaviour
        Mockito.when(changeLogService.findAll()).thenReturn(Arrays.asList(changeLog1, changeLog2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/changelog/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].changeLogID", Matchers.is(1)))
                .andExpect(jsonPath("$[1].changeLogID", Matchers.is(2)))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(changeLog1, changeLog2))));
    }

    @Test
    @DisplayName("shouldFindAllLoginLogsIfUserHasRoleAdmin")
    public void shouldFindAllLoginLogsIfUserHasRoleAdmin() throws Exception{
        // create test behaviour
        Mockito.when(changeLogService.findAll()).thenReturn(Arrays.asList(changeLog1, changeLog2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/changelog/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("ADMIN")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].changeLogID", Matchers.is(1)))
                .andExpect(jsonPath("$[1].changeLogID", Matchers.is(2)))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(changeLog1, changeLog2))));
    }
}
