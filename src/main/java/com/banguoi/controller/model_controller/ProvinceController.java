package com.banguoi.controller.model_controller;

import com.banguoi.model.Province;
import com.banguoi.service.province.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.banguoi.model.Product;
import com.banguoi.service.product.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProvinceController {

    @Autowired
    ProvinceService provinceService;

    @Autowired
    ProductService productService;

    @GetMapping("/provinces/view/{id}")
    public ModelAndView viewProvince(@PathVariable("id") Long id, Pageable pageable) {
        Province province = provinceService.findById(id);
        if (province == null) {
            return new ModelAndView("/error.404");
        }

        Page<Product> products = productService.findAllByProvince(province, pageable);
        ModelAndView modelAndView = new ModelAndView("/province/view");
        modelAndView.addObject("products", products);
        modelAndView.addObject("province", province);
        return modelAndView;
    }

    @GetMapping("/provinces")
    public ModelAndView showListProvince() {
        Iterable<Province> provinces = provinceService.findAll();

        ModelAndView modelAndView = new ModelAndView("/province/list");
        modelAndView.addObject("provinces", provinces);
        return modelAndView;
    }

    @GetMapping("/provinces/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/province/create");
        modelAndView.addObject("province", new Province());
        return modelAndView;
    }

    @PostMapping("/provinces/create")
    public ModelAndView createProvince(@ModelAttribute("province") Province province) {
        provinceService.save(province);

        ModelAndView modelAndView = new ModelAndView("/province/create");
        modelAndView.addObject("province", province);
        modelAndView.addObject("message", "created product compliment");
        return modelAndView;
    }
}
