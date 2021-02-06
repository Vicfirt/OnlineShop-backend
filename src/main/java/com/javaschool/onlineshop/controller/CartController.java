package com.javaschool.onlineshop.controller;

import com.javaschool.onlineshop.model.dto.ProductDTO;
import com.javaschool.onlineshop.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CartController {

    private final ProductService productService;

    public CartController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/cart")
    public ResponseEntity<List<ProductDTO>> getCart(@RequestBody List<Long> productIdList) {
        return ResponseEntity.ok(productService.getProductsInCart(productIdList));
    }
}
