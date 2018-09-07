package com.banguoi.service.product;

import com.banguoi.model.Product;
import com.banguoi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<Product> findAll(Pageable pageable);

    Page<Product> findProductsByUser(User user, Pageable pageable);

    Product findById(Long id);

    void save(Product product);

    void remove(Long id);
}
