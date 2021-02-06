package com.javaschool.onlineshop.controller;


import com.javaschool.onlineshop.model.dto.CustomerDTO;
import com.javaschool.onlineshop.model.dto.ProductDTO;
import com.javaschool.onlineshop.service.CustomerService;
import com.javaschool.onlineshop.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * This class is responsible for displaying homepage and catalog content.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ContentController {

    private final ProductService productService;

    private final CustomerService customerService;

    public ContentController(ProductService productService, CustomerService customerService) {
        this.productService = productService;
        this.customerService = customerService;
    }

    /**
     * The method is used to return the start page.
     * @param model will be sent to the view
     * @return homepage view
     */
    @GetMapping
    public String homePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<ProductDTO> products = productService.findSaleProducts();
        model.addAttribute("products", products);
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("CUSTOMER"))
                || authentication.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYEE"))) {
            CustomerDTO customer = customerService.getByUsername(authentication.getName());
            model.addAttribute("customer", customer);
        }
        return "home_page";
    }

    @GetMapping("/catalog")
    public ResponseEntity<List<ProductDTO>> catalog() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    /**
     * The method is used to return the catalog filtered by category.
     * @param categoryId id of the category to filter
     * @return catalog view
     */
    @GetMapping(value = {"/catalog/category/{categoryId}"})
    public ResponseEntity<List<ProductDTO>> catalogByCategory(@PathVariable Integer categoryId) {
        List<ProductDTO> productDTOList = productService.findAllActiveProductsByCategory(categoryId);
        return ResponseEntity.ok(productDTOList);
    }

    /**
     * The method is used to return the catalog filtered by brand.
     * @param brandName name of the brand to filter by
     * @return catalog view
     */
    @GetMapping("catalog/brand/{brandName}")
    public ResponseEntity<List<ProductDTO>> catalogByBrand(@PathVariable String brandName) {
        List<ProductDTO> productDTOList = productService.findAllActiveProductByBrand(brandName);
        return ResponseEntity.ok(productDTOList);
    }


    /**
     * The method is used to filter catalog by product name and return it.
     * @param productName name of the product to filter by
     * @return catalog view
     */
    @GetMapping("catalog/name/{productName}")
    public ResponseEntity<List<ProductDTO>> catalogByName(@PathVariable String productName) {
        List<ProductDTO> productDTOList = productService.findAllActiveProductsByName(productName);
        return ResponseEntity.ok(productDTOList);
    }
}
