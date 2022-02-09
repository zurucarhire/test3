package com.cellulant.iprs.repository;

import com.cellulant.iprs.model.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
    }

    @Test
    void findByRoleID() {
    }

    @Test
    void findByRoleName() {
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

        roleRepository.save(role);

        Optional<Role> role1 = roleRepository.findByRoleName("ADMIN");

        assertEquals(role, roleRepository.findByRoleName("ADMIN"));
    }
}
