package com.pcmc.bwg.dto.auth;

public class LoginResponse {

    private String token;
    private String tokenType = "Bearer";
    private long expiresInMinutes;
    private Long id;
    private String name;
    private String role;

    public LoginResponse(String token, long expiresInMinutes, Long id, String name, String role) {
        this.token = token;
        this.expiresInMinutes = expiresInMinutes;
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public long getExpiresInMinutes() {
        return expiresInMinutes;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
