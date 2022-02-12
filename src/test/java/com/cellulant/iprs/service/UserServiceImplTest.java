package com.cellulant.iprs.service;

import com.cellulant.iprs.exception.ResourceFoundException;
import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.exception.UnprocessedResourceException;
import com.cellulant.iprs.model.Role;
import com.cellulant.iprs.model.User;
import com.cellulant.iprs.model.UserRole;
import com.cellulant.iprs.repository.RoleRepository;
import com.cellulant.iprs.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Autowired
    private IUserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    protected MockMvc mockMvc;

    private User user1, user2;
    private UserRole userRole1, userRole2;
    private Role role1, role2;

    @BeforeEach
    public void setupModel() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        role1 = Role.builder().roleID(1L).roleName("ADMIN")
                .dateCreated(timestamp).dateModified(timestamp).updatedBy(1L)
                .insertedBy(1L).active(1).description("hello").build();

        role2 = Role.builder().roleID(2L).roleName("USER")
                .dateCreated(timestamp).dateModified(timestamp).updatedBy(2L)
                .insertedBy(2L).active(2).description("world").build();

        Collection<Role> roles = new ArrayList<>();
        roles.add(role1);
        user1 = User.builder()
                .userID(1L)
                .clientID(1L)
                .roleID(1)
                .userName("joeabala")
                .fullName("joseph abala")
                .emailAddress("joe@gmail.com")
                .password("$2a$10$mshYoSOeC.zPw7q94KzDIePZmTad.qjQLkgXlhwBd8sNUjeHCQcSa")
                .idNumber("27711223")
                .msisdn("0788993322")
                .active(1)
                .roles(roles)
                .dateCreated(timestamp)
                .dateModified(timestamp)
                .updatedBy(1L)
                .createdBy(1L)
                .canAccessUi("yes")
                .build();

        user2 = User.builder()
                .userID(2L)
                .clientID(2L)
                .roleID(2)
                .userName("marymugambi")
                .fullName("mary mugambi")
                .password("$2a$10$EZaTn4oPdn49BwoIKMozoeJYYHr6KVlni4YyQo47ZsYci7pC.wz8y")
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

        role1 = Role.builder().roleName("ADMIN")
                .dateCreated(timestamp).dateModified(timestamp).updatedBy(1L)
                .insertedBy(1L).active(1).description("hello").build();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("shouldCreateUser")
    public void shouldCreateUser() {
        when(roleRepository.findByRoleID(anyLong())).thenReturn(Optional.ofNullable(role1));

        // when
        userService.create(1, user1);

        // then
        // capture user value inserted
        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user1);
    }

    @Test
    @DisplayName("shouldCreateUserThrowUserNotFoundError")
    public void shouldCreateUserThrowUserNotFoundError()  {
        when(roleRepository.findByRoleID(2L)).thenReturn(Optional.ofNullable(role2));

        assertThatThrownBy(() -> userService.create(1, user1))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Role not found " + 1);

        // mock never saves any user, mock never executed
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("shouldUpdateUser")
    public void shouldUpdateUser() {
        // create mock behaviour
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user1));
        // execute service call
        userService.update(user1.getUserID(), user1.getUpdatedBy(), user1);

        // verify
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("shouldUpdateUserThrowUserNotFoundError")
    public void shouldUpdateUserThrowUserNotFoundError() {
        // create mock behaviour
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));
        // execute service call
        assertThatThrownBy(() -> userService.update(user2.getUserID(), user2.getUpdatedBy(), user2))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not found " + user2.getUserID());
        // verify
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("shouldDeleteUser")
    public void shouldDeleteUser() {
        // create mock behaviour
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user1));
        // execute service call
        userService.delete(user1.getUserID(), user1.getUpdatedBy());

        // verify
        verify(userRepository).deleteByUserID(anyLong());
    }

    @Test
    @DisplayName("shouldDeleteUserThrowUserNotFoundError")
    public void shouldDeleteUserThrowUserNotFoundError() {
        // create mock behaviour
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));
        // execute service call
        assertThatThrownBy(() -> userService.delete(user2.getUserID(), user2.getUpdatedBy()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not found " + user2.getUserID());
        // verify
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("shouldFindAllUsers")
    public void shouldFindAllUsers(){
        // create mock behaviour
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Execute service call
        List<User> usersList = userService.findAll();

        // assert
        assertEquals(2, usersList.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("shouldChangePasswordThrowUserNotFoundError")
    public void shouldChangePasswordThrowUserNotFoundError(){
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));

        assertThatThrownBy(() -> userService.changePassword(user2.getUpdatedBy(), user2.getUserID(),
                "joe123","hello1", "hello1"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not found " + user2.getUserID());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("shouldChangePasswordIncorrectOldPassword")
    public void shouldChangePasswordIncorrectOldPassword(){
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));

        assertThatThrownBy(() -> userService.changePassword(user1.getUpdatedBy(), user1.getUserID(),
                "joe1234","hello1", "hello1"))
                .isInstanceOf(UnprocessedResourceException.class)
                .hasMessageContaining("Password mismatch");

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("shouldChangePasswordIncorrectNewConfirmPassword")
    public void shouldChangePasswordIncorrectNewConfirmPassword(){
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));

        assertThatThrownBy(() -> userService.changePassword(user1.getUpdatedBy(), user1.getUserID(),
                "joe123","hello12", "hello22"))
                .isInstanceOf(UnprocessedResourceException.class)
                .hasMessageContaining("Passwords do not match");

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("shouldChangePassword")
    public void shouldChangePassword(){
        // create mock behaviour
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));

        // execute service call
        userService.changePassword(user1.getUpdatedBy(), user1.getUserID(),
                "joe123","hello1", "hello1");

        // verify
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("shouldUpdateAccount")
    public void shouldUpdateAccount() {
        // create mock behaviour
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user1));
        // execute service call
        userService.updateAccount(user1.getUserID(), user1.getEmailAddress(), user1.getIdNumber(), user1.getMsisdn());

        // verify
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("shouldUpdateAccountThrowUserNotFoundError")
    public void shouldUpdateAccountThrowUserNotFoundError() {
        // create mock behaviour
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));
        // execute service call
        assertThatThrownBy(() -> userService.updateAccount(user2.getUserID(), user2.getEmailAddress(), user2.getIdNumber(), user2.getMsisdn()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not found " + user2.getUserID());
        // verify
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("shouldUpdateUserRole")
    public void shouldUpdateUserRole() {
        // create mock behaviour
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user1));
        // execute service call
        userService.updateUserRole(user1.getUserID(), user1.getRoleID(), user1.getUpdatedBy());

        // verify
        verify(userRepository).save(any(User.class));

        when(userService.updateUserRole(1, 1, 1)).thenReturn(userRole1);
        assertEquals(1, userRole1.getUserID());
    }

    @Test
    @DisplayName("shouldUpdateUserRoleThrowUserNotFoundError")
    public void shouldUpdateUserRoleThrowUserNotFoundError() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));

        assertThatThrownBy(() -> userService.updateUserRole(user2.getUserID(), user2.getRoleID(), user2.getUpdatedBy()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not found " + user2.getUserID());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("shouldFindAllUserRoles")
    public void shouldFindAllUserRoles(){
        // create mock behaviour
        when(userRepository.findAllUserRoles()).thenReturn(Arrays.asList(userRole1, userRole2));

        // Execute service call
        List<UserRole> usersList = userService.findAllUserRoles();

        // assert
        assertEquals(2, usersList.size());
        verify(userRepository, times(1)).findAllUserRoles();
    }
}
