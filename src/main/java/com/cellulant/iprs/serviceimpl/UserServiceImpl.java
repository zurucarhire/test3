package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.exception.UnprocessedResourceException;
import com.cellulant.iprs.model.*;
import com.cellulant.iprs.repository.RoleRepository;
import com.cellulant.iprs.repository.UserRepository;
import com.cellulant.iprs.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(s);
        if (user == null){
            log.error("user not found {}", s);
            throw new ResourceNotFoundException("user not found");
        }
        log.info("user found {}", user.getRoles());
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            log.info("role {}", role);
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
    public UserPaging getAllUsers(Integer draw, Integer pageNo, Integer pageSize, String sortBy)
    {
        log.info("pga {} {}", pageNo, pageSize);
        Pageable paging = PageRequest.of(pageNo, pageSize);

        long count = userRepository.count();
        log.info("count {}", count);
        Page<User> pagedResult = userRepository.findAll(paging);
        List<User> videosList = pagedResult.getContent();
        log.info("countee {}", videosList);
        return UserPaging.builder()
                .draw(draw)
                .recordsFiltered(count)
                .recordsTotal(count)
                .data(videosList)
                .build();

//        if(pagedResult.hasContent()) {
//            return pagedResult.getContent();
//        } else {
//
//            return new ArrayList<User>();
//        }
       // return userPaging;
    }

    @Override
    public User create(User user) {
        Role role = roleRepository.findByRoleID(user.getRoleID()).
                orElseThrow(() -> new ResourceNotFoundException("User not found " + user.getRoleID()));;
        user.getRoles().add(role);
        return userRepository.save(user);
    }

    @Override
    public User update(long id, User user) {
        User user1 = userRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("User not found " + id));
        user1.setClient(user.getClient());
        user1.setFullName(user.getFullName());
        user1.setEmailAddress(user.getEmailAddress());
        user1.setIdNumber(user.getIdNumber());
        user1.setMsisdn(user.getMsisdn());
        return userRepository.save(user1);
    }

    @Override
    public User delete(long id) {
        User user1 = userRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("User not found " + id));
        user1.setActive("INACTIVE");
        log.info("active {}", user1);
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
    public User editUserRole(long userId, int roleId) {
        User user1 = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found " + userId));
        user1.setRoleID(roleId);
        return userRepository.save(user1);
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
