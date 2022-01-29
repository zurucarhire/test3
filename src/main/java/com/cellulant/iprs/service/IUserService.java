package com.cellulant.iprs.service;

import com.cellulant.iprs.model.User;
import com.cellulant.iprs.model.UserPaging;
import com.cellulant.iprs.model.UserRole;

import java.util.List;

public interface IUserService {
    List<User> findAll();
    List<UserRole> findAllUserRoles();
    UserPaging getAllUsers(Integer draw, Integer pageNo, Integer pageSize, String sortBy);
    User create(User user);
    User update(long id, User user);
    User delete(long id);
    void changePassword(long userId, String oldPassword, String newPassword, String confirmPassword);
    User editUserRole(long userId, int roleId);
    User editAccount(long userId, String email, String idNumber, String msisdn);
}
