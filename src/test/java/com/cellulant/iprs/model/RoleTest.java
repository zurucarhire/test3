package com.cellulant.iprs.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;

@SpringBootTest
class RoleTest {

    private Role role, role1, role2, role3;
    private Date date = new Date();

    @BeforeEach
    public void setup(){
        role = Role.builder()
                .roleID(1L)
                .roleName("ROLE_ADMIN")
                .roleAlias("ADMIN")
                .description("admin role")
                .permissions("READ,WRITE,UPDATE,DELETE")
                .active(0)
                .createdBy(1L)
                .updatedBy(1L)
                .dateCreated(date)
                .dateModified(date)
                .build();

        role1 = Role.builder()
                .roleID(1L)
                .roleName("ROLE_ADMIN")
                .roleAlias("ADMIN")
                .description("admin role")
                .permissions("ALL")
                .active(0)
                .createdBy(1L)
                .updatedBy(1L)
                .dateCreated(date)
                .dateModified(date)
                .build();

        role2 = Role.builder()
                .roleID(2L)
                .roleName("ROLE_EDITOR")
                .roleAlias("EDITOR")
                .description("editor role")
                .permissions("EXEC")
                .active(0)
                .createdBy(1L)
                .updatedBy(1L)
                .dateCreated(date)
                .dateModified(date)
                .build();
    }
    @Test
    void testToString() {
        assertEquals(role.toString(), "Role(roleID=1, roleName=ROLE_ADMIN, roleAlias=ADMIN, description=admin role, permissions=READ,WRITE,UPDATE,DELETE, active=0, createdBy=1, updatedBy=1, dateCreated="+date+", dateModified="+date+")");
    }

    @Test
    void testEquals() {
        Role role1 = new Role();
        Role role2 = new Role();
        assertEquals(role1, role2);
        //assertEquals(role, this.role1);

        assertEquals(role.getRoleID(), 1L);
        assertEquals(role.getRoleName(), "ROLE_ADMIN");
        assertEquals(role.getRoleAlias(), "ADMIN");
        assertEquals(role.getActive(), 0);
        assertEquals(role.getPermissions(), "READ,WRITE,UPDATE,DELETE");
        assertEquals(role.getDescription(), "admin role");
        assertEquals(role.getCreatedBy(), 1L);
        assertEquals(role.getUpdatedBy(), 1L);
        assertEquals(role.getDateCreated(), date);
        assertEquals(role.getDateModified(), date);
    }

    @Test
    void testNotEquals() {
        Role role1 = new Role();
        Role role2 = Role.builder().roleID(1L).build();
        assertNotEquals(role1, role2);
       // assertNotEquals(this.role, this.role3);
    }
}
