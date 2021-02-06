package com.javaschool.onlineshop.mappers;


import com.javaschool.onlineshop.model.dto.OrderDTO;
import com.javaschool.onlineshop.model.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderElementMapper.class})
public interface OrderMapper {

    OrderDTO orderToOrderDTO(Order order);

    Order orderDTOToOrder(OrderDTO orderInfoDTO);
}
