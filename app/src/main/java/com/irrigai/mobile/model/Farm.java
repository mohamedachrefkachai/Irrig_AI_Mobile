package com.irrigai.mobile.model;

public class Farm {
    private String id;
    private String ownerId;
    private String name;
    private String location;
    private double width;
    private double length;
    private long createdAt;

    public Farm() {}

    public Farm(String id, String name, String location, double width, double length) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.width = width;
        this.length = length;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }

    public double getLength() { return length; }
    public void setLength(double length) { this.length = length; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}
