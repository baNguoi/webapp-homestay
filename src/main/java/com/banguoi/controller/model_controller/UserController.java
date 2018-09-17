package com.banguoi.controller.model_controller;

import com.banguoi.model.*;
import com.banguoi.service.product.ProductService;
import com.banguoi.service.province.ProvinceService;
import com.banguoi.service.roles.RoleService;
import com.banguoi.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private ProductService productService;

    @Autowired
    private ProvinceService provinceService;

    @ModelAttribute("provinces")
    public Iterable<Province> findAllProvince() {
        return provinceService.findAll();
    }

    private String getPrincipal() {
        String email = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        return email;
    }

    @ModelAttribute("roles")
    public Iterable<Role> listRoles() {
        return roleService.findAll();
    }

    @RequestMapping(value = "/user/manager", method = RequestMethod.GET)
    public ModelAndView listPostHomestay(@RequestParam(value = "name", defaultValue = "") Optional<String> name,
                                         @RequestParam(value = "province") Optional<Province> province,Pageable pageable) {
        Page<Product> products;
        User user = userService.findUserByEmail(getPrincipal());

        if (name.isPresent() && province.isPresent()) {
            products = productService.findAllByUserAndNameContainingAndProvince(user, name.get(), province.get(), pageable);
        } else {
            products = productService.findProductsByUser(user, pageable);
        }

        ModelAndView modelAndView = new ModelAndView("/homestay/list");
        modelAndView.addObject("products", products);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/users/create")
    public ModelAndView showCreateForm() {

        User user = new User();

        ModelAndView modelAndView = new ModelAndView("/user/create");
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
        modelAndView.addObject("message", "User created successfully");

        return modelAndView;
    }

    @GetMapping("/user/detail/{id}")
    public ModelAndView detailUser(@PathVariable("id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ModelAndView("/error.404");
        }

        ModelAndView modelAndView = new ModelAndView("/user/detail");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/user/create-homestay")
    public ModelAndView showCreateHomestayForm() {
        User user = userService.findUserByEmail(getPrincipal());
        Product product = new Product();
        ModelAndView modelAndView = new ModelAndView("/homestay/create");
        modelAndView.addObject("product", product);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/user/create-homestay")
    public ModelAndView createNewHomestay(@ModelAttribute("product") Product product) {
        User user = userService.findUserByEmail(getPrincipal());
        productService.save(product, user);

        ModelAndView modelAndView = new ModelAndView("/image/upload");
        modelAndView.addObject("product", product);
        modelAndView.addObject("message", "Product created successfully");
        return modelAndView;
    }

    @GetMapping("/users/update/{id}")
    public ModelAndView showUpdateForm(@PathVariable("id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ModelAndView("/error.404");
        }

        ModelAndView modelAndView = new ModelAndView("/homestay/editUser");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/users/update")
    public ModelAndView updateUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        new User().validate(user, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return new ModelAndView("/homestay/editUser");
        }

        userService.save(user);
        ModelAndView modelAndView = new ModelAndView("/homestay/editUser");
        modelAndView.addObject("user", user);
        modelAndView.addObject("message", "User updated successfully");
        return modelAndView;
    }
}
