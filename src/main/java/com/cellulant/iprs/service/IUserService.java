package com.cellulant.iprs.service;

import com.cellulant.iprs.dto.ResetPasswordDTO;
import com.cellulant.iprs.entity.User;
import com.cellulant.iprs.dto.UserRoleDTO;

import java.util.List;

public interface IUserService {
    List<User> findAll();
    List<UserRoleDTO> findAllUserRoles();
    User create(long createdBy, User user);
    User update(long id, long updatedBy, User user);
    Long delete(long id, long updatedBy);
    void changePassword(long userId, String oldPassword, String newPassword, String confirmPassword);
    UserRoleDTO updateUserRole(long userId, long roleId, long updatedBy);
    User updateAccount(long userId, String email, String idNumber, String msisdn);
    ResetPasswordDTO resetPassword(long userId, String updatedBy);
}
