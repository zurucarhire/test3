package com.cellulant.iprs.controller;

import com.cellulant.iprs.model.RequestType;
import com.cellulant.iprs.model.User;
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
        requestType1 = RequestType.builder()
                .requestTypeID(1L)
                .requestTypeName("Alias")
                .active(1)
                .createdBy(1L)
                .updatedBy(1L)
                .build();
        requestType2 = RequestType.builder().requestTypeID(2L).requestTypeName("Passport").active(1).createdBy(2L).build();
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("shouldForbidCreateRequestTypeIfRoleUser")
    public void shouldForbidCreateRequestTypeIfRoleUser() throws Exception {
        mockMvc.perform(post("/api/iprs/requesttype/create/{createdBy}", requestType1.getCreatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(requestType1)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowCreateRequestTypeIfRoleCreator")
    public void shouldAllowCreateRequestTypeIfRoleCreator() throws Exception {
        // create test behaviour
        when(requestTypeService.create(anyLong(), any(RequestType.class))).thenReturn(requestType1);
        // mock route and validate
        mockMvc.perform(post("/api/iprs/requesttype/create/{createdBy}", requestType1.getCreatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("CREATOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(requestType1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.requestTypeName").value("Alias"))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.containsString("/api/iprs/requesttype/create")));

    }

    @Test
    @DisplayName("shouldAllowCreateRequestTypeIfRoleEditor")
    public void shouldAllowCreateRequestTypeIfRoleEditor() throws Exception {
        // create test behaviour
        when(requestTypeService.create(anyLong(), any(RequestType.class))).thenReturn(requestType1);
        // mock route and validate
        mockMvc.perform(post("/api/iprs/requesttype/create/{createdBy}", requestType1.getCreatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(requestType1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.requestTypeName").value("Alias"))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.containsString("/api/iprs/requesttype/create")));

    }

    @Test
    @DisplayName("shouldAllowCreateRequestTypeIfRoleAdmin")
    public void shouldAllowCreateRequestTypeIfRoleAdmin() throws Exception {
        // create test behaviour
        when(requestTypeService.create(anyLong(), any(RequestType.class))).thenReturn(requestType1);
        // mock route and validate
        mockMvc.perform(post("/api/iprs/requesttype/create/{createdBy}", requestType1.getCreatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("ADMIN"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(requestType1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.requestTypeName").value("Alias"))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.containsString("/api/iprs/requesttype/create")));

    }

    @Test
    @DisplayName("shouldForbidUpdateRequestTypeIfRoleUser")
    public void shouldForbidUpdateRequestTypeIfRoleUser() throws Exception {
        mockMvc.perform(put("/api/iprs/requesttype/update/{requestTypeId}/{updatedBy}",
                        requestType1.getRequestTypeID(),requestType1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(requestType1)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldRejectUpdateRequestTypeIfRoleCreator")
    public void shouldRejectUpdateRequestTypeIfRoleCreator() throws Exception {
        mockMvc.perform(put("/api/iprs/requesttype/update/{requestTypeId}/{updatedBy}",
                        requestType1.getRequestTypeID(),requestType1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("CREATOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(requestType1)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowUpdateUserIfRoleEditor")
    public void shouldAllowUpdateUserIfRoleEditor() throws Exception {
        when(requestTypeService.update(1, 1, requestType1)).thenReturn(requestType2);
        mockMvc.perform(put("/api/iprs/requesttype/update/{requestTypeId}/{updatedBy}",
                                requestType1.getRequestTypeID(),
                                requestType1.getUpdatedBy())
                                .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR"))
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(requestType1))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requestTypeID").value(2))
                .andExpect(jsonPath("$.requestTypeName").value("Passport"));
    }

    @Test
    @DisplayName("shouldAllowUpdateUserIfRoleAdmin")
    public void shouldAllowUpdateUserIfRoleAdmin() throws Exception {
        when(requestTypeService.update(1, 1, requestType1)).thenReturn(requestType2);
        mockMvc.perform(put("/api/iprs/requesttype/update/{requestTypeId}/{updatedBy}",
                        requestType1.getRequestTypeID(),
                        requestType1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("ADMIN"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(requestType1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requestTypeID").value(2))
                .andExpect(jsonPath("$.requestTypeName").value("Passport"));
    }

    @Test
    @DisplayName("shouldRejectDeleteRequestTypeIfRoleUser")
    public void shouldRejectDeleteRequestTypeIfRoleUser() throws Exception {
        mockMvc.perform(delete("/api/iprs/requesttype/delete/{requestTypeId}/{updatedBy}",
                        requestType1.getRequestTypeID(), requestType1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("USER"))
                        .with(csrf())).
                andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldRejectDeleteRequestTypeIfRoleCreator")
    public void shouldRejectDeleteRequestTypeIfRoleCreator() throws Exception {
        mockMvc.perform(delete("/api/iprs/requesttype/delete/{requestTypeId}/{updatedBy}",
                        requestType1.getRequestTypeID(), requestType1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("CREATOR"))
                        .with(csrf())).
                andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldRejectDeleteRequestTypeIfRoleEditor")
    public void shouldRejectDeleteRequestTypeIfRoleEditor() throws Exception {
        mockMvc.perform(delete("/api/iprs/requesttype/delete/{requestTypeId}/{updatedBy}",
                        requestType1.getRequestTypeID(), requestType1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("EDITOR"))
                        .with(csrf())).
                andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowDeleteRequestTypeIfRoleAdmin")
    public void shouldAllowDeleteRequestTypeIfRoleAdmin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/api/iprs/requesttype/delete/{requestTypeId}/{updatedBy}",
                requestType1.getRequestTypeID(), requestType1.getUpdatedBy())
                .with(SecurityMockMvcRequestPostProcessors.user("test").roles("ADMIN"))
                .with(csrf())).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "1");
    }

    @Test
    @DisplayName("shouldFindAllRequestTypesIfRoleUser")
    public void shouldFindAllRequestTypesIfRoleUser() throws Exception{
        // create test behaviour
        Mockito.when(requestTypeService.findAll()).thenReturn(Arrays.asList(requestType1, requestType2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/requesttype/findall")
                .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].requestTypeName", Matchers.is("Alias")))
                .andExpect(jsonPath("$[1].requestTypeName", Matchers.is("Passport")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(requestType1, requestType2))));
    }

    @Test
    @DisplayName("shouldFindAllRequestTypesIfRoleCreator")
    public void shouldFindAllRequestTypesIfRoleCreator() throws Exception{
        // create test behaviour
        Mockito.when(requestTypeService.findAll()).thenReturn(Arrays.asList(requestType1, requestType2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/requesttype/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("CREATOR")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].requestTypeName", Matchers.is("Alias")))
                .andExpect(jsonPath("$[1].requestTypeName", Matchers.is("Passport")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(requestType1, requestType2))));
    }

    @Test
    @DisplayName("shouldFindAllRequestTypesIfRoleEditor")
    public void shouldFindAllRequestTypesIfRoleEditor() throws Exception{
        // create test behaviour
        Mockito.when(requestTypeService.findAll()).thenReturn(Arrays.asList(requestType1, requestType2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/requesttype/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].requestTypeName", Matchers.is("Alias")))
                .andExpect(jsonPath("$[1].requestTypeName", Matchers.is("Passport")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(requestType1, requestType2))));
    }

    @Test
    @DisplayName("shouldFindAllRequestTypesIfRoleAdmin")
    public void shouldFindAllRequestTypesIfRoleAdmin() throws Exception{
        // create test behaviour
        Mockito.when(requestTypeService.findAll()).thenReturn(Arrays.asList(requestType1, requestType2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/requesttype/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("ADMIN")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].requestTypeName", Matchers.is("Alias")))
                .andExpect(jsonPath("$[1].requestTypeName", Matchers.is("Passport")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(requestType1, requestType2))));
    }

    @Test
    @DisplayName("shouldFindAllActiveRequestTypesIfRoleUser")
    public void shouldFindAllActiveRequestTypesIfRoleUser() throws Exception{
        // create test behaviour
        Mockito.when(requestTypeService.findAllActive()).thenReturn(Arrays.asList(requestType1, requestType2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/requesttype/findallactive")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].requestTypeName", Matchers.is("Alias")))
                .andExpect(jsonPath("$[1].requestTypeName", Matchers.is("Passport")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(requestType1, requestType2))));
    }

    @Test
    @DisplayName("shouldFindAllActiveRequestTypesIfRoleCreator")
    public void shouldFindAllActiveRequestTypesIfRoleCreator() throws Exception{
        // create test behaviour
        Mockito.when(requestTypeService.findAllActive()).thenReturn(Arrays.asList(requestType1, requestType2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/requesttype/findallactive")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("CREATOR")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].requestTypeName", Matchers.is("Alias")))
                .andExpect(jsonPath("$[1].requestTypeName", Matchers.is("Passport")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(requestType1, requestType2))));
    }

    @Test
    @DisplayName("shouldFindAllActiveRequestTypesIfRoleEditor")
    public void shouldFindAllActiveRequestTypesIfRoleEditor() throws Exception{
        // create test behaviour
        Mockito.when(requestTypeService.findAllActive()).thenReturn(Arrays.asList(requestType1, requestType2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/requesttype/findallactive")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].requestTypeName", Matchers.is("Alias")))
                .andExpect(jsonPath("$[1].requestTypeName", Matchers.is("Passport")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(requestType1, requestType2))));
    }

    @Test
    @DisplayName("shouldFindAllActiveRequestTypesIfRoleAdmin")
    public void shouldFindAllActiveRequestTypesIfRoleAdmin() throws Exception{
        // create test behaviour
        Mockito.when(requestTypeService.findAllActive()).thenReturn(Arrays.asList(requestType1, requestType2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/requesttype/findallactive")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("ADMIN")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].requestTypeName", Matchers.is("Alias")))
                .andExpect(jsonPath("$[1].requestTypeName", Matchers.is("Passport")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(requestType1, requestType2))));
    }
}
