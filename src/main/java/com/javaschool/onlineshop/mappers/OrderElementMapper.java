package com.javaschool.onlineshop.mappers;

import com.javaschool.onlineshop.model.dto.OrderElementDTO;
import com.javaschool.onlineshop.model.entity.OrderElement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderElementMapper {

    OrderElementDTO mapOrderElementToDTO(OrderElement orderElement);

    OrderElement mapOrderElementToOrderDTO(OrderElementDTO orderElementDTO);
}
