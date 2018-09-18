package com.banguoi.repository;

import com.banguoi.model.Product;
import com.banguoi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.banguoi.model.Province;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    Page<Product> findAllByProvince(Province province, Pageable pageable);

    Page<Product> findAllByNameContainingAndProvince(String name, Province province, Pageable pageable);

    Page<Product> findAllByUserAndNameContainingAndProvince(User user, String name, Province province, Pageable pageable);

    Page<Product> findAllByNameContainingAndProvinceAndBedroomOrBedsOrGuests(String name,
                                                                             Province province,
                                                                             int bedroom, int beds,
                                                                             int guests, Pageable pageable);

    Page<Product> findAllByPrice(double price, Pageable pageable);

    Page<Product> findProductsByUser(User user, Pageable pageable);

    Page<Product> findAllByNameContainingAndProvinceAndBedroomAndBedsAndGuests(String name,
                                                                               Province province,
                                                                               int bedroom, int beds,
                                                                               int guests, Pageable pageable);
}
