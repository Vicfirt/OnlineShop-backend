package com.javaschool.onlineshop.service;

import com.javaschool.onlineshop.model.dto.CustomerDTO;

import java.security.Principal;
import java.util.Map;

public interface CustomerService {

    Map<String, Object> login(String email);

    CustomerDTO getCustomer();

    CustomerDTO addCustomer(CustomerDTO customerDTO);

    CustomerDTO getByUsername(String username);

    void updateCustomer(CustomerDTO customerDTO, Principal principal);

    CustomerDTO getById(Long id);
}
