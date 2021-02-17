package com.javaschool.onlineshop.service;

import com.javaschool.onlineshop.model.dto.CustomerDTO;

import java.util.Map;

public interface CustomerService {

    Map<String, Object> login(String email);

    CustomerDTO addCustomer(CustomerDTO customerDTO);

    CustomerDTO getByUsername(String username);

    void updateCustomer(String customerName,CustomerDTO customerDTO);
}
