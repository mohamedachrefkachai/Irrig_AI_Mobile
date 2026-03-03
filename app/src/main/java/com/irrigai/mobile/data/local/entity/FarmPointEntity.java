package com.irrigai.mobile.data.local.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * A point on the farm perimeter. Order defines the polygon.
 * x, y are normalized 0-1 for scale-independent storage.
 */
@Entity(tableName = "farm_points")
public class FarmPointEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long farmId;

    /** Order of the point in the perimeter (0, 1, 2, ...). */
    public int pointOrder;

    /** Normalized x (0-1). */
    public float x;

    /** Normalized y (0-1). */
    public float y;

    public FarmPointEntity() {}

    @Ignore
    public FarmPointEntity(long farmId, int pointOrder, float x, float y) {
        this.farmId = farmId;
        this.pointOrder = pointOrder;
        this.x = x;
        this.y = y;
    }
}
