package com.banguoi.controller.model_controller;

import com.banguoi.model.Image;
import com.banguoi.service.image.ImageService;
import com.banguoi.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/images", method = RequestMethod.GET)
    public String listImage(Model model) {
        Iterable<Image> images = imageService.findAll();
        model.addAttribute("images", images);
        return "/image/list";
    }

    @RequestMapping(value = "/images/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable("id") Long id, @RequestParam("pId") Long pId) {
        Image image = imageService.findById(id);
        imageService.remove(image.getId());
        return "redirect:/user/manager";
    }
}
