package com.javaschool.onlineshop.service.impl;

import com.javaschool.onlineshop.mappers.CustomerMapper;
import com.javaschool.onlineshop.model.dto.CustomerDTO;
import com.javaschool.onlineshop.security.authData.JwtResponse;
import com.javaschool.onlineshop.model.entity.Customer;
import com.javaschool.onlineshop.repository.CustomerRepository;
import com.javaschool.onlineshop.security.jwt.JwtProvider;
import com.javaschool.onlineshop.service.CustomerService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper,
                               PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public JwtResponse login(String email) {
        Customer customer = customerRepository.findCustomerByCustomerEmailAddress(email);
        String role = customer.getRole();
        String token = jwtProvider.createToken(email, role);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setEmail(email);
        jwtResponse.setToken(token);
        jwtResponse.setRole(role);
        return jwtResponse;
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
    public void updateCustomer(String customerName, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findCustomerByCustomerEmailAddress(customerName);
        customer.setActive(customerDTO.getActive());
        customer.setCustomerPassword(passwordEncoder.encode(customerDTO.getCustomerPassword()));
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setCustomerDateOfBirth(customerDTO.getCustomerDateOfBirth());
        customer.setCustomerLastName(customerDTO.getCustomerLastName());
        customer.setCustomerFirstName(customerDTO.getCustomerFirstName());
        customer.setBuilding(customerDTO.getBuilding());
        customer.setRoom(customerDTO.getRoom());
        customer.setRole(customerDTO.getRole());
        customer.setCountry(customerDTO.getCountry());
        customer.setCity(customerDTO.getCity());
        customer.setStreet(customerDTO.getStreet());
        customer.setPostcode(customerDTO.getPostcode());
        customerRepository.save(customer);
    }
}
