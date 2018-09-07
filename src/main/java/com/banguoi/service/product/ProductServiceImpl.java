package com.banguoi.service.product;

import com.banguoi.model.Product;
import com.banguoi.model.User;
import com.banguoi.repository.ProductRepository;
import com.banguoi.service.user.UserService;
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
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findProductByUser(User user, Pageable pageable) {
        return productRepository.findProductByUser(user, pageable);
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
    public void remove(Long id) {
        productRepository.delete(id);
    }
}
