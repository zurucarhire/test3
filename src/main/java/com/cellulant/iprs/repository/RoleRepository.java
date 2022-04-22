package com.cellulant.iprs.repository;

import com.cellulant.iprs.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleID(Long id);
    Optional<Role> findByRoleNameIgnoreCase(String rolName);

}
