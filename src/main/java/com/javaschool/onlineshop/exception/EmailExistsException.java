package com.javaschool.onlineshop.exception;

public class EmailExistsException extends RuntimeException {

    private final String customerEmailAddressError;

    public EmailExistsException(String customerEmailAddressError) {
        this.customerEmailAddressError = customerEmailAddressError;
    }

    public String getCustomerEmailAddressError() {
        return customerEmailAddressError;
    }
}
