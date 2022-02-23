package com.cellulant.iprs.repository;

import com.cellulant.iprs.entity.User;
import com.cellulant.iprs.dto.UserRoleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserID(long userId);
    Optional<User> findByUserName(String username);
    Optional<User> findByUserNameIgnoreCase(String username);
    Optional<User> findByEmailAddressIgnoreCase(String emailAddress);
    Optional<User> findByIdNumber(String IDNumber);
    Optional<User> findByMsisdn(String msisdn);
    Long deleteByUserID(long userId);
    @Query("SELECT new com.cellulant.iprs.dto.UserRoleDTO(u.userID, u.userName, u.active, r.roleID, r.roleAlias, r.roleName, r.permissions) FROM User u INNER JOIN Role r ON u.roleID = r.roleID where u.userID = :userId")
    UserRoleDTO findUserRolesById(long userId);
    @Query("SELECT new com.cellulant.iprs.dto.UserRoleDTO(u.userID, u.userName, u.active, r.roleID, r.roleAlias, r.roleName, r.permissions) FROM User u INNER JOIN Role r ON u.roleID = r.roleID")
    List<UserRoleDTO> findAllUserRoles();
    @Query("SELECT new com.cellulant.iprs.dto.UserRoleDTO(u.userID, u.userName, u.active, r.roleID, r.roleAlias, r.roleName, r.permissions) FROM User u INNER JOIN Role r ON u.roleID = r.roleID")
    List<UserRoleDTO> deleteUserRole();
}
