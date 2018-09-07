package com.banguoi.controller.security_controller;

import com.banguoi.model.Product;
import com.banguoi.model.User;
import com.banguoi.service.product.ProductService;
import com.banguoi.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    @RequestMapping(value = {"/", "home"})
    public String home() {
        return "/home";
    }

    @RequestMapping(value = "/user")
    public ModelAndView user(Pageable pageable) {
        User user = userService.findUserByEmail(getPrincipal());
        Page<Product> products;
        products = productService.findProductsByUser(user, pageable);
        ModelAndView modelAndView = new ModelAndView("/userPage");
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @RequestMapping(value = "/admin")
    public String admin() {
        return "/adminPage";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/403")
    public String Error403() {
        return "/403";
    }
}