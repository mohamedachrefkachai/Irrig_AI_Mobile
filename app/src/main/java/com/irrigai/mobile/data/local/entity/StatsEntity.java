package com.irrigai.mobile.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stats")
public class StatsEntity {

    @PrimaryKey
    public int id;

    @NonNull
    public String label; // "Mon", "Tue", etc. or "6h", "12h", etc.

    public double temperature;
    public double wind;
    public double rain;
    public int activeValves;
    public double irrigationDuration;

    public StatsEntity() {
        this.label = "";
    }

    public StatsEntity(int id, String label, double temperature, double wind, double rain,
                       int activeValves, double irrigationDuration) {
        this.id = id;
        this.label = label != null ? label : "";
        this.temperature = temperature;
        this.wind = wind;
        this.rain = rain;
        this.activeValves = activeValves;
        this.irrigationDuration = irrigationDuration;
    }
}
