package com.javaschool.onlineshop.mappers;


import com.javaschool.onlineshop.model.dto.CustomerDTO;
import com.javaschool.onlineshop.model.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDTO customerToCustomerDTO(Customer customer);

    Customer customerDTOToCustomer(CustomerDTO customerDTO);
}
