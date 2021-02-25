package com.javaschool.onlineshop.repository;

import com.javaschool.onlineshop.model.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByIsActiveTrue();

    List<Product> findByProductIdIn(List<Long> productIdList);

    @Query(value = "SELECT p FROM Product p where p.category in :categoryList")
    List<Product> findByCategories(@Param("categoryList") List<String> categoriesToFilter);

    @Query(value = "SELECT p FROM Product p where p.productBrand in :brandList")
    List<Product> findByBrands(@Param("brandList") List<String> brandsToFilter);

    @Query(value = "SELECT p FROM Product p where p.productBrand in :brandList AND p.category in :categoryList")
    List<Product> findByBrandsAndCategories(@Param("brandList") List<String> brandsToFilter,
                                            @Param("categoryList") List<String> categoriesToFilter);

    @Query(value = "SELECT oel.product FROM OrderElement oel GROUP BY oel.product.productId " +
            "ORDER BY SUM(oel.elementPrice) DESC ")
    List<Product> findTop(Pageable pageable);

}
