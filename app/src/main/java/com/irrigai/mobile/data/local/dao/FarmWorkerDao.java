package com.irrigai.mobile.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.irrigai.mobile.data.local.entity.FarmEntity;
import com.irrigai.mobile.data.local.entity.FarmWorkerCrossRefEntity;

import java.util.List;

@Dao
public interface FarmWorkerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FarmWorkerCrossRefEntity ref);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FarmWorkerCrossRefEntity> refs);

    @Query("DELETE FROM farm_worker_cross_ref WHERE farmId = :farmId AND workerUserId = :workerUserId")
    void delete(long farmId, int workerUserId);

    @Query("SELECT * FROM farm_worker_cross_ref WHERE workerUserId = :workerUserId")
    List<FarmWorkerCrossRefEntity> getByWorkerUserId(int workerUserId);

    @Query("SELECT farms.* FROM farms INNER JOIN farm_worker_cross_ref " +
           "ON farms.id = farm_worker_cross_ref.farmId " +
           "WHERE farm_worker_cross_ref.workerUserId = :workerUserId " +
           "ORDER BY farms.id ASC")
    List<FarmEntity> getFarmsForWorker(int workerUserId);
}

