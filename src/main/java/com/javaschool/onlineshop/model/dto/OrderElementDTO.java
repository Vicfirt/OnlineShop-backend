package com.javaschool.onlineshop.model.dto;


public class OrderElementDTO {

    private Long orderItemId;

    private ProductDTO product;

    private Long quantityInOrder;

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public Long getQuantityInOrder() {
        return quantityInOrder;
    }

    public void setQuantityInOrder(Long quantityInOrder) {
        this.quantityInOrder = quantityInOrder;
    }
}
