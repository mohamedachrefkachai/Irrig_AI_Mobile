package com.irrigai.mobile.data.local.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Colored zone (polygon). pointsJson format: "x1,y1;x2,y2;x3,y3;..."
 * color: hex int or 0 for default pastel.
 */
@Entity(tableName = "farm_zones")
public class ZoneAreaEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long farmId;
    public int color;
    public String pointsJson;

    public ZoneAreaEntity() {}

    @Ignore
    public ZoneAreaEntity(long farmId, int color, String pointsJson) {
        this.farmId = farmId;
        this.color = color;
        this.pointsJson = pointsJson != null ? pointsJson : "";
    }
}
