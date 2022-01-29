package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.model.Role;
import com.cellulant.iprs.model.User;
import com.cellulant.iprs.repository.RoleRepository;
import com.cellulant.iprs.service.IClientService;
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
        return roleRepository.save(role);
    }

    @Override
    public Role update(int id, String description) {
        Role role = roleRepository.findByRoleID(id).
                orElseThrow(() -> new ResourceNotFoundException("User not found " + id));

        role.setDescription(description);
        return roleRepository.save(role);
    }

    @Override
    public Role delete(int id) {
        Role role = roleRepository.findByRoleID(id).
                orElseThrow(() -> new ResourceNotFoundException("User not found " + id));

        role.setActive(0);
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
