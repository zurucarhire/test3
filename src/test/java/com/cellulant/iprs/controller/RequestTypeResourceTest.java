package com.cellulant.iprs.controller;

import com.cellulant.iprs.model.RequestType;
import com.cellulant.iprs.service.IRequestTypeService;
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

import java.util.Arrays;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RequestTypeResourceTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private IRequestTypeService requestTypeService;

    @Autowired
    private ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    private static RequestType requestType1, requestType2;

    @BeforeAll
    public static void setupModel() {
        requestType1 = RequestType.builder().requestTypeName("Alias").build();
        requestType2 = RequestType.builder().requestTypeName("Passport").build();
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("shouldFindAllUsersIfUserHasRoleUser")
    public void shouldFindAllUsersIfUserHasRoleUser() throws Exception{
        // create test behaviour
        Mockito.when(requestTypeService.findAll()).thenReturn(Arrays.asList(requestType1, requestType2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/requesttype/findall")
                .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].requestName", Matchers.is("Alias")))
                .andExpect(jsonPath("$[1].requestName", Matchers.is("Passport")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(requestType1, requestType2))));
    }

    @Test
    @DisplayName("shouldFindAllUsersIfUserHasRoleEditor")
    public void shouldFindAllUsersIfUserHasRoleEditor() throws Exception{
        // create test behaviour
        Mockito.when(requestTypeService.findAll()).thenReturn(Arrays.asList(requestType1, requestType2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/requesttype/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].requestName", Matchers.is("Alias")))
                .andExpect(jsonPath("$[1].requestName", Matchers.is("Passport")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(requestType1, requestType2))));
    }
}
