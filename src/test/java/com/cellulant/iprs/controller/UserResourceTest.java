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
               .dateCreated(timestamp)
               .dateModified(timestamp)
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
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowCreateUserIfRoleCreator")
    public void shouldAllowCreateUserIfRoleCreator() throws Exception {
        // create test behaviour
        when(userService.create(anyLong(), any(User.class))).thenReturn(user1);
        // mock route and validate
        mockMvc.perform(post("/api/iprs/user/create/{createdBy}", user1.getCreatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("CREATOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value("joeabala"))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.containsString("/api/iprs/user/create")));

    }

    @Test
    @DisplayName("shouldAllowCreateUserIfRoleAdmin")
    public void shouldAllowCreateUserIfRoleEditor() throws Exception {
        when(userService.create(anyLong(), any(User.class))).thenReturn(user1);
        mockMvc.perform(post("/api/iprs/user/create/{createdBy}", user1.getCreatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userID").value(1))
                .andExpect(jsonPath("$.userName").value("joeabala"))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.containsString("/api/iprs/user/create")));

    }

    @Test
    @DisplayName("shouldAllowCreateUserIfRoleAdmin")
    public void shouldAllowCreateUserIfRoleAdmin() throws Exception {
        when(userService.create(anyLong(), any(User.class))).thenReturn(user1);
        mockMvc.perform(post("/api/iprs/user/create/{createdBy}", user1.getCreatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("ADMIN"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userID").value(1))
                .andExpect(jsonPath("$.userName").value("joeabala"))
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.containsString("/api/iprs/user/create")));

    }

    @Test
    @DisplayName("shouldRejectUpdateUserIfRoleUser")
    public void shouldRejectUpdateUserIfRoleUser() throws Exception {
        mockMvc.perform(put("/api/iprs/user/update/{userId}/{updatedBy}",
                        user1.getUserID(),user1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldRejectUpdateUserIfRoleCreator")
    public void shouldRejectUpdateUserIfRoleCreator() throws Exception {
        mockMvc.perform(put("/api/iprs/user/update/{userId}/{updatedBy}",
                        user1.getUserID(), user1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("CREATOR"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowUpdateUserIfRoleEditor")
    public void shouldAllowUpdateUserIfRoleEditor() throws Exception {
        when(userService.update(anyLong(), anyLong(), any(User.class))).thenReturn(user1);
        mockMvc.perform(
                        put("/api/iprs/user/update/{userId}/{updatedBy}",
                                user1.getUserID(),
                                user1.getUpdatedBy())
                                .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR"))
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
        when(userService.update(anyLong(), anyLong(), any(User.class))).thenReturn(user1);
        mockMvc.perform(
                put("/api/iprs/user/update/{userId}/{updatedBy}",
                        user1.getUserID(), user1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("ADMIN"))
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
    @DisplayName("shouldAllowDeleteUserIfRoleEditor")
    public void shouldRejectDeleteUserIfRoleUser() throws Exception {
        mockMvc.perform(delete("/api/iprs/user/delete/{userId}/{updatedBy}",
                        user1.getUserID(), user1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER"))
                        .with(csrf())).
                andExpect(status().isForbidden());
    }


    @Test
    @DisplayName("shouldRejectDeleteUserIfRoleCreator")
    public void shouldRejectDeleteUserIfRoleCreator() throws Exception {
        mockMvc.perform(delete("/api/iprs/user/delete/{userId}/{updatedBy}",
                        user1.getUserID(), user1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("CREATOR"))
                        .with(csrf())).
                andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowDeleteUserIfRoleEditor")
    public void shouldAllowDeleteUserIfRoleEditor() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/api/iprs/user/delete/{userId}/{updatedBy}",
                user1.getUserID(), user1.getUpdatedBy())
                .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR"))
                .with(csrf())).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "1");
    }

    @Test
    @DisplayName("shouldAllowDeleteUserIfRoleAdmin")
    public void shouldAllowDeleteUserIfRoleAdmin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/api/iprs/user/delete/{userId}/{updatedBy}",
                                user1.getUserID(), user1.getUpdatedBy())
                                .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("ADMIN"))
                                .with(csrf())).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "1");
    }

    @Test
    @DisplayName("shouldAllowDeleteUserIfRoleUser")
    public void shouldAllowFindAllUsersIfRoleUser() throws Exception {
        when(userService.findAll()).thenReturn(Arrays.asList(user1, user2));
        mockMvc.perform(get("/api/iprs/user/findall")
                                .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER"))
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userID").value(1))
                .andExpect(jsonPath("$[0].userName").value("joeabala"))
                .andExpect(jsonPath("$[1].userID").value(2))
                .andExpect(jsonPath("$[1].userName").value("marymugambi"));
    }

    @Test
    @DisplayName("shouldAllowDeleteUserIfRoleCreator")
    public void findAll() throws Exception {
        when(userService.findAll()).thenReturn(Arrays.asList(user1, user2));
        mockMvc.perform(get("/api/iprs/user/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("CREATOR"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userID").value(1))
                .andExpect(jsonPath("$[0].userName").value("joeabala"))
                .andExpect(jsonPath("$[1].userID").value(2))
                .andExpect(jsonPath("$[1].userName").value("marymugambi"));
    }

    @Test
    @DisplayName("shouldAllowFindAllUsersIfRoleEditor")
    public void shouldAllowFindAllUsersIfRoleEditor() throws Exception {
        when(userService.findAll()).thenReturn(Arrays.asList(user1, user2));
        mockMvc.perform(get("/api/iprs/user/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userID").value(1))
                .andExpect(jsonPath("$[0].userName").value("joeabala"))
                .andExpect(jsonPath("$[1].userID").value(2))
                .andExpect(jsonPath("$[1].userName").value("marymugambi"));
    }

    @Test
    @DisplayName("shouldAllowDeleteUserIfRoleAdmin")
    public void shouldAllowFindAllUsersIfRoleAdmin() throws Exception {
        when(userService.findAll()).thenReturn(Arrays.asList(user1, user2));
        mockMvc.perform(get("/api/iprs/user/findall")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("ADMIN"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userID").value(1))
                .andExpect(jsonPath("$[0].userName").value("joeabala"))
                .andExpect(jsonPath("$[1].userID").value(2))
                .andExpect(jsonPath("$[1].userName").value("marymugambi"));
    }

    @Test
    @DisplayName("shouldRejectChangePasswordIfRoleUser")
    public void shouldRejectChangePasswordIfRoleUser() throws Exception {
       mockMvc.perform(put("/api/iprs/user/changepassword/{updatedBy}", 1)
                                .param("userId", "1")
                                .param("oldPassword", "123")
                                .param("newPassword", "123")
                                .param("confirmPassword", "123")
                                .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER"))
                                .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldRejectChangePasswordIfRoleCreator")
    public void shouldRejectChangePasswordIfRoleCreator() throws Exception {
        mockMvc.perform(put("/api/iprs/user/changepassword/{updatedBy}", 1)
                        .param("userId", "1")
                        .param("oldPassword", "123")
                        .param("newPassword", "123")
                        .param("confirmPassword", "123")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("CREATOR"))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowChangePasswordIfRoleEditor")
    public void shouldAllowChangePasswordIfRoleEditor() throws Exception {
        mockMvc.perform(put("/api/iprs/user/changepassword/{updatedBy}", 1)
                        .param("userId", "1")
                        .param("oldPassword", "123")
                        .param("newPassword", "123")
                        .param("confirmPassword", "123")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("shouldAllowUpdateUserIfRoleAdmin")
    public void shouldAllowChangePasswordIfRoleAdmin() throws Exception {
        mockMvc.perform(put("/api/iprs/user/changepassword/{updatedBy}", 1)
                        .param("userId", "1")
                        .param("oldPassword", "123")
                        .param("newPassword", "123")
                        .param("confirmPassword", "123")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("shouldReturn404IfNoInputsSuppliedUpdateAccount")
    public void shouldReturn404IfNoInputsSuppliedUpdateAccount() throws Exception {
        mockMvc.perform(put("/api/iprs/user/updateaccount")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("ADMIN")))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("shouldRejectUpdateAccountIfRoleUser")
    public void shouldRejectUpdateAccountIfRoleUser() throws Exception {
        mockMvc.perform(put("/api/iprs/user/updateaccount")
                        .param("userId", user1.getUserID().toString())
                        .param("email", user1.getEmailAddress())
                        .param("idNumber", user1.getIdNumber())
                        .param("msisdn", user1.getMsisdn())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldRejectUpdateAccountIfRoleCreator")
    public void shouldRejectUpdateAccountIfRoleCreator() throws Exception {
        mockMvc.perform(put("/api/iprs/user/updateaccount")
                        .param("userId", user1.getUserID().toString())
                        .param("email", user1.getEmailAddress())
                        .param("idNumber", user1.getIdNumber())
                        .param("msisdn", user1.getMsisdn())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("CREATOR")))
                .andExpect(status().isForbidden());
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
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR"))
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
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("ADMIN"))
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID").value(1))
                .andExpect(jsonPath("$.userName").value("joeabala"));
    }

    @Test
    @DisplayName("shouldReturn404IfNoInputsSuppliedUpdateUserRole")
    public void shouldReturn404IfNoInputsSuppliedUpdateUserRole() throws Exception {
        mockMvc.perform(delete("/api/iprs/user/updateuserrole")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("ADMIN")))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("shouldRejectUpdateUserRoleIfRoleUser")
    public void shouldRejectUpdateUserRoleIfRoleUser() throws Exception {
        mockMvc.perform(put("/api/iprs/user/updateuserrole/{userId}/{roleId}/{updatedBy}",1, 1, 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER"))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldRejectUpdateUserRoleIfRoleCreator")
    public void shouldRejectUpdateUserRoleIfRoleCreator() throws Exception {
        mockMvc.perform(put("/api/iprs/user/updateuserrole/{userId}/{roleId}/{updatedBy}",1, 1, 1)
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("CREATOR"))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowUpdateUserRoleIfRoleEditor")
    public void shouldAllowUpdateUserRoleIfRoleEditor() throws Exception {
        when(userService.updateUserRole(anyLong(), anyLong(), anyLong())).thenReturn(userRole1);
        mockMvc.perform(put("/api/iprs/user/updateuserrole/{userId}/{roleId}/{updatedBy}",1, 1, 1)
                                .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR"))
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
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("ADMIN"))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID").value(1))
                .andExpect(jsonPath("$.userName").value("joeabala"));
    }

    @Test
    @DisplayName("shouldRejectDeleteUserRoleIfInvalidInput")
    public void shouldReturn404IfNoInputsSuppliedDeleteUserRole() throws Exception {
        mockMvc.perform(delete("/api/iprs/user/deleteuserrole")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("ADMIN")))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("shouldAllowDeleteUserRoleIfRoleUser")
    public void shouldRejectDeleteUserRoleIfRoleUser() throws Exception {
        mockMvc.perform(delete("/api/iprs/user/deleteuserrole/{userId}/{updatedBy}",
                userRole1.getUserID(),user1.getUpdatedBy())
                .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER"))
                .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowDeleteUserRoleIfRoleCreator")
    public void shouldRejectDeleteUserRoleIfRoleCreator() throws Exception {
        mockMvc.perform(delete("/api/iprs/user/deleteuserrole/{userId}/{updatedBy}",
                        userRole1.getUserID(),user1.getUpdatedBy())
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("CREATOR"))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowDeleteUserRoleIfRoleEditor")
    public void shouldAllowDeleteUserRoleIfRoleEditor() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/api/iprs/user/deleteuserrole/{userId}/{updatedBy}",
                userRole1.getUserID(),user1.getUpdatedBy())
                .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR"))
                .with(csrf())).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "1");
    }

    @Test
    @DisplayName("shouldAllowDeleteUserRoleIfRoleAdmin")
    public void shouldAllowDeleteUserRoleIfRoleAdmin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/api/iprs/user/deleteuserrole/{userId}/{updatedBy}",
                userRole1.getUserID(),user1.getUpdatedBy())
                .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("ADMIN"))
                .with(csrf())).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "1");
    }

    @Test
    @DisplayName("shouldRejectFindAllUserRolesIfRoleUser")
    public void shouldRejectFindAllUserRolesIfRoleUser() throws Exception {
        mockMvc.perform(get("/api/iprs/user/findalluserroles")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldRejectFindAllUserRolesIfRoleCreator")
    public void shouldRejectFindAllUserRolesIfRoleCreator() throws Exception {
        mockMvc.perform(get("/api/iprs/user/findalluserroles")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("CREATOR"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("shouldAllowFindAllUserRolesIfRoleEditor")
    public void shouldAllowFindAllUserRolesIfRoleEditor() throws Exception {
        when(userService.findAllUserRoles()).thenReturn(Arrays.asList(userRole1, userRole2));
        mockMvc.perform(get("/api/iprs/user/findalluserroles")
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("EDITOR"))
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
                        .with(SecurityMockMvcRequestPostProcessors.user("dd").roles("ADMIN"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roleID").value(1))
                .andExpect(jsonPath("$[0].userName").value("joeabala"))
                .andExpect(jsonPath("$[1].roleID").value(2))
                .andExpect(jsonPath("$[1].userName").value("marymugambi"));
    }

}
