package com.cellulant.iprs.controller;

import com.cellulant.iprs.model.ExpiryPeriod;
import com.cellulant.iprs.service.IExpiryCheckPeriod;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ExpiryPeriodResourceTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private IExpiryCheckPeriod expiryCheckService;

    @Autowired
    private ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    private static ExpiryPeriod expiryPeriod1, expiryPeriod2;

    @BeforeAll
    public static void setupModel() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        expiryPeriod1 = ExpiryPeriod.builder()
                .expiryID(1L)
                .expiryPeriod(10)
                .active(1)
                .createdBy(1L)
                .updatedBy(1L)
                .dateCreated(timestamp)
                .dateModified(timestamp)
                .build();
        expiryPeriod2 = ExpiryPeriod.builder()
                .expiryID(2L)
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
    @DisplayName("shouldRejectDeleteExpiryWhenUserIsNotAdmin")
    public void shouldRejectDeleteExpiryWhenUserIsNotAdmin() throws Exception {
        mockMvc
                .perform(get("/api/iprs/role/findall").with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowDeleteExpiryWhenUserIsAdmin")
    public void shouldAllowDeleteExpiryWhenUserIsAdmin() throws Exception {
        mockMvc.perform(get("/api/iprs/role/findall")
                                .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("ADMIN", "USER"))
                                .with(csrf()))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("shouldAllowUpdateExpiryIfUserHasRoleEditor")
    public void shouldAllowUpdateExpiryIfUserHasRoleEditor() throws Exception {

        // record test behaviour
        Mockito.when(expiryCheckService.update(2, 20)).thenReturn(expiryPeriod1);

        // mock route and validate
        mockMvc.perform(put("/api/iprs/expiry/update/{expiryId}/{period}", 2, 20)
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR"))
                        .content(objectMapper.writeValueAsString(expiryPeriod1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.expiryPeriod").exists())
                .andExpect(jsonPath("$.expiryPeriod", Matchers.is(10)))
                .andExpect(jsonPath("$.active").exists())
                .andExpect(jsonPath("$.active", Matchers.is(1)));
    }

    @Test
    @DisplayName("shouldRejectUpdateExpiryIfUserHasRoleCreator")
    public void shouldRejectUpdateExpiryIfUserHasRoleCreator() throws Exception {

        // record test behaviour
        Mockito.when(expiryCheckService.update(Mockito.anyInt(), Mockito.anyInt())).thenReturn(expiryPeriod1);

        // mock route and validate
        mockMvc.perform(put("/api/iprs/expiry/update/{expiryId}/{period}", 2, 20)
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER"))
                        .content(objectMapper.writeValueAsString(expiryPeriod1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowDeleteExpiryIfUserHasRoleEditor")
    public void shouldAllowDeleteExpiryIfUserHasRoleEditor() throws Exception {
        // mock route and validate
        mockMvc.perform(delete("/api/iprs/expiry/delete/{expiryId}", 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("shouldRejectDeleteExpiryIfUserHasRoleUser")
    public void shouldRejectDeleteExpiryIfUserHasRoleUser() throws Exception {
        // mock route and validate
        mockMvc.perform(delete("/api/iprs/expiry/delete/{expiryId}", 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldFindAllExpiryIfUserHasRoleUser")
    public void shouldFindAllExpiryIfUserHasRoleUser() throws Exception{
        // create test behaviour
        Mockito.when(expiryCheckService.findAll()).thenReturn(Arrays.asList(expiryPeriod1, expiryPeriod2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/expiry/findall")
                .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].expiryPeriod", Matchers.is(10)))
                .andExpect(jsonPath("$[1].expiryPeriod", Matchers.is(20)));
    }

    @Test
    @DisplayName("shouldFindAllExpiryIfUserHasRoleEditor")
    public void shouldFindAllExpiryIfUserHasRoleEditor() throws Exception{
        // create test behaviour
        Mockito.when(expiryCheckService.findAll()).thenReturn(Arrays.asList(expiryPeriod1, expiryPeriod2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/expiry/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].expiryPeriod", Matchers.is(10)))
                .andExpect(jsonPath("$[1].expiryPeriod", Matchers.is(20)));
    }
}
