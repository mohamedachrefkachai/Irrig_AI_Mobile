package com.irrigai.mobile.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.irrigai.mobile.data.local.entity.ZoneAreaEntity;

import java.util.List;

@Dao
public interface ZoneAreaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ZoneAreaEntity zone);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ZoneAreaEntity> zones);

    @Query("SELECT * FROM farm_zones WHERE farmId = :farmId")
    List<ZoneAreaEntity> getZonesByFarmId(long farmId);

    @Query("DELETE FROM farm_zones WHERE farmId = :farmId")
    void deleteByFarmId(long farmId);

    @Query("SELECT * FROM farm_zones")
    List<ZoneAreaEntity> getAllZones();

    @Delete
    void delete(ZoneAreaEntity zone);

    @Query("DELETE FROM farm_zones")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM farm_zones")
    int getCount();
}
