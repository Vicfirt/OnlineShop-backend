package com.javaschool.onlineshop.controller;

import com.javaschool.onlineshop.exception.EmailExistsException;
import com.javaschool.onlineshop.exception.FieldInputException;
import com.javaschool.onlineshop.model.dto.CustomerDTO;
import com.javaschool.onlineshop.repository.OrderRepository;
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

/**
 * This class handles requests related to registration and editing a profile
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * This method processes the request for registration of a new user.
     * @param customerDTO            customer to be registered
     * @param bindingResult          used for form validation
     * @return                       response informing about successful registration
     */
    @PostMapping("/signup")
    public ResponseEntity<String> toSignUp(@Valid @RequestBody CustomerDTO customerDTO,
                                           BindingResult bindingResult) {
        CustomerDTO customerExists = customerService.getByUsername(customerDTO.getCustomerEmailAddress());
        if (customerExists != null) {
            throw new EmailExistsException("User with email: " + customerExists.getCustomerEmailAddress() + " already exists!");
        }
        if (bindingResult.hasErrors()) {
            throw new FieldInputException(bindingResult, "Validation error!");
        }
        customerService.addCustomer(customerDTO);
        return ResponseEntity.ok("User has been registered!");
    }

    /**
     * This method processes a request for information about a previously registered customer.
     * @param customer              customer to be authenticated
     * @return                      response with information about authenticated customer
     */
    @GetMapping("/customer")
    public ResponseEntity<CustomerDTO> getCustomer(@AuthenticationPrincipal CustomUserPrincipal customer) {
        return ResponseEntity.ok(customerService.getByUsername(customer.getUsername()));
    }

    /**
     * This method processes a request to update customer information.
     * @param customer              customer to be authenticated
     * @param updatedCustomer       it contains data necessary for updating customer
     * @param bindingResult         used for form validation
     * @return
     */
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
