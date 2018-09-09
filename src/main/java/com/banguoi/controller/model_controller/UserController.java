package com.banguoi.controller.model_controller;

import com.banguoi.model.Product;
import com.banguoi.model.Role;
import com.banguoi.model.User;
import com.banguoi.service.product.ProductService;
import com.banguoi.service.roles.RoleService;
import com.banguoi.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ProductService productService;

    private User getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }

        User user = userService.findUserByEmail(userName);
        return user;
    }

    @ModelAttribute("roles")
    public Iterable<Role> listRoles() {
        return roleService.findAll();
    }

    @RequestMapping(value = "/user/manager", method = RequestMethod.GET)
    public ModelAndView listPostHomestay(Pageable pageable) {
        Page<Product> products = productService.findProductsByUser(getPrincipal(), pageable);

        ModelAndView modelAndView = new ModelAndView("/homestay/list");
        modelAndView.addObject("products", products);
        modelAndView.addObject("user", getPrincipal());
        return modelAndView;
    }
}
