package com.irrigai.mobile.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.irrigai.mobile.data.local.entity.ValveEntity;

import java.util.List;

@Dao
public interface ValveDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ValveEntity valve);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ValveEntity> valves);

    @Query("SELECT * FROM farm_valves WHERE farmId = :farmId")
    List<ValveEntity> getValvesByFarmId(long farmId);

    @Query("DELETE FROM farm_valves WHERE farmId = :farmId")
    void deleteByFarmId(long farmId);

    @Query("SELECT * FROM farm_valves")
    List<ValveEntity> getAllValves();

    @Query("DELETE FROM farm_valves")
    void deleteAll();

    @Update
    void update(ValveEntity valve);

    @Delete
    void delete(ValveEntity valve);

    @Query("SELECT COUNT(*) FROM farm_valves")
    int getCount();
}
