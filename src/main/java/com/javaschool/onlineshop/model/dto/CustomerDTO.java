package com.javaschool.onlineshop.model.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class CustomerDTO {

    private Long customerId;

    @Size(min = 2, max = 16, message = "First name must contain 2 to 16 characters.")
    private String customerFirstName;

    @Size(min = 2, max = 16, message = "Last name must contain 2 to 16 characters.")
    private String customerLastName;

    @PastOrPresent
    private LocalDate customerDateOfBirth;

    @Email(message = "Please, enter correct email address.")
    private String customerEmailAddress;

    @Size(min = 6, max = 15, message = "A password must contain 6 to 15 characters.")
    private String customerPassword;

    private String role;

    private Boolean active;

    @Size(min = 7, max = 11, message = "Your phone number myst contain 7 to 11 numbers.")
    private String phoneNumber;

    @Size(min = 2, max = 40, message = "The number of letters in the country name must be between 2 and 20")
    private String country;

    @Size(min = 2, max = 20, message = "The number of letters in the city name must be between 2 and 20")
    private String city;

    @Size(min = 4, max = 10, message = "The number of characters in the postcode must be be from 4 to 10")
    private String postcode;

    @Size(min = 2, max = 40, message = "The number of letters in the street name must be between 2 and 40")
    private String street;

    @Size(min = 1, max = 10000)
    private String building;

    @Size(min = 1, max = 10000)
    private String room;


    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    public LocalDate getCustomerDateOfBirth() {
        return customerDateOfBirth;
    }

    public void setCustomerDateOfBirth(LocalDate customerDateOfBirth) {
        this.customerDateOfBirth = customerDateOfBirth;
    }

    public String getCustomerEmailAddress() {
        return customerEmailAddress;
    }

    public void setCustomerEmailAddress(String customerEmailAddress) {
        this.customerEmailAddress = customerEmailAddress;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
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
}