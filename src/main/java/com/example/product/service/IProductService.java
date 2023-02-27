package com.example.product.service;

import com.example.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {

    Page<Product> findAll(String name, Pageable pageable);

    Product findOne(Long id);

    void save(Product product);

    void delete(Long id);
}
