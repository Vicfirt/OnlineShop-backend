package com.javaschool.onlineshop.repository;

import com.javaschool.onlineshop.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findCustomerByCustomerEmailAddress(String username);
}
