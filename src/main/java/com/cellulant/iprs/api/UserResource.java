package com.cellulant.iprs.api;

import com.cellulant.iprs.model.ResetPasswordDTO;
import com.cellulant.iprs.model.User;
import com.cellulant.iprs.model.UserRole;
import com.cellulant.iprs.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @PostMapping("/create/{createdBy}")
    public ResponseEntity<User> create(@PathVariable(value = "createdBy") long createdBy, @RequestBody User user) {
        log.info("createUser {}", user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/iprs/user/create").toUriString());
        return ResponseEntity.created(uri).body(userService.create(createdBy, user));
    }

    @PutMapping(value = "/update/{userId}/{updatedBy}")
    public ResponseEntity<User> update(@PathVariable(value = "userId") long userId,
                                       @PathVariable(value = "updatedBy") long updatedBy,
                                       @RequestBody User user) {
        log.info("update {} {}", userId, user);
        return ResponseEntity.ok(userService.update(userId, updatedBy, user));
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

    @PutMapping(value = "/updateaccount")
    public ResponseEntity<User> updateAccount(@RequestParam("userId") long userId,
                                              @RequestParam("email") String email,
                                              @RequestParam("idNumber") String idNumber,
                                              @RequestParam("msisdn") String msisdn) {
        log.info("updateAccount {} {} {} {}", userId, email, idNumber, msisdn);
        return ResponseEntity.ok(userService.updateAccount(userId, email, idNumber, msisdn));
    }

    @PutMapping(value = "/resetpassword/{userId}/{updatedBy}")
    public ResponseEntity<ResetPasswordDTO> resetPassword(@PathVariable(value = "userId") long userId,
                                                          @PathVariable(value = "updatedBy") String updatedBy) {
        log.info("resetPassword {} {}", userId, updatedBy);
        return ResponseEntity.ok(userService.resetPassword(userId, updatedBy));
    }

    @DeleteMapping(value = "/delete/{userId}/{updatedBy}")
    public ResponseEntity<Long> deleteUser(@PathVariable(value = "userId") long userId,
                                           @PathVariable(value = "updatedBy") long updatedBy) {
        log.info("delete {} ", userId);
        userService.delete(userId, updatedBy);
        return ResponseEntity.ok(userId);
    }

    // =================================== USER ROLES ===========================================

    @PutMapping(value = "/updateuserrole/{userId}/{roleId}/{updatedBy}")
    public ResponseEntity<UserRole> updateUserRole(@PathVariable(value = "userId") long userId,
                                                 @PathVariable(value = "roleId") long roleId,
                                                 @PathVariable(value = "updatedBy") long updatedBy) {
        log.info("editUserRole {} {} {}", roleId, userId, updatedBy);
        return ResponseEntity.ok(userService.updateUserRole(userId, roleId,updatedBy));
    }

    @GetMapping("/findalluserroles")
    public ResponseEntity<List<UserRole>> findAllUserRoles() {
        return ResponseEntity.ok().body(userService.findAllUserRoles());
    }

}
