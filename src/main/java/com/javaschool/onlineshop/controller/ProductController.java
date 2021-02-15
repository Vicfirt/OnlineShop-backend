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

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/add", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addProduct(@RequestPart(name = "imgFile", required = false) MultipartFile file,
            @ModelAttribute @Valid ProductDTO productDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new FieldInputException(bindingResult, "Validation error");
        }
        else {
            productService.addProduct(productDTO, file);
            return ResponseEntity.ok("Product has been added successfully");
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @PutMapping(value = "/edit", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateProduct(@RequestPart(name = "imgFile", required = false) MultipartFile file,
                                             @ModelAttribute @Valid ProductDTO productDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new FieldInputException(bindingResult, "Validation error");
        }
        else {
            productService.addProduct(productDTO, file);
            return ResponseEntity.ok("Product has been updated successfully");
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<List<ProductDTO>> deleteProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        List<CategoryDTO> categoryDTOList = productService.getAllCategories();
        return ResponseEntity.ok(categoryDTOList);
    }

    @PostMapping("/category")
    public ResponseEntity<List<CategoryDTO>> addNewCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                                            BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new FieldInputException(bindingResult, "Validation error");
        } else {
            return ResponseEntity.ok(productService.addNewCategory(categoryDTO));
        }
    }

    @GetMapping("/brands")
    public ResponseEntity<List<String>> getBrands(){
        return ResponseEntity.ok(productService.getAllAvailableBrands());
    }

    @PostMapping("/filter")
    public ResponseEntity<List<ProductDTO>> getFilteredProducts(@RequestBody FilterParametersDTO filterParameters) {
        List<ProductDTO> productList = productService.filterByParameters(filterParameters);
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> findAllProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @GetMapping("/active")
    public ResponseEntity<List<ProductDTO>> findAllActiveProducts() {
        return ResponseEntity.ok(productService.findAllActiveProducts());
    }
}
