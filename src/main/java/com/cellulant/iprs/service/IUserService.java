package com.cellulant.iprs.service;

import com.cellulant.iprs.model.ResetPasswordDTO;
import com.cellulant.iprs.model.User;
import com.cellulant.iprs.model.UserPaging;
import com.cellulant.iprs.model.UserRole;

import java.util.List;

public interface IUserService {
    List<User> findAll();
    List<UserRole> findAllUserRoles();
    User create(long createdBy, User user);
    User update(long id, long updatedBy, User user);
    Long delete(long id, long updatedBy);
    void changePassword(long updatedBy, long userId, String oldPassword, String newPassword, String confirmPassword);
    UserRole updateUserRole(long userId, long roleId, long updatedBy);
    void deleteUserRole(long userId, long updatedBy);
    User updateAccount(long userId, String email, String idNumber, String msisdn);
    ResetPasswordDTO resetPassword(long userId, String updatedBy);
}
