package com.javaschool.onlineshop.security;

import com.javaschool.onlineshop.model.entity.Customer;
import com.javaschool.onlineshop.repository.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public UserDetailsServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Customer customer = customerRepository.findCustomerByCustomerEmailAddress(username);
        if (customer == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserPrincipal(customer);
    }
}
