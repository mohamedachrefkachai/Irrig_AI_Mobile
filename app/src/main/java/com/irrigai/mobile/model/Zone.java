package com.irrigai.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class Zone {
    private String id;
    private String farmId;
    private String name;
    private String cropType;
    private double width;
    private double length;
    private double x; // position x
    private double y; // position y
    private String mode; // AUTO, MANUAL
    private double moistureThreshold;
    private long createdAt;
    private List<Tree> trees = new ArrayList<>();

    public Zone() {}

    public Zone(String id, String name, String cropType, double width, double length) {
        this.id = id;
        this.name = name;
        this.cropType = cropType;
        this.width = width;
        this.length = length;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFarmId() { return farmId; }
    public void setFarmId(String farmId) { this.farmId = farmId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCropType() { return cropType; }
    public void setCropType(String cropType) { this.cropType = cropType; }

    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }

    public double getLength() { return length; }
    public void setLength(double length) { this.length = length; }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public double getMoistureThreshold() { return moistureThreshold; }
    public void setMoistureThreshold(double moistureThreshold) { this.moistureThreshold = moistureThreshold; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public List<Tree> getTrees() { return trees; }
    public void setTrees(List<Tree> trees) { this.trees = trees; }
}
