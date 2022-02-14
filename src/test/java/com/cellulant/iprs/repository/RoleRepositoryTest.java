package com.cellulant.iprs.repository;

import com.cellulant.iprs.model.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Client repository tests
 */
@Testcontainers
@DataJpaTest
@ContextConfiguration(initializers = RoleRepositoryTest.Initializer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoleRepositoryTest {
    // create Postgres container definition
    @Container
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("testrepo")
            .withPassword("testrepo")
            .withInitScript("init.sql")
            .withDatabaseName("testrepo");

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

    /**
     * Establish connection to postgres instance
     */
    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues values = TestPropertyValues.of(
                    "spring.datasource.url=" + container.getJdbcUrl(),
                    "spring.datasource.password=" + container.getPassword(),
                    "spring.datasource.username=" + container.getUsername()
            );
            values.applyTo(configurableApplicationContext);
        }
    }

    @Test
    @DisplayName("shouldFindByRoleID")
    void findByRoleID() {
        roleRepository.save(role1);
        Optional<Role> role = roleRepository.findByRoleID(role1.getRoleID());
        assertThat(role).isNotEmpty();
       // assertThat(role.get()).isEqualTo(role1);
        assertThat(role.get()).usingRecursiveComparison().ignoringFields("dateModified").isEqualTo(role1);
    }

    @Test
    @DisplayName("shouldFindByRoleNameIgnoreCase")
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
