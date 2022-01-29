package com.cellulant.iprs.api;

import com.cellulant.iprs.model.User;
import com.cellulant.iprs.model.UserPaging;
import com.cellulant.iprs.model.UserRole;
import com.cellulant.iprs.repository.ClientRepository;
import com.cellulant.iprs.repository.UserRepository;
import com.cellulant.iprs.service.IUserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/iprs/user")
@Slf4j
public class UserResource {
    private final IUserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/findall")
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping("/findalluserroles")
    public ResponseEntity<List<UserRole>> findAllUserRoles(){
        return ResponseEntity.ok().body(userService.findAllUserRoles());
    }

    @PostMapping("/findall2")
    public ResponseEntity<UserPaging> findAll2(@RequestParam("draw") int draw,
                                               @RequestParam("pagenumber") int pagenumber,
                                                     @RequestParam("pagesize") int pagesize,
                                                     @RequestParam("sortby") String sortby){
        log.info("find {} {} {} {}", draw, pagenumber, pagesize, sortby);
        return ResponseEntity.ok().body(userService.getAllUsers(draw, pagenumber, pagesize, sortby));
    }

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody User user){
        log.info("createUser {}", user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/iprs/user/create").toUriString());
        return ResponseEntity.created(uri).body(userService.create(user));
    }

//    @PutMapping(value = "/update/{userId}/{clientId}/{fullName}/{idNumber}/{msisdn}")
//    public ResponseEntity<User> update(@PathVariable(value = "userId") long userId,
//                                       @PathVariable(value = "clientId") long clientId,
//                                       @PathVariable(value = "fullName") long fullName,
//                                       @PathVariable(value = "idNumber") long idNumber,
//                                       @PathVariable(value = "msisdn") long msisdn) {
//        log.info("updateBooking {} {}",userId, clientId);
//        return new ResponseEntity<User>(userService.update(1, "joeabala@"), HttpStatus.OK);
//    }

    @PutMapping(value = "/update/{userId}")
    public ResponseEntity<User> update(@PathVariable(value = "userId") long userId, @RequestBody User user) {
        log.info("update {} {}",userId, user);
        return new ResponseEntity<User>(userService.update(userId, user), HttpStatus.OK);
    }

    @PutMapping(value = "/changepassword")
    public ResponseEntity<Void> changePassword(@RequestParam("userId") long userId,
                                                @RequestParam("oldPassword") String oldPassword,
                                               @RequestParam("newPassword") String newPassword,
                                               @RequestParam("confirmPassword") String confirmPassword) {
        log.info("changePassword {} {} {} {}",userId, oldPassword, newPassword, confirmPassword);
        userService.changePassword(userId, oldPassword, newPassword, confirmPassword);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/edituserrole/{userId}/{roleId}")
    public ResponseEntity<User> editUserRole(@PathVariable(value = "userId") long userId,
                                             @PathVariable(value = "roleId") int roleId) {
        log.info("editUserRole {} {}", roleId, userId);
        return new ResponseEntity<User>(userService.editUserRole(userId, roleId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/edituserrole/{userId}/{roleId}")
    public ResponseEntity<User> deleteUserRole(@PathVariable(value = "userId") long userId,
                                             @PathVariable(value = "roleId") int roleId) {
        log.info("editUserRole {} {}", roleId, userId);
        return new ResponseEntity<User>(userService.editUserRole(userId, roleId), HttpStatus.OK);
    }

    @PutMapping(value = "/editaccount")
    public ResponseEntity<User> editAccount(@RequestParam("userId") long userId,
                                               @RequestParam("email") String email,
                                               @RequestParam("idNumber") String idNumber,
                                               @RequestParam("msisdn") String msisdn) {
        log.info("editAccount {} {} {} {}",userId, email, idNumber, msisdn);
        return new ResponseEntity<User>(userService.editAccount(userId, email, idNumber, msisdn), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{userId}")
    public ResponseEntity<User> delete(@PathVariable(value = "userId") long userId) {
        log.info("delete {} ",userId);
        return new ResponseEntity<User>(userService.delete(userId), HttpStatus.OK);
    }

//    @PutMapping(value = "/update")
//    public ResponseEntity<User> update(@RequestParam("id") long id,  @RequestParam("emailaddress") String emailAddress) {
//        log.info("updateBooking {} {}",id, emailAddress);
//        return new ResponseEntity<User>(userService.update(id, emailAddress), HttpStatus.OK);
//    }

//    @RequestMapping(value = "/data/users", method = RequestMethod.GET)
//    public DataTablesOutput<User> getUsers(@Valid DataTablesInput input) {
//        log.info("helllloooo");
//        return userRepository.findAll(input);
//    }

    @RequestMapping(value = "/data/users", method = RequestMethod.POST)
    public DataTablesOutput<User> getUsers(@Valid @RequestBody DataTablesInput input) {
        log.info("hello {}", input);
        return userRepository.findAll(input);
    }
}

@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}
