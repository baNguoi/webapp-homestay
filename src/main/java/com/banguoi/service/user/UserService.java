package com.banguoi.service.user;

import com.banguoi.model.User;

public interface UserService {

    Iterable<User> findUserByNameContaining(String name);

    User findUserByEmail(String email);

    Iterable<User> findAll();

    User findById(Long id);

    void save(User user);

    void remove(Long id);
}
