package com.irrigai.mobile.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.irrigai.mobile.data.local.entity.DashboardEntity;

@Dao
public interface DashboardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DashboardEntity dashboard);

    @Query("SELECT * FROM dashboard WHERE id = 1 LIMIT 1")
    DashboardEntity getDashboard();

    @Query("SELECT COUNT(*) FROM dashboard")
    int getCount();
}
