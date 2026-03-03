package com.irrigai.mobile.model;

public class Worker {
    private String id;
    private String userId;
    private String farmId;
    private String fullName;
    private String phone;
    private String photoUrl;
    private String status; // ACTIVE, INVITED
    private long createdAt;
    
    public Worker() {}

    public Worker(String id, String userId, String farmId, String fullName) {
        this.id = id;
        this.userId = userId;
        this.farmId = farmId;
        this.fullName = fullName;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getFarmId() { return farmId; }
    public void setFarmId(String farmId) { this.farmId = farmId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}

