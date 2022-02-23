package com.cellulant.iprs.controller;

import com.cellulant.iprs.entity.Client;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        client1 = Client.builder()
                .clientID(1L)
                .clientName("Cellulant")
                .clientDescription("cellulant")
                .active(1)
                .createdBy(1L)
                .updatedBy(1L)
                .build();
        client2 = Client.builder()
                .clientID(2L)
                .clientName("KCB")
                .clientDescription("kcb")
                .active(1)
                .createdBy(1L)
                .updatedBy(1L)
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
    @DisplayName("shouldForbidCreateClientIfRoleUser")
    public void shouldForbidCreateClientIfRoleUser() throws Exception {
        mockMvc.perform(post("/api/iprs/client/create/{createdBy}", client1.getCreatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("USER"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(client1)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowCreateClientIfRoleCreator")
    public void shouldAllowCreateClientIfRoleCreator() throws Exception {
        // create test behaviour
        when(clientService.create(anyLong(), any(Client.class))).thenReturn(client1);
        // mock route and validate
        mockMvc.perform(post("/api/iprs/client/create/{createdBy}", client1.getCreatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("CREATOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(client1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clientName").value("Cellulant"))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.containsString("/api/iprs/client/create")));

    }

    @Test
    @DisplayName("shouldAllowCreateClientIfRoleCreator")
    public void shouldAllowCreateClientIfRoleEditor() throws Exception {
        // create test behaviour
        when(clientService.create(anyLong(), any(Client.class))).thenReturn(client1);
        // mock route and validate
        mockMvc.perform(post("/api/iprs/client/create/{createdBy}", client1.getCreatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(client1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clientName").value("Cellulant"))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.containsString("/api/iprs/client/create")));

    }

    @Test
    @DisplayName("shouldAllowCreateClientIfRoleAdmin")
    public void shouldAllowCreateClientIfRoleAdmin() throws Exception {
        // create test behaviour
        when(clientService.create(anyLong(), any(Client.class))).thenReturn(client1);
        // mock route and validate
        mockMvc.perform(post("/api/iprs/client/create/{createdBy}", client1.getCreatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("ADMIN"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(client1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clientName").value("Cellulant"))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.containsString("/api/iprs/client/create")));

    }

    @Test
    @DisplayName("shouldForbidCreateClientIfRoleUser")
    public void shouldForbidUpdateClientIfRoleUser() throws Exception {
        mockMvc.perform(put("/api/iprs/client/update/{clientId}/{updatedBy}",
                        client1.getClientID(), client1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("USER"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(client1)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldForbidUpdateClientIfRoleCreator")
    public void shouldForbidUpdateClientIfRoleCreator() throws Exception {
        mockMvc.perform(put("/api/iprs/client/update/{clientId}/{updatedBy}",
                        client1.getClientID(), client1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("CREATOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(client1)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowUpdateClientIfRoleEditor")
    public void shouldAllowUpdateClientIfRoleEditor() throws Exception {
        when(clientService.update(1, 1, client1)).thenReturn(client1);
        mockMvc.perform(put("/api/iprs/client/update/{clientId}/{updatedBy}",
                        client1.getClientID(),
                        client1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("EDITOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(client1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientID").value(1))
                .andExpect(jsonPath("$.clientName").value("Cellulant"));
    }

    @Test
    @DisplayName("shouldAllowUpdateClientIfRoleAdmin")
    public void shouldAllowUpdateClientIfRoleAdmin() throws Exception {
        when(clientService.update(1, 1, client1)).thenReturn(client1);
        mockMvc.perform(put("/api/iprs/client/update/{clientId}/{updatedBy}",
                        client1.getClientID(),
                        client1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("ADMIN"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(client1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientID").value(1))
                .andExpect(jsonPath("$.clientName").value("Cellulant"));
    }

    @Test
    @DisplayName("shouldForbidDeleteClientIfRoleUser")
    public void shouldForbidDeleteClientIfRoleUser() throws Exception {
        mockMvc.perform(delete("/api/iprs/client/delete/{clientId}/{updatedBy}",
                        client1.getClientID(), client1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("USER"))
                        .with(csrf())).
                andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldForbidDeleteClientIfRoleCreator")
    public void shouldForbidDeleteClientIfRoleCreator() throws Exception {
        mockMvc.perform(delete("/api/iprs/client/delete/{clientId}/{updatedBy}",
                        client1.getClientID(), client1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("CREATOR"))
                        .with(csrf())).
                andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldForbidDeleteClientIfRoleEditor")
    public void shouldForbidDeleteClientIfRoleEditor() throws Exception {
        mockMvc.perform(delete("/api/iprs/client/delete/{clientId}/{updatedBy}",
                        client1.getClientID(), client1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("EDITOR"))
                        .with(csrf())).
                andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowDeleteRequestTypeIfRoleAdmin")
    public void shouldAllowDeleteClientIfRoleAdmin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/api/iprs/client/delete/{clientId}/{updatedBy}",
                client1.getClientID(), client1.getUpdatedBy())
                .with(SecurityMockMvcRequestPostProcessors.user("test").roles("ADMIN"))
                .with(csrf())).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "1");
    }

    @Test
    @DisplayName("shouldFindAllClientsIfRoleUser")
    public void shouldFindAllClientsIfRoleUser() throws Exception{
        // create test behaviour
        Mockito.when(clientService.findAll()).thenReturn(Arrays.asList(client1, client2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/client/findall")
                .with(SecurityMockMvcRequestPostProcessors.user("test").roles("USER")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].clientName", Matchers.is("Cellulant")))
                .andExpect(jsonPath("$[1].clientName", Matchers.is("KCB")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(client1, client2))));
    }

    @Test
    @DisplayName("shouldFindAllClientsIfRoleCreator")
    public void shouldFindAllClientsIfRoleCreator() throws Exception{
        // create test behaviour
        Mockito.when(clientService.findAll()).thenReturn(Arrays.asList(client1, client2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/client/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("CREATOR")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].clientName", Matchers.is("Cellulant")))
                .andExpect(jsonPath("$[1].clientName", Matchers.is("KCB")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(client1, client2))));
    }

    @Test
    @DisplayName("shouldFindAllClientsIfRoleEditor")
    public void shouldFindAllClientsIfRoleEditor() throws Exception{
        // create test behaviour
        Mockito.when(clientService.findAll()).thenReturn(Arrays.asList(client1, client2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/client/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("EDITOR")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].clientName", Matchers.is("Cellulant")))
                .andExpect(jsonPath("$[1].clientName", Matchers.is("KCB")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(client1, client2))));
    }

    @Test
    @DisplayName("shouldFindAllClientsIfRoleAdmin")
    public void shouldFindAllClientsIfRoleAdmin() throws Exception{
        // create test behaviour
        Mockito.when(clientService.findAll()).thenReturn(Arrays.asList(client1, client2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/client/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("ADMIN")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].clientName", Matchers.is("Cellulant")))
                .andExpect(jsonPath("$[1].clientName", Matchers.is("KCB")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(client1, client2))));
    }

    @Test
    @DisplayName("shouldFindAllActiveClientsIfRoleUser")
    public void shouldFindAllActiveClientsIfRoleUser() throws Exception{
        // create test behaviour
        Mockito.when(clientService.findAllActive()).thenReturn(Arrays.asList(client1, client2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/client/findallactive")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("USER")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].clientName", Matchers.is("Cellulant")))
                .andExpect(jsonPath("$[1].clientName", Matchers.is("KCB")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(client1, client2))));
    }

    @Test
    @DisplayName("shouldFindAllActiveClientsIfRoleUser")
    public void shouldFindAllActiveClientsIfRoleCreator() throws Exception{
        // create test behaviour
        Mockito.when(clientService.findAllActive()).thenReturn(Arrays.asList(client1, client2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/client/findallactive")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("CREATOR")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].clientName", Matchers.is("Cellulant")))
                .andExpect(jsonPath("$[1].clientName", Matchers.is("KCB")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(client1, client2))));
    }

    @Test
    @DisplayName("shouldFindAllActiveClientsIfRoleUser")
    public void shouldFindAllActiveClientsIfRoleEditor() throws Exception{
        // create test behaviour
        Mockito.when(clientService.findAllActive()).thenReturn(Arrays.asList(client1, client2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/client/findallactive")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("EDITOR")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].clientName", Matchers.is("Cellulant")))
                .andExpect(jsonPath("$[1].clientName", Matchers.is("KCB")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(client1, client2))));
    }

    @Test
    @DisplayName("shouldFindAllActiveClientsIfRoleAdmin")
    public void shouldFindAllActiveClientsIfRoleAdmin() throws Exception{
        // create test behaviour
        Mockito.when(clientService.findAllActive()).thenReturn(Arrays.asList(client1, client2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/client/findallactive")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("ADMIN")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].clientName", Matchers.is("Cellulant")))
                .andExpect(jsonPath("$[1].clientName", Matchers.is("KCB")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(client1, client2))));
    }
}
