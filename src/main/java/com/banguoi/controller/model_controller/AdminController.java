package com.banguoi.controller.model_controller;

import com.banguoi.model.Product;
import com.banguoi.model.Province;
import com.banguoi.model.Role;
import com.banguoi.model.User;
import com.banguoi.service.product.ProductService;
import com.banguoi.service.province.ProvinceService;
import com.banguoi.service.roles.RoleService;
import com.banguoi.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProvinceService provinceService;

    @ModelAttribute("roles")
    public Iterable<Role> listRoles() {
        return roleService.findAll();
    }

    @ModelAttribute("provinces")
    public Iterable<Province> listProvince() {
        return provinceService.findAll();
    }

    @GetMapping("/users")
    public ModelAndView listUser(@RequestParam("s") Optional<String> s) {
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

    @GetMapping("/admin/products")
    public String listProduct(@RequestParam("s") Optional<Province> province, Pageable pageable, Model model) {
        Page<Product> products;

        if (province.isPresent()) {
            products = productService.findAllByProvince(province.get(), pageable);
        } else {
            products = productService.findAll(pageable);
        }
        model.addAttribute("products", products);
        return "/admin/list";
    }


    @PostMapping("/users/update/{id}")
    public ModelAndView updateUser(@PathVariable("id") Long id, @RequestParam("role") Role role) {
        User user = userService.findById(id);
        user.setRole(role);

        userService.save(user);
        Iterable<User> users = userService.findAll();

        ModelAndView modelAndView = new ModelAndView("/user/list");
        modelAndView.addObject("user", user);
        modelAndView.addObject("users", users);
        modelAndView.addObject("message", "Role chang successfully");
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
}
