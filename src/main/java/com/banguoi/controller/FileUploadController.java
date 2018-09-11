package com.banguoi.controller;

import com.banguoi.model.Image;
import com.banguoi.model.Product;
import com.banguoi.service.image.ImageService;
import com.banguoi.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@Transactional
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
    public String singleFileUpload(@RequestParam("file") CommonsMultipartFile[] files, @RequestParam("id") Long id,
                                   Model model) {

        String UPLOADED_FOLDER = environment.getProperty("url.Image");
        String uploadRootPath = environment.getProperty("url.uploadRootDir");

        File uploadRootDir = new File(uploadRootPath);
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdir();
        }

        Product product = productService.findById(id);
        List<Image> images = product.getImages();

        String nameProduct = product.getName();
        nameProduct = nameProduct.replaceAll("\\s+", "");

        for (CommonsMultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }

            Image image = new Image();

            int idA = 1;
            if (images.size() > 0) {
                for (Image i : images) {
                    if (i.getName().equals(nameProduct + "(" + idA + ")" + ".jpg")) {
                        idA++;
                    } else {
                        image.setName(nameProduct + "(" + idA + ")" + ".jpg");
                        break;
                    }
                }
            } else {
                image.setName(nameProduct + "(" + idA + ")" + ".jpg");
            }

            if (image.getName() == null) {
                image.setName(nameProduct + "(" + idA + ")" + ".jpg");
            }

            image.setProduct(product);
            imageService.save(image);

            images.add(image);

            try {
                File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + image.getName());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

                stream.write(file.getBytes());
                stream.close();

                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + image.getName());
                Files.write(path, bytes);

                model.addAttribute("message", "You uploaded successfully");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("Error Write file:" + image.getName());
            }
        }

        product.setImages(images);
        return "redirect:/user/manager";
    }
}
