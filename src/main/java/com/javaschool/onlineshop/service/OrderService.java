package com.javaschool.onlineshop.service;

import com.javaschool.onlineshop.model.dto.OrderDTO;
import com.javaschool.onlineshop.model.dto.OrderObjectDTO;

import java.util.List;

public interface OrderService {

    OrderDTO addOrder(OrderObjectDTO orderObjectDTO);

    List<OrderDTO> findOrdersByEmail(String customerEmail);
}
