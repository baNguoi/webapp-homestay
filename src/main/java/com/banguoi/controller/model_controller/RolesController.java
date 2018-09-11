package com.banguoi.controller.model_controller;

import com.banguoi.model.Role;
import com.banguoi.service.roles.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RolesController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/roles")
    public ModelAndView listUser() {
        Iterable<Role> roles = roleService.findAll();

        ModelAndView modelAndView = new ModelAndView("/role/list");
        modelAndView.addObject("roles", roles);
        return modelAndView;
    }

    @GetMapping("/roles/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/role/create");
        modelAndView.addObject("role", new Role());
        return modelAndView;
    }

    @PostMapping("/roles/create")
    public ModelAndView createRoles(@ModelAttribute("role") Role role) {

        roleService.save(role);
        ModelAndView modelAndView = new ModelAndView("/role/create");
        modelAndView.addObject("role", role);
        modelAndView.addObject("message", "Role created compliment");

        return modelAndView;
    }

    @GetMapping("/roles/update/{id}")
    public ModelAndView showUpdateForm(@PathVariable("id") Long id) {
        Role role = roleService.findById(id);

        if (role == null) {
            return new ModelAndView("/error.404");
        }

        ModelAndView modelAndView = new ModelAndView("/role/edit");
        modelAndView.addObject("role", role);
        return modelAndView;
    }

    @PostMapping("/roles/update")
    public ModelAndView updateUser(@ModelAttribute("role") Role role) {
        roleService.save(role);
        ModelAndView modelAndView = new ModelAndView("/role/edit");
        modelAndView.addObject("role", role);
        modelAndView.addObject("message", "Role updated successfully");
        return modelAndView;
    }

    @GetMapping("/roles/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable("id") Long id) {
        Role role = roleService.findById(id);

        if (role == null) {
            return new ModelAndView("/error.404");
        }

        ModelAndView modelAndView = new ModelAndView("/role/delete");
        modelAndView.addObject("role", role);
        return modelAndView;
    }

    @PostMapping("/roles/delete")
    public String deleteUser(@ModelAttribute("role") Role role) {
        roleService.remove(role.getId());
        return "redirect:/roles";
    }

    @GetMapping("/roles/detail/{id}")
    public ModelAndView detailUser(@PathVariable("id") Long id) {
        Role role = roleService.findById(id);

        if (role == null) {
            return new ModelAndView("/error.404");
        }

        ModelAndView modelAndView = new ModelAndView("/role/detail");
        modelAndView.addObject("role", role);
        return modelAndView;
    }
}
