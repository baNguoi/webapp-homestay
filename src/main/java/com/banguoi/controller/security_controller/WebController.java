package com.banguoi.controller.security_controller;

import com.banguoi.model.Image;
import com.banguoi.model.Product;
import com.banguoi.model.Province;
import com.banguoi.model.User;
import com.banguoi.service.product.ProductService;
import com.banguoi.service.province.ProvinceService;
import com.banguoi.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@Transactional
public class WebController {

    @Autowired
    private UserService userService;

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

    @RequestMapping(value = {"/", "home"})
    public ModelAndView home(@RequestParam("product") Optional<String> product, Pageable pageable) {
        Page<Product> products;

        if (product.isPresent()) {
            products = productService.findAllByNameContaining(product.get(), pageable);
        } else {
            products = productService.findAll(pageable);
        }

        for (Product p : products) {
            for (Image i : p.getImages()) {
            }
        }

        ModelAndView modelAndView = new ModelAndView("/home");

        modelAndView.addObject("carousel", "carouselExampleIndicators");
        modelAndView.addObject("id", "#");
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @RequestMapping(value = "/user")
    public ModelAndView user(Pageable pageable) {
        User user = userService.findUserByEmail(getPrincipal());
        Page<Product> products;
        products = productService.findProductsByUser(user, pageable);
        ModelAndView modelAndView = new ModelAndView("/userPage");
        modelAndView.addObject("products", products);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/admin")
    public String admin() {
        ModelAndView modelAndView = new ModelAndView("/adminPage");

        String email = getPrincipal();
        User admin = userService.findUserByEmail(email);

        modelAndView.addObject("admin", admin);
        return "/adminPage";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "accessDenied";
    }
}