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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
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
        modelAndView.addObject("product", product);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/user/create")
    public ModelAndView createNewHomestay(@ModelAttribute("product") Product product) {
        User user = userService.findUserByEmail(getPrincipal());
        productService.save(product, user);

        ModelAndView modelAndView = new ModelAndView("/image/upload");
        modelAndView.addObject("product", product);
        modelAndView.addObject("message", "Product created compliment");
        return modelAndView;
    }
}
