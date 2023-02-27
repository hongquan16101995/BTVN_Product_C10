package com.example.product.repository;

import com.example.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "select p from Product p where p.name like :name")
    Page<Product> findName(@Param("name") String name, Pageable pageable);
}
