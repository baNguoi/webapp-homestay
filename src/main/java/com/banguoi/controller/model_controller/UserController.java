package com.banguoi.controller.model_controller;

import com.banguoi.model.Role;
import com.banguoi.model.User;
import com.banguoi.service.roles.RoleService;
import com.banguoi.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @ModelAttribute("roles")
    public Iterable<Role> listRoles() {
        return roleService.findAll();
    }

    @GetMapping("/users")
    public ModelAndView listUser(@RequestParam("s")Optional<String> s) {
        Iterable<User> users;
        if (s.isPresent()) {
            users = userService.findUserByNameContaining(s.get());
        } else {
            users = userService.findAll();
        }

        ModelAndView modelAndView = new ModelAndView("user/list");
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping("/users/create")
    public ModelAndView showCreateForm() {

        User user = new User();

        ModelAndView modelAndView = new ModelAndView("user/create");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/users/create")
    public ModelAndView createUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        new User().validate(user, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return new ModelAndView("user/create");
        }

        userService.save(user);
        ModelAndView modelAndView = new ModelAndView("user/create");
        modelAndView.addObject("user", user);
        modelAndView.addObject("message", "User created compliment");

        return modelAndView;
    }

    @GetMapping("/users/update/{id}")
    public ModelAndView showUpdateForm(@PathVariable("id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ModelAndView("/error.404");
        }

        ModelAndView modelAndView = new ModelAndView("/user/edit");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/users/update")
    public ModelAndView updateUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        new User().validate(user, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return new ModelAndView("/user/edit");
        }

        userService.save(user);
        ModelAndView modelAndView = new ModelAndView("/user/edit");
        modelAndView.addObject("user", user);
        modelAndView.addObject("message", "User updated successfully");
        return modelAndView;
    }

    @GetMapping("/users/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable("id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ModelAndView("/error.404");
        }

        ModelAndView modelAndView = new ModelAndView("/user/delete");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/users/delete")
    public String deleteUser(@ModelAttribute("user") User user) {
        userService.remove(user.getId());

        return "redirect:/users";
    }

    @GetMapping("/users/detail/{id}")
    public ModelAndView detailUser(@PathVariable("id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ModelAndView("/error.404");
        }

        ModelAndView modelAndView = new ModelAndView("/user/detail");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
