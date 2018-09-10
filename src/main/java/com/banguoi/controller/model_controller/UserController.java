package com.banguoi.controller.model_controller;

import com.banguoi.model.Image;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Transactional
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ProductService productService;

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
    public ModelAndView listPostHomestay(Pageable pageable) {
        User user = userService.findUserByEmail(getPrincipal());
        Page<Product> products = productService.findProductsByUser(user, pageable);

        for (Product p : products) {
            for (Image i : p.getImages()) {
            }
        }

        ModelAndView modelAndView = new ModelAndView("/homestay/list");
        modelAndView.addObject("carousel", "carouselExampleIndicators");
        modelAndView.addObject("id", "#");
        modelAndView.addObject("products", products);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/user/create")
    public ModelAndView showCreateHomestayForm() {
        User user = userService.findUserByEmail(getPrincipal());
        Product product = new Product();
        ModelAndView modelAndView = new ModelAndView("/homestay/create");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
