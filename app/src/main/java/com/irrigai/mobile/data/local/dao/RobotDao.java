package com.irrigai.mobile.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.irrigai.mobile.data.local.entity.RobotEntity;

import java.util.List;

@Dao
public interface RobotDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RobotEntity> robots);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RobotEntity robot);

    @Query("SELECT * FROM robots ORDER BY name")
    List<RobotEntity> getAllRobots();

    @Query("SELECT * FROM robots WHERE id = :id LIMIT 1")
    RobotEntity getById(String id);

    @Query("UPDATE robots SET status = :status WHERE id = :id")
    void updateStatus(String id, String status);

    @Query("SELECT COUNT(*) FROM robots")
    int getCount();
}
