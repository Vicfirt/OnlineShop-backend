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
import com.javaschool.onlineshop.service.ProductService;
import com.javaschool.onlineshop.utils.FileUploader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<ProductDTO> findAllProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();
        productList.forEach(product -> productDTOList.add(productMapper.productToProductDTO(product)));
        return productDTOList;
    }

    @Override
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.getOne(productId);
        return productMapper.productToProductDTO(product);
    }

    @Override
    public void addProduct(ProductDTO productDTO, MultipartFile file) {
        Product product = productMapper.productDTOToProduct(productDTO);
        product.setAmountInStock(product.getAmountInStock() - 1);
        if (product.getAmountInStock() == 0){
            product.setActive(false);
        }
        if (file == null) {
            product.setProductImage("default.jpg");
        }
        else {
            String fileName = FileUploader.uploadFile(file);
            product.setProductImage(fileName);
        }
        productRepository.save(product);
    }

    @Override
    public List<ProductDTO> deleteProduct(Long productId) {
            productRepository.deleteById(productId);
        List<Product> productList = productRepository.findByIsActiveTrue();
        List<ProductDTO> productDTOList = new ArrayList<>();
        productList.forEach(product -> productDTOList.add(productMapper.productToProductDTO(product)));
        return productDTOList;
    }

    @Override
    public List<ProductDTO> findAllActiveProducts() {
        List<Product> productList = productRepository.findByIsActiveTrue();
        List<ProductDTO> productDTOList = new ArrayList<>();
        productList.forEach(product -> productDTOList.add(productMapper.productToProductDTO(product)));
        return productDTOList;
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
    public List<String> getAllAvailableBrands() {
        List<ProductDTO> productDTOList = findAllActiveProducts();
        return new ArrayList<>(getBrandNames(productDTOList));
    }

    @Override
    public List<ProductDTO> getProductsInCart(List<Long> productIdList) {
        List<Product> productList = productRepository.findByProductIdIn(productIdList);
        List<ProductDTO> productDTOList = new ArrayList<>();
        productList.forEach(product -> productDTOList.add(productMapper.productToProductDTO(product)));
        return productDTOList;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        categoryList.forEach(category -> categoryDTOList.add(categoryMapper.categoryToCategoryDTO(category)));
        return categoryDTOList;
    }

    @Override
    public List<CategoryDTO> addNewCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.categoryDTOToCategory(categoryDTO);
        categoryRepository.save(category);
        return getAllCategories();
    }

    @Override
    public List<ProductDTO> filterByParameters(FilterParametersDTO filterParametersDTO) {
        List<String> categoriesToFilter = filterParametersDTO.getCategoriesToFilter();
        List<String> brandsToFilter = filterParametersDTO.getBrandsToFilter();
        List<Product> productList;
        if (categoriesToFilter.isEmpty()&& brandsToFilter.isEmpty()){
            return findAllProducts();
        }
        if (categoriesToFilter.isEmpty()) {
            productList = productRepository.findByBrands(brandsToFilter);
        }
       else if (brandsToFilter.isEmpty()){
            productList = productRepository.findByCategories(categoriesToFilter);
        }
       else {
           productList = productRepository.findByBrandsAndCategories(brandsToFilter, categoriesToFilter);
        }
        List<ProductDTO> productDTOList = new ArrayList<>();
        productList.forEach(product -> productDTOList.add(productMapper.productToProductDTO(product)));
        return productDTOList;
    }
}
