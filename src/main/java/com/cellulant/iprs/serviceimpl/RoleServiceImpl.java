package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.exception.ResourceFoundException;
import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.model.Role;
import com.cellulant.iprs.repository.RoleRepository;
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

    @Override
    public Role create(Role role) {
        roleRepository.findByRoleName(role.getRoleName()).ifPresent(s -> {
            throw new ResourceFoundException("Role exists " + s.getRoleName());
        });
        return roleRepository.save(role);
    }

    @Override
    public Role update(long id, Role role) {
        Role role1 = roleRepository.findByRoleID(id).
                orElseThrow(() -> new ResourceNotFoundException("Role not found " + id));

        role1.setDescription(role.getDescription());
        role1.setActive(role.getActive());
        return roleRepository.save(role);
    }

    @Override
    public void delete(long id) {
        roleRepository.findByRoleID(id).
                orElseThrow(() -> new ResourceNotFoundException("Role not found " + id));
        roleRepository.deleteById(id);
    }

    @Override
    public List<Role> findAllActiveRoles() {
        return roleRepository.findAllActiveRoles();
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
