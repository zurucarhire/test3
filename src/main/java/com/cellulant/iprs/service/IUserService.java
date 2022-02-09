package com.cellulant.iprs.service;

import com.cellulant.iprs.model.User;
import com.cellulant.iprs.model.UserPaging;
import com.cellulant.iprs.model.UserRole;

import java.util.List;

public interface IUserService {
    List<User> findAll();
    List<UserRole> findAllUserRoles();
    User create(long insertedBy, User user);
    User update(long id, long updatedBy, User user);
    User delete(long id, long updatedBy);
    void changePassword(long userId, String oldPassword, String newPassword, String confirmPassword);
    UserRole editUserRole(long userId, int roleId);
    UserRole deleteUserRole(long userId);
    User editAccount(long userId, String email, String idNumber, String msisdn);
}
