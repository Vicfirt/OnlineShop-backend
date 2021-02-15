package com.javaschool.onlineshop.controller;

import com.javaschool.onlineshop.exception.FieldInputException;
import com.javaschool.onlineshop.model.dto.OrderDTO;
import com.javaschool.onlineshop.model.dto.OrderObjectDTO;
import com.javaschool.onlineshop.security.CustomUserPrincipal;
import com.javaschool.onlineshop.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;

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
    public ResponseEntity<OrderDTO> addOrder(@Valid @RequestBody OrderObjectDTO orderObjectDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new FieldInputException(bindingResult, "Validation error");
        } else {
            return ResponseEntity.ok(orderService.addOrder(orderObjectDTO));
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllCustomerOrders(@AuthenticationPrincipal CustomUserPrincipal customer) {
        return ResponseEntity.ok(orderService.findOrdersByEmail(customer.getUsername()));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@AuthenticationPrincipal CustomUserPrincipal customer,
                                                 @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.findOrderById(orderId));
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok("Order has been deleted");
    }

    @PatchMapping("/order/{orderId}")
    public ResponseEntity<List<OrderDTO>> updateOrder(@PathVariable Long orderId, @RequestBody String orderStatus) {
        return ResponseEntity.ok(orderService.updateOrder(orderId, orderStatus));
    }

    @GetMapping("/orders/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAllOrders());
    }
}
