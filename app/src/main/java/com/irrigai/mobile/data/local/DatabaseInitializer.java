package com.irrigai.mobile.data.local;

import android.content.Context;

import com.irrigai.mobile.data.local.entity.DashboardEntity;
import com.irrigai.mobile.data.local.entity.RobotEntity;
import com.irrigai.mobile.data.local.entity.StatsEntity;
import com.irrigai.mobile.data.local.entity.WeatherEntity;
import com.irrigai.mobile.data.local.entity.WorkerEntity;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Seeds the local SQLite database with mock data for testing.
 * Called on first app launch or when tables are empty.
 */
public class DatabaseInitializer {

    public static void seedIfEmpty(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // Dashboard
                if (db.dashboardDao().getCount() == 0) {
                    db.dashboardDao().insert(new DashboardEntity(
                            24.5, 12.3, true, 3, 4, 5
                    ));
                }

                // Robots
                if (db.robotDao().getCount() == 0) {
                    List<RobotEntity> robots = Arrays.asList(
                            new RobotEntity("r1", "Robot Alpha", "working", 85, "Zone 1"),
                            new RobotEntity("r2", "Robot Beta", "idle", 92, "Zone 2"),
                            new RobotEntity("r3", "Robot Gamma", "error", 45, "Zone 1"),
                            new RobotEntity("r4", "Robot Delta", "working", 78, "Zone 3")
                    );
                    db.robotDao().insertAll(robots);
                }

                // Workers
                if (db.workerDao().getCount() == 0) {
                    List<WorkerEntity> workers = Arrays.asList(
                            new WorkerEntity("w1", "Jean Dupont", "Technician", "ON_DUTY", true, "", "Check Zone 1 valves"),
                            new WorkerEntity("w2", "Marie Martin", "Operator", "OFF_DUTY", false, "Returns Monday", ""),
                            new WorkerEntity("w3", "Paul Bernard", "Supervisor", "ON_DUTY", true, "", "Robot maintenance")
                    );
                    db.workerDao().insertAll(workers);
                }

                // Weather
                if (db.weatherDao().getCount() == 0) {
                    db.weatherDao().insert(new WeatherEntity(
                            24.5, 12.0, "Partly cloudy", "partly_cloudy",
                            65.0, 0.15, "Normal irrigation recommended",
                            true, 26.0, "Clear", 0.1
                    ));
                }

                // Stats for charts (last 7 days)
                if (db.statsDao().getCount() == 0) {
                    List<StatsEntity> stats = Arrays.asList(
                            new StatsEntity(1, "Mon", 22, 10, 0, 4, 45),
                            new StatsEntity(2, "Tue", 24, 12, 5, 5, 60),
                            new StatsEntity(3, "Wed", 23, 8, 0, 5, 50),
                            new StatsEntity(4, "Thu", 25, 15, 2, 6, 55),
                            new StatsEntity(5, "Fri", 26, 11, 0, 5, 70),
                            new StatsEntity(6, "Sat", 24, 9, 10, 3, 30),
                            new StatsEntity(7, "Sun", 23, 14, 0, 5, 48)
                    );
                    db.statsDao().insertAll(stats);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
