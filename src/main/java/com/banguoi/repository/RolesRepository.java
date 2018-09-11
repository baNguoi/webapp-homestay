package com.banguoi.repository;

import com.banguoi.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RolesRepository extends CrudRepository<Role, Long> {
    Role findByRoles(String roles);
}
