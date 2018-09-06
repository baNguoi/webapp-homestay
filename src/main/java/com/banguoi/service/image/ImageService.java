package com.banguoi.service.image;

import com.banguoi.model.Image;

public interface ImageService {

    Iterable<Image> findAll();

    Image findById(Long id);

    void save(Image image);

    void remove(Long id);
}
