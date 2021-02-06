package com.javaschool.onlineshop.controller;

import com.javaschool.onlineshop.exception.FieldInputError;
import com.javaschool.onlineshop.model.dto.OrderDTO;
import com.javaschool.onlineshop.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    @ResponseBody
    public ResponseEntity<OrderDTO> addOrder(@Valid @RequestBody OrderDTO orderDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new FieldInputError(bindingResult, "Validation error");
        } else {
            return ResponseEntity.ok(orderService.addOrder(orderDTO));
        }
    }
}
