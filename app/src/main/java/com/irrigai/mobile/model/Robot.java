package com.irrigai.mobile.model;

public class Robot {
    private String id;
    private String farmId;
    private String name;
    private int batteryLevel;
    private String status; // ONLINE, OFFLINE, BUSY
    private String healthStatus; // OK, WARNING, CRITICAL
    private long lastSync;
    private String location;
    
    public Robot() {}

    public Robot(String id, String name, int batteryLevel, String status) {
        this.id = id;
        this.name = name;
        this.batteryLevel = batteryLevel;
        this.status = status;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFarmId() { return farmId; }
    public void setFarmId(String farmId) { this.farmId = farmId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getBatteryLevel() { return batteryLevel; }
    public void setBatteryLevel(int batteryLevel) { this.batteryLevel = batteryLevel; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getHealthStatus() { return healthStatus; }
    public void setHealthStatus(String healthStatus) { this.healthStatus = healthStatus; }

    public long getLastSync() { return lastSync; }
    public void setLastSync(long lastSync) { this.lastSync = lastSync; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}

