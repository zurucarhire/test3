package com.cellulant.iprs.controller;

import com.cellulant.iprs.api.UserResource;
import com.cellulant.iprs.model.Role;
import com.cellulant.iprs.model.User;
import com.cellulant.iprs.model.UserRole;
import com.cellulant.iprs.service.IUserService;
import com.cellulant.iprs.serviceimpl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserResourceTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    IUserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private static User user1, user2;
    private static UserRole userRole1, userRole2;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    /**
     * Execute code before running each test
     */
    @BeforeAll
    public static void setupModel() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        user1 = User.builder()
                .userID(1L)
                .clientID(1L)
                .userName("joeabala")
                .fullName("joseph abala")
                .emailAddress("joe@gmail.com")
                .idNumber("27711223")
                .msisdn("0788993322")
                .active(1)
                .updatedBy(1L)
                .createdBy(1L)
                .canAccessUi("yes")
                .build();

        user2 = User.builder()
                .userID(2L)
                .clientID(2L)
                .userName("marymugambi")
                .fullName("mary mugambi")
                .idNumber("33311221")
                .msisdn("0722334455")
                .emailAddress("mary@gmail.com")
                .active(1)
                .dateCreated(timestamp)
                .dateModified(timestamp)
                .updatedBy(2L)
                .createdBy(2L)
                .canAccessUi("no")
                .build();

        userRole1 = UserRole.builder()
                .userID(1)
                .roleID(1)
                .userName("joeabala")
                .roleAlias("ADMIN")
                .active(1)
                .build();

        userRole2 = UserRole.builder()
                .userID(2)
                .roleID(2)
                .userName("marymugambi")
                .roleAlias("EDITOR")
                .active(1)
                .build();
    }

    /**
     * Execute code after running each test
     */
    @AfterEach
    public void tearDown() {

    }

    @Test
    @DisplayName("shouldForbidCreateUserIfRoleUser")
    public void shouldForbidCreateUserIfRoleUser() throws Exception {
        mockMvc.perform(post("/api/iprs/user/create/{createdBy}", user1.getCreatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("USER"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowCreateUserIfRoleCreator")
    public void shouldAllowCreateUserIfRoleCreator() throws Exception {
        // create test behaviour
        when(userService.create(anyLong(), any(User.class))).thenReturn(user1);
        // mock route and validate
        mockMvc.perform(post("/api/iprs/user/create/{createdBy}", user1.getCreatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("CREATOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value("joeabala"))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.containsString("/api/iprs/user/create")));

    }

    @Test
    @DisplayName("shouldAllowCreateUserIfRoleEditor")
    public void shouldAllowCreateUserIfRoleEditor() throws Exception {
        // create test behaviour
        when(userService.create(anyLong(), any(User.class))).thenReturn(user1);
        // mock route and validate
        mockMvc.perform(post("/api/iprs/user/create/{createdBy}", user1.getCreatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("EDITOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value("joeabala"))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.containsString("/api/iprs/user/create")));

    }

    @Test
    @DisplayName("shouldAllowCreateUserIfRoleAdmin")
    public void shouldAllowCreateUserIfRoleAdmin() throws Exception {
        // create test behaviour
        when(userService.create(anyLong(), any(User.class))).thenReturn(user1);
        // mock route and validate
        mockMvc.perform(post("/api/iprs/user/create/{createdBy}", user1.getCreatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("ADMIN"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value("joeabala"))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.containsString("/api/iprs/user/create")));

    }

    @Test
    @DisplayName("shouldForbidUpdateUserIfRoleUser")
    public void shouldForbidUpdateUserIfRoleUser() throws Exception {
        mockMvc.perform(put("/api/iprs/user/update/{userId}/{updatedBy}",
                        user1.getUserID(),user1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldForbidUpdateUserIfRoleCreator")
    public void shouldForbidUpdateUserIfRoleCreator() throws Exception {
        mockMvc.perform(put("/api/iprs/user/update/{userId}/{updatedBy}",
                        user1.getUserID(),user1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("CREATOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowUpdateUserIfRoleEditor")
    public void shouldAllowUpdateUserIfRoleEditor() throws Exception {
        when(userService.update(1, 1, user1)).thenReturn(user1);
        mockMvc.perform(put("/api/iprs/user/update/{userId}/{updatedBy}",
                        user1.getUserID(),
                        user1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("EDITOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID").value(1))
                .andExpect(jsonPath("$.userName").value("joeabala"));
    }

    @Test
    @DisplayName("shouldAllowUpdateUserIfRoleAdmin")
    public void shouldAllowUpdateUserIfRoleAdmin() throws Exception {
        when(userService.update(1, 1, user1)).thenReturn(user1);
        mockMvc.perform(put("/api/iprs/user/update/{userId}/{updatedBy}",
                        user1.getUserID(),
                        user1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("ADMIN"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID").value(1))
                .andExpect(jsonPath("$.userName").value("joeabala"));
    }

    @Test
    @DisplayName("shouldForbidDeleteUserIfRoleUser")
    public void shouldForbidDeleteUserIfRoleUser() throws Exception {
        mockMvc.perform(delete("/api/iprs/user/delete/{userId}/{updatedBy}",
                        user1.getUserID(),user1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("USER"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldForbidDeleteUserIfRoleCreator")
    public void shouldForbidDeleteUserIfRoleCreator() throws Exception {
        mockMvc.perform(delete("/api/iprs/user/delete/{userId}/{updatedBy}",
                        user1.getUserID(),user1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("CREATOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldForbidDeleteUserIfRoleEditor")
    public void shouldForbidDeleteUserIfRoleEditor() throws Exception {
        mockMvc.perform(delete("/api/iprs/user/delete/{userId}/{updatedBy}",
                        user1.getUserID(),user1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("EDITOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowDeleteUserIfRoleAdmin")
    public void shouldAllowDeleteUserIfRoleAdmin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/api/iprs/user/delete/{userId}/{updatedBy}",
                user1.getUserID(), user1.getUpdatedBy())
                .with(SecurityMockMvcRequestPostProcessors.user("test").roles("ADMIN"))
                .with(csrf())).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "1");
    }

    @Test
    @DisplayName("shouldAllowChangePasswordIfRoleUser")
    public void shouldAllowChangePasswordIfRoleUser() throws Exception {
        mockMvc.perform(put("/api/iprs/user/changepassword")
                        .param("userId", "1")
                        .param("oldPassword", "123")
                        .param("newPassword", "123")
                        .param("confirmPassword", "123")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("USER"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("shouldAllowChangePasswordIfRoleCreator")
    public void shouldAllowChangePasswordIfRoleCreator() throws Exception {
        mockMvc.perform(put("/api/iprs/user/changepassword")
                        .param("userId", "1")
                        .param("oldPassword", "123")
                        .param("newPassword", "123")
                        .param("confirmPassword", "123")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("CREATOR"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("shouldAllowChangePasswordIfRoleEditor")
    public void shouldAllowChangePasswordIfRoleEditor() throws Exception {
        mockMvc.perform(put("/api/iprs/user/changepassword")
                        .param("userId", "1")
                        .param("oldPassword", "123")
                        .param("newPassword", "123")
                        .param("confirmPassword", "123")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("EDITOR"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("shouldAllowChangePasswordIfRoleAdmin")
    public void shouldAllowChangePasswordIfRoleAdmin() throws Exception {
        mockMvc.perform(put("/api/iprs/user/changepassword")
                        .param("userId", "1")
                        .param("oldPassword", "123")
                        .param("newPassword", "123")
                        .param("confirmPassword", "123")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("shouldAllowUpdateAccountIfRoleUser")
    public void shouldAllowUpdateAccountIfRoleUser() throws Exception {
        when(userService.updateAccount(anyLong(), anyString(), anyString(), anyString())).thenReturn(user1);
        mockMvc.perform(put("/api/iprs/user/updateaccount")
                        .param("userId", user1.getUserID().toString())
                        .param("email", user1.getEmailAddress())
                        .param("idNumber", user1.getIdNumber())
                        .param("msisdn", user1.getMsisdn())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("USER"))
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID").value(1))
                .andExpect(jsonPath("$.userName").value("joeabala"));
    }

    @Test
    @DisplayName("shouldAllowUpdateAccountIfRoleCreator")
    public void shouldAllowUpdateAccountIfRoleCreator() throws Exception {
        when(userService.updateAccount(anyLong(), anyString(), anyString(), anyString())).thenReturn(user1);
        mockMvc.perform(put("/api/iprs/user/updateaccount")
                        .param("userId", user1.getUserID().toString())
                        .param("email", user1.getEmailAddress())
                        .param("idNumber", user1.getIdNumber())
                        .param("msisdn", user1.getMsisdn())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("CREATOR"))
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID").value(1))
                .andExpect(jsonPath("$.userName").value("joeabala"));
    }

    @Test
    @DisplayName("shouldAllowUpdateAccountIfRoleEditor")
    public void shouldAllowUpdateAccountIfRoleEditor() throws Exception {
        when(userService.updateAccount(anyLong(), anyString(), anyString(), anyString())).thenReturn(user1);
        mockMvc.perform(put("/api/iprs/user/updateaccount")
                        .param("userId", user1.getUserID().toString())
                        .param("email", user1.getEmailAddress())
                        .param("idNumber", user1.getIdNumber())
                        .param("msisdn", user1.getMsisdn())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("EDITOR"))
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID").value(1))
                .andExpect(jsonPath("$.userName").value("joeabala"));
    }

    @Test
    @DisplayName("shouldAllowUpdateAccountIfRoleAdmin")
    public void shouldAllowUpdateAccountIfRoleAdmin() throws Exception {
        when(userService.updateAccount(anyLong(), anyString(), anyString(), anyString())).thenReturn(user1);
        mockMvc.perform(put("/api/iprs/user/updateaccount")
                        .param("userId", user1.getUserID().toString())
                        .param("email", user1.getEmailAddress())
                        .param("idNumber", user1.getIdNumber())
                        .param("msisdn", user1.getMsisdn())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("ADMIN"))
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID").value(1))
                .andExpect(jsonPath("$.userName").value("joeabala"));
    }

    @Test
    @DisplayName("shouldForbidResetPasswordIfRoleUser")
    public void shouldForbidResetPasswordIfRoleUser() throws Exception {
        mockMvc.perform(put("/api/iprs/user/resetpassword/{userId}/{updatedBy}",
                        user1.getUserID(),user1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("USER"))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldForbidResetPasswordIfRoleCreator")
    public void shouldForbidResetPasswordIfRoleCreator() throws Exception {
        mockMvc.perform(put("/api/iprs/user/resetpassword/{userId}/{updatedBy}",
                        user1.getUserID(),user1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("USER"))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowResetPasswordIfRoleEditor")
    public void shouldAllowResetPasswordIfRoleEditor() throws Exception {
        mockMvc.perform(put("/api/iprs/user/resetpassword/{userId}/{updatedBy}",
                        user1.getUserID(),user1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("EDITOR"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("shouldAllowResetPasswordIfRoleAdmin")
    public void shouldAllowResetPasswordIfRoleAdmin() throws Exception {
        mockMvc.perform(put("/api/iprs/user/resetpassword/{userId}/{updatedBy}",
                        user1.getUserID(),user1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("shouldForbidUpdateUserRoleIfRoleUser")
    public void shouldForbidUpdateUserRoleIfRoleUser() throws Exception {
        mockMvc.perform(put("/api/iprs/user/updateuserrole/{userId}/{roleId}/{updatedBy}",1, 1, 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("USER"))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldForbidUpdateUserRoleIfRoleCreator")
    public void shouldForbidUpdateUserRoleIfRoleCreator() throws Exception {
        mockMvc.perform(put("/api/iprs/user/updateuserrole/{userId}/{roleId}/{updatedBy}",1, 1, 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("CREATOR"))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowUpdateUserRoleIfRoleEditor")
    public void shouldAllowUpdateUserRoleIfRoleEditor() throws Exception {
        when(userService.updateUserRole(anyLong(), anyLong(), anyLong())).thenReturn(userRole1);
        mockMvc.perform(put("/api/iprs/user/updateuserrole/{userId}/{roleId}/{updatedBy}",1, 1, 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("EDITOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID").value(1))
                .andExpect(jsonPath("$.userName").value("joeabala"));
    }

    @Test
    @DisplayName("shouldAllowUpdateUserRoleIfRoleAdmin")
    public void shouldAllowUpdateUserRoleIfRoleAdmin() throws Exception {
        when(userService.updateUserRole(anyLong(), anyLong(), anyLong())).thenReturn(userRole1);
        mockMvc.perform(put("/api/iprs/user/updateuserrole/{userId}/{roleId}/{updatedBy}",1, 1, 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("ADMIN"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID").value(1))
                .andExpect(jsonPath("$.userName").value("joeabala"));
    }

    @Test
    @DisplayName("shouldForbidFindAllUserRolesIfRoleUser")
    public void shouldForbidFindAllUserRolesIfRoleUser() throws Exception {
        mockMvc.perform(get("/api/iprs/user/findalluserroles")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowFindAllUserRolesIfRoleCreator")
    public void shouldAllowFindAllUserRolesIfRoleCreator() throws Exception {
        when(userService.findAllUserRoles()).thenReturn(Arrays.asList(userRole1, userRole2));
        mockMvc.perform(get("/api/iprs/user/findalluserroles")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("CREATOR"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roleID").value(1))
                .andExpect(jsonPath("$[0].userName").value("joeabala"))
                .andExpect(jsonPath("$[1].roleID").value(2))
                .andExpect(jsonPath("$[1].userName").value("marymugambi"));
    }

    @Test
    @DisplayName("shouldAllowFindAllUserRolesIfRoleEditor")
    public void shouldAllowFindAllUserRolesIfRoleEditor() throws Exception {
        when(userService.findAllUserRoles()).thenReturn(Arrays.asList(userRole1, userRole2));
        mockMvc.perform(get("/api/iprs/user/findalluserroles")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("EDITOR"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roleID").value(1))
                .andExpect(jsonPath("$[0].userName").value("joeabala"))
                .andExpect(jsonPath("$[1].roleID").value(2))
                .andExpect(jsonPath("$[1].userName").value("marymugambi"));
    }

    @Test
    @DisplayName("shouldAllowFindAllUserRolesIfRoleAdmin")
    public void shouldAllowFindAllUserRolesIfRoleAdmin() throws Exception {
        when(userService.findAllUserRoles()).thenReturn(Arrays.asList(userRole1, userRole2));
        mockMvc.perform(get("/api/iprs/user/findalluserroles")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("ADMIN"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roleID").value(1))
                .andExpect(jsonPath("$[0].userName").value("joeabala"))
                .andExpect(jsonPath("$[1].roleID").value(2))
                .andExpect(jsonPath("$[1].userName").value("marymugambi"));
    }

    @Test
    @DisplayName("shouldFindAllUsersIfRoleUser")
    public void shouldFindAllUsersIfRoleUser() throws Exception{
        // create test behaviour
        Mockito.when(userService.findAll()).thenReturn(Arrays.asList(user1, user2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/user/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("USER")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].userName", Matchers.is("joeabala")))
                .andExpect(jsonPath("$[1].userName", Matchers.is("marymugambi")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(user1, user2))));
    }

    @Test
    @DisplayName("shouldFindAllUsersIfRoleUser")
    public void shouldFindAllUsersIfRoleCreator() throws Exception{
        // create test behaviour
        Mockito.when(userService.findAll()).thenReturn(Arrays.asList(user1, user2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/user/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("CREATOR")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].userName", Matchers.is("joeabala")))
                .andExpect(jsonPath("$[1].userName", Matchers.is("marymugambi")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(user1, user2))));
    }

    @Test
    @DisplayName("shouldFindAllUsersIfRoleUser")
    public void shouldFindAllUsersIfRoleEditor() throws Exception{
        // create test behaviour
        Mockito.when(userService.findAll()).thenReturn(Arrays.asList(user1, user2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/user/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("EDITOR")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].userName", Matchers.is("joeabala")))
                .andExpect(jsonPath("$[1].userName", Matchers.is("marymugambi")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(user1, user2))));
    }

    @Test
    @DisplayName("shouldFindAllUsersIfRoleAdmin")
    public void shouldFindAllUsersIfRoleAdmin() throws Exception{
        // create test behaviour
        Mockito.when(userService.findAll()).thenReturn(Arrays.asList(user1, user2));

        // mock route and validate
        mockMvc.perform(get("/api/iprs/user/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("ADMIN")))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].userName", Matchers.is("joeabala")))
                .andExpect(jsonPath("$[1].userName", Matchers.is("marymugambi")))
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(user1, user2))));
    }
}
