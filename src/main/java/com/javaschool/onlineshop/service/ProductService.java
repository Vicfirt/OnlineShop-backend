package com.javaschool.onlineshop.service;

import com.javaschool.onlineshop.model.dto.CategoryDTO;
import com.javaschool.onlineshop.model.dto.FilterParametersDTO;
import com.javaschool.onlineshop.model.dto.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ProductService {

    List<ProductDTO> findAllProducts();

    ProductDTO getProductById(Long productId);

    void addProduct(ProductDTO productDTO, MultipartFile file);

    List<ProductDTO> deleteProduct(Long productId);

    List<ProductDTO> findAllActiveProducts();

    List<String> getAllAvailableBrands();

    List<ProductDTO> getProductsInCart(List<Long> productIdList);

    List<CategoryDTO> getAllCategories();

    List<CategoryDTO> addNewCategory(CategoryDTO categoryDTO);

    List<ProductDTO> filterByParameters(FilterParametersDTO filterParametersDTO);
}
