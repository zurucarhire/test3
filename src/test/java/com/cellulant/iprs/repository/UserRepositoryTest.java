package com.cellulant.iprs.repository;

import com.cellulant.iprs.model.Role;
import com.cellulant.iprs.model.User;
import org.junit.jupiter.api.*;
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

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Client repository tests
 */
@DataJpaTest
public class UserRepositoryTest {
    // create Postgres container definition

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    private Role role1, role2, role3;
    private User user1, user2, user3;

    /**
     * Execute code before running each test
     */
    @BeforeEach
    public void setupModel() {
        role1 = Role.builder().roleName("ROLE_ADMIN").roleID(1L)
                .updatedBy(1L).permissions("READ,WRITE,UPDATE,DELETE")
                .description("admin role").roleAlias("ADMIN")
                .createdBy(1L).active(0).build();
        role2 = Role.builder().roleID(2L).roleName("EDITOR").roleAlias("EDITOR")
                .updatedBy(1L).permissions("READ,WRITE,UPDATE").description("editor")
                .createdBy(1L).active(1).build();
        role3 = Role.builder().roleID(3L).roleName("USER").roleAlias("USER")
                .updatedBy(1L).permissions("READ").description("user")
                .createdBy(1L).active(0).build();

        Collection<Role> roles = new ArrayList<>();
        roles.add(role1);

        user1 = User.builder()
                .userID(3L)
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
                .updatedBy(2L)
                .createdBy(2L)
                .canAccessUi("no")
                .build();
    }

    /**
     * Execute code after running each test
     */
    @AfterEach
    public void tearDown() {
        user1 = null;
        user2 = null;
    }

    @Test
    @DisplayName("shouldFindByUserID")
    @Disabled
    void findByUserID() {
        roleRepository.save(role1);
        userRepository.save(user1);
        Optional<User> user = userRepository.findByUserID(user1.getUserID());
        assertThat(user).isNotEmpty();
        assertThat(user.get()).usingRecursiveComparison().ignoringFields("dateCreated","dateModified").isEqualTo(user1);
    }

    @Test
    @DisplayName("shouldFindByUserNameIgnoreCase")
    @Disabled
    void findByUserNameIgnoreCase() {
        roleRepository.save(role1);
        userRepository.save(user1);
        Optional<User> user = userRepository.findByUserNameIgnoreCase(user1.getUserName());
        assertThat(user).isNotEmpty();
        assertThat(user.get()).usingRecursiveComparison().ignoringFields("dateCreated","dateModified").isEqualTo(user1);
    }

    @Test
    @DisplayName("shouldFindByUserNameIgnoreCase")
    @Disabled
    void findByMsisdn() {
        roleRepository.save(role1);
        userRepository.save(user1);
        Optional<User> user = userRepository.findByMsisdn(user1.getMsisdn());
        assertThat(user).isNotEmpty();
    }

    @Test
    @DisplayName("shouldFindByUserNameIgnoreCase")
    @Disabled
    void findByUserName() {
        roleRepository.save(role2);
        userRepository.save(user2);
        Optional<User> user = userRepository.findByUserName(user2.getUserName());
        assertThat(user).isNotEmpty();
        assertThat(user.get()).usingRecursiveComparison().ignoringFields("dateCreated","dateModified").isEqualTo(user2);
    }

    @Test
    @DisplayName("shouldFindAllActive")
    @Disabled
    void findAllActive() {
        roleRepository.saveAll(Arrays.asList(role1, role2, role3));
        List<Role> requestType = roleRepository.findAllActive();
        assertThat(requestType).hasSize(2);
    }
}
