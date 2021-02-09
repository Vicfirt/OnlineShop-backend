package com.javaschool.onlineshop.service.impl;

import com.javaschool.onlineshop.mappers.OrderMapper;
import com.javaschool.onlineshop.model.dto.OrderDTO;
import com.javaschool.onlineshop.model.dto.OrderObjectDTO;
import com.javaschool.onlineshop.model.entity.Order;
import com.javaschool.onlineshop.model.entity.OrderElement;
import com.javaschool.onlineshop.model.entity.Product;
import com.javaschool.onlineshop.repository.OrderElementRepository;
import com.javaschool.onlineshop.repository.OrderRepository;
import com.javaschool.onlineshop.repository.ProductRepository;
import com.javaschool.onlineshop.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final ProductRepository productRepository;

    private final OrderElementRepository orderElementRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, ProductRepository productRepository, OrderElementRepository orderElementRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.productRepository = productRepository;
        this.orderElementRepository = orderElementRepository;
    }

    @Override
    public OrderDTO addOrder(OrderObjectDTO orderObjectDTO) {
        Order order = new Order();
        List<OrderElement> orderElementList = new ArrayList<>();
        for (Map.Entry<Long, Long> entry : orderObjectDTO.getProductInformation().entrySet()) {
            Product product = productRepository.findById(entry.getKey()).get();
            OrderElement orderElement = new OrderElement();
            orderElement.setProduct(product);
            orderElement.setQuantityInOrder(entry.getValue());
            orderElementList.add(orderElement);
            orderElementRepository.save(orderElement);
        }
        order.getOrderElementList().addAll(orderElementList);
        order.setCustomerFirstName(orderObjectDTO.getCustomerFirstName());
        order.setCustomerLastName(orderObjectDTO.getCustomerLastName());
        order.setCustomerEmailAddress(orderObjectDTO.getCustomerEmailAddress());
        order.setCountry(orderObjectDTO.getCountry());
        order.setCity(orderObjectDTO.getCity());
        order.setStreet(orderObjectDTO.getStreet());
        order.setBuilding(orderObjectDTO.getBuilding());
        order.setRoom(orderObjectDTO.getRoom());
        order.setShippingType(orderObjectDTO.getShippingType());
        order.setPaymentMethod(orderObjectDTO.getPaymentMethod());
        order.setStatus(orderObjectDTO.getStatus());
        order.setTotal(orderObjectDTO.getTotal());
        order.setPostcode(orderObjectDTO.getPostcode());
        orderRepository.save(order);
        return orderMapper.orderToOrderDTO(order);
    }

    @Override
    public List<OrderDTO> findAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orderList.forEach(order -> orderDTOList.add(orderMapper.orderToOrderDTO(order)));
        return orderDTOList;
    }

    @Override
    public List<OrderDTO> findOrdersByEmail(String customerEmail) {
        List<Order> orderList = orderRepository.findOrdersByCustomerEmailAddress(customerEmail);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orderList.forEach(order -> orderDTOList.add(orderMapper.orderToOrderDTO(order)));
        return orderDTOList;
    }

    @Override
    public OrderDTO findOrderById(Long orderId) {
        Order order = orderRepository.getOne(orderId);
        return orderMapper.orderToOrderDTO(order);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<OrderDTO> updateOrder(Long orderId, String orderStatus) {
        Order order = orderRepository.getOne(orderId);
        order.setStatus(orderStatus);
        orderRepository.save(order);
        List<Order> orderList = orderRepository.findAll();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orderList.forEach(orderInList -> orderDTOList.add(orderMapper.orderToOrderDTO(orderInList)));
        return orderDTOList;
    }
}
