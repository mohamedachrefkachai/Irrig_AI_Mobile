package com.irrigai.mobile.model;

public class Dashboard {
    private double temperature;
    private double windSpeed;
    private boolean isDay;
    private int workersCount;
    private int robotsCount;
    private int activeValves;
    
    public Dashboard() {}
    
    // Getters and Setters
    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }
    
    public double getWindSpeed() { return windSpeed; }
    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }
    
    public boolean isDay() { return isDay; }
    public void setDay(boolean day) { isDay = day; }
    
    public int getWorkersCount() { return workersCount; }
    public void setWorkersCount(int workersCount) { this.workersCount = workersCount; }
    
    public int getRobotsCount() { return robotsCount; }
    public void setRobotsCount(int robotsCount) { this.robotsCount = robotsCount; }
    
    public int getActiveValves() { return activeValves; }
    public void setActiveValves(int activeValves) { this.activeValves = activeValves; }
}

