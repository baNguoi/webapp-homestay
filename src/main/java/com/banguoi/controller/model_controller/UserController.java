package com.banguoi.controller.model_controller;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

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

    @RequestMapping(value = "/user/managerHomestays", method = RequestMethod.GET)
    public ModelAndView listPostHomestay(Pageable pageable) {
        User user = userService.findUserByEmail(getPrincipal());
        Page<Product> products = productService.findProductsByUser(user, pageable);

        ModelAndView displayListHomestaysModelAndView = new ModelAndView("/homestay/list");
        if (products.getTotalElements() == 0) {
            String message = "You don't have any homestay!";
            displayListHomestaysModelAndView.addObject("message", message);
        }
        displayListHomestaysModelAndView.addObject("products", products);
        displayListHomestaysModelAndView.addObject("user", user);
        return displayListHomestaysModelAndView;
    }

    @GetMapping("/user/createHomestay")
    public ModelAndView showCreateHomestayForm() {
        User user = userService.findUserByEmail(getPrincipal());
        Product product = new Product();

        ModelAndView creatHomestayModelAndView = new ModelAndView("/homestay/create");
        creatHomestayModelAndView.addObject("product", product);
        creatHomestayModelAndView.addObject("user", user);
        return creatHomestayModelAndView;
    }

    @PostMapping("/user/createHomestay")
    public ModelAndView createNewHomestay(@ModelAttribute("product") Product product) {
        User user = userService.findUserByEmail(getPrincipal());
        productService.save(product, user);

        ModelAndView modelAndView = new ModelAndView("/image/upload");
        modelAndView.addObject("product", product);
        modelAndView.addObject("message", "Product created successfully");
        return modelAndView;
    }
}
