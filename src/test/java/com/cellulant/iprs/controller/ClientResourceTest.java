package com.cellulant.iprs.controller;

import com.cellulant.iprs.model.Client;
import com.cellulant.iprs.service.IClientService;
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
public class ClientResourceTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private IClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    private static Client client1, client2;

    @BeforeAll
    public static void setupModel() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        client1 = Client.builder().clientID(1L)
                .clientName("Cellulant")
                .clientDescription("cellulant")
                .active(1)
                .createdBy(1L)
                .updatedBy(1L)
                .dateCreated(timestamp)
                .dateModified(timestamp)
                .build();
        client2 = Client.builder().clientID(2L)
                .clientName("KCB")
                .clientDescription("kcb")
                .active(1)
                .createdBy(1L)
                .updatedBy(1L)
                .dateCreated(timestamp)
                .dateModified(timestamp)
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
    @DisplayName("shouldFindAllUsersIfUserHasRoleUser")
    public void shouldFindAllClientsIfUserHasRoleUser() throws Exception{
        // create test behaviour
        Mockito.when(clientService.findAll()).thenReturn(Arrays.asList(client1, client2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/client/findall")
                .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].clientName", Matchers.is("Cellulant")))
                .andExpect(jsonPath("$[1].clientName", Matchers.is("KCB")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(client1, client2))));
    }

    @Test
    @DisplayName("shouldFindAllUsersIfUserHasRoleEditor")
    public void shouldFindAllClientsIfUserHasRoleEditor() throws Exception{
        // create test behaviour
        Mockito.when(clientService.findAll()).thenReturn(Arrays.asList(client1, client2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/client/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].clientName", Matchers.is("Cellulant")))
                .andExpect(jsonPath("$[1].clientName", Matchers.is("KCB")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(client1, client2))));
    }
}
