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
        Page<Product> products = productRepository.findProductsByUser(user, pageable);

        products.forEach(product -> Hibernate.initialize(product.getImages()));
        return products;
    }

    @Override
    public Product findById(Long id) {
        Product product = productRepository.findOne(id);
        Hibernate.initialize(product.getImages());
        return product;
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
    public Page<Product> findAllByNameContainingAndProvince(String name, Province province, Pageable pageable) {
        Page<Product> products = productRepository.findAllByNameContainingAndProvince(name, province, pageable);

        products.forEach(product -> Hibernate.initialize(product.getImages()));
        return products;
    }

    @Override
    public Page<Product> findAllByUserAndNameContainingAndProvince(User user, String name, Province province, Pageable pageable) {
        Page<Product> products = productRepository.findAllByUserAndNameContainingAndProvince(user, name, province, pageable);

        products.forEach(product -> Hibernate.initialize(product.getImages()));
        return products;
    }

    @Override
    public Page<Product> findAllByNameContainingAndProvinceAndBedroomOrBedsOrGuests(String name, Province province,
                                                                                    int bedroom, int beds,
                                                                                    int guests, Pageable pageable) {
        Page<Product> products =  productRepository.findAllByNameContainingAndProvinceAndBedroomOrBedsOrGuests(name, province, bedroom, beds, guests, pageable);

        products.forEach(product -> Hibernate.initialize(product.getImages()));
        return products;
    }

    @Override
    public Page<Product> findAllByPrice(double price, Pageable pageable) {
        return productRepository.findAllByPrice(price, pageable);
    }


    @Override
    public Page<Product> findAllByNameContainingAndProvinceAndBedroomAndBedsAndGuests(String name, Province province,
                                                                                      int bedroom, int beds,
                                                                                      int guests, Pageable pageable) {
        Page<Product> products = productRepository.findAllByNameContainingAndProvinceAndBedroomAndBedsAndGuests(name, province, bedroom, beds, guests, pageable);

        products.forEach(product -> Hibernate.initialize(product.getImages()));
        return products;
    }
}
