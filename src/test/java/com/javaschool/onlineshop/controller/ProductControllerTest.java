package com.javaschool.onlineshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaschool.onlineshop.model.dto.CategoryDTO;
import com.javaschool.onlineshop.model.dto.FilterParametersDTO;
import com.javaschool.onlineshop.model.dto.ProductDTO;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create_products_before_test.sql", "/sql/create_categories_before_test.sql",
        "/sql/create_customer_before_test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create_categories_after_test.sql", "/sql/create_products_after_tests.sql",
        "/sql/create_customer_after_test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    @WithUserDetails("test@mail.com")
    public void addProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("Macbook");
        productDTO.setProductBrand("Apple");
        productDTO.setActive(true);
        productDTO.setAmountInStock(9);
        productDTO.setProductCapacity(14);
        productDTO.setCategory("Electronics");
        productDTO.setProductDescription("Good");
        productDTO.setProductModel("new");
        productDTO.setProductWeight(100);
        productDTO.setProductPrice(13.0);
        FileInputStream inputFile = new FileInputStream("C:/Users/IvaNi/IdeaProjects/springbootbackend/src/main/resources/media/3fb12e45-1dde-4ca0-8ef0-ce4db1b922d1.new blance shoes.jpg");
        MockMultipartFile multipartFile = new MockMultipartFile("imgFile", "imgFile", MediaType.MULTIPART_FORM_DATA_VALUE, inputFile);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(multipart("/product/new")
                .file(multipartFile)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getProduct() throws Exception {
        mockMvc.perform(get("/product/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", Matchers.equalTo(4)))
                .andExpect(jsonPath("$.productName", Matchers.equalTo("Iphone")))
                .andExpect(jsonPath("$.productBrand", Matchers.equalTo("World")))
                .andExpect(jsonPath("$.category", Matchers.equalTo("Books")));
    }

    @Test
    @WithUserDetails("test@mail.com")
    public void deleteProduct() throws Exception {
        mockMvc.perform(delete("/product/deletion/4"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProductWithoutPermission() throws Exception {
        mockMvc.perform(delete("/product/deletion/4"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getCategories() throws Exception {
        mockMvc.perform(get("/product/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].categoryId").exists())
                .andExpect(jsonPath("$[*].categoryName").exists());
    }

    @Test
    @WithUserDetails("test@mail.com")
    public void addNewCategory() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("Others");
        mockMvc.perform(post("/product/category")
                .content(objectMapper.writeValueAsString(categoryDTO))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", Matchers.iterableWithSize(4)));
    }

    @Test
    public void addNewCategoryWithoutPermission() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("Others");
        mockMvc.perform(post("/category")
                .content(objectMapper.writeValueAsString(categoryDTO))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isForbidden());
    }


    @Test
    public void getBrands() throws Exception {
        mockMvc.perform(get("/product/brands"))
                .andExpect(status().isOk());
    }

    @Test
    public void getFilteredProducts() throws Exception {
        FilterParametersDTO filterParametersDTO = new FilterParametersDTO();
        List<String> brandsToFilter = new ArrayList<>();
        List<String> categoriesToFilter = new ArrayList<>();
        brandsToFilter.add("Apple");
        filterParametersDTO.setBrandsToFilter(brandsToFilter);
        filterParametersDTO.setCategoriesToFilter(categoriesToFilter);
        mockMvc.perform(post("/product/filter")
                .content(objectMapper.writeValueAsString(filterParametersDTO))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", Matchers.iterableWithSize(1)));
    }
}