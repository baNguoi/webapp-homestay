package com.banguoi.service.image;

import com.banguoi.model.Image;
import com.banguoi.model.Product;
import com.banguoi.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Iterable<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Iterable<Image> findImageByProduct(Product product) {
        return imageRepository.findImageByProduct(product);
    }

    @Override
    public Image findById(Long id) {
        return imageRepository.findOne(id);
    }

    @Override
    public void save(Image image) {
        imageRepository.save(image);
    }

    @Override
    public void remove(Long id) {
        imageRepository.delete(id);
    }
}
