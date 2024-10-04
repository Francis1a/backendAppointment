package com.application.appointment.dto;

public class LoginResponse {
    private String email;
    private String role;
    private String message;
    private String token;

    public LoginResponse(String email, String role, String message, String token) {
        this.email = email;
        this.role = role;
        this.message = message;
        this.token = token;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
