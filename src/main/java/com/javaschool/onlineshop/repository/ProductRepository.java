package com.javaschool.onlineshop.repository;

import com.javaschool.onlineshop.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByProductBrandAndIsActiveTrue(String brandName);

    List<Product> findAllByIsActiveTrue();

    List<Product> findAllByCategoryIdAndIsActiveTrue(Integer categoryId);

    List<Product> findAllByProductNameAndIsActiveTrue(String productName);

    List<Product> findByProductIdIn(List<Long> productIdList);
}
