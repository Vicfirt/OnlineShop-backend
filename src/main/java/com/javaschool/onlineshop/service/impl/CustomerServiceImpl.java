package com.javaschool.onlineshop.service.impl;

import com.javaschool.onlineshop.mappers.CustomerMapper;
import com.javaschool.onlineshop.model.dto.CustomerDTO;
import com.javaschool.onlineshop.model.entity.Customer;
import com.javaschool.onlineshop.repository.CustomerRepository;
import com.javaschool.onlineshop.security.JwtProvider;
import com.javaschool.onlineshop.service.CustomerService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public Map<String, Object> login(String email) {
        Customer customer = customerRepository.findCustomerByCustomerEmailAddress(email);
        String role = customer.getRole();
        String token = jwtProvider.createToken(email, role);
        Map<String, Object> response = new HashMap<>();
        response.put("email", email);
        response.put("token", token);
        response.put("role", role);
        return response;
    }

    @Override
    public CustomerDTO getCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepository.findCustomerByCustomerEmailAddress(authentication.getName());
        return customerMapper.customerToCustomerDTO(customer);
    }

    @Override
    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setCustomerPassword(passwordEncoder.encode(customerDTO.getCustomerPassword()));
        customerRepository.save(customer);
        return customerDTO;
    }

    @Override
    public CustomerDTO getByUsername(String username) {
        return customerMapper.customerToCustomerDTO(customerRepository.findCustomerByCustomerEmailAddress(username));
    }

    @Override
    public void updateCustomer(CustomerDTO customerDTO, Principal principal) {

    }

    @Override
    public CustomerDTO getById(Long id) {
        return null;
    }
}
