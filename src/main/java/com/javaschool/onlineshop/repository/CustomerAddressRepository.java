package com.javaschool.onlineshop.repository;

import com.javaschool.onlineshop.model.entity.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {
}
