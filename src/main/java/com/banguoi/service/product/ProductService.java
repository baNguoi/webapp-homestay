package com.banguoi.service.product;

import com.banguoi.model.Product;
import com.banguoi.model.User;
import com.banguoi.model.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductService {

    Page<Product> findAll(Pageable pageable);

    Page<Product> findProductsByUser(User user, Pageable pageable);

    Product findById(Long id);

    void save(Product product, String email);

    void save(Product product, User user);

    void remove(Long id);

    Page<Product> findAllByProvince(Province province, Pageable pageable);

    Page<Product> findAllByNameContaining(String name, Pageable pageable);

    Page<Product> findAllByBedroom(int bedroom, Pageable pageable);

    Page<Product> findAllByPrice(double price, Pageable pageable);
}
