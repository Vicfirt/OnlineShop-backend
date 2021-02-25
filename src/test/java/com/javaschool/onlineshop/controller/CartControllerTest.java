package com.javaschool.onlineshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create_products_before_test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create_products_after_tests.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getCart() throws Exception {
        List<Long> productsIdList = new ArrayList<>();
        productsIdList.add(1L);
        productsIdList.add(4L);

        mockMvc.perform(post("/cart")
                .content(objectMapper.writeValueAsString(productsIdList))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].productId").isNotEmpty())
                .andExpect(jsonPath("$[*].productName").isNotEmpty())
                .andExpect(jsonPath("$[*].productPrice").isNotEmpty())
                .andExpect(jsonPath("$[*].category").isNotEmpty())
                .andExpect(jsonPath("$[*].productBrand").isNotEmpty())
                .andExpect(jsonPath("$[*].productModel").isNotEmpty())
                .andExpect(jsonPath("$[*].productWeight").isNotEmpty())
                .andExpect(jsonPath("$[*].productCapacity").isNotEmpty())
                .andExpect(jsonPath("$[*].amountInStock").isNotEmpty())
                .andExpect(jsonPath("$[*].productDescription").isNotEmpty())
                .andExpect(jsonPath("$[*].productImage").isNotEmpty());
    }
}