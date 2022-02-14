package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.exception.ResourceExistsException;
import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.model.Role;
import com.cellulant.iprs.repository.RoleRepository;
import com.cellulant.iprs.service.IChangeLogService;
import com.cellulant.iprs.service.IRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements IRoleService {

    private final RoleRepository roleRepository;
    private final IChangeLogService changeLogService;

    @Override
    public Role create(long createdBy, Role role) {
        roleRepository.findByRoleNameIgnoreCase(role.getRoleName()).ifPresent(s -> {
            throw new ResourceExistsException("Resource Already Exists");
        });
        Role createdRole = roleRepository.save(role);
        changeLogService.create(createdBy, "create role " + role.getRoleName());
        return createdRole;
    }

    @Override
    public Role update(long id, long updatedBy, Role role) {
        Role role1 = roleRepository.findByRoleID(id).
                orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));

        role1.setDescription(role.getDescription());
        role1.setActive(role.getActive());

        Role updatedRole = roleRepository.save(role);
        changeLogService.create(updatedBy, "update role " + role.getRoleName());
        return updatedRole;
    }

    @Override
    public void delete(long roleId, long updatedBy) {
        Role role = roleRepository.findByRoleID(roleId).
                orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));
        changeLogService.create(updatedBy, "deleted role " + role.getRoleName());
        roleRepository.deleteById(roleId);
    }

    @Override
    public List<Role> findAllActive() {
        return roleRepository.findAllActive();
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
