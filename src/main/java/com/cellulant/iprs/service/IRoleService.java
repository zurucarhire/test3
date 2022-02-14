package com.cellulant.iprs.service;

import com.cellulant.iprs.model.Role;

import java.util.List;

public interface IRoleService {
    Role create(long createdBy, Role role);
    Role update(long id, long updatedBy, Role role);
    void delete(long id, long updatedBy);
    List<Role> findAllActive();
    List<Role> findAll();
}
