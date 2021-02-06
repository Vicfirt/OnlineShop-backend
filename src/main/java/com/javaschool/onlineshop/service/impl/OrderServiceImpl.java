package com.javaschool.onlineshop.service.impl;

import com.javaschool.onlineshop.mappers.OrderMapper;
import com.javaschool.onlineshop.model.dto.OrderDTO;
import com.javaschool.onlineshop.model.entity.Order;
import com.javaschool.onlineshop.repository.OrderRepository;
import com.javaschool.onlineshop.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDTO addOrder(OrderDTO orderDTO) {
        Order order = orderMapper.orderDTOToOrder(orderDTO);
        Order orderToSave = new Order();
        orderToSave.getProductList().addAll(order.getProductList());
        orderToSave.setCustomerFirstName(order.getCustomerFirstName());
        orderToSave.setCustomerLastName(order.getCustomerLastName());
        orderToSave.setCustomerEmail(order.getCustomerEmail());
        orderToSave.setBuilding(order.getBuilding());
        orderToSave.setCity(order.getCity());
        orderToSave.setCountry(order.getCountry());
        orderToSave.setStreet(order.getStreet());
        orderToSave.setPaymentMethod(order.getPaymentMethod());
        orderToSave.setShippingType(order.getShippingType());
        orderToSave.setStatus(order.getStatus());
        orderRepository.save(orderToSave);
        return orderMapper.orderToOrderDTO(orderToSave);

    }
}
