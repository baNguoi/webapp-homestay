package com.banguoi.controller.security_controller;

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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import java.util.Optional;

@Controller
@SessionAttributes("user")
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
    public String home(@RequestParam(value = "name", defaultValue = "") Optional<String> name,
                       @RequestParam(value = "province") Optional<Province> province,
                       @RequestParam(value = "bedroom", defaultValue = "") Optional<Integer> bedroom,
                       @RequestParam(value = "beds", defaultValue = "") Optional<Integer> beds,
                       @RequestParam(value = "guests", defaultValue = "") Optional<Integer> guests,
                       Pageable pageable, ModelMap model) {

        Page<Product> products;

        if (name.isPresent() && province.isPresent() && beds.isPresent() && bedroom.isPresent()) {
            if (bedroom.get() == 0 && beds.get() == 0 && guests.get() == 0) {
                products = productService.findAllByNameContainingAndProvince(name.get(), province.get(), pageable);
            } else if (bedroom.get() == 0 || beds.get() == 0 || guests.get() == 0) {
                products = productService.findAllByNameContainingAndProvinceAndBedroomOrBedsOrGuests(name.get(),
                        province.get(), bedroom.get(), beds.get(), guests.get(), pageable);
            } else {
                products = productService.findAllByNameContainingAndProvinceAndBedroomAndBedsAndGuests(name.get(),
                        province.get(), bedroom.get(), beds.get(), guests.get(), pageable);
            }
            model.addAttribute("products", products);
            return "/home.selected";
        } else {
            products = productService.findAll(pageable);
            model.addAttribute("products", products);
            return "/home";
        }
    }

    @RequestMapping(value = "/user")
    public ModelAndView user(Pageable pageable) {
        User user = userService.findUserByEmail(getPrincipal());
        Page<Product> products;
        products = productService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("homestay/userPage");
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
    public String login(@CookieValue(value = "setUser", defaultValue = "") String setUser, Model model) {
        Cookie cookie = new Cookie("setUser", setUser);
        model.addAttribute("cookieValue", cookie);
        return "login";
    }

    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "accessDenied";
    }
}
