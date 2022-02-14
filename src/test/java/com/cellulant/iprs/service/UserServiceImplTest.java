package com.cellulant.iprs.service;

import com.cellulant.iprs.exception.ResourceExistsException;
import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.exception.UnprocessedResourceException;
import com.cellulant.iprs.model.*;
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

    @MockBean
    private IChangeLogService changeLogService;

    private User user1, user2;
    private UserRole userRole1, userRole2;
    private Role role1, role2;

    private static ChangeLog changeLog;

    @BeforeEach
    public void setupModel() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        role1 = Role.builder().roleID(1L).roleName("ADMIN")
                .dateCreated(timestamp).dateModified(timestamp).updatedBy(1L)
                .createdBy(1L).active(1).description("hello").build();

        role2 = Role.builder().roleID(2L).roleName("USER")
                .dateCreated(timestamp).dateModified(timestamp).updatedBy(2L)
                .createdBy(2L).active(2).description("world").build();

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
                .createdBy(1L).active(1).description("hello").build();

        changeLog = ChangeLog.builder()
                .narration("hello world 2")
                .insertedBy(2L)
                .build();
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    @DisplayName("shouldCreateUser")
    public void shouldCreateUser() {
        when(roleRepository.findByRoleID(anyLong())).thenReturn(Optional.ofNullable(role1));

        when(changeLogService.create(anyLong(), anyString()))
                .thenReturn(changeLog);

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
    @DisplayName("shouldCreateUserThrowResourceExistsExceptionIfEntityExists")
    public void shouldCreateUserThrowResourceExistsExceptionIfEntityExists()  {
        when(roleRepository.findByRoleID(1L)).thenReturn(Optional.ofNullable(null));

        assertThatThrownBy(() -> userService.create(1, user1))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Role Not Found");

        // mock never saves any user, mock never executed
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("shouldCreateUserThrowResourceExistsExceptionIfUsernameExists")
    public void shouldCreateUserThrowResourceExistsExceptionIfUsernameExists()  {
        when(roleRepository.findByRoleID(1L)).thenReturn(Optional.ofNullable(role1));
        when(userRepository.findByUserNameIgnoreCase(anyString())).thenReturn(Optional.ofNullable(user1));

        assertThatThrownBy(() -> userService.create(1, user1))
                .isInstanceOf(ResourceExistsException.class)
                .hasMessageContaining("Username Already Exists");

        // mock never saves any user, mock never executed
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("shouldCreateUserThrowResourceExistsExceptionIfEmailAddressExists")
    public void shouldCreateUserThrowResourceExistsExceptionIfEmailAddressExists()  {
        when(roleRepository.findByRoleID(1L)).thenReturn(Optional.ofNullable(role1));
        when(userRepository.findByEmailAddressIgnoreCase(anyString())).thenReturn(Optional.ofNullable(user1));

        assertThatThrownBy(() -> userService.create(1, user1))
                .isInstanceOf(ResourceExistsException.class)
                .hasMessageContaining("Email Address Already Exists");

        // mock never saves any user, mock never executed
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("shouldCreateUserThrowResourceExistsExceptionIfIDNumberExists")
    public void shouldCreateUserThrowResourceExistsExceptionIfIDNumberExists()  {
        when(roleRepository.findByRoleID(1L)).thenReturn(Optional.ofNullable(role1));
        when(userRepository.findByIdNumber(anyString())).thenReturn(Optional.ofNullable(user1));

        assertThatThrownBy(() -> userService.create(1, user1))
                .isInstanceOf(ResourceExistsException.class)
                .hasMessageContaining("ID Number Already Exists");

        // mock never saves any user, mock never executed
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("shouldCreateUserThrowResourceExistsExceptionIfIDNumberExists")
    public void shouldCreateUserThrowResourceExistsExceptionIfMsisdnExists()  {
        when(roleRepository.findByRoleID(1L)).thenReturn(Optional.ofNullable(role1));
        when(userRepository.findByMsisdn(anyString())).thenReturn(Optional.ofNullable(user1));

        assertThatThrownBy(() -> userService.create(1, user1))
                .isInstanceOf(ResourceExistsException.class)
                .hasMessageContaining("Phone Number Already Exists");

        // mock never saves any user, mock never executed
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("shouldUpdateUser")
    public void shouldUpdateUser() {
        // create mock behaviour
        when(userRepository.findByUserID(anyLong())).thenReturn(Optional.ofNullable(user1));

        when(changeLogService.create(anyLong(), anyString()))
                .thenReturn(changeLog);

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
                .hasMessageContaining("User Not Found");
        // verify
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("shouldUpdateUserThrowResourceNotFoundIfUserNameConflicts")
    public void shouldUpdateUserThrowResourceNotFoundIfUserNameConflicts() {
        // create mock behaviour
        when(userRepository.findByUserID(1L)).thenReturn(Optional.ofNullable(user2));

        when(userRepository.findByUserNameIgnoreCase(user1.getUserName()))
                .thenReturn(Optional.ofNullable(user1));

        // execute service call
        assertThatThrownBy(() -> userService.update(1, 1, user1))
                .isInstanceOf(ResourceExistsException.class)
                .hasMessageContaining("Username Already Exists");

        // mock never saves any user, mock never executed
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("shouldUpdateUserThrowResourceNotFoundIfEmailAddressConflicts")
    public void shouldUpdateUserThrowResourceNotFoundIfEmailAddressConflicts() {
        // create mock behaviour
        when(userRepository.findByUserID(1L)).thenReturn(Optional.ofNullable(user2));

        when(userRepository.findByEmailAddressIgnoreCase(user1.getEmailAddress()))
                .thenReturn(Optional.ofNullable(user1));

        // execute service call
        assertThatThrownBy(() -> userService.update(1, 1, user1))
                .isInstanceOf(ResourceExistsException.class)
                .hasMessageContaining("Email Address Already Exists");

        // mock never saves any user, mock never executed
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("shouldUpdateUserThrowResourceNotFoundIfIDNumberConflicts")
    public void shouldUpdateUserThrowResourceNotFoundIfIDNumberConflicts() {
        // create mock behaviour
        when(userRepository.findByUserID(1L)).thenReturn(Optional.ofNullable(user2));

        when(userRepository.findByIdNumber(user1.getIdNumber()))
                .thenReturn(Optional.ofNullable(user1));

        // execute service call
        assertThatThrownBy(() -> userService.update(1, 1, user1))
                .isInstanceOf(ResourceExistsException.class)
                .hasMessageContaining("ID Number Already Exists");

        // mock never saves any user, mock never executed
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("shouldUpdateUserThrowResourceNotFoundIfIDNumberConflicts")
    public void shouldUpdateUserThrowResourceNotFoundIfMsisdnConflicts() {
        // create mock behaviour
        when(userRepository.findByUserID(1L)).thenReturn(Optional.ofNullable(user2));

        when(userRepository.findByMsisdn(user1.getMsisdn()))
                .thenReturn(Optional.ofNullable(user1));

        // execute service call
        assertThatThrownBy(() -> userService.update(1, 1, user1))
                .isInstanceOf(ResourceExistsException.class)
                .hasMessageContaining("Phone Number Already Exists");

        // mock never saves any user, mock never executed
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("shouldUpdateUserThrowResourceNotFoundIfIDNumberConflicts")
    public void shouldUpdateUserThrowResourceNotFoundIfMsisdnConflicts2() {
        // create mock behaviour
        when(userRepository.findByUserID(1L)).thenReturn(Optional.ofNullable(user2));

        when(userRepository.findByMsisdn(user1.getMsisdn()))
                .thenReturn(Optional.ofNullable(user1));

        // execute service call
        assertThatThrownBy(() -> userService.update(1, 1, user1))
                .isInstanceOf(ResourceExistsException.class)
                .hasMessageContaining("Phone Number Already Exists");

        // mock never saves any user, mock never executed
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
    @DisplayName("shouldDeleteUserThrowResourceNotFoundExceptionIfEntityNotFound")
    public void shouldDeleteUserThrowResourceNotFoundExceptionIfEntityNotFound() {
        // create mock behaviour
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(null));
        // execute service call
        assertThatThrownBy(() -> userService.delete(user1.getUserID(), user1.getUpdatedBy()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User Not Found");
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
    @DisplayName("shouldChangePasswordThrowResourceNotFoundExceptionIfEntityNotFound")
    public void shouldChangePasswordThrowResourceNotFoundExceptionIfEntityNotFound(){
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(null));

        assertThatThrownBy(() -> userService.changePassword(user1.getUserID(),
                "joe123","hello1", "hello1"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User Not Found");

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("shouldChangePasswordIncorrectOldPassword")
    public void shouldChangePasswordIncorrectOldPassword(){
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));

        assertThatThrownBy(() -> userService.changePassword(user1.getUserID(),
                "joe1234","hello1", "hello1"))
                .isInstanceOf(UnprocessedResourceException.class)
                .hasMessageContaining("Old password is incorrect");

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("shouldChangePasswordIncorrectNewConfirmPassword")
    public void shouldChangePasswordIncorrectNewConfirmPassword(){
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));

        assertThatThrownBy(() -> userService.changePassword(user1.getUserID(),
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
        userService.changePassword(user1.getUserID(),
                "joe123","hello1", "hello1");

        // verify
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("shouldUpdateAccount")
    public void shouldUpdateAccount() {
        // create mock behaviour
        when(userRepository.findByUserID(anyLong())).thenReturn(Optional.ofNullable(user1));
        // execute service call
        userService.updateAccount(user1.getUserID(), user1.getEmailAddress(), user1.getIdNumber(), user1.getMsisdn());

        // verify
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("shouldUpdateAccount")
    public void shouldResetPassword() {
        // create mock behaviour
        when(userRepository.findByUserID(anyLong())).thenReturn(Optional.ofNullable(user1));
        // execute service call
        userService.resetPassword(user1.getUserID(), anyString());
        // verify
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("shouldResetPasswordThrowResourceNotFoundExceptionIfEntityNotFound")
    public void shouldResetPasswordThrowResourceNotFoundExceptionIfEntityNotFound() {
        // create mock behaviour
        when(userRepository.findByUserID(anyLong())).thenReturn(Optional.ofNullable(null));
        assertThatThrownBy(() -> userService.resetPassword(user1.getUserID(),anyString()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User Not Found");

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("shouldUpdateAccountThrowUserNotFoundError")
    public void shouldUpdateAccountThrowUserNotFoundError() {
        // create mock behaviour
        when(userRepository.findByUserID(1L)).thenReturn(Optional.ofNullable(null));
        // execute service call
        assertThatThrownBy(() -> userService.updateAccount(user1.getUserID(), user1.getEmailAddress(), user1.getIdNumber(), user1.getMsisdn()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User Not Found");
        // verify
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("shouldUpdateUserRole")
    public void shouldUpdateUserRole() {
        // create mock behaviour
        when(userRepository.findByUserID(anyLong())).thenReturn(Optional.ofNullable(user1));
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
        when(userRepository.findByUserID(1L)).thenReturn(Optional.ofNullable(null));

        assertThatThrownBy(() -> userService.updateUserRole(user1.getUserID(), user1.getRoleID(), user1.getUpdatedBy()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User Not Found");

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
