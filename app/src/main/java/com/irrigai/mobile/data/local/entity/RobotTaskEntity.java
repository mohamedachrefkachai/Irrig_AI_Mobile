package com.irrigai.mobile.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents a logical task assigned to a robot within a farm.
 */
@Entity(tableName = "robot_tasks")
public class RobotTaskEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    public String robotId;

    public long farmId;

    @NonNull
    public String title;

    public String status; // e.g. "PENDING", "RUNNING", "COMPLETED"

    public Long startedAtMillis;

    public Long completedAtMillis;

    public RobotTaskEntity(@NonNull String robotId,
                           long farmId,
                           @NonNull String title,
                           String status,
                           Long startedAtMillis,
                           Long completedAtMillis) {
        this.robotId = robotId;
        this.farmId = farmId;
        this.title = title;
        this.status = status != null ? status : "PENDING";
        this.startedAtMillis = startedAtMillis;
        this.completedAtMillis = completedAtMillis;
    }
}

