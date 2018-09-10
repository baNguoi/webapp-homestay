package com.banguoi.service.user;

import com.banguoi.model.Role;
import com.banguoi.model.User;
import com.banguoi.repository.RolesRepository;
import com.banguoi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository roleRepository;

    @Override
    public Iterable<User> findUserByNameContaining(String name) {
        return userRepository.findUserByNameContaining(name);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public void save(User user) {
        Role userRole = roleRepository.findByRoles("ROLE_USER");
        user.setRole(userRole);
        userRepository.save(user);
    }

    @Override
    public void remove(Long id) {
        userRepository.delete(id);
    }
}
