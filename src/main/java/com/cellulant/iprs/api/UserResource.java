package com.cellulant.iprs.api;

import com.cellulant.iprs.model.User;
import com.cellulant.iprs.model.UserPaging;
import com.cellulant.iprs.model.UserRole;
import com.cellulant.iprs.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/iprs/user")
@Slf4j
public class UserResource {
    private final IUserService userService;

    @GetMapping("/findall")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping("/findalluserroles")
    public ResponseEntity<List<UserRole>> findAllUserRoles() {
        return ResponseEntity.ok().body(userService.findAllUserRoles());
    }

    @PostMapping("/create/{insertedBy}")
    public ResponseEntity<User> create(@PathVariable(value = "insertedBy") long insertedBy, @RequestBody User user) {
        log.info("createUser {}", user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/iprs/user/create").toUriString());
        return ResponseEntity.created(uri).body(userService.create(insertedBy, user));
    }

    @PutMapping(value = "/update/{userId}/{updatedBy}")
    public ResponseEntity<User> update(@PathVariable(value = "userId") long userId,
                                       @PathVariable(value = "updatedBy") long updatedBy,
                                       @RequestBody User user) {
        log.info("update {} {}", userId, user);
        return new ResponseEntity<User>(userService.update(userId, updatedBy, user), HttpStatus.OK);
    }

    @PutMapping(value = "/changepassword")
    public ResponseEntity<Void> changePassword(@RequestParam("userId") long userId,
                                               @RequestParam("oldPassword") String oldPassword,
                                               @RequestParam("newPassword") String newPassword,
                                               @RequestParam("confirmPassword") String confirmPassword) {
        log.info("changePassword {} {} {} {}", userId, oldPassword, newPassword, confirmPassword);
        userService.changePassword(userId, oldPassword, newPassword, confirmPassword);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/edituserrole/{userId}/{roleId}")
    public ResponseEntity<UserRole> editUserRole(@PathVariable(value = "userId") long userId,
                                                 @PathVariable(value = "roleId") int roleId) {
        log.info("editUserRole {} {}", roleId, userId);
        return new ResponseEntity<UserRole>(userService.editUserRole(userId, roleId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteuserrole/{userId}")
    public ResponseEntity<UserRole> deleteUserRole(@PathVariable(value = "userId") long userId) {
        log.info("deleteUserRole {}", userId);
        return new ResponseEntity<UserRole>(userService.deleteUserRole(userId), HttpStatus.OK);
    }

    @PutMapping(value = "/editaccount")
    public ResponseEntity<User> editAccount(@RequestParam("userId") long userId,
                                            @RequestParam("email") String email,
                                            @RequestParam("idNumber") String idNumber,
                                            @RequestParam("msisdn") String msisdn) {
        log.info("editAccount {} {} {} {}", userId, email, idNumber, msisdn);
        return new ResponseEntity<User>(userService.editAccount(userId, email, idNumber, msisdn), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{userId}/{updatedBy}")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "userId") long userId,
                                           @PathVariable(value = "updatedBy") long updatedBy) {
        log.info("delete {} ", userId);
        return new ResponseEntity<User>(userService.delete(userId, updatedBy), HttpStatus.OK);
    }
}
