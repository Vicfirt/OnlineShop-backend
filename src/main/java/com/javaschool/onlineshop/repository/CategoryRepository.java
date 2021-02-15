package com.javaschool.onlineshop.repository;

import com.javaschool.onlineshop.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
