package com.cellulant.iprs.controller;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        role1 = Role.builder().roleName("ADMIN")
                .dateCreated(timestamp).dateModified(timestamp).updatedBy(1L)
                .insertedBy(1L).active(1).description("hello").build();
        role2 = Role.builder().roleID(2L).roleName("USER")
                .dateCreated(timestamp).dateModified(timestamp).updatedBy(1L)
                .insertedBy(1L).active(1).description("world").build();
        role3 = Role.builder().roleID(2L).roleName("USER")
                .dateCreated(timestamp).dateModified(timestamp).updatedBy(1L)
                .insertedBy(1L).active(1).description("").build();
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void shouldRejectDeletingReviewsWhenUserIsNotAdmin() throws Exception {
        mockMvc
                .perform(get("/api/iprs/role/findall").with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldAllowDeletingReviewsWhenUserIsAdmin() throws Exception {
        mockMvc.perform(get("/api/iprs/role/findall")
                                .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("ADMIN", "USER"))
                                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should Allow Create If User Has Role Creator")
    public void shouldAllowCreateIfUserHasRoleCreator() throws Exception {

        // create test behaviour
        Mockito.when(roleService.create(any(Role.class))).thenReturn(role2);

        // mock route and validate
        mockMvc.perform(post("/api/iprs/role/create")
                .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("CREATOR"))
                .content(objectMapper.writeValueAsString(role2))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                //.andExpect(jsonPath("$.roleID").value(1))
                .andExpect(jsonPath("$.roleName").value("USER"))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.containsString("/api/iprs/role/create")));

        //verify(roleService).create(any(Role.class));

        // verify test is performed once
        //verify(roleService, Mockito.times(1)).create(any(Role.class));
    }

    @Test
    public void whenPostRequestToUsersAndInValidUser_thenCorrectResponse() throws Exception {
        mockMvc.perform(post("/api/iprs/role/create")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("CREATOR"))
                        .content(objectMapper.writeValueAsString(role3))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", Is.is(Arrays.asList("Description is mandatory"))));
    }

    @Test
    @DisplayName("shouldRejectCreateIfUserHasRoleCreator")
    public void shouldRejectCreateIfUserHasRoleCreator() throws Exception {

        // create test behaviour
        Mockito.when(roleService.create(any(Role.class))).thenReturn(role1);

        // mock route and validate
        mockMvc.perform(post("/api/iprs/role/create")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER"))
                        .content(objectMapper.writeValueAsString(role1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldApproveUpdateRoleIfUserHasRoleEditor")
    public void shouldApproveUpdateRoleIfUserHasRoleEditor() throws Exception {

        // record test behaviour
        Mockito.when(roleService.update(2, role1)).thenReturn(role2);

        // mock route and validate
        mockMvc.perform(put("/api/iprs/role/update/{roleId}/{description}", 2,"world")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR"))
                        .content(objectMapper.writeValueAsString(role2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.roleName").exists())
                .andExpect(jsonPath("$.roleName", Matchers.is("USER")))
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.description", Matchers.is("world")));

        // verify test is performed once
        //verify(roleService, Mockito.times(1)).update(2, "world");
    }

    @Test
    @DisplayName("shouldRejectUpdateRoleIfUserHasRoleCreator")
    public void shouldRejectUpdateRoleIfUserHasRoleCreator() throws Exception {

        // record test behaviour
        Mockito.when(roleService.update(Mockito.anyInt(), any(Role.class))).thenReturn(role2);

        // mock route and validate
        mockMvc.perform(put("/api/iprs/role/update/{roleId}/{description}", 2,"world")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER"))
                        .content(objectMapper.writeValueAsString(role2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        // verify test is performed once
       // verify(roleService, Mockito.times(0)).update(2, "world");
    }

    @Test
    @DisplayName("shouldAllowDeleteRoleIfUserHasRoleEditor")
    public void shouldAllowDeleteRoleIfUserHasRoleEditor() throws Exception {
        // mock route and validate
        mockMvc.perform(delete("/api/iprs/role/delete/{roleId}", 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR")))
                .andExpect(status().isOk());

        // verify test is performed once
        //verify(roleService, Mockito.times(1)).delete(1);
    }

    @Test
    @DisplayName("shouldRejectDeleteRoleIfUserHasRoleUser")
    public void shouldRejectDeleteRoleIfUserHasRoleUser() throws Exception {
        // mock route and validate
        mockMvc.perform(delete("/api/iprs/role/delete/{roleId}", 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER")))
                .andExpect(status().isForbidden());

        // verify test is performed once
       // verify(roleService, Mockito.times(0)).delete(1);
    }

    @Test
    @DisplayName("shouldFindAllUsersIfUserHasRoleUser")
    public void shouldFindAllUsersIfUserHasRoleUser() throws Exception{
        // create test behaviour
        Mockito.when(roleService.findAll()).thenReturn(Arrays.asList(role1, role2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/role/findall")
                .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].roleName", Matchers.is("ADMIN")))
                .andExpect(jsonPath("$[1].roleName", Matchers.is("USER")));

        // verify test is performed once
       // verify(roleService, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("shouldFindAllUsersIfUserHasRoleEditor")
    public void shouldFindAllUsersIfUserHasRoleEditor() throws Exception{
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

        // verify test is performed once
       // verify(roleService, Mockito.times(1)).findAll();
    }
}
