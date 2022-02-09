package com.cellulant.iprs.service;

import com.cellulant.iprs.model.Role;

import java.util.List;

public interface IRoleService {
    Role create(Role role);
    Role update(int id, String description);
    Role delete(int id);
    List<Role> findAll();
}
