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

    @PostMapping("/create/{createdBy}")
    public ResponseEntity<Role> create(@PathVariable(value = "createdBy") long createdBy,
                                       @Valid @RequestBody Role role){
        log.info("createRole {}", role);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/iprs/role/create").toUriString());
        return ResponseEntity.created(uri).body(roleService.create(createdBy, role));
    }

    @PutMapping(value = "/update/{roleId}/{updatedBy}")
    public ResponseEntity<Role> update(@PathVariable(value = "roleId") long roleId,
                                       @PathVariable(value = "updatedBy") long updatedBy,
                                       @RequestBody Role role) {
        log.info("update {} {}",roleId, role);
        return ResponseEntity.ok(roleService.update(roleId, updatedBy, role));
    }

    @DeleteMapping(value = "/delete/{roleId}/{updatedBy}")
    public ResponseEntity<Long> delete(@PathVariable(value = "roleId") long roleId,
                                       @PathVariable(value = "updatedBy") long updatedBy) {
        log.info("delete {}",roleId);
        roleService.delete(roleId, updatedBy);
        return ResponseEntity.ok(roleId);
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Role>> findAll(){
        log.info("findAllRoles");
        return ResponseEntity.ok().body(roleService.findAll());
    }

    @GetMapping("/findallactive")
    public ResponseEntity<List<Role>> findAllActive(){
        log.info("findAllActiveRoles");
        return ResponseEntity.ok().body(roleService.findAllActive());
    }
}
