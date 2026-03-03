package com.irrigai.mobile.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.irrigai.mobile.data.local.entity.StatsEntity;

import java.util.List;

@Dao
public interface StatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<StatsEntity> stats);

    @Query("SELECT * FROM stats ORDER BY id")
    List<StatsEntity> getAllStats();

    @Query("SELECT COUNT(*) FROM stats")
    int getCount();
}
