package com.irrigai.mobile.data.local.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Valve placed on the farm. x, y normalized 0-1. rotationAngle in degrees. isActive for simulation.
 */
@Entity(tableName = "farm_valves")
public class ValveEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long farmId;
    public float x;
    public float y;
    public float rotationAngle;
    public boolean isActive;

    public ValveEntity() {}

    @Ignore
    public ValveEntity(long farmId, float x, float y) {
        this.farmId = farmId;
        this.x = x;
        this.y = y;
        this.rotationAngle = 0f;
        this.isActive = true;
    }

    @Ignore
    public ValveEntity(long farmId, float x, float y, float rotationAngle, boolean isActive) {
        this.farmId = farmId;
        this.x = x;
        this.y = y;
        this.rotationAngle = rotationAngle;
        this.isActive = isActive;
    }
}
