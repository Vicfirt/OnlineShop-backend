package com.javaschool.onlineshop.model.entity;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.Column;

@Table(name = "order_element")
@Entity
public class OrderElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @OneToOne
    private Product product;

    @Column(name = "quantity")
    private Long quantityInOrder;

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getQuantityInOrder() {
        return quantityInOrder;
    }

    public void setQuantityInOrder(Long quantityInOrder) {
        this.quantityInOrder = quantityInOrder;
    }
}
