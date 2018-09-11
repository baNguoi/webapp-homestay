package com.banguoi.service.product;

import com.banguoi.model.Product;
import com.banguoi.model.Province;
import com.banguoi.model.User;
import com.banguoi.repository.ProductRepository;
import com.banguoi.service.user.UserService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);

        products.forEach(product -> Hibernate.initialize(product.getImages()));
        return products;
    }

    @Override
    public Page<Product> findProductsByUser(User user, Pageable pageable) {
        return productRepository.findProductsByUser(user, pageable);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findOne(id);
    }

    @Override
    public void save(Product product, String email) {
        User user = userService.findUserByEmail(email);
        product.setUser(user);
        productRepository.save(product);
    }

    @Override
    public void save(Product product, User user) {
        product.setUser(user);
        productRepository.save(product);
    }

    @Override
    public void remove(Long id) {
        productRepository.delete(id);
    }

    @Override
    public Page<Product> findAllByProvince(Province province, Pageable pageable) {
        Page<Product> products = productRepository.findAllByProvince(province, pageable);

        products.forEach(product -> Hibernate.initialize(product.getImages()));
        return products;
    }

    @Override
    public Page<Product> findAllByNameContaining(String name, Pageable pageable) {
        Page<Product> products = productRepository.findAllByNameContaining(name, pageable);

        products.forEach(product -> Hibernate.initialize(product.getImages()));
        return products;
    }

    @Override
    public Page<Product> findAllByBedroom(int bedroom, Pageable pageable) {
        return productRepository.findAllByBedroom(bedroom, pageable);
    }

    @Override
    public Page<Product> findAllByPrice(double price, Pageable pageable) {
        return productRepository.findAllByPrice(price, pageable);
    }
}
