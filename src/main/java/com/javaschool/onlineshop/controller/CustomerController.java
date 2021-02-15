package com.javaschool.onlineshop.controller;

import com.javaschool.onlineshop.exception.EmailExistsException;
import com.javaschool.onlineshop.exception.FieldInputException;
import com.javaschool.onlineshop.model.dto.CustomerDTO;
import com.javaschool.onlineshop.security.CustomUserPrincipal;
import com.javaschool.onlineshop.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> toSignUp(@Valid @RequestBody CustomerDTO customerDTO,
                                           BindingResult bindingResult) {
        CustomerDTO customerExists = customerService.getByUsername(customerDTO.getCustomerEmailAddress());
        if (customerExists != null) {
            throw new EmailExistsException("Email with email: " + customerExists.getCustomerEmailAddress() + " already exists!");
        }
        if (bindingResult.hasErrors()) {
            throw new FieldInputException(bindingResult, "Validation error!");
        }
        customerService.addCustomer(customerDTO);
        return ResponseEntity.ok("User has been registered!");
    }

    @GetMapping("/customer")
    public ResponseEntity<CustomerDTO> getCustomer(@AuthenticationPrincipal CustomUserPrincipal customer) {
        return ResponseEntity.ok(customerService.getCustomer(customer.getUsername()));
    }

    @PutMapping("/customer")
    public ResponseEntity<String> editCustomer(@AuthenticationPrincipal CustomUserPrincipal customer,
                                               @Valid @RequestBody CustomerDTO updatedCustomer,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new FieldInputException(bindingResult, "Validation error!");
        }
        customerService.updateCustomer(customer.getUsername(), updatedCustomer);
        return ResponseEntity.ok("User has been updated!");
    }
}
