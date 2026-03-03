package com.irrigai.mobile.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.irrigai.mobile.data.local.entity.WeatherEntity;

@Dao
public interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeatherEntity weather);

    @Query("SELECT * FROM weather WHERE id = 1 LIMIT 1")
    WeatherEntity getWeather();

    @Query("SELECT COUNT(*) FROM weather")
    int getCount();
}
