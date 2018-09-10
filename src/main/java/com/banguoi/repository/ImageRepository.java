package com.banguoi.repository;

import com.banguoi.model.Image;
import com.banguoi.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {
    Iterable<Image> findAllByProduct(Product product);
}
