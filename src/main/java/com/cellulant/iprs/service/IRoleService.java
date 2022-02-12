package com.cellulant.iprs.service;

import com.cellulant.iprs.model.Role;

import java.util.List;

public interface IRoleService {
    Role create(Role role);
    Role update(long id, Role role);
    void delete(long id);
    List<Role> findAllActiveRoles();
    List<Role> findAll();
}
