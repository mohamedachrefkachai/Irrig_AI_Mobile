package com.irrigai.mobile.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.irrigai.mobile.data.local.dao.DashboardDao;
import com.irrigai.mobile.data.local.dao.FarmDao;
import com.irrigai.mobile.data.local.dao.FarmPointDao;
import com.irrigai.mobile.data.local.dao.FarmWorkerDao;
import com.irrigai.mobile.data.local.dao.RobotDao;
import com.irrigai.mobile.data.local.dao.RobotTaskDao;
import com.irrigai.mobile.data.local.dao.SensorDao;
import com.irrigai.mobile.data.local.dao.StatsDao;
import com.irrigai.mobile.data.local.dao.MaintenanceLogDao;
import com.irrigai.mobile.data.local.dao.TreeDao;
import com.irrigai.mobile.data.local.dao.UserDao;
import com.irrigai.mobile.data.local.dao.ValveDao;
import com.irrigai.mobile.data.local.dao.WeatherDao;
import com.irrigai.mobile.data.local.dao.WorkerDao;
import com.irrigai.mobile.data.local.dao.ZoneAreaDao;
import com.irrigai.mobile.data.local.entity.DashboardEntity;
import com.irrigai.mobile.data.local.entity.FarmEntity;
import com.irrigai.mobile.data.local.entity.FarmPointEntity;
import com.irrigai.mobile.data.local.entity.FarmWorkerCrossRefEntity;
import com.irrigai.mobile.data.local.entity.MaintenanceLogEntity;
import com.irrigai.mobile.data.local.entity.RobotEntity;
import com.irrigai.mobile.data.local.entity.SensorEntity;
import com.irrigai.mobile.data.local.entity.StatsEntity;
import com.irrigai.mobile.data.local.entity.TreeEntity;
import com.irrigai.mobile.data.local.entity.UserEntity;
import com.irrigai.mobile.data.local.entity.ValveEntity;
import com.irrigai.mobile.data.local.entity.WeatherEntity;
import com.irrigai.mobile.data.local.entity.RobotTaskEntity;
import com.irrigai.mobile.data.local.entity.WorkerEntity;
import com.irrigai.mobile.data.local.entity.ZoneAreaEntity;

@Database(entities = { UserEntity.class, DashboardEntity.class, RobotEntity.class,
        WorkerEntity.class, WeatherEntity.class, StatsEntity.class,
        FarmEntity.class, FarmPointEntity.class, TreeEntity.class, ValveEntity.class,
        SensorEntity.class, ZoneAreaEntity.class,
        FarmWorkerCrossRefEntity.class, RobotTaskEntity.class, MaintenanceLogEntity.class }, version = 7, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract FarmDao farmDao();
    public abstract DashboardDao dashboardDao();
    public abstract RobotDao robotDao();
    public abstract FarmWorkerDao farmWorkerDao();
    public abstract RobotTaskDao robotTaskDao();
    public abstract MaintenanceLogDao maintenanceLogDao();
    public abstract WorkerDao workerDao();
    public abstract WeatherDao weatherDao();
    public abstract StatsDao statsDao();
    public abstract FarmPointDao farmPointDao();
    public abstract TreeDao treeDao();
    public abstract ValveDao valveDao();
    public abstract SensorDao sensorDao();
    public abstract ZoneAreaDao zoneAreaDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "irrigai_local_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
