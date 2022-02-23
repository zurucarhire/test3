package com.cellulant.iprs.repository;

import com.cellulant.iprs.entity.Role;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Client repository tests
 */
@DataJpaTest
public class RoleRepositoryTest {
    // create Postgres container definition

    @Autowired
    RoleRepository roleRepository;

    private Role role1, role2, role3;

    /**
     * Execute code before running each test
     */
    @BeforeEach
    public void setupModel() {
        role1 = Role.builder().roleName("ADMIN").roleID(1L)
                .updatedBy(1L).permissions("READ,WRITE,UPDATE,DELETE")
                .description("admin").roleAlias("ADMIN")
                .createdBy(1L).active(1).build();
        role2 = Role.builder().roleID(2L).roleName("EDITOR").roleAlias("EDITOR")
                .updatedBy(1L).permissions("READ,WRITE,UPDATE").description("editor")
                .createdBy(1L).active(1).build();
        role3 = Role.builder().roleID(3L).roleName("USER").roleAlias("USER")
                .updatedBy(1L).permissions("READ").description("user")
                .createdBy(1L).active(0).build();
    }

    /**
     * Execute code after running each test
     */
    @AfterEach
    public void tearDown() {
    }

    @Test
    @DisplayName("shouldFindByRoleID")
    @Disabled
    void findByRoleID() {
        roleRepository.save(role1);
        Optional<Role> role = roleRepository.findByRoleID(role1.getRoleID());
        assertThat(role).isNotEmpty();
       // assertThat(role.get()).isEqualTo(role1);
        assertThat(role.get()).usingRecursiveComparison().ignoringFields("dateModified").isEqualTo(role1);
    }

    @Test
    @DisplayName("shouldFindByRoleNameIgnoreCase")
    @Disabled
    void findByRoleNameIgnoreCase() {
        roleRepository.save(role1);
        Optional<Role> role = roleRepository.findByRoleNameIgnoreCase(role1.getRoleName());
        assertThat(role).isNotEmpty();
        //assertThat(role.get()).isEqualTo(role1);
        assertThat(role.get()).usingRecursiveComparison().ignoringFields("dateModified").isEqualTo(role1);
    }

    @Test
    @DisplayName("shouldFindAllActive")
    void findAllActive() {
        roleRepository.saveAll(Arrays.asList(role1, role2, role3));
        List<Role> requestType = roleRepository.findAllActive();
        assertThat(requestType).hasSize(2);
    }
}
