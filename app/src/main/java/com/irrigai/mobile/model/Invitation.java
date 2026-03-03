package com.irrigai.mobile.model;

public class Invitation {
    private String id;
    private String email;
    private String farmId;
    private String role;
    private String token;
    private String status; // PENDING, ACCEPTED
    private long createdAt;

    public Invitation() {}

    public Invitation(String email, String farmId, String token) {
        this.email = email;
        this.farmId = farmId;
        this.token = token;
        this.role = "WORKER";
        this.status = "PENDING";
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFarmId() { return farmId; }
    public void setFarmId(String farmId) { this.farmId = farmId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}
