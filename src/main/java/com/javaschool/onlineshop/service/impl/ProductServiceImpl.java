package com.javaschool.onlineshop.service.impl;

import com.javaschool.onlineshop.mappers.ProductMapper;
import com.javaschool.onlineshop.model.dto.ProductDTO;
import com.javaschool.onlineshop.model.entity.Product;
import com.javaschool.onlineshop.repository.ProductRepository;
import com.javaschool.onlineshop.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;

    ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDTO> findAllProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();
        productList.forEach(product -> productDTOList.add(productMapper.productToProductDTO(product)));
        return productDTOList;
    }

    @Override
    public List<ProductDTO> findAllProductsByPrice(Double minPrice, Double maxPrice) {
        return null;
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return null;
    }

    @Override
    public void addProduct(ProductDTO productDTO, MultipartFile file) {

    }

    @Override
    public void updateProduct(ProductDTO productDTO, MultipartFile file) {

    }

    @Override
    public void deleteProduct(Long id) {

    }

    @Override
    public List<ProductDTO> findAllActiveProducts() {
        List<Product> productList = productRepository.findAllByIsActiveTrue();
        List<ProductDTO> productDTOList = new ArrayList<>();
        productList.forEach(product -> productDTOList.add(productMapper.productToProductDTO(product)));
        return productDTOList;
    }

    @Override
    public List<ProductDTO> findAllActiveProductsByCategory(Integer categoryId) {
        List<Product> productList = productRepository.findAllByCategoryIdAndIsActiveTrue(categoryId);
        List<ProductDTO> productDTOList = new ArrayList<>();
        productList.forEach(product -> productDTOList.add(productMapper.productToProductDTO(product)));
        return productDTOList;
    }

    @Override
    public List<ProductDTO> findAllActiveProductByBrand(String brandName) {
        List<Product> productList = productRepository.findAllByProductBrandAndIsActiveTrue(brandName);
        List<ProductDTO> productDTOList = new ArrayList<>();
        productList.forEach(product -> productDTOList.add(productMapper.productToProductDTO(product)));
        return productDTOList;
    }

    @Override
    public List<ProductDTO> findAllActiveProductsByName(String productName) {
        List<Product> productList = productRepository.findAllByProductNameAndIsActiveTrue(productName);
        List<ProductDTO> productDTOList = new ArrayList<>();
        productList.forEach(product -> productDTOList.add(productMapper.productToProductDTO(product)));
        return productDTOList;
    }

    @Override
    public List<ProductDTO> findAllActiveProductsByPrice(Double minPrice, Double maxPrice) {
        return null;
    }

    @Override
    public void decreaseAmount(Long productId, Integer amount) {
    }

    @Override
    public Set<String> getBrandNames(List<ProductDTO> products) {
        Set<String> brandNames = new TreeSet<>();
        for (ProductDTO product : products) {
            brandNames.add(product.getProductBrand());
        }
        return brandNames;
    }

    @Override
    public Set<String> getAllAvailableBrands() {
        List<ProductDTO> productDTOList = findAllActiveProducts();
        return getBrandNames(productDTOList);
    }

    @Override
    public List<ProductDTO> findSaleProducts() {
        return null;
    }

    @Override
    public List<ProductDTO> getProductsInCart(List<Long> productIdList) {
        List<Product> productList = productRepository.findByProductIdIn(productIdList);
        List<ProductDTO> productDTOList = new ArrayList<>();
        productList.forEach(product -> productDTOList.add(productMapper.productToProductDTO(product)));
        return productDTOList;
    }
}
