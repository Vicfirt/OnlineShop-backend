package com.javaschool.onlineshop.model.dto;

public class AuthenticationRequestDTO {

    private String username;

    private String password;

    public AuthenticationRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AuthenticationRequestDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
