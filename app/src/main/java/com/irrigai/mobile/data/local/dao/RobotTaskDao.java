package com.irrigai.mobile.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.irrigai.mobile.data.local.entity.RobotTaskEntity;

import java.util.List;

@Dao
public interface RobotTaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(RobotTaskEntity task);

    @Query("SELECT * FROM robot_tasks WHERE robotId = :robotId ORDER BY id DESC")
    List<RobotTaskEntity> getTasksForRobot(String robotId);

    @Query("SELECT * FROM robot_tasks WHERE robotId = :robotId ORDER BY id DESC LIMIT 1")
    RobotTaskEntity getLastTaskForRobot(String robotId);
}

