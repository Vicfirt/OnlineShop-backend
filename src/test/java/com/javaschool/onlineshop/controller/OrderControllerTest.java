package com.javaschool.onlineshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaschool.onlineshop.model.dto.OrderObjectDTO;
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
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create_products_before_test.sql", "/sql/create_orders_before_test.sql",
        "/sql/create_customer_before_test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create_orders_after_test.sql", "/sql/create_products_after_tests.sql",
        "/sql/create_customer_after_test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addOrder() throws Exception {
        Map<Long, Long> productsInfo = new HashMap<>();
        productsInfo.put(1L,3L);
        productsInfo.put(4L,1L);
        OrderObjectDTO orderObjectDTO = new OrderObjectDTO();
        orderObjectDTO.setProductInformation(productsInfo);
        orderObjectDTO.setTotal(200.0);
        orderObjectDTO.setBuilding("12");
        orderObjectDTO.setCity("Moscow");
        orderObjectDTO.setCustomerEmailAddress("random@mail.com");
        orderObjectDTO.setCountry("Russia");
        orderObjectDTO.setRoom("999");
        orderObjectDTO.setCustomerFirstName("Ivan");
        orderObjectDTO.setCustomerLastName("Ivanov");
        orderObjectDTO.setDate(LocalDate.now());
        orderObjectDTO.setPaymentMethod("Card");
        orderObjectDTO.setShippingType("Courier");
        orderObjectDTO.setStreet("any street");
        orderObjectDTO.setPostcode(198376);
        orderObjectDTO.setStatus("Accepted");
        mockMvc.perform(post("/order")
                .content(objectMapper.writeValueAsString(orderObjectDTO))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void postOrderBadReq() throws Exception {
        OrderObjectDTO orderObjectDTO = new OrderObjectDTO();
        Map<Long, Long> productsInfo = new HashMap<>();
        orderObjectDTO.setProductInformation(productsInfo);
        mockMvc.perform(post("/order")
                .content(objectMapper.writeValueAsString(orderObjectDTO))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails("john@mail.com")
    public void getAllCustomerOrders() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].orderId").isNotEmpty())
                .andExpect(jsonPath("$[*].total").isNotEmpty())
                .andExpect(jsonPath("$[*].customerEmailAddress").value("john@mail.com"))
                .andExpect(jsonPath("$[*].customerFirstName").value("John"));
    }

    @Test
    @WithUserDetails("john@mail.com")
    public void getOrderById() throws Exception {
        mockMvc.perform(get("/order/info/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.city", Matchers.equalTo("Moscow")))
                .andExpect(jsonPath("$.country", Matchers.equalTo("Russia")))
                .andExpect(jsonPath("$.customerFirstName", Matchers.equalTo("John")));
    }

    @Test
    @WithUserDetails("test@mail.com")
    public void updateOrder() throws Exception {
        String status = "Rejected";
        mockMvc.perform(patch("/order/status/1")
                .content(status)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].orderId").isNotEmpty())
                .andExpect(jsonPath("$[*].total").isNotEmpty())
                .andExpect(jsonPath("$[*].customerEmailAddress").isNotEmpty())
                .andExpect(jsonPath("$[*].customerFirstName").isNotEmpty());
    }
}