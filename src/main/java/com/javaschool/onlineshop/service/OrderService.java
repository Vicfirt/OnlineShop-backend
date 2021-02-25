package com.javaschool.onlineshop.service;

import com.javaschool.onlineshop.model.dto.OrderDTO;
import com.javaschool.onlineshop.model.dto.OrderObjectDTO;
import com.javaschool.onlineshop.model.dto.StatisticsDTO;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public interface OrderService {

    OrderDTO addOrder(OrderObjectDTO orderObjectDTO) throws IOException, TimeoutException;

    List<OrderDTO> findOrdersByEmail(String customerEmail);

    OrderDTO findOrderById(Long orderId);

    void deleteOrder(Long orderId);

    List<OrderDTO> updateOrder(Long orderId, String orderStatus);

    List<OrderDTO> findAllOrders();

    List<StatisticsDTO> findSalesSumInEachCategory();
}
