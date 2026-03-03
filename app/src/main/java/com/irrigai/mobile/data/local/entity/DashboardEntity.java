package com.irrigai.mobile.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dashboard")
public class DashboardEntity {

    @PrimaryKey
    public int id = 1; // Single row

    public double temperature;
    public double windSpeed;
    public boolean isDay;
    public int workersCount;
    public int robotsCount;
    public int activeValves;

    public DashboardEntity() {}

    public DashboardEntity(double temperature, double windSpeed, boolean isDay,
                           int workersCount, int robotsCount, int activeValves) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.isDay = isDay;
        this.workersCount = workersCount;
        this.robotsCount = robotsCount;
        this.activeValves = activeValves;
    }
}
