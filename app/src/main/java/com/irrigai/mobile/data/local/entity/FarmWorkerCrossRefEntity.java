package com.irrigai.mobile.data.local.entity;

import androidx.room.Entity;

/**
 * Cross-reference entity for many-to-many relationship between farms and workers.
 * workerUserId references UserEntity.id for a user with userType = "worker".
 */
@Entity(tableName = "farm_worker_cross_ref", primaryKeys = { "farmId", "workerUserId" })
public class FarmWorkerCrossRefEntity {

    public long farmId;

    public int workerUserId;

    public FarmWorkerCrossRefEntity(long farmId, int workerUserId) {
        this.farmId = farmId;
        this.workerUserId = workerUserId;
    }
}

