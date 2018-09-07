package com.banguoi.repository;

import com.banguoi.model.Product;
import com.banguoi.model.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    Page<Product> findAllByProvince(Province province , Pageable pageable);
}
