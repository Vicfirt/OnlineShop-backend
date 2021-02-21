package com.javaschool.onlineshop.service.impl;

import com.javaschool.onlineshop.mappers.OrderMapper;
import com.javaschool.onlineshop.model.dto.OrderDTO;
import com.javaschool.onlineshop.model.entity.Order;
import com.javaschool.onlineshop.repository.OrderElementRepository;
import com.javaschool.onlineshop.repository.OrderRepository;
import com.javaschool.onlineshop.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceImplTest {

    @Autowired
    OrderServiceImpl orderService;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    OrderRepository orderRepository;

    @MockBean
    OrderElementRepository orderElementRepository;

    @MockBean
    OrderMapper orderMapper;


    @Test
    public void findAllOrders() {
        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        orderList.add(order);
        Mockito.when(orderRepository.findAll()).thenReturn(orderList);
        Mockito.when(orderMapper.orderToOrderDTO(orderList.get(0))).thenReturn(new OrderDTO());
        orderService.findAllOrders();
        Mockito.verify(orderRepository, Mockito.atLeast(1)).findAll();
        Mockito.verify(orderMapper, Mockito.atLeast(1)).orderToOrderDTO(order);
    }

    @Test
    public void findOrdersByEmail() {
        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        orderList.add(order);
        Mockito.when(orderRepository.findOrdersByCustomerEmailAddress("ivan@mail.ru")).thenReturn(orderList);
        Mockito.when(orderMapper.orderToOrderDTO(orderList.get(0))).thenReturn(new OrderDTO());
        orderService.findOrdersByEmail("ivan@mail.ru");
        Mockito.verify(orderRepository, Mockito.times(1)).findOrdersByCustomerEmailAddress("ivan@mail.ru");
        Mockito.verify(orderMapper, Mockito.atLeast(1)).orderToOrderDTO(order);
    }

    @Test
    public void findOrderById() {
        Order order = new Order();
        Mockito.when(orderRepository.getOne(5L)).thenReturn(order);
        Mockito.when(orderMapper.orderToOrderDTO(order)).thenReturn(new OrderDTO());
        orderService.findOrderById(5L);
        Mockito.verify(orderRepository, Mockito.times(1)).getOne(5L);
        Mockito.verify(orderMapper, Mockito.times(1)).orderToOrderDTO(order);
    }

    @Test
    public void deleteOrder() {
        Mockito.doNothing().when(orderRepository).deleteById(4L);
        orderService.deleteOrder(4L);
        Mockito.verify(orderRepository, Mockito.times(1)).deleteById(4L);
    }

    @Test
    public void updateOrder() {
        Order order = new Order();
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order());
        Mockito.when(orderRepository.getOne(6L)).thenReturn(order);
        Mockito.when(orderRepository.save(order)).thenReturn(order);
        Mockito.when(orderRepository.findAll()).thenReturn(orderList);
        Mockito.when(orderMapper.orderToOrderDTO(orderList.get(0))).thenReturn(new OrderDTO());
        orderService.updateOrder(6L, "Rejected");
        assertEquals("Rejected", order.getStatus());
        Mockito.verify(orderRepository, Mockito.times(1)).getOne(6L);
        Mockito.verify(orderRepository, Mockito.times(1)).save(order);
        Mockito.verify(orderRepository, Mockito.times(1)).findAll();
        Mockito.verify(orderMapper, Mockito.atLeast(1)).orderToOrderDTO(orderList.get(0));
    }

    @Test
    public void findSalesSumInEachCategory() {
        List<Object> objects = new ArrayList<>();
        LocalDate date = LocalDate.now().minusMonths(1);
        Mockito.when(orderRepository.findSalesSumInEachCategory(date)).thenReturn(objects);
        orderService.findSalesSumInEachCategory();
        Mockito.verify(orderRepository, Mockito.times(1)).findSalesSumInEachCategory(date);
    }
}