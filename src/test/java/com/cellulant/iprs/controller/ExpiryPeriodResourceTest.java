package com.cellulant.iprs.controller;

import com.cellulant.iprs.model.ExpiryPeriod;
import com.cellulant.iprs.service.IExpiryCheckPeriodService;
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
public class ExpiryPeriodResourceTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private IExpiryCheckPeriodService expiryCheckPeriodService;

    @Autowired
    private ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    private static ExpiryPeriod expiryPeriod1, expiryPeriod2;

    @BeforeAll
    public static void setupModel() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        expiryPeriod1 = ExpiryPeriod.builder()
                .expiryPeriodID(1L)
                .expiryPeriod(10)
                .active(1)
                .createdBy(1L)
                .updatedBy(1L)
                .dateCreated(timestamp)
                .dateModified(timestamp)
                .build();
        expiryPeriod2 = ExpiryPeriod.builder()
                .expiryPeriodID(2L)
                .expiryPeriod(20)
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
    @DisplayName("shouldForbidCreateExpiryPeriodIfRoleUser")
    public void shouldForbidCreateExpiryPeriodIfRoleUser() throws Exception {
        mockMvc.perform(post("/api/iprs/expiryperiod/create/{createdBy}", expiryPeriod1.getCreatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("USER"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(expiryPeriod1)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowCreateExpiryPeriodIfRoleCreator")
    public void shouldAllowCreateExpiryPeriodIfRoleCreator() throws Exception {
        // create test behaviour
        when(expiryCheckPeriodService.create(anyLong(), any(ExpiryPeriod.class))).thenReturn(expiryPeriod1);
        // mock route and validate
        mockMvc.perform(post("/api/iprs/expiryperiod/create/{createdBy}", expiryPeriod1.getCreatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("CREATOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(expiryPeriod1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.expiryPeriod").value(10))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.containsString("/api/iprs/expiryperiod/create")));

    }

    @Test
    @DisplayName("shouldAllowCreateExpiryPeriodIfRoleEditor")
    public void shouldAllowCreateExpiryPeriodIfRoleEditor() throws Exception {
        // create test behaviour
        when(expiryCheckPeriodService.create(anyLong(), any(ExpiryPeriod.class))).thenReturn(expiryPeriod1);
        // mock route and validate
        mockMvc.perform(post("/api/iprs/expiryperiod/create/{createdBy}", expiryPeriod1.getCreatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("ADMIN"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(expiryPeriod1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.expiryPeriod").value(10))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.containsString("/api/iprs/expiryperiod/create")));

    }

    @Test
    @DisplayName("shouldAllowCreateExpiryPeriodIfRoleAdmin")
    public void shouldAllowCreateExpiryPeriodIfRoleAdmin() throws Exception {
        // create test behaviour
        when(expiryCheckPeriodService.create(anyLong(), any(ExpiryPeriod.class))).thenReturn(expiryPeriod1);
        // mock route and validate
        mockMvc.perform(post("/api/iprs/expiryperiod/create/{createdBy}", expiryPeriod1.getCreatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("ADMIN"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(expiryPeriod1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.expiryPeriod").value(10))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.containsString("/api/iprs/expiryperiod/create")));

    }

    @Test
    @DisplayName("shouldForbidUpdateExpiryPeriodIfRoleUser")
    public void shouldForbidUpdateExpiryPeriodIfRoleUser() throws Exception {
        mockMvc.perform(put("/api/iprs/expiryperiod/update/{requestTypeId}/{updatedBy}",
                        expiryPeriod1.getExpiryPeriod(), expiryPeriod1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(expiryPeriod1)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldForbidUpdateExpiryPeriodIfRoleCreator")
    public void shouldForbidUpdateExpiryPeriodIfRoleCreator() throws Exception {
        mockMvc.perform(put("/api/iprs/expiryperiod/update/{requestTypeId}/{updatedBy}",
                        expiryPeriod1.getExpiryPeriod(), expiryPeriod1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("CREATOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(expiryPeriod1)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowUpdateUserIfRoleEditor")
    public void shouldAllowUpdateExpiryPeriodIfRoleEditor() throws Exception {
        when(expiryCheckPeriodService.update(1, 10, 1)).thenReturn(expiryPeriod1);
        mockMvc.perform(put("/api/iprs/expiryperiod/update/{expiryPeriodId}/{period}/{updatedBy}",
                        expiryPeriod1.getExpiryPeriodID(),
                        expiryPeriod1.getExpiryPeriod(),
                        expiryPeriod1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("EDITOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(expiryPeriod1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expiryPeriodID").value(1))
                .andExpect(jsonPath("$.expiryPeriod").value(10));
    }

    @Test
    @DisplayName("shouldAllowUpdateExpiryPeriodIfRoleAdmin")
    public void shouldAllowUpdateExpiryPeriodIfRoleAdmin() throws Exception {
        when(expiryCheckPeriodService.update(1, 10, 1)).thenReturn(expiryPeriod1);
        mockMvc.perform(put("/api/iprs/expiryperiod/update/{expiryPeriodId}/{period}/{updatedBy}",
                        expiryPeriod1.getExpiryPeriodID(),
                        expiryPeriod1.getExpiryPeriod(),
                        expiryPeriod1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("ADMIN"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(expiryPeriod1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expiryPeriodID").value(1))
                .andExpect(jsonPath("$.expiryPeriod").value(10));
    }

    @Test
    @DisplayName("shouldFindAllExpiryPeriodIfRoleUser")
    public void shouldFindAllExpiryPeriodIfRoleUser() throws Exception{
        // create test behaviour
        Mockito.when(expiryCheckPeriodService.findAll()).thenReturn(Arrays.asList(expiryPeriod1, expiryPeriod2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/expiryperiod/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("USER")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].expiryPeriod", Matchers.is(10)))
                .andExpect(jsonPath("$[1].expiryPeriod", Matchers.is(20)))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(expiryPeriod1, expiryPeriod2))));
    }

    @Test
    @DisplayName("shouldFindAllExpiryPeriodIfRoleCreator")
    public void shouldFindAllExpiryPeriodIfRoleCreator() throws Exception{
        // create test behaviour
        Mockito.when(expiryCheckPeriodService.findAll()).thenReturn(Arrays.asList(expiryPeriod1, expiryPeriod2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/expiryperiod/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("CREATOR")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].expiryPeriod", Matchers.is(10)))
                .andExpect(jsonPath("$[1].expiryPeriod", Matchers.is(20)))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(expiryPeriod1, expiryPeriod2))));
    }

    @Test
    @DisplayName("shouldFindAllExpiryPeriodIfRoleEditor")
    public void shouldFindAllExpiryPeriodIfRoleEditor() throws Exception{
        // create test behaviour
        Mockito.when(expiryCheckPeriodService.findAll()).thenReturn(Arrays.asList(expiryPeriod1, expiryPeriod2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/expiryperiod/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("EDITOR")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].expiryPeriod", Matchers.is(10)))
                .andExpect(jsonPath("$[1].expiryPeriod", Matchers.is(20)))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(expiryPeriod1, expiryPeriod2))));
    }

    @Test
    @DisplayName("shouldFindAllRequestTypesIfRoleAdmin")
    public void shouldFindAllExpiryPeriodIfRoleAdmin() throws Exception{
        // create test behaviour
        Mockito.when(expiryCheckPeriodService.findAll()).thenReturn(Arrays.asList(expiryPeriod1, expiryPeriod2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/expiryperiod/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("ADMIN")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].expiryPeriod", Matchers.is(10)))
                .andExpect(jsonPath("$[1].expiryPeriod", Matchers.is(20)))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(expiryPeriod1, expiryPeriod2))));
    }
}
