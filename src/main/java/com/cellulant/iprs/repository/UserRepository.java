package com.cellulant.iprs.repository;

import com.cellulant.iprs.model.User;
import com.cellulant.iprs.model.UserRole;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);
    Optional<User> findByUserNameIgnoreCase(String username);
    Optional<User> findByEmailAddress(String emailAddress);
    Optional<User> findByEmailAddressIgnoreCase(String emailAddress);
    Optional<User> findByIdNumber(String IDNumber);
    Optional<User> findByMsisdn(String msisdn);
    Long deleteByUserID(long userId);
    @Query("SELECT new com.cellulant.iprs.model.UserRole(u.userID, u.userName, u.active, r.roleID, r.roleAlias, r.roleName, r.permissions) FROM User u INNER JOIN Role r ON u.roleID = r.roleID where u.userID = :userId")
    UserRole findUserRolesById(long userId);
    @Query("SELECT new com.cellulant.iprs.model.UserRole(u.userID, u.userName, u.active, r.roleID, r.roleAlias, r.roleName, r.permissions) FROM User u INNER JOIN Role r ON u.roleID = r.roleID")
    List<UserRole> findAllUserRoles();
    @Query("SELECT new com.cellulant.iprs.model.UserRole(u.userID, u.userName, u.active, r.roleID, r.roleAlias, r.roleName, r.permissions) FROM User u INNER JOIN Role r ON u.roleID = r.roleID")
    List<UserRole> deleteUserRole();
}
