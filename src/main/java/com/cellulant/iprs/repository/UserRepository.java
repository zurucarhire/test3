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
    Long deleteByUserID(long userId);

}
