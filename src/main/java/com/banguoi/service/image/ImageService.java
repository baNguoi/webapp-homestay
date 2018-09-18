package com.banguoi.service.image;

import com.banguoi.model.Image;
import com.banguoi.model.Product;

public interface ImageService {

    Iterable<Image> findAll();

    Iterable<Image> findAllByProduct(Product product);

    Image findById(Long id);

    void save(Image image);

    void remove(Long id);
}
