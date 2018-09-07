package com.banguoi.repository;

import com.banguoi.model.Product;
import com.banguoi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    Page<Product> findProductByUser(User user, Pageable pageable);
}
