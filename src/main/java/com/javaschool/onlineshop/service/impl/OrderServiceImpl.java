package com.javaschool.onlineshop.service.impl;

import com.javaschool.onlineshop.mappers.OrderMapper;
import com.javaschool.onlineshop.model.dto.OrderDTO;
import com.javaschool.onlineshop.model.dto.OrderObjectDTO;
import com.javaschool.onlineshop.model.dto.StatisticsDTO;
import com.javaschool.onlineshop.model.entity.Order;
import com.javaschool.onlineshop.model.entity.OrderElement;
import com.javaschool.onlineshop.model.entity.Product;
import com.javaschool.onlineshop.repository.OrderElementRepository;
import com.javaschool.onlineshop.repository.OrderRepository;
import com.javaschool.onlineshop.repository.ProductRepository;
import com.javaschool.onlineshop.service.OrderService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * This class is responsible for processing data received from order repository as well as preparing it for
 *  * sending to the Client Application.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final ProductRepository productRepository;

    private final OrderElementRepository orderElementRepository;

    private final MessageServiceImpl messageService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, ProductRepository productRepository, OrderElementRepository orderElementRepository, MessageServiceImpl messageService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.productRepository = productRepository;
        this.orderElementRepository = orderElementRepository;
        this.messageService = messageService;
    }

    /**
     * This method is responsible for add new order operation.
     * @param orderObjectDTO         it contains data to create new order
     * @return just saved orders
     * @throws IOException           it appears when queue name length equals to 0
     * @throws TimeoutException      it appears when message send timeout has expired
     */
    @Override
    public OrderDTO addOrder(OrderObjectDTO orderObjectDTO) throws IOException, TimeoutException {
        List<OrderElement> orderElementList = new ArrayList<>();
        for (Map.Entry<Long, Long> entry : orderObjectDTO.getProductInformation().entrySet()) {
            Product product = productRepository.findById(entry.getKey()).get();
            product.setAmountInStock(product.getAmountInStock() - entry.getValue().intValue());
            if (product.getAmountInStock() == 0) {
                product.setActive(false);
            }
            productRepository.save(product);
            OrderElement orderElement = new OrderElement();
            orderElement.setProduct(product);
            orderElement.setQuantityInOrder(entry.getValue());
            orderElement.setElementPrice(product.getProductPrice() * entry.getValue());
            orderElementList.add(orderElement);
            orderElementRepository.save(orderElement);
        }
        Order order = createOrder(orderElementList, orderObjectDTO);
        orderRepository.save(order);
        return orderMapper.orderToOrderDTO(order);
    }

    /**
     * This method creates new order based on given parameters.
     * @param orderElementList          order items to be added to order
     * @param orderObjectDTO            it contains address and customer information
     * @return created order
     * @throws IOException              it appears when queue name length equals to 0
     * @throws TimeoutException         it appears when message send timeout has expired
     */
    private Order createOrder(List<OrderElement> orderElementList, OrderObjectDTO orderObjectDTO) throws IOException, TimeoutException {
        Order order = new Order();
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
        messageService.sendMessage("Update");
        return order;
    }

    /**
     * This method is responsible for getting list of all orders.
     * @return converted orders list
     */
    @Override
    public List<OrderDTO> findAllOrders() {
        return orderRepository.findAll().stream().map(orderMapper::orderToOrderDTO)
                .collect(Collectors.toList());
    }

    /**
     * This method is responsible for getting orders list of a particular customer.
     * @param customerEmail             it specifies customer
     * @return converted customer orders list
     */
    @Override
    public List<OrderDTO> findOrdersByEmail(String customerEmail) {
        return orderRepository.findOrdersByCustomerEmailAddress(customerEmail).stream().map(orderMapper::orderToOrderDTO)
                .collect(Collectors.toList());
    }

    /**
     * This method is responsible for getting order by specified id.
     * @param orderId                   specifies the order to be fetched
     * @return  converted order
     */
    @Override
    public OrderDTO findOrderById(Long orderId) {
        Order order = orderRepository.getOne(orderId);
        return orderMapper.orderToOrderDTO(order);
    }

    /**
     * This method is responsible for deleting order by specified id.
     * @param orderId                   specifies the order to be deleted
     */
    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    /**
     * This method is responsible for updating order with represented status.
     * @param orderId                   specifies the order to be updated
     * @param orderStatus               status to update order with
     * @return converted orders list
     */
    @Override
    public List<OrderDTO> updateOrder(Long orderId, String orderStatus) {
        Order order = orderRepository.getOne(orderId);
        order.setStatus(orderStatus);
        orderRepository.save(order);
       return orderRepository.findAll().stream().map(orderMapper::orderToOrderDTO)
               .collect(Collectors.toList());
    }

    /**
     * This method is responsible for getting sales statistics by categories.
     * @return  queried data
     */
    @Override
    public List<StatisticsDTO> findSalesSumInEachCategory() {
        return orderRepository.findSalesSumInEachCategory(LocalDate.now().minusMonths(1));
    }
}
