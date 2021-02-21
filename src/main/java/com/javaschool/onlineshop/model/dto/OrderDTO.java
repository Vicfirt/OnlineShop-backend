package com.javaschool.onlineshop.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public class OrderDTO {

    private Long orderId;

    private Double total;

    @Size(min = 2, max = 40, message = "The number of letters in the country name must be between 2 and 20")
    private String country;

    @Size(min = 2, max = 20, message = "The number of letters in the city name must be between 2 and 20")
    private String city;

    @Size(min = 4, max = 10, message = "The number of characters in the postcode must be be from 4 to 10")
    private Integer postcode;

    @Size(min = 2, max = 40, message = "The number of letters in the street name must be between 2 and 40")
    private String street;

    @Size(min = 1, max = 10000)
    private String building;

    @Size(min = 1, max = 10000)
    private String room;

    @Email
    private String customerEmailAddress;

    @Size(min = 2, max = 16, message = "First name must contain 2 to 16 characters.")
    private String customerFirstName;

    @Size(min = 2, max = 16, message = "Last name must contain 2 to 16 characters.")
    private String customerLastName;

    private String paymentMethod;

    private String shippingType;

    private String status;

    private List<OrderElementDTO> orderElementList;

    private LocalDate date;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPostcode() {
        return postcode;
    }

    public void setPostcode(Integer postcode) {
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getCustomerEmailAddress() {
        return customerEmailAddress;
    }

    public void setCustomerEmailAddress(String customerEmailAddress) {
        this.customerEmailAddress = customerEmailAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderElementDTO> getOrderElementList() {
        return orderElementList;
    }

    public void setOrderElementList(List<OrderElementDTO> orderElementList) {
        this.orderElementList = orderElementList;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }
}
