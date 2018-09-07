package com.banguoi.service.user;

import com.banguoi.model.User;

public interface UserService {

    Iterable<User> findUserByNameContaining(String name);

    Iterable<User> findAll();

    User findUserByEmail(String email);

    User findById(Long id);

    void save(User user);

    void remove(Long id);
}
