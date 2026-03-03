package com.irrigai.mobile.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Maintenance history for robots, linked to a farm.
 */
@Entity(tableName = "maintenance_logs")
public class MaintenanceLogEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    public String robotId;

    public long farmId;

    public String description;

    /** UTC millis when maintenance occurred. */
    public Long maintenanceAtMillis;

    /** Cumulative hours worked at maintenance time, if tracked. */
    public Integer hoursWorked;

    public String performedBy;

    public MaintenanceLogEntity(@NonNull String robotId,
                                long farmId,
                                String description,
                                Long maintenanceAtMillis,
                                Integer hoursWorked,
                                String performedBy) {
        this.robotId = robotId;
        this.farmId = farmId;
        this.description = description;
        this.maintenanceAtMillis = maintenanceAtMillis;
        this.hoursWorked = hoursWorked;
        this.performedBy = performedBy;
    }
}

