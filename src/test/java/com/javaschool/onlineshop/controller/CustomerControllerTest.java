package com.javaschool.onlineshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaschool.onlineshop.model.dto.CustomerDTO;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create_customer_before_test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create_customer_after_test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void toSignUp() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerEmailAddress("petr@mail.com");
        customerDTO.setCustomerPassword("12345678");
        customerDTO.setCustomerFirstName("Petr");
        customerDTO.setCustomerLastName("Petrov");
        customerDTO.setActive(true);
        customerDTO.setBuilding("123");
        customerDTO.setCity("Moscow");
        customerDTO.setCountry("Russia");
        customerDTO.setCustomerDateOfBirth(LocalDate.now());
        customerDTO.setPhoneNumber("89930491736");
        customerDTO.setPostcode("908374");
        customerDTO.setStreet("Some street");
        customerDTO.setRole("CUSTOMER");
        customerDTO.setRoom("123");
        mockMvc.perform(post("/signup")
                .content(objectMapper.writeValueAsString(customerDTO))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.equalTo("User has been registered!")));
    }

    public void toSignUpWithEmptyFields() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        mockMvc.perform(post("/signup")
                .content(objectMapper.writeValueAsString(customerDTO))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void signUpCustomerExists() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerEmailAddress("john@mail.com");
        customerDTO.setCustomerPassword("12345678");
        customerDTO.setCustomerFirstName("John");
        customerDTO.setCustomerLastName("Johnson");
        customerDTO.setActive(true);
        customerDTO.setBuilding("123");
        customerDTO.setCity("Saint Petersburg");
        customerDTO.setCountry("Russia");
        customerDTO.setCustomerDateOfBirth(LocalDate.now());
        customerDTO.setPhoneNumber("89930491736");
        customerDTO.setPostcode("908374");
        customerDTO.setStreet("Some street");
        customerDTO.setRole("CUSTOMER");
        customerDTO.setRoom("123");
        mockMvc.perform(post("/signup")
                .content(objectMapper.writeValueAsString(customerDTO))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails("john@mail.com")
    public void getCustomer() throws Exception {
        mockMvc.perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", Matchers.equalTo(11)))
                .andExpect(jsonPath("$.city", Matchers.equalTo("Moscow")))
                .andExpect(jsonPath("$.country", Matchers.equalTo("Russia")))
                .andExpect(jsonPath("$.customerFirstName", Matchers.equalTo("John")));
    }

    @Test
    public void getCustomerWithoutPermission() throws Exception {
        mockMvc.perform(get("/customer"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("john@mail.com")
    public void editCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerEmailAddress("ivanov@mail.ru");
        customerDTO.setCustomerPassword("12345678");
        customerDTO.setCustomerFirstName("Pavel");
        customerDTO.setCustomerLastName("Ivanov");
        customerDTO.setActive(true);
        customerDTO.setBuilding("123");
        customerDTO.setCity("New York");
        customerDTO.setCountry("USA");
        customerDTO.setCustomerDateOfBirth(LocalDate.now());
        customerDTO.setPhoneNumber("89930491736");
        customerDTO.setPostcode("908374");
        customerDTO.setStreet("Some street");
        customerDTO.setRole("CUSTOMER");
        customerDTO.setRoom("123");
        mockMvc.perform(put("/customer")
                .content(objectMapper.writeValueAsString(customerDTO))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.equalTo("User has been updated!")));

    }
}