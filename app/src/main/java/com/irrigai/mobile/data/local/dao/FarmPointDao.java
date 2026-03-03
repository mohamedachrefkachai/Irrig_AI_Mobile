package com.irrigai.mobile.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.irrigai.mobile.data.local.entity.FarmPointEntity;

import java.util.List;

@Dao
public interface FarmPointDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FarmPointEntity> points);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(FarmPointEntity point);

    @Query("SELECT * FROM farm_points WHERE farmId = :farmId ORDER BY pointOrder ASC")
    List<FarmPointEntity> getPointsByFarmId(long farmId);

    @Query("DELETE FROM farm_points WHERE farmId = :farmId")
    void deleteByFarmId(long farmId);

    @Query("SELECT * FROM farm_points ORDER BY pointOrder ASC")
    List<FarmPointEntity> getAllPoints();

    @Query("DELETE FROM farm_points")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM farm_points")
    int getCount();
}
