package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.exception.ResourceFoundException;
import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.exception.UnprocessedResourceException;
import com.cellulant.iprs.model.*;
import com.cellulant.iprs.repository.ChangeLogRepository;
import com.cellulant.iprs.repository.RoleRepository;
import com.cellulant.iprs.repository.UserRepository;
import com.cellulant.iprs.service.IChangeLogService;
import com.cellulant.iprs.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
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
    public User create(long createdBy, User user) {
        Role role = roleRepository.findByRoleID(user.getRoleID()).
                orElseThrow(() -> new ResourceNotFoundException("Role not found " + user.getRoleID()));

        userRepository.findByUserNameIgnoreCase(user.getUserName()).ifPresent(s -> {
            throw new UnprocessedResourceException("Username exists, " + s.getUserName());
        });

        userRepository.findByEmailAddressIgnoreCase(user.getEmailAddress()).ifPresent(s -> {
            throw new UnprocessedResourceException("Email address exists, " + s.getEmailAddress());
        });

        userRepository.findByIdNumber(user.getIdNumber()).ifPresent(s -> {
            throw new UnprocessedResourceException("ID Number exists, " + s.getIdNumber());
        });

        userRepository.findByMsisdn(user.getMsisdn()).ifPresent(s -> {
            throw new UnprocessedResourceException("Phone number exists, " + s.getMsisdn());
        });

        user.getRoles().add(role);

        user.setPassword("nil");
        user.setCanAccessUi("YES");
        user.setActive(1);
        user.setPasswordAttempts(0);
        user.setCreatedBy(createdBy);
        user.setUpdatedBy(createdBy);
        user.setLastLoginDate(new Date());
        User user1 = userRepository.save(user);

        changeLogService.create(createdBy, "create user " + user.getUserName());

        return user1;
    }

    @Override
    public User update(long userId, long updatedBy, User user) {
        User user1 = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found " + userId));

        if (!user.getUserName().equals(user1.getUserName())){
            userRepository.findByUserNameIgnoreCase(user.getUserName()).ifPresent(s -> {
                throw new UnprocessedResourceException("Username exists, " + s.getUserName());
            });
        }

        if (!user.getEmailAddress().equals(user1.getEmailAddress())){
            userRepository.findByEmailAddressIgnoreCase(user.getEmailAddress()).ifPresent(s -> {
                throw new UnprocessedResourceException("Email exists, " + s.getEmailAddress());
            });
        }

        if (!user.getIdNumber().equals(user1.getIdNumber())){
            userRepository.findByIdNumber(user.getIdNumber()).ifPresent(s -> {
                throw new UnprocessedResourceException("ID Number exists, " + s.getIdNumber());
            });
        }

        if (!user.getMsisdn().equals(user1.getMsisdn())){
            userRepository.findByMsisdn(user.getMsisdn()).ifPresent(s -> {
                throw new UnprocessedResourceException("Phone number exists, " + s.getMsisdn());
            });
        }
        user1.setClient(user.getClient());
        user1.setFullName(user.getFullName());
        user1.setEmailAddress(user.getEmailAddress());
        user1.setIdNumber(user.getIdNumber());
        user1.setMsisdn(user.getMsisdn());
        user1.setRoleID(user.getRoleID());
        user1.setActive(user.getActive());

        changeLogService.create(updatedBy, "update user " + user.getUserName());

        return userRepository.save(user1);
    }

    @Override
    public Long delete(long userId, long updatedBy) {
        User user1 = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found " + userId));

        changeLogService.create(updatedBy, "deleted user " + user1.getUserName());

        return userRepository.deleteByUserID(userId);
    }

    @Override
    public void changePassword(long userId, String oldPassword, String newPassword, String confirmPassword) {
        User user1 = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(!passwordEncoder.matches(oldPassword, user1.getPassword())){
            throw new UnprocessedResourceException("Old password is incorrect");
        }

        if (!newPassword.equals(confirmPassword)){
            throw new UnprocessedResourceException("Passwords do not match");
        }

        log.info("the -> {}", passwordEncoder.encode("Zuru@123"));
        user1.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user1);
    }

    @Override
    public User updateAccount(long userId, String email, String idNumber, String msisdn) {
        User user1 = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found " + userId));
        user1.setEmailAddress(email);
        user1.setIdNumber(idNumber);
        user1.setMsisdn(msisdn);
        return userRepository.save(user1);
    }

    @Override
    public ResetPasswordDTO resetPassword(long userId, String updatedBy) {
        User user1 = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found"));
        RandomString session = new RandomString();
        String generatedPassword = session.nextString();
        log.info("session -> {}", generatedPassword);
        user1.setPassword(passwordEncoder.encode(generatedPassword));
        userRepository.save(user1);
        return ResetPasswordDTO.builder().username(user1.getUserName()).password(generatedPassword).build();
    }

    @Override
    public UserRole updateUserRole(long userId, long roleId, long updatedBy) {
        User user1 = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found " + userId));
        user1.setRoleID(roleId);
        userRepository.save(user1);
        return userRepository.findUserRolesById(userId);
    }

    @Override
    public void deleteUserRole(long userId, long updatedBy) {
        User user1 = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found " + userId));
       // user1.setActive("INACTIVE");
//        userRepository.save(user1);
//        userRepository.findUserRolesById(userId);

    }

    @Override
    public List<UserRole> findAllUserRoles() {
        return userRepository.findAllUserRoles();
    }
}
