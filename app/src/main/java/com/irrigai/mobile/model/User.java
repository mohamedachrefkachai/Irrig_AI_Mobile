package com.irrigai.mobile.model;

public class User {
    private String id;
    private String email;
    private String name;
    private String farmId;
    private String role; // OWNER, WORKER, or superadmin
    
    public User() {}
    
    public User(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFarmId() { return farmId; }
    public void setFarmId(String farmId) { this.farmId = farmId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}

