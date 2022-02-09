package com.cellulant.iprs.repository;

import com.cellulant.iprs.model.User;
import com.cellulant.iprs.model.UserRole;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends DataTablesRepository<User, Long> {
    User findByUserName(String username);
    User findByUserID(long userId);
    @Query("SELECT new com.cellulant.iprs.model.UserRole(u.userID, u.userName, u.active, r.roleID, r.roleName) FROM User u INNER JOIN Role r ON u.roleID = r.roleID")
    List<UserRole> findUserRoles();
    @Query("SELECT new com.cellulant.iprs.model.UserRole(u.userID, u.userName, u.active, r.roleID, r.roleName) FROM User u INNER JOIN Role r ON u.roleID = r.roleID where u.userID = :userId")
    UserRole findUserRolesById(long userId);
}
