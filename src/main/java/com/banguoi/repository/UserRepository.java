package com.banguoi.repository;

import com.banguoi.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    Iterable<User> findUserByNameContaining(String name);
}
