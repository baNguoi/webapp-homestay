package com.banguoi.controller.model_controller;

import com.banguoi.model.Product;
import com.banguoi.model.Province;
import com.banguoi.service.product.ProductService;
import com.banguoi.service.province.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {
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

    @GetMapping("/products")
    public ModelAndView showListProduct(Pageable pageable) {
        Page<Product> products = productService.findAll(pageable);

        ModelAndView modelAndView = new ModelAndView("/product/list");

        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/products/create")
    public ModelAndView showFormCreate() {

        Product product = new Product();
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @PostMapping("/products/create")
    public ModelAndView createProduct(@ModelAttribute("product") Product product) {
        String email = getPrincipal();
        productService.save(product, email);

        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", product);
        modelAndView.addObject("message", "Product created compliment");
        return modelAndView;
    }

    @GetMapping("/products/detail/{id}")
    public ModelAndView detailProduct(@PathVariable("id") Long id) {
        Product product = productService.findById(id);

        if (product == null) {
            return new ModelAndView("/accessDenied");
        }

        ModelAndView modelAndView = new ModelAndView("/product/detail");
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @GetMapping("/products/uploadImage/{id}")
    public ModelAndView showFormUpload(@PathVariable("id") Long id) {
        Product product = productService.findById(id);

        if (product == null) {
            return new ModelAndView("/accessDenied");
        }

        ModelAndView modelAndView = new ModelAndView("/image/upload");
        modelAndView.addObject("product", product);
        return modelAndView;
    }
}
