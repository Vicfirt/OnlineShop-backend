package com.javaschool.onlineshop.service;

import com.javaschool.onlineshop.model.dto.CustomerDTO;
import com.javaschool.onlineshop.security.authData.JwtResponse;

public interface CustomerService {

    JwtResponse login(String email);

    CustomerDTO addCustomer(CustomerDTO customerDTO);

    CustomerDTO getByUsername(String username);

    void updateCustomer(String customerName, CustomerDTO customerDTO);
}
