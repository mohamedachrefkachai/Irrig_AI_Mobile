package com.irrigai.mobile.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.irrigai.mobile.data.local.entity.FarmEntity;

import java.util.List;

@Dao
public interface FarmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(FarmEntity farm);

    @Query("SELECT * FROM farms WHERE id = :farmId LIMIT 1")
    FarmEntity getById(long farmId);

    @Query("SELECT * FROM farms WHERE name = :name LIMIT 1")
    FarmEntity getByName(String name);

    @Query("SELECT * FROM farms WHERE ownerId = :ownerId ORDER BY id ASC")
    List<FarmEntity> getByOwnerId(int ownerId);

    @Query("DELETE FROM farms WHERE id = :farmId")
    void deleteById(long farmId);
}
