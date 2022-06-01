package com.cellulant.iprs.api;

import com.cellulant.iprs.dto.ResetPasswordDTO;
import com.cellulant.iprs.entity.User;
import com.cellulant.iprs.dto.UserRoleDTO;
import com.cellulant.iprs.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/psm/user")
@Slf4j
public class UserResource {
    private final IUserService userService;

    @GetMapping("/findall")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody User user) {
        log.info("createUser {}", user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/iprs/user/create").toUriString());
        return ResponseEntity.created(uri).body(userService.create(user));
    }

    @PutMapping(value = "/update/{userId}/{updatedBy}")
    public ResponseEntity<User> update(@PathVariable(value = "userId") long userId,
                                       @PathVariable(value = "updatedBy") long updatedBy,
                                       @RequestBody User user) {
        log.info("update {} {}", userId, user);
        return ResponseEntity.ok(userService.update(userId, updatedBy, user));
    }

    @PutMapping(value = "/updateaccount/{userId}/{tag}/{value}")
    public ResponseEntity<User> updateAccount(@PathVariable(value = "userId") long userId,
                                              @PathVariable(value = "tag") String tag,
                                              @PathVariable(value = "value") String value
                                              ) {
        log.info("updateAccount {} {} {}", userId, tag, value);
        return ResponseEntity.ok(userService.updateAccount2(userId, tag, value));
    }

    @PutMapping("/uploadpic")
    public ResponseEntity<User> uploadImage(@RequestParam("userId") Long userId,
                                            @RequestParam("imageFile") MultipartFile image) {
        log.info("uploadImage  {} {}", userId, image);
        return ResponseEntity.ok(userService.uploadImage(userId, image));
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
    public ResponseEntity<UserRoleDTO> updateUserRole(@PathVariable(value = "userId") long userId,
                                                      @PathVariable(value = "roleId") long roleId,
                                                      @PathVariable(value = "updatedBy") long updatedBy) {
        log.info("editUserRole {} {} {}", roleId, userId, updatedBy);
        return ResponseEntity.ok(userService.updateUserRole(userId, roleId,updatedBy));
    }

    @GetMapping("/findalluserroles")
    public ResponseEntity<List<UserRoleDTO>> findAllUserRoles() {
        return ResponseEntity.ok().body(userService.findAllUserRoles());
    }

}
