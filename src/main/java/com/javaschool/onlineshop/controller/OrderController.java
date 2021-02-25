package com.javaschool.onlineshop.controller;

import com.javaschool.onlineshop.exception.FieldInputException;
import com.javaschool.onlineshop.model.dto.OrderDTO;
import com.javaschool.onlineshop.model.dto.OrderObjectDTO;
import com.javaschool.onlineshop.model.dto.StatisticsDTO;
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
import org.springframework.web.bind.annotation.PatchMapping;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * This class handles requests related to actions with orders.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * This method processes a request to add new order.
     * @param orderObjectDTO                order to be saved
     * @param bindingResult                 used for form validation
     * @return saved order
     * @throws IOException                  it appears when queue name length equals to 0
     * @throws TimeoutException             it appears when message send timeout has expired
     */
    @PostMapping("/order")
    public ResponseEntity<OrderDTO> addOrder(@Valid @RequestBody OrderObjectDTO orderObjectDTO, BindingResult bindingResult)
            throws IOException, TimeoutException {
        if (bindingResult.hasErrors()) {
            throw new FieldInputException(bindingResult, "Validation error");
        } else {
            return ResponseEntity.ok(orderService.addOrder(orderObjectDTO));
        }
    }

    /**
     * This method processes a request to get all orders of an authorized customer.
     * @param customer                  customers principal
     * @return all customer orders
     */
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllCustomerOrders(@AuthenticationPrincipal CustomUserPrincipal customer) {
        return ResponseEntity.ok(orderService.findOrdersByEmail(customer.getUsername()));
    }

    /**
     * This method processes a request to get particular order by id.
     * @param orderId                     specifies order to be fetched
     * @return  order with specified id
     */
    @GetMapping("/order/info/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.findOrderById(orderId));
    }

    /**
     * This method processes a request to change status of an order with specified id.
     * @param orderId                   specifies order to be changed
     * @param orderStatus               status to be set
     * @return                          all orders
     */
    @PatchMapping("/order/status/{orderId}")
    public ResponseEntity<List<OrderDTO>> updateOrder(@PathVariable Long orderId, @RequestBody String orderStatus) {
        return ResponseEntity.ok(orderService.updateOrder(orderId, orderStatus));
    }

    /**
     * This method processes a request to get all orders.
     * @return  all orders
     */
    @GetMapping("/orders/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    /**
     * This method processes a request to get sales statistics by categories.
     * @return queried data
     */
    @GetMapping("/statistics")
    public ResponseEntity<List<StatisticsDTO>> getStatistics() {
        return ResponseEntity.ok(orderService.findSalesSumInEachCategory());
    }
}
