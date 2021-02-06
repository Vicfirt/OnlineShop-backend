package com.javaschool.onlineshop.controller;

import com.javaschool.onlineshop.exception.EmailExistsException;
import com.javaschool.onlineshop.exception.FieldInputError;
import com.javaschool.onlineshop.model.dto.CustomerDTO;
import com.javaschool.onlineshop.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registration(@Valid @RequestBody CustomerDTO customerDTO,
                                               BindingResult bindingResult) {
        CustomerDTO customerExists = customerService.getByUsername(customerDTO.getCustomerEmailAddress());
        if (customerExists != null) {
            throw new EmailExistsException("Email with email: " + customerExists.getCustomerEmailAddress() + " already exists!");
        }
        if (bindingResult.hasErrors()) {
            throw new FieldInputError(bindingResult, "Validation error!");
        }
        customerService.addCustomer(customerDTO);
        return ResponseEntity.ok("User has been registered!");

    }

}
