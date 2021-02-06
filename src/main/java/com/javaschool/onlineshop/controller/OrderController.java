package com.javaschool.onlineshop.controller;

import com.javaschool.onlineshop.exception.FieldInputError;
import com.javaschool.onlineshop.model.dto.OrderDTO;
import com.javaschool.onlineshop.model.dto.OrderObjectDTO;
import com.javaschool.onlineshop.security.CustomUserPrincipal;
import com.javaschool.onlineshop.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    @ResponseBody
    public ResponseEntity<OrderDTO> addOrder(@Valid @RequestBody OrderObjectDTO orderObjectDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new FieldInputError(bindingResult, "Validation error");
        } else {
            return ResponseEntity.ok(orderService.addOrder(orderObjectDTO));
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllCustomerOrders(@AuthenticationPrincipal CustomUserPrincipal username) {
        return ResponseEntity.ok(orderService.findOrdersByEmail(username.getUsername()));
    }
}
