package com.cellulant.iprs.service;

import com.cellulant.iprs.dto.ResetPasswordDTO;
import com.cellulant.iprs.entity.User;
import com.cellulant.iprs.dto.UserRoleDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService {
    List<User> findAll();
    List<UserRoleDTO> findAllUserRoles();
    User create(User user);
    User update(long id, long updatedBy, User user);
    Long delete(long id, long updatedBy);
    void changePassword(long userId, String oldPassword, String newPassword, String confirmPassword);
    UserRoleDTO updateUserRole(long userId, long roleId, long updatedBy);
    User updateAccount(long userId, String email, String idNumber, String msisdn);
    User updateAccount2(long userId, String tag, String value);
    User uploadImage(Long userId, MultipartFile image);
    ResetPasswordDTO resetPassword(long userId, String updatedBy);
}
