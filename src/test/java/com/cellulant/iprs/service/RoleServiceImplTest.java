package com.cellulant.iprs.service;

import com.cellulant.iprs.exception.ResourceExistsException;
import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.model.Role;
import com.cellulant.iprs.repository.RoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @Autowired
    private IRoleService roleService;

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    private static Role role1, role2;

    @BeforeAll
    public static void setupModel() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        role1 = Role.builder().roleName("ADMIN").roleID(1L)
                .dateCreated(timestamp).dateModified(timestamp).updatedBy(1L)
                .insertedBy(1L).active(1).description("").build();
        role2 = Role.builder().roleID(2L).roleName("USER")
                .dateCreated(timestamp).dateModified(timestamp).updatedBy(1L)
                .insertedBy(1L).active(1).description("").build();
    }

    @Test
    @DisplayName("Should create config")
    public void shouldCreateRole() {
        // when
        roleService.create(role1);

        // then
        // capture student value inserted
        ArgumentCaptor<Role> roleArgumentCaptor =
                ArgumentCaptor.forClass(Role.class);

        verify(roleRepository).save(roleArgumentCaptor.capture());
        Role capturedRole = roleArgumentCaptor.getValue();
        assertThat(capturedRole).isEqualTo(role1);
    }

    @Test
    @DisplayName("Should create config")
    public void shouldCreateRoleThrowError()  {
        // given
        given(roleRepository.findByRoleName(anyString()))
                .willReturn(java.util.Optional.of(role1));

        // when, then
        assertThatThrownBy(() -> roleService.create(role1))
                .isInstanceOf(ResourceExistsException.class)
                .hasMessageContaining("Role exists " + role1.getRoleName());

        // mock never saves any role, mock never executed
        verify(roleRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should create config")
    public void shouldUpdateRole() {
        // create mock behaviour
        when(roleRepository.findByRoleID(anyLong())).thenReturn(Optional.ofNullable(role1));
        // execute service call
        Role role = roleService.update(1L, role1);
        System.out.println(">>>>> "+role);
        // verify
        verify(roleRepository).save(role1);
    }

    @Test
    @DisplayName("Should create config")
    public void shouldUpdateRoleThrowError() {
        when(roleRepository.findByRoleID(1L)).thenReturn(Optional.ofNullable(role1));

        assertThatThrownBy(() -> roleService.update(2L, role1))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Role not found " + 2L);

        verify(roleRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete Role")
    public void shouldDeleteRole(){
        when(roleRepository.findByRoleID(anyLong())).thenReturn(Optional.ofNullable(role1));
        roleService.delete(1L);
        verify(roleRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should create config")
    public void shouldDeleteRoleThrowErrorIfRoleNotFound() {
        when(roleRepository.findByRoleID(1L)).thenReturn(Optional.ofNullable(role1));

        assertThatThrownBy(() -> roleService.delete(2L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Role not found " + 2L);

        verify(roleRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Should create config")
    public void shouldFindAllRoles()  {
        // when
        roleService.findAll();

        // then
        // verify that role repository.findAll() is invoked
        verify(roleRepository, times(1)).findAll();
    }
}
