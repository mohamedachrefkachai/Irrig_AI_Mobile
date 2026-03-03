package com.irrigai.mobile.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "robots")
public class RobotEntity {

    @PrimaryKey
    @NonNull
    public String id;

    @NonNull
    public String name;

    @NonNull
    public String status; // "error", "working", "idle"

    public int batteryLevel;

    @NonNull
    public String location;

    public RobotEntity(@NonNull String id, @NonNull String name, @NonNull String status,
                       int batteryLevel, @NonNull String location) {
        this.id = id;
        this.name = name;
        this.status = status != null ? status : "idle";
        this.batteryLevel = batteryLevel;
        this.location = location != null ? location : "";
    }
}
