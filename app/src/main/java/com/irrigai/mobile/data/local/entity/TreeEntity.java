package com.irrigai.mobile.data.local.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Tree placed on the farm. x, y normalized 0-1. rotationAngle in degrees.
 */
@Entity(tableName = "farm_trees")
public class TreeEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long farmId;
    public float x;
    public float y;
    public float rotationAngle;

    public TreeEntity() {}

    @Ignore
    public TreeEntity(long farmId, float x, float y) {
        this.farmId = farmId;
        this.x = x;
        this.y = y;
        this.rotationAngle = 0f;
    }

    @Ignore
    public TreeEntity(long farmId, float x, float y, float rotationAngle) {
        this.farmId = farmId;
        this.x = x;
        this.y = y;
        this.rotationAngle = rotationAngle;
    }
}
