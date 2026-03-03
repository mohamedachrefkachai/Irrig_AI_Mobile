package com.irrigai.mobile.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.irrigai.mobile.data.local.entity.WorkerEntity;

import java.util.List;

@Dao
public interface WorkerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<WorkerEntity> workers);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WorkerEntity worker);

    @Update
    void update(WorkerEntity worker);

    @Query("SELECT * FROM workers WHERE id = :workerId LIMIT 1")
    WorkerEntity getWorkerById(String workerId);

    @Query("SELECT * FROM workers ORDER BY name")
    List<WorkerEntity> getAllWorkers();

    @Query("UPDATE workers SET assignedTask = :task, remark = :remark WHERE id = :workerId")
    void updateTaskAndRemark(String workerId, String task, String remark);

    @Query("UPDATE workers SET status = :status, inDuty = :inDuty WHERE id = :workerId")
    void updateStatus(String workerId, String status, boolean inDuty);

    @Query("SELECT COUNT(*) FROM workers")
    int getCount();

    @Query("SELECT COUNT(*) FROM workers WHERE status = 'ON_DUTY'")
    int getOnDutyCount();
}
