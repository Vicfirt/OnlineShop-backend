package com.javaschool.onlineshop.service.impl;

import com.javaschool.onlineshop.mappers.CategoryMapper;
import com.javaschool.onlineshop.mappers.ProductMapper;
import com.javaschool.onlineshop.model.dto.CategoryDTO;
import com.javaschool.onlineshop.model.dto.FilterParametersDTO;
import com.javaschool.onlineshop.model.dto.ProductDTO;
import com.javaschool.onlineshop.model.entity.Category;
import com.javaschool.onlineshop.model.entity.Product;
import com.javaschool.onlineshop.repository.CategoryRepository;
import com.javaschool.onlineshop.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceImplTest {

    @Autowired
    ProductServiceImpl productService;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    ProductMapper productMapper;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    CategoryMapper categoryMapper;

    @Test
    public void getProductById() {
        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();
        product.setProductId(4L);
        Mockito.when(productRepository.getOne(4L)).thenReturn(product);
        Mockito.when(productMapper.productToProductDTO(product)).thenReturn(productDTO);
        productService.getProductById(4L);
        Mockito.verify(productRepository, Mockito.atLeast(1)).getOne(4L);
        Mockito.verify(productMapper, Mockito.atLeast(1)).productToProductDTO(product);
    }


    @Test
    public void findAllProducts() {
        List<Product> productList = new ArrayList<>();
        List<ProductDTO> productDTOList = new ArrayList<>();
        productList.add(new Product());
        productDTOList.add(new ProductDTO());
        Mockito.when(productRepository.findAll()).thenReturn(productList);
        Mockito.when(productMapper.productToProductDTO(productList.get(0))).thenReturn(productDTOList.get(0));
        productService.findAllProducts();
        assertEquals(1, productList.size());
        Mockito.verify(productRepository, Mockito.atLeast(1)).findAll();
        Mockito.verify(productMapper, Mockito.atLeast(1)).productToProductDTO(productList.get(0));
    }


    @Test
    public void addProduct() {
        MultipartFile multipartFile = new MockMultipartFile("TESTFILE", "SomeName".getBytes());
        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();
        product.setProductId(5L);
        product.setAmountInStock(55);
        product.setProductImage(multipartFile.getOriginalFilename());
        Mockito.when(productMapper.productDTOToProduct(productDTO)).thenReturn(product);
        Mockito.when(productRepository.save(product)).thenReturn(product);
        productService.addProduct(productDTO, multipartFile);
        assertEquals((Integer) 54, product.getAmountInStock());
        Mockito.verify(productMapper, Mockito.atLeast(1)).productDTOToProduct(productDTO);
        Mockito.verify(productRepository, Mockito.atLeast(1)).save(product);
    }


    @Test
    public void deleteProduct() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        List<ProductDTO> productDTOList = new ArrayList<>();
        productDTOList.add(new ProductDTO());
        Mockito.doNothing().when(productRepository).deleteById(5L);
        Mockito.when(productRepository.findByIsActiveTrue()).thenReturn(productList);
        Mockito.when(productMapper.productToProductDTO(productList.get(0))).thenReturn(productDTOList.get(0));
        productService.deleteProduct(5L);
        Mockito.verify(productRepository, Mockito.atLeast(1)).deleteById(5L);
        Mockito.verify(productMapper, Mockito.atLeast(1)).productToProductDTO(productList.get(0));
        Mockito.verify(productRepository, Mockito.atLeast(1)).findByIsActiveTrue();
    }


    @Test
    public void findAllActiveProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        List<ProductDTO> productDTOList = new ArrayList<>();
        productDTOList.add(new ProductDTO());
        Mockito.when(productRepository.findByIsActiveTrue()).thenReturn(productList);
        Mockito.when(productMapper.productToProductDTO(productList.get(0))).thenReturn(productDTOList.get(0));
        productService.findAllActiveProducts();
        Mockito.verify(productRepository, Mockito.atLeast(1)).findByIsActiveTrue();
        Mockito.verify(productMapper, Mockito.atLeast(1)).productToProductDTO(productList.get(0));
    }

    @Test
    public void getProductsInCart() {
        List<Long> productIdList = new ArrayList<>();
        productIdList.add(1L);
        productIdList.add(2L);
        Product productOne = new Product();
        productOne.setProductId(1L);
        Product productTwo = new Product();
        productTwo.setProductId(2L);
        List<Product> productList = new ArrayList<>(Arrays.asList(productOne, productTwo));
        Mockito.when(productRepository.findByProductIdIn(productIdList)).thenReturn(productList);
        Mockito.when(productMapper.productToProductDTO(productOne)).thenReturn(new ProductDTO());
        productService.getProductsInCart(productIdList);
        assertEquals(2, productList.size());
        assertEquals(2, productIdList.size());
        Mockito.verify(productRepository, Mockito.atLeast(1)).findByProductIdIn(productIdList);
        Mockito.verify(productMapper, Mockito.atLeast(1)).productToProductDTO(productOne);

    }


    @Test
    public void getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category());
        Mockito.doReturn(categoryList).when(categoryRepository).findAll();
        Mockito.when(categoryMapper.categoryToCategoryDTO(categoryList.get(0))).thenReturn(new CategoryDTO());
        productService.getAllCategories();
        Mockito.verify(categoryRepository, Mockito.atLeast(1)).findAll();
        Mockito.verify(categoryMapper, Mockito.atLeast(1)).categoryToCategoryDTO(categoryList.get(0));
    }

    @Test
    public void addNewCategory() {
        Category category = new Category();
        CategoryDTO categoryDTO = new CategoryDTO();
        Mockito.when(categoryMapper.categoryDTOToCategory(categoryDTO)).thenReturn(category);
        Mockito.when(categoryRepository.save(category)).thenReturn(category);
        productService.addNewCategory(categoryDTO);
        Mockito.verify(categoryRepository, Mockito.atLeast(1)).save(category);
        Mockito.verify(categoryMapper, Mockito.atLeast(1)).categoryDTOToCategory(categoryDTO);
    }

    @Test
    public void filterByParameters() {
        FilterParametersDTO filterParametersDTO = new FilterParametersDTO();
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        List<String> categoriesToFilter = new ArrayList<>();
        categoriesToFilter.add("Electronics");
        List<String> brandsToFilter = new ArrayList<>();
        brandsToFilter.add("Apple");
        filterParametersDTO.setBrandsToFilter(brandsToFilter);
        filterParametersDTO.setCategoriesToFilter(categoriesToFilter);
        Mockito.when(productRepository.findByBrandsAndCategories(brandsToFilter, categoriesToFilter)).thenReturn(productList);
        Mockito.when(productMapper.productToProductDTO(productList.get(0))).thenReturn(new ProductDTO());
        productService.filterByParameters(filterParametersDTO);
        assertEquals(1, productList.size());
        Mockito.verify(productRepository, Mockito.atLeast(1)).findByBrandsAndCategories(brandsToFilter, categoriesToFilter);
        Mockito.verify(productMapper, Mockito.atLeast(1)).productToProductDTO(productList.get(0));
    }
}