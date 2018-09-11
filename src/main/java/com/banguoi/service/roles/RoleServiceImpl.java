package com.banguoi.service.roles;

import com.banguoi.model.Role;
import com.banguoi.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleServiceImpl implements RoleService {

    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public Iterable<Role> findAll() {
        return rolesRepository.findAll();
    }

    @Override
    public Role findById(Long id) {
        return rolesRepository.findOne(id);
    }

    @Override
    public void save(Role role) {
        rolesRepository.save(role);
    }

    @Override
    public void remove(Long id) {
        rolesRepository.delete(id);
    }
}
