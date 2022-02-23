package com.cellulant.iprs.service;

import com.cellulant.iprs.exception.ResourceExistsException;
import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.entity.ChangeLog;
import com.cellulant.iprs.entity.Role;
import com.cellulant.iprs.repository.RoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @Autowired
    private IRoleService roleService;

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IChangeLogService changeLogService;

    private static Role role1, role2;
    private static ChangeLog changeLog;

    @BeforeAll
    public static void setupModel() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        role1 = Role.builder().roleName("ADMIN").roleID(1L)
                .dateCreated(timestamp).dateModified(timestamp).updatedBy(1L)
                .createdBy(1L).active(1).description("").build();
        role2 = Role.builder().roleID(2L).roleName("USER")
                .dateCreated(timestamp).dateModified(timestamp).updatedBy(1L)
                .createdBy(1L).active(1).description("").build();

        changeLog = ChangeLog.builder()
                .narration("hello world 2")
                .insertedBy(2L)
                .build();
    }

    @Test
    @DisplayName("shouldCreateRole")
    public void shouldCreateRole() {
        when(roleRepository.findByRoleNameIgnoreCase(role1.getRoleName()))
                .thenReturn(Optional.ofNullable(null));

        when(changeLogService.create(anyLong(), anyString()))
                .thenReturn(changeLog);

        // when
        roleService.create(1L, role1);

        // then
        // capture student value inserted
        ArgumentCaptor<Role> roleArgumentCaptor =
                ArgumentCaptor.forClass(Role.class);

        verify(roleRepository).save(roleArgumentCaptor.capture());
        Role capturedRole = roleArgumentCaptor.getValue();
        assertThat(capturedRole).isEqualTo(role1);
    }

    @Test
    @DisplayName("shouldCreateClientThrowResourceExistsExceptionIfEntityExists")
    public void shouldCreateClientThrowResourceExistsExceptionIfEntityExists()  {
        // given
        given(roleRepository.findByRoleNameIgnoreCase(anyString()))
                .willReturn(java.util.Optional.of(role1));

        // when, then
        assertThatThrownBy(() -> roleService.create(1L, role1))
                .isInstanceOf(ResourceExistsException.class)
                .hasMessageContaining("Resource Already Exists");

        // mock never saves any role, mock never executed
        verify(roleRepository, never()).save(any());
    }

    @Test
    @DisplayName("shouldUpdateRole")
    public void shouldUpdateRole() {
        // create mock behaviour
        when(roleRepository.findByRoleID(anyLong())).thenReturn(Optional.ofNullable(role1));

        when(changeLogService.create(anyLong(), anyString()))
                .thenReturn(changeLog);

        // execute service call
        roleService.update(1L, 1L, role1);

        // verify
        verify(roleRepository).save(role1);
    }

    @Test
    @DisplayName("shouldUpdateRoleThrowResourceNotFoundExceptionIfEntityNotFound")
    public void shouldUpdateRoleThrowResourceNotFoundExceptionIfEntityNotFound() {
        when(roleRepository.findByRoleID(1L)).thenReturn(Optional.ofNullable(null));

        assertThatThrownBy(() -> roleService.update(1L, 1L, role1))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Resource Not Found");

        verify(roleRepository, never()).save(any());
    }

    @Test
    @DisplayName("shouldDeleteRole")
    public void shouldDeleteRole(){
        when(roleRepository.findByRoleID(anyLong())).thenReturn(Optional.ofNullable(role1));
        roleService.delete(1L, 1L);
        verify(roleRepository).deleteById(1L);
    }

    @Test
    @DisplayName("shouldDeleteRoleThrowResourceNotFoundExceptionIfEntityNotFound")
    public void shouldDeleteRoleThrowResourceNotFoundExceptionIfEntityNotFound() {
        when(roleRepository.findByRoleID(1L)).thenReturn(Optional.ofNullable(null));

        assertThatThrownBy(() -> roleService.delete(1L, 1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Resource Not Found");

        verify(roleRepository, never()).delete(any());
    }

    @Test
    @DisplayName("shouldFindAllRoles")
    public void shouldFindAllRoles()  {
        // create mock behaviour
        when(roleRepository.findAll()).thenReturn(Arrays.asList(role1, role2));

        // Execute service call
        List<Role> roleList = roleService.findAll();

        // assert
        Assertions.assertEquals(2, roleList.size());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("shouldFindAllActiveRoles")
    public void shouldFindAllActiveRoles()  {
        // create mock behaviour
        when(roleRepository.findAllActive()).thenReturn(Arrays.asList(role1, role2));

        // Execute service call
        List<Role> roleList = roleService.findAllActive();

        // assert
        Assertions.assertEquals(2, roleList.size());
        verify(roleRepository, times(1)).findAllActive();
    }
}
