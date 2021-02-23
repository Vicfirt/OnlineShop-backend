package com.javaschool.onlineshop.controller;

import com.javaschool.onlineshop.model.dto.ProductDTO;
import com.javaschool.onlineshop.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * This class processes a request to get products in the cart.
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CartController {

    private final ProductService productService;

    public CartController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * This method gets a list of items in the cart according to the received list of id.
     *
     * @param productIdList list with id of products in the cart
     * @return response with list of products in cart
     */
    @PostMapping("/cart")
    public ResponseEntity<List<ProductDTO>> getCart(@RequestBody List<Long> productIdList) {
        return ResponseEntity.ok(productService.getProductsInCart(productIdList));
    }
}
