package com.irrigai.mobile.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.irrigai.mobile.data.local.entity.SensorEntity;

import java.util.List;

@Dao
public interface SensorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(SensorEntity sensor);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<SensorEntity> sensors);

    @Query("SELECT * FROM farm_sensors WHERE farmId = :farmId")
    List<SensorEntity> getSensorsByFarmId(long farmId);

    @Query("DELETE FROM farm_sensors WHERE farmId = :farmId")
    void deleteByFarmId(long farmId);

    @Query("SELECT * FROM farm_sensors")
    List<SensorEntity> getAllSensors();

    @Update
    void update(SensorEntity sensor);

    @Delete
    void delete(SensorEntity sensor);

    @Query("DELETE FROM farm_sensors")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM farm_sensors")
    int getCount();
}
