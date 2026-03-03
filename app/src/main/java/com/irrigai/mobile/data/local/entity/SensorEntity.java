package com.irrigai.mobile.data.local.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Sensor on the farm. type: moisture, temperature, ph. x,y normalized 0-1.
 */
@Entity(tableName = "farm_sensors")
public class SensorEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long farmId;
    public float x;
    public float y;
    public String type;   // "moisture", "temperature", "ph"
    public String status; // "ok", "warning", "error"
    public String lastValue;

    public SensorEntity() {}

    @Ignore
    public SensorEntity(long farmId, float x, float y, String type) {
        this.farmId = farmId;
        this.x = x;
        this.y = y;
        this.type = type;
        this.status = "ok";
        this.lastValue = "";
    }
}
