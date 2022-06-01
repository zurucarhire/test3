package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.configuration.AppConfig;
import com.cellulant.iprs.dto.ResetPasswordDTO;
import com.cellulant.iprs.dto.UserRoleDTO;
import com.cellulant.iprs.exception.ResourceExistsException;
import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.exception.UnprocessedResourceException;
import com.cellulant.iprs.entity.*;
import com.cellulant.iprs.repository.RoleRepository;
import com.cellulant.iprs.repository.UserRepository;
import com.cellulant.iprs.service.IChangeLogService;
import com.cellulant.iprs.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final IChangeLogService changeLogService;

    @Override
    public List<User> findAll() {
        //return null;
        return userRepository.findAll();
    }

    @Override
    public User create(User user) {
        Role role = roleRepository.findByRoleID(user.getRoleID()).
                orElseThrow(() -> new ResourceNotFoundException("Role Not Found"));

        userRepository.findByEmailAddressIgnoreCase(user.getEmailAddress()).ifPresent(s -> {
            throw new ResourceExistsException("Email Address Already Exists");
        });

        user.getRoles().add(role);

        if (!user.getPassword().equals("nil"))
            user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User update(long userId, long updatedBy, User user) {
        User user1 = userRepository.findByUserID(userId).
                orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        if (!user.getEmailAddress().equals(user1.getEmailAddress())){
            userRepository.findByEmailAddressIgnoreCase(user.getEmailAddress()).ifPresent(s -> {
                throw new ResourceExistsException("Username Already Exists");
            });
        }

        if (!user.getEmailAddress().equals(user1.getEmailAddress())){
            userRepository.findByEmailAddressIgnoreCase(user.getEmailAddress()).ifPresent(s -> {
                throw new ResourceExistsException("Email Address Already Exists");
            });
        }

        changeLogService.create(updatedBy, "update user " + user.getEmailAddress());

        return userRepository.save(user1);
    }

    @Override
    public Long delete(long userId, long updatedBy) {
        User user1 = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        changeLogService.create(updatedBy, "deleted user " + user1.getEmailAddress());

        return userRepository.deleteByUserID(userId);
    }

    @Override
    public void changePassword(long userId, String oldPassword, String newPassword, String confirmPassword) {
        User user1 = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        if(!passwordEncoder.matches(oldPassword, user1.getPassword())){
            throw new UnprocessedResourceException("Old password is incorrect");
        }

        if (!newPassword.equals(confirmPassword)){
            throw new UnprocessedResourceException("Passwords do not match");
        }

        user1.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user1);
    }

    @Override
    public User updateAccount(long userId, String email, String idNumber, String msisdn) {
        User user1 = userRepository.findByUserID(userId).
                orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        user1.setEmailAddress(email);

        return userRepository.save(user1);
    }

    @Override
    public User updateAccount2(long userId, String tag, String value) {
        User user1 = userRepository.findByUserID(userId).
                orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        if (tag.equals("username")){
            user1.setEmailAddress(value);
        } else if (tag.equals("fullname")){
            user1.setFullName(value);
        } else if (tag.equals("businessname")){
            user1.setBusinessName(value);
        } else if (tag.equals("shoplocation")){
            user1.setShopLocation(value);
        } else if (tag.equals("phone")){
            user1.setPhone(value);
        }
        return userRepository.save(user1);

    }

    @Override
    public User uploadImage(Long userId, MultipartFile image) {
        User user1 = userRepository.findByUserID(userId).
                orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        try {
            byte[] bytes = image.getBytes();
            //Path pathProfilePic = Paths.get(AppConfig.UPLOAD_PATH + image.getOriginalFilename());
            //Files.write(pathProfilePic, bytesProfilePic);

            String fileName = System.currentTimeMillis()+".png";
            Path path = Paths.get(AppConfig.UPLOAD_PATH + fileName);
            Files.write(path, bytes);

            user1.setProfileImage(fileName);

            return userRepository.save(user1);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnprocessedResourceException("Something went wrong please try again");
        }
    }

    @Override
    public ResetPasswordDTO resetPassword(long userId, String updatedBy) {
        User user1 = userRepository.findByUserID(userId).
                orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        RandomString session = new RandomString();
        String generatedPassword = session.nextString();
        log.info("session -> {}", generatedPassword);
        user1.setPassword(passwordEncoder.encode(generatedPassword));
        userRepository.save(user1);
        return ResetPasswordDTO.builder().username(user1.getEmailAddress()).password(generatedPassword).build();
    }

    @Override
    public UserRoleDTO updateUserRole(long userId, long roleId, long updatedBy) {
        User user1 = userRepository.findByUserID(userId).
                orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        user1.setRoleID(roleId);
        userRepository.save(user1);
        return null;
    }

    @Override
    public List<UserRoleDTO> findAllUserRoles() {
        return null;
    }
}
