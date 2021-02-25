package com.javaschool.onlineshop.controller;

import com.javaschool.onlineshop.exception.FieldInputException;
import com.javaschool.onlineshop.model.dto.CategoryDTO;
import com.javaschool.onlineshop.model.dto.FilterParametersDTO;
import com.javaschool.onlineshop.model.dto.ProductDTO;
import com.javaschool.onlineshop.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * This class handles requests related to actions with products.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * This method processes a request to add new product.
     * @param file                      image to be attached to product
     * @param productDTO                product to be saved
     * @param bindingResult             used for form validation
     * @return message to inform about success
     */
    @PostMapping(value = "/new", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addProduct(@RequestPart(name = "imgFile", required = false) MultipartFile file,
                                             @ModelAttribute @Valid ProductDTO productDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new FieldInputException(bindingResult, "Validation error");
        } else {
            productService.addProduct(productDTO, file);
            return ResponseEntity.ok("Product has been added successfully");
        }
    }

    /**
     * This method processes a request to get particular product by id.
     * @param productId              specifies product to be fetched
     * @return requested product
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    /**
     * This method processes a request to edit requested product.
     * @param file                  image to be reattached to product
     * @param productDTO            product with new data to update
     * @param bindingResult         used for form validation
     * @return message to inform about success
     */
    @PutMapping(value = "/edition", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateProduct(@RequestPart(name = "imgFile", required = false) MultipartFile file,
                                                @ModelAttribute @Valid ProductDTO productDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new FieldInputException(bindingResult, "Validation error");
        } else {
            productService.addProduct(productDTO, file);
            return ResponseEntity.ok("Product has been updated successfully");
        }
    }

    /**
     * This method processes a request to delete requested product.
     * @param productId              specifies product to be deleted
     * @return list of products
     */
    @DeleteMapping("/deletion/{productId}")
    public ResponseEntity<List<ProductDTO>> deleteProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }

    /**
     * This method processes a request to get all product categories.
     * @return list of all categories
     */
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        List<CategoryDTO> categoryDTOList = productService.getAllCategories();
        return ResponseEntity.ok(categoryDTOList);
    }

    /**
     * This method processes a request to add new category.
     * @param categoryDTO           category to be added
     * @param bindingResult         used for form validation
     * @return list of all categories
     */
    @PostMapping("/category")
    public ResponseEntity<List<CategoryDTO>> addNewCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new FieldInputException(bindingResult, "Validation error");
        } else {
            return ResponseEntity.ok(productService.addNewCategory(categoryDTO));
        }
    }

    /**
     * This method processes a request to get all available brands.
     * @return list of brands
     */
    @GetMapping("/brands")
    public ResponseEntity<List<String>> getBrands() {
        return ResponseEntity.ok(productService.getAllAvailableBrands());
    }

    /**
     * This method processes a request to get products by represented filter parameters.
     * @param filterParameters          parameters to filter by.
     * @return filtered product list
     */
    @PostMapping("/filter")
    public ResponseEntity<List<ProductDTO>> getFilteredProducts(@RequestBody FilterParametersDTO filterParameters) {
        List<ProductDTO> productList = productService.filterByParameters(filterParameters);
        return ResponseEntity.ok(productList);
    }

    /**
     * This method processes a request to get all products in shop.
     * @return list of all products
     */
    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> findAllProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    /**
     * This method processes a request to get all active products in shop.
     * @return list of active products
     */
    @GetMapping("/active")
    public ResponseEntity<List<ProductDTO>> findAllActiveProducts() {
        return ResponseEntity.ok(productService.findAllActiveProducts());
    }

    /**
     * This method processes a request to get top five most selling products
     * @return list top five most selling products
     */
    @GetMapping("/top")
    public ResponseEntity<List<ProductDTO>> getTop() {
        return ResponseEntity.ok(productService.findTop());
    }
}
