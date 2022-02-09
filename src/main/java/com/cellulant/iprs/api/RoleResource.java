package com.cellulant.iprs.api;

import com.cellulant.iprs.model.Role;
import com.cellulant.iprs.service.IRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/iprs/role")
@Slf4j
public class RoleResource {
    private final IRoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<Role> create(@Valid @RequestBody Role role){
        log.info("createRole {}", role);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/iprs/role/create").toUriString());
        return ResponseEntity.created(uri).body(roleService.create(role));
    }

    @PutMapping(value = "/update/{roleId}/{description}")
    public ResponseEntity<Role> update(@PathVariable(value = "roleId") int roleId,
                                       @PathVariable(value = "description") String description) {
        log.info("update {} {}",roleId, description);
        return new ResponseEntity<Role>(roleService.update(roleId, description), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{roleId}")
    public ResponseEntity<Role> update(@PathVariable(value = "roleId") int roleId) {
        log.info("delete {}",roleId);
        return new ResponseEntity<Role>(roleService.delete(roleId), HttpStatus.OK);
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Role>> findAll(){
        log.info("findAllRoles");
        return ResponseEntity.ok().body(roleService.findAll());
    }
}
