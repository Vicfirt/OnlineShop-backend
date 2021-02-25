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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * This class is responsible for processing data received from product repository as well as preparing it for
 *  sending to the Client Application.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final FileUploader fileUploader;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productMapper,
                              CategoryRepository categoryRepository,
                              CategoryMapper categoryMapper,
                              FileUploader fileUploader) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.fileUploader = fileUploader;
    }

    /**
     * This method is responsible for getting list of all products.
     * @return converted product list
     */
    @Override
    public List<ProductDTO> findAllProducts() {
        return productRepository.findAll().stream().map(productMapper::productToProductDTO)
                .collect(Collectors.toList());
    }

    /**
     * This method is responsible for getting product by specified id.
     * @param productId             specifies product to be fetched
     * @return converted product
     */
    @Override
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.getOne(productId);
        return productMapper.productToProductDTO(product);
    }

    /**
     * This method is responsible for adding new product to catalog.
     * @param productDTO            product to be added
     * @param file                  image to be attached to the product
     */
    @Override
    public void addProduct(ProductDTO productDTO, MultipartFile file) {
        Product product = productMapper.productDTOToProduct(productDTO);
        if (file == null) {
            product.setProductImage("default.jpg");
        } else {
            String fileName = fileUploader.uploadFile(file);
            product.setProductImage(fileName);
        }
        productRepository.save(product);
    }

    /**
     * This method is responsible for deleting product by specified id.
     * @param productId             specifies product to be deleted
     * @return                      converted product list
     */
    @Override
    public List<ProductDTO> deleteProduct(Long productId) {
        productRepository.deleteById(productId);
        return productRepository.findByIsActiveTrue().stream().map(productMapper::productToProductDTO)
                .collect(Collectors.toList());
    }

    /**
     * This method is responsible for getting list of all active products.
     * @return converted list of active products
     */
    @Override
    public List<ProductDTO> findAllActiveProducts() {
       return productRepository.findByIsActiveTrue().stream().map(productMapper::productToProductDTO)
               .collect(Collectors.toList());
    }

    /**
     * This method is used to return sorted set of brand names.
     * @param products              products whose brand names will be used
     * @return                      names of the product brands
     */
    private Set<String> getBrandNames(List<ProductDTO> products) {
        Set<String> brandNames = new TreeSet<>();
        for (ProductDTO product : products) {
            brandNames.add(product.getProductBrand());
        }
        return brandNames;
    }

    /**
     * This method returns all available brand names.
     * @return brand names of available products
     */
    @Override
    public List<String> getAllAvailableBrands() {
        List<ProductDTO> productDTOList = findAllActiveProducts();
        return new ArrayList<>(getBrandNames(productDTOList));
    }

    /**
     * This method is responsible for fetching products with specified id.
     * @param productIdList                 list of id specifies the products to be fetched
     * @return  converted product list
     */
    @Override
    public List<ProductDTO> getProductsInCart(List<Long> productIdList) {
        return productRepository.findByProductIdIn(productIdList).stream().map(productMapper::productToProductDTO)
                .collect(Collectors.toList());
    }

    /**
     * This method returns all categories;
     * @return converted categories list
     */
    @Override
    public List<CategoryDTO> getAllCategories() {
       return categoryRepository.findAll().stream().map(categoryMapper::categoryToCategoryDTO)
               .collect(Collectors.toList());
    }

    /**
     * This method is responsible for adding new category to the shop.
     * @param categoryDTO           category to be saved
     * @return                      converted categories list
     */
    @Override
    public List<CategoryDTO> addNewCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.categoryDTOToCategory(categoryDTO);
        categoryRepository.save(category);
        return getAllCategories();
    }

    /**
     * This method gets a list of products that match the request filter parameters
     * @param filterParametersDTO               it contains parameters to filter by
     * @return converted product list
     */
    @Override
    public List<ProductDTO> filterByParameters(FilterParametersDTO filterParametersDTO) {
        List<String> categoriesToFilter = filterParametersDTO.getCategoriesToFilter();
        List<String> brandsToFilter = filterParametersDTO.getBrandsToFilter();
        List<Product> productList;
        if (categoriesToFilter.isEmpty() && brandsToFilter.isEmpty()) {
            return findAllProducts();
        }
        if (categoriesToFilter.isEmpty()) {
            productList = productRepository.findByBrands(brandsToFilter);
        } else if (brandsToFilter.isEmpty()) {
            productList = productRepository.findByCategories(categoriesToFilter);
        } else {
            productList = productRepository.findByBrandsAndCategories(brandsToFilter, categoriesToFilter);
        }
        List<ProductDTO> productDTOList = new ArrayList<>();
        productList.forEach(product -> productDTOList.add(productMapper.productToProductDTO(product)));
        return productDTOList;
    }

    /**
     * This method responsible for getting top five most selling products.
     * @return converted product list
     */
    @Override
    public List<ProductDTO> findTop() {
       return productRepository.findTop(PageRequest.of(0, 5)).stream()
               .map(productMapper::productToProductDTO).collect(Collectors.toList());
    }
}
