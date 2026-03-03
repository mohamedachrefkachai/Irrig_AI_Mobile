package com.irrigai.mobile.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workers")
public class WorkerEntity {

    @PrimaryKey
    @NonNull
    public String id;

    @NonNull
    public String name;

    @NonNull
    public String role;

    @NonNull
    public String status; // "ON_DUTY" or "OFF_DUTY"

    public boolean inDuty;

    @NonNull
    public String remark;

    @NonNull
    public String assignedTask;

    public WorkerEntity(@NonNull String id, @NonNull String name, @NonNull String role,
                        @NonNull String status, boolean inDuty, @NonNull String remark,
                        @NonNull String assignedTask) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.status = status != null ? status : "OFF_DUTY";
        this.inDuty = inDuty;
        this.remark = remark != null ? remark : "";
        this.assignedTask = assignedTask != null ? assignedTask : "";
    }
}
