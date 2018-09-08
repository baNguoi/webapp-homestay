package com.banguoi.controller.model_controller;

import com.banguoi.model.Product;
import com.banguoi.model.Province;
import com.banguoi.service.product.ProductService;
import com.banguoi.service.province.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ProvinceController {

    @Autowired
    ProvinceService provinceService;

    @Autowired
    ProductService productService;

    @GetMapping("/view-province/{id}")
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
}
