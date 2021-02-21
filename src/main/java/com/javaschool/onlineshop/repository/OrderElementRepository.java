package com.javaschool.onlineshop.repository;

import com.javaschool.onlineshop.model.entity.OrderElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderElementRepository extends JpaRepository<OrderElement, Long> {
}
