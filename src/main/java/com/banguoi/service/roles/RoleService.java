package com.banguoi.service.roles;

import com.banguoi.model.Role;

public interface RoleService {

    Iterable<Role> findAll();

    Role findById(Long id);

    void save(Role role);

    void remove(Long id);
}
