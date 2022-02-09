package com.cellulant.iprs.service;

import com.cellulant.iprs.exception.ResourceFoundException;
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

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
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
        role1 = Role.builder().roleName("ADMIN").roleID(1)
                .dateCreated(timestamp).dateModified(timestamp).updatedBy(1)
                .insertedBy(1).active(1).description("").build();
        role2 = Role.builder().roleID(2).roleName("USER")
                .dateCreated(timestamp).dateModified(timestamp).updatedBy(1)
                .insertedBy(1).active(1).description("").build();
    }

    @Test
    @DisplayName("Should create config")
    public void shouldCreateRole() throws Exception {
        roleService.create(role1);
        verify(roleRepository, times(1)).save(role1);
    }

    @Test
    @DisplayName("Should create config")
    public void shouldThrowResourceFoundExceptionWhenRoleExists() throws Exception {
        Throwable e = null;
        when(roleRepository.findByRoleName("ADMIN"))
                .thenReturn(java.util.Optional.of(role1));

        try {
            roleService.create(role1);
        } catch (Throwable ex) {
            e = ex;
        }
        // assert exception
        assertTrue(e instanceof ResourceFoundException);
        verify(roleRepository, times(0)).save(role1);
    }


    @Test
    @DisplayName("Should create config")
    public void shouldCreateRole2() throws Exception {
        // given
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        Role role = Role.builder()
                .roleID(1)
                .roleName("ADMIN")
                .active(1)
                .dateCreated(timestamp)
                .dateModified(timestamp)
                .description("admin role")
                .insertedBy(1)
                .updatedBy(1)
                .build();

        // when
        roleService.create(role);

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
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        Role role = Role.builder()
                .roleID(1)
                .roleName("ADMIN")
                .active(1)
                .dateCreated(timestamp)
                .dateModified(timestamp)
                .description("admin role")
                .insertedBy(1)
                .updatedBy(1)
                .build();

        given(roleRepository.findByRoleName(anyString()))
                .willReturn(java.util.Optional.of(role));

        // when, then
        assertThatThrownBy(() -> roleService.create(role))
                .isInstanceOf(ResourceFoundException.class)
                .hasMessageContaining("Role exists " + role.getRoleName());

        // mock never saves any role, mock never executed
        verify(roleRepository, never()).save(any());
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

//    @Test
//    @DisplayName("Should update Config")
//    public void shouldUpdateRole() throws  Exception{
//        // create mock behaviour
//        when(roleRepository.findById(1L)).thenReturn(Optional.ofNullable(role1));
//
//        roleService.create(role1);
//        // execute service call
//        roleService.update(1, "hello world");
//        verify(roleRepository).save(role1);
//        JSONAssert.assertEquals("{enabled:\"false\"}", objectMapper.writeValueAsString(role1), JSONCompareMode.LENIENT);
//       // verify(roleRepository).findByName(config1.getName());
//    }
}
