package com.javaschool.onlineshop.service.impl;

import com.javaschool.onlineshop.mappers.CustomerMapper;
import com.javaschool.onlineshop.model.dto.CustomerDTO;
import com.javaschool.onlineshop.model.entity.Customer;
import com.javaschool.onlineshop.repository.CustomerRepository;
import com.javaschool.onlineshop.security.jwt.JwtProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerServiceImplTest {

    @Autowired
    CustomerServiceImpl customerService;

    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    CustomerMapper customerMapper;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    JwtProvider jwtProvider;

    @Test
    public void getByUsername() {
        CustomerDTO customer = new CustomerDTO();
        customer.setCustomerEmailAddress("vasya@mail.ru");
        customerService.getByUsername("vasya@mail.ru");
        Mockito.when(customerMapper.customerToCustomerDTO(customerRepository
                .findCustomerByCustomerEmailAddress("vasya@mail.ru")))
                .thenReturn(customer);
        assertEquals("vasya@mail.ru", customer.getCustomerEmailAddress());
        Mockito.verify(customerRepository, Mockito.atLeast(1))
                .findCustomerByCustomerEmailAddress("vasya@mail.ru");
    }


    @Test
    public void updateCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        Customer customer = new Customer();
        customerDTO.setActive(true);
        customerDTO.setCustomerPassword("password");
        customerDTO.setPhoneNumber("89990384756");
        customerDTO.setCustomerDateOfBirth(LocalDate.of(1997, 2, 15));
        customerDTO.setCustomerLastName("Ivanov");
        customerDTO.setCustomerFirstName("Ivan");
        customerDTO.setBuilding("22");
        customerDTO.setRoom("17");
        customerDTO.setRole("CUSTOMER");
        customerDTO.setCountry("Russia");
        customerDTO.setCity("Moscow");
        customerDTO.setStreet("Sadovaya");
        customerDTO.setPostcode("098364");
        Mockito.when(customerRepository.findCustomerByCustomerEmailAddress("ivan@mail.com")).thenReturn(customer);
        Mockito.when(passwordEncoder.encode("password")).thenReturn(customerDTO.getCustomerPassword());
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);
        customerService.updateCustomer("ivan@mail.com", customerDTO);
        assertEquals(customer.getActive(), customerDTO.getActive());
        assertEquals(customer.getCustomerPassword(), customerDTO.getCustomerPassword());
        assertEquals(customer.getPhoneNumber(), customerDTO.getPhoneNumber());
        assertEquals(customer.getCustomerDateOfBirth(), customerDTO.getCustomerDateOfBirth());
        assertEquals(customer.getCustomerLastName(), customerDTO.getCustomerLastName());
        assertEquals(customer.getCustomerFirstName(), customerDTO.getCustomerFirstName());
        assertEquals(customer.getBuilding(), customerDTO.getBuilding());
        assertEquals(customer.getRoom(), customerDTO.getRoom());
        assertEquals(customer.getRole(), customerDTO.getRole());
        assertEquals(customer.getCountry(), customerDTO.getCountry());
        assertEquals(customer.getCity(), customerDTO.getCity());
        assertEquals(customer.getStreet(), customerDTO.getStreet());
        assertEquals(customer.getPostcode(), customerDTO.getPostcode());
        Mockito.verify(passwordEncoder, Mockito.atLeast(1)).encode("password");
        Mockito.verify(customerRepository, Mockito.atLeast(1)).save(customer);
    }

    @Test
    public void login() {
        Customer customer = new Customer();
        customer.setCustomerEmailAddress("petr@mail.com");
        customer.setRole("CUSTOMER");
        Mockito.when(customerRepository.findCustomerByCustomerEmailAddress("petr@mail.com")).thenReturn(customer);
        customerService.login("petr@mail.com");
        Mockito.verify(customerRepository, Mockito.atLeast(1))
                .findCustomerByCustomerEmailAddress("petr@mail.com");
        Mockito.verify(jwtProvider, Mockito.atLeast(1)).createToken("petr@mail.com", "CUSTOMER");

    }

    @Test
    public void addCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        Customer customer = new Customer();
        customerDTO.setCustomerPassword("password");
        Mockito.when(customerMapper.customerDTOToCustomer(customerDTO)).thenReturn(customer);
        Mockito.when(passwordEncoder.encode("password")).thenReturn(customerDTO.getCustomerPassword());
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);
        customerService.addCustomer(customerDTO);
        assertEquals(customerDTO.getCustomerPassword(), customer.getCustomerPassword());
        Mockito.verify(passwordEncoder, Mockito.atLeast(1)).encode(customerDTO.getCustomerPassword());
        Mockito.verify(customerRepository, Mockito.atLeast(1)).save(customer);
    }
}