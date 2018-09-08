package com.banguoi.controller;

import com.banguoi.model.Image;
import com.banguoi.model.Product;
import com.banguoi.service.image.ImageService;
import com.banguoi.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileUploadController {

    @Autowired
    private Environment environment;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ProductService productService;

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSizePerFile(10000000);
        return multipartResolver;
    }

    @PostMapping("/products/uploadImage")
    public String singleFileUpload(@RequestParam("file") MultipartFile[] files,@RequestParam("id") Long id, Model model) {
        String UPLOADED_FOLDER = environment.getProperty("url.Image");

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }

            Product product = productService.findById(id);

            int idA = 1;
            Iterable<Image> images = imageService.findAllByProduct(product);
            for (Image im : images ) {
                idA++;
            }

            Image image = new Image();
            image.setName(product.getName() + "(" + idA + ")" + ".jpg");
            image.setProduct(product);

            imageService.save(image);

            System.out.println(image.getName());
            System.out.println(image.getProduct().getName());

            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + image.getName());
                Files.write(path, bytes);

                model.addAttribute("message", "You uploaded successfully");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "/image/uploadStatus";
    }
}
