package com.irrigai.mobile.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.irrigai.mobile.data.local.entity.MaintenanceLogEntity;

import java.util.List;

@Dao
public interface MaintenanceLogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(MaintenanceLogEntity log);

    @Query("SELECT * FROM maintenance_logs WHERE robotId = :robotId ORDER BY maintenanceAtMillis DESC")
    List<MaintenanceLogEntity> getLogsForRobot(String robotId);

    @Query("SELECT * FROM maintenance_logs WHERE robotId = :robotId ORDER BY maintenanceAtMillis DESC LIMIT 1")
    MaintenanceLogEntity getLastLogForRobot(String robotId);
}

