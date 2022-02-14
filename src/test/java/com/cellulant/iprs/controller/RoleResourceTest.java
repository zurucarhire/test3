package com.cellulant.iprs.controller;

import com.cellulant.iprs.model.Client;
import com.cellulant.iprs.model.Role;
import com.cellulant.iprs.service.IRoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
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

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleResourceTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private IRoleService roleService;

    @Autowired
    private ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    private static Role role1, role2,role3;

    @BeforeAll
    public static void setupModel() {
        role1 = Role.builder().roleID(1L).roleName("ADMIN").updatedBy(1L)
                .permissions("READ, WRITE, UPDATE, DELETE")
                .createdBy(1L).active(1).description("hello").build();
        role2 = Role.builder().roleID(2L).roleName("USER").updatedBy(1L)
                .permissions("READ")
                .createdBy(1L).active(1).description("world").build();
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("shouldForbidCreateRoleIfRoleUser")
    public void shouldForbidCreateRoleIfRoleUser() throws Exception {
        mockMvc.perform(post("/api/iprs/role/create/{createdBy}", role1.getCreatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("USER"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(role1)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowCreateRoleIfRoleCreator")
    public void shouldAllowCreateRoleIfRoleCreator() throws Exception {
        // create test behaviour
        when(roleService.create(anyLong(), any(Role.class))).thenReturn(role1);
        // mock route and validate
        mockMvc.perform(post("/api/iprs/role/create/{createdBy}", role1.getCreatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("CREATOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(role1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roleName").value("ADMIN"))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.containsString("/api/iprs/role/create")));

    }

    @Test
    @DisplayName("shouldAllowCreateRoleIfRoleEditor")
    public void shouldAllowCreateRoleIfRoleEditor() throws Exception {
        // create test behaviour
        when(roleService.create(anyLong(), any(Role.class))).thenReturn(role1);
        // mock route and validate
        mockMvc.perform(post("/api/iprs/role/create/{createdBy}", role1.getCreatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("EDITOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(role1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roleName").value("ADMIN"))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.containsString("/api/iprs/role/create")));

    }

    @Test
    @DisplayName("shouldAllowCreateRoleIfRoleAdmin")
    public void shouldAllowCreateRoleIfRoleAdmin() throws Exception {
        // create test behaviour
        when(roleService.create(anyLong(), any(Role.class))).thenReturn(role1);
        // mock route and validate
        mockMvc.perform(post("/api/iprs/role/create/{createdBy}", role1.getCreatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("ADMIN"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(role1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roleName").value("ADMIN"))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.containsString("/api/iprs/role/create")));

    }

    @Test
    @DisplayName("shouldForbidUpdateRoleIfRoleUser")
    public void shouldForbidUpdateRoleIfRoleUser() throws Exception {
        mockMvc.perform(put("/api/iprs/role/update/{roleId}/{updatedBy}",
                        role1.getRoleID(),role1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(role1)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldForbidUpdateRoleIfRoleCreator")
    public void shouldForbidUpdateRoleIfRoleCreator() throws Exception {
        mockMvc.perform(put("/api/iprs/role/update/{roleId}/{updatedBy}",
                        role1.getRoleID(),role1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("CREATOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(role1)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowUpdateRoleIfRoleEditor")
    public void shouldAllowUpdateRoleIfRoleEditor() throws Exception {
        when(roleService.update(1, 1, role1)).thenReturn(role1);
        mockMvc.perform(put("/api/iprs/role/update/{roleId}/{updatedBy}",
                        role1.getRoleID(),
                        role1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("EDITOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(role1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleID").value(1))
                .andExpect(jsonPath("$.roleName").value("ADMIN"));
    }

    @Test
    @DisplayName("shouldAllowUpdateRoleIfRoleAdmin")
    public void shouldAllowUpdateRoleIfRoleAdmin() throws Exception {
        when(roleService.update(1, 1, role1)).thenReturn(role1);
        mockMvc.perform(put("/api/iprs/role/update/{roleId}/{updatedBy}",
                        role1.getRoleID(),
                        role1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("ADMIN"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(role1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleID").value(1))
                .andExpect(jsonPath("$.roleName").value("ADMIN"));
    }

    @Test
    @DisplayName("shouldForbidDeleteRoleIfRoleUser")
    public void shouldForbidDeleteRoleIfRoleUser() throws Exception {
        mockMvc.perform(delete("/api/iprs/role/delete/{roleId}/{updatedBy}",
                        role1.getRoleID(), role1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("USER"))
                        .with(csrf())).
                andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldForbidDeleteRoleIfRoleCreator")
    public void shouldForbidDeleteRoleIfRoleCreator() throws Exception {
        mockMvc.perform(delete("/api/iprs/role/delete/{roleId}/{updatedBy}",
                        role1.getRoleID(), role1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("CREATOR"))
                        .with(csrf())).
                andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldForbidDeleteRoleIfRoleEditor")
    public void shouldForbidDeleteRoleIfRoleEditor() throws Exception {
        mockMvc.perform(delete("/api/iprs/role/delete/{roleId}/{updatedBy}",
                        role1.getRoleID(), role1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("EDITOR"))
                        .with(csrf())).
                andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowDeleteRoleIfRoleAdmin")
    public void shouldAllowDeleteRoleIfRoleAdmin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/api/iprs/role/delete/{roleId}/{updatedBy}",
                role1.getRoleID(), role1.getUpdatedBy())
                .with(SecurityMockMvcRequestPostProcessors.user("test").roles("ADMIN"))
                .with(csrf())).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "1");
    }

    @Test
    @DisplayName("shouldFindAllRolesIfRoleUser")
    public void shouldFindAllRolesIfRoleUser() throws Exception{
        // create test behaviour
        Mockito.when(roleService.findAll()).thenReturn(Arrays.asList(role1, role2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/role/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].roleName", Matchers.is("ADMIN")))
                .andExpect(jsonPath("$[1].roleName", Matchers.is("USER")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(role1, role2))));
    }

    @Test
    @DisplayName("shouldFindAllRolesIfRoleCreator")
    public void shouldFindAllRolesIfRoleCreator() throws Exception{
        // create test behaviour
        Mockito.when(roleService.findAll()).thenReturn(Arrays.asList(role1, role2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/role/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("CREATOR")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].roleName", Matchers.is("ADMIN")))
                .andExpect(jsonPath("$[1].roleName", Matchers.is("USER")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(role1, role2))));
    }

    @Test
    @DisplayName("shouldFindAllRolesIfRoleEditor")
    public void shouldFindAllRolesIfRoleEditor() throws Exception{
        // create test behaviour
        Mockito.when(roleService.findAll()).thenReturn(Arrays.asList(role1, role2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/role/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].roleName", Matchers.is("ADMIN")))
                .andExpect(jsonPath("$[1].roleName", Matchers.is("USER")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(role1, role2))));
    }

    @Test
    @DisplayName("shouldFindAllRolesIfRoleAdmin")
    public void shouldFindAllRolesIfRoleAdmin() throws Exception{
        // create test behaviour
        Mockito.when(roleService.findAll()).thenReturn(Arrays.asList(role1, role2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/role/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("ADMIN")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].roleName", Matchers.is("ADMIN")))
                .andExpect(jsonPath("$[1].roleName", Matchers.is("USER")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(role1, role2))));
    }

    @Test
    @DisplayName("shouldFindAllActiveRolesIfRoleUser")
    public void shouldFindAllActiveRolesIfRoleUser() throws Exception{
        // create test behaviour
        Mockito.when(roleService.findAllActive()).thenReturn(Arrays.asList(role1, role2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/role/findallactive")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].roleName", Matchers.is("ADMIN")))
                .andExpect(jsonPath("$[1].roleName", Matchers.is("USER")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(role1, role2))));
    }

    @Test
    @DisplayName("shouldFindAllActiveRolesIfRoleCreator")
    public void shouldFindAllActiveRolesIfRoleCreator() throws Exception{
        // create test behaviour
        Mockito.when(roleService.findAllActive()).thenReturn(Arrays.asList(role1, role2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/role/findallactive")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("CREATOR")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].roleName", Matchers.is("ADMIN")))
                .andExpect(jsonPath("$[1].roleName", Matchers.is("USER")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(role1, role2))));
    }

    @Test
    @DisplayName("shouldFindAllActiveRolesIfRoleEditor")
    public void shouldFindAllActiveRolesIfRoleEditor() throws Exception{
        // create test behaviour
        Mockito.when(roleService.findAllActive()).thenReturn(Arrays.asList(role1, role2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/role/findallactive")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].roleName", Matchers.is("ADMIN")))
                .andExpect(jsonPath("$[1].roleName", Matchers.is("USER")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(role1, role2))));
    }

    @Test
    @DisplayName("shouldFindAllActiveRolesIfRoleAdmin")
    public void shouldFindAllActiveRolesIfRoleAdmin() throws Exception{
        // create test behaviour
        Mockito.when(roleService.findAllActive()).thenReturn(Arrays.asList(role1, role2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/role/findallactive")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("ADMIN")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].roleName", Matchers.is("ADMIN")))
                .andExpect(jsonPath("$[1].roleName", Matchers.is("USER")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(role1, role2))));
    }
}
