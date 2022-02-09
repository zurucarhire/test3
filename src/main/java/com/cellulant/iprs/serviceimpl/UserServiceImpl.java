package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.exception.UnprocessedResourceException;
import com.cellulant.iprs.model.*;
import com.cellulant.iprs.repository.ChangeLogRepository;
import com.cellulant.iprs.repository.RoleRepository;
import com.cellulant.iprs.repository.UserRepository;
import com.cellulant.iprs.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements IUserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ChangeLogRepository changeLogRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(s);
        if (user == null){
            log.error("user not found {}", s);
            throw new ResourceNotFoundException("user not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public List<UserRole> findAllUserRoles() {
        return userRepository.findUserRoles();
    }

    @Override
    public User create(long insertedBy, User user) {
        Role role = roleRepository.findByRoleID(user.getRoleID()).
                orElseThrow(() -> new ResourceNotFoundException("Role not found " + user.getRoleID()));;
        user.getRoles().add(role);

        ChangeLog changeLog = ChangeLog.builder()
                .insertedBy(insertedBy)
                .narration("create user " + user.getUserName())
                .build();

        changeLogRepository.save(changeLog);
        return userRepository.save(user);
    }

    @Override
    public User update(long userId, long updatedBy, User user) {
        User user1 = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found " + userId));
        user1.setClient(user.getClient());
        user1.setFullName(user.getFullName());
        user1.setEmailAddress(user.getEmailAddress());
        user1.setIdNumber(user.getIdNumber());
        user1.setMsisdn(user.getMsisdn());

        ChangeLog changeLog = ChangeLog.builder()
                .insertedBy(updatedBy)
                .narration("update user " + user.getUserName())
                .build();

        changeLogRepository.save(changeLog);
        return userRepository.save(user1);
    }

    @Override
    public User delete(long userId, long updatedBy) {
        User user1 = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found " + userId));
        user1.setActive("INACTIVE");

        ChangeLog changeLog = ChangeLog.builder()
                .insertedBy(updatedBy)
                .narration("deleted user " + user1.getUserName())
                .build();

        changeLogRepository.save(changeLog);
        return userRepository.save(user1);
    }

    @Override
    public void changePassword(long userId, String oldPassword, String newPassword, String confirmPassword) {
        User user1 = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found " + userId));

        if(!passwordEncoder.matches(oldPassword, user1.getPassword())){
            throw new UnprocessedResourceException("Password mismatch");
        }

        if (!newPassword.equals(confirmPassword)){
            throw new UnprocessedResourceException("Passwords do not match");
        }

        user1.setPassword(passwordEncoder.encode(newPassword));
        log.info("user1 {}", user1);
        userRepository.save(user1);
    }

    @Override
    public UserRole editUserRole(long userId, int roleId) {
        User user1 = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found " + userId));
        user1.setRoleID(roleId);
         userRepository.save(user1);
         return userRepository.findUserRolesById(userId);
    }

    @Override
    public UserRole deleteUserRole(long userId) {
        User user1 = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found " + userId));
        user1.setActive("INACTIVE");
        userRepository.save(user1);
        return userRepository.findUserRolesById(userId);
    }

    @Override
    public User editAccount(long userId, String email, String idNumber, String msisdn) {
        User user1 = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found " + userId));
        user1.setEmailAddress(email);
        user1.setIdNumber(idNumber);
        user1.setMsisdn(msisdn);
        return userRepository.save(user1);
    }
}
