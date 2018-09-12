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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
    public String singleFileUpload(@RequestParam("file") MultipartFile[] files, @RequestParam("id") Long id) {

        String UPLOADED_FOLDER = environment.getProperty("url.Image");
        String uploadRootPath = environment.getProperty("url.uploadRootDir");

        File uploadServer = new File(uploadRootPath);
        if (!uploadServer.exists()) {
            uploadServer.mkdir();
        }

        Product product = productService.findById(id);
        List<Image> images = product.getImages();

        String nameProduct = product.getName();
        nameProduct = nameProduct.replaceAll("\\s+", "");

        for (MultipartFile file : files) {
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
                File serverFile = new File(uploadServer.getAbsolutePath() + File.separator + image.getName());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

                byte[] bytes = file.getBytes();
                stream.write(bytes);
                stream.close();


                Path filePath = Paths.get(UPLOADED_FOLDER + File.separator + image.getName());
                Files.write(filePath, bytes);

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