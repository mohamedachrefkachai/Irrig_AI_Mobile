package com.irrigai.mobile.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.irrigai.mobile.data.local.dao.DashboardDao;
import com.irrigai.mobile.data.local.dao.DashboardDao_Impl;
import com.irrigai.mobile.data.local.dao.FarmDao;
import com.irrigai.mobile.data.local.dao.FarmDao_Impl;
import com.irrigai.mobile.data.local.dao.FarmPointDao;
import com.irrigai.mobile.data.local.dao.FarmPointDao_Impl;
import com.irrigai.mobile.data.local.dao.FarmWorkerDao;
import com.irrigai.mobile.data.local.dao.FarmWorkerDao_Impl;
import com.irrigai.mobile.data.local.dao.MaintenanceLogDao;
import com.irrigai.mobile.data.local.dao.MaintenanceLogDao_Impl;
import com.irrigai.mobile.data.local.dao.RobotDao;
import com.irrigai.mobile.data.local.dao.RobotDao_Impl;
import com.irrigai.mobile.data.local.dao.RobotTaskDao;
import com.irrigai.mobile.data.local.dao.RobotTaskDao_Impl;
import com.irrigai.mobile.data.local.dao.SensorDao;
import com.irrigai.mobile.data.local.dao.SensorDao_Impl;
import com.irrigai.mobile.data.local.dao.StatsDao;
import com.irrigai.mobile.data.local.dao.StatsDao_Impl;
import com.irrigai.mobile.data.local.dao.TreeDao;
import com.irrigai.mobile.data.local.dao.TreeDao_Impl;
import com.irrigai.mobile.data.local.dao.UserDao;
import com.irrigai.mobile.data.local.dao.UserDao_Impl;
import com.irrigai.mobile.data.local.dao.ValveDao;
import com.irrigai.mobile.data.local.dao.ValveDao_Impl;
import com.irrigai.mobile.data.local.dao.WeatherDao;
import com.irrigai.mobile.data.local.dao.WeatherDao_Impl;
import com.irrigai.mobile.data.local.dao.WorkerDao;
import com.irrigai.mobile.data.local.dao.WorkerDao_Impl;
import com.irrigai.mobile.data.local.dao.ZoneAreaDao;
import com.irrigai.mobile.data.local.dao.ZoneAreaDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile UserDao _userDao;

  private volatile FarmDao _farmDao;

  private volatile DashboardDao _dashboardDao;

  private volatile RobotDao _robotDao;

  private volatile FarmWorkerDao _farmWorkerDao;

  private volatile RobotTaskDao _robotTaskDao;

  private volatile MaintenanceLogDao _maintenanceLogDao;

  private volatile WorkerDao _workerDao;

  private volatile WeatherDao _weatherDao;

  private volatile StatsDao _statsDao;

  private volatile FarmPointDao _farmPointDao;

  private volatile TreeDao _treeDao;

  private volatile ValveDao _valveDao;

  private volatile SensorDao _sensorDao;

  private volatile ZoneAreaDao _zoneAreaDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(7) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `email` TEXT NOT NULL, `password` TEXT NOT NULL, `name` TEXT NOT NULL, `userType` TEXT NOT NULL, `farmName` TEXT, `workerIdentifier` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `dashboard` (`id` INTEGER NOT NULL, `temperature` REAL NOT NULL, `windSpeed` REAL NOT NULL, `isDay` INTEGER NOT NULL, `workersCount` INTEGER NOT NULL, `robotsCount` INTEGER NOT NULL, `activeValves` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `robots` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `status` TEXT NOT NULL, `batteryLevel` INTEGER NOT NULL, `location` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `workers` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `role` TEXT NOT NULL, `status` TEXT NOT NULL, `inDuty` INTEGER NOT NULL, `remark` TEXT NOT NULL, `assignedTask` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `weather` (`id` INTEGER NOT NULL, `temperature` REAL NOT NULL, `windSpeed` REAL NOT NULL, `condition` TEXT, `icon` TEXT, `humidity` REAL NOT NULL, `rainPrediction` REAL, `irrigationSuggestion` TEXT, `isDay` INTEGER NOT NULL, `tomorrowTemp` REAL NOT NULL, `tomorrowCondition` TEXT, `tomorrowRainChance` REAL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `stats` (`id` INTEGER NOT NULL, `label` TEXT NOT NULL, `temperature` REAL NOT NULL, `wind` REAL NOT NULL, `rain` REAL NOT NULL, `activeValves` INTEGER NOT NULL, `irrigationDuration` REAL NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `farms` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `ownerId` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `farm_points` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `farmId` INTEGER NOT NULL, `pointOrder` INTEGER NOT NULL, `x` REAL NOT NULL, `y` REAL NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `farm_trees` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `farmId` INTEGER NOT NULL, `x` REAL NOT NULL, `y` REAL NOT NULL, `rotationAngle` REAL NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `farm_valves` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `farmId` INTEGER NOT NULL, `x` REAL NOT NULL, `y` REAL NOT NULL, `rotationAngle` REAL NOT NULL, `isActive` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `farm_sensors` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `farmId` INTEGER NOT NULL, `x` REAL NOT NULL, `y` REAL NOT NULL, `type` TEXT, `status` TEXT, `lastValue` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `farm_zones` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `farmId` INTEGER NOT NULL, `color` INTEGER NOT NULL, `pointsJson` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `farm_worker_cross_ref` (`farmId` INTEGER NOT NULL, `workerUserId` INTEGER NOT NULL, PRIMARY KEY(`farmId`, `workerUserId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `robot_tasks` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `robotId` TEXT NOT NULL, `farmId` INTEGER NOT NULL, `title` TEXT NOT NULL, `status` TEXT, `startedAtMillis` INTEGER, `completedAtMillis` INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `maintenance_logs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `robotId` TEXT NOT NULL, `farmId` INTEGER NOT NULL, `description` TEXT, `maintenanceAtMillis` INTEGER, `hoursWorked` INTEGER, `performedBy` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'f477f4bafdd3e813ade3f8699be35dcf')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `users`");
        db.execSQL("DROP TABLE IF EXISTS `dashboard`");
        db.execSQL("DROP TABLE IF EXISTS `robots`");
        db.execSQL("DROP TABLE IF EXISTS `workers`");
        db.execSQL("DROP TABLE IF EXISTS `weather`");
        db.execSQL("DROP TABLE IF EXISTS `stats`");
        db.execSQL("DROP TABLE IF EXISTS `farms`");
        db.execSQL("DROP TABLE IF EXISTS `farm_points`");
        db.execSQL("DROP TABLE IF EXISTS `farm_trees`");
        db.execSQL("DROP TABLE IF EXISTS `farm_valves`");
        db.execSQL("DROP TABLE IF EXISTS `farm_sensors`");
        db.execSQL("DROP TABLE IF EXISTS `farm_zones`");
        db.execSQL("DROP TABLE IF EXISTS `farm_worker_cross_ref`");
        db.execSQL("DROP TABLE IF EXISTS `robot_tasks`");
        db.execSQL("DROP TABLE IF EXISTS `maintenance_logs`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(7);
        _columnsUsers.put("id", new TableInfo.Column("id", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("email", new TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("password", new TableInfo.Column("password", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("userType", new TableInfo.Column("userType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("farmName", new TableInfo.Column("farmName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("workerIdentifier", new TableInfo.Column("workerIdentifier", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.irrigai.mobile.data.local.entity.UserEntity).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final HashMap<String, TableInfo.Column> _columnsDashboard = new HashMap<String, TableInfo.Column>(7);
        _columnsDashboard.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDashboard.put("temperature", new TableInfo.Column("temperature", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDashboard.put("windSpeed", new TableInfo.Column("windSpeed", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDashboard.put("isDay", new TableInfo.Column("isDay", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDashboard.put("workersCount", new TableInfo.Column("workersCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDashboard.put("robotsCount", new TableInfo.Column("robotsCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDashboard.put("activeValves", new TableInfo.Column("activeValves", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDashboard = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDashboard = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDashboard = new TableInfo("dashboard", _columnsDashboard, _foreignKeysDashboard, _indicesDashboard);
        final TableInfo _existingDashboard = TableInfo.read(db, "dashboard");
        if (!_infoDashboard.equals(_existingDashboard)) {
          return new RoomOpenHelper.ValidationResult(false, "dashboard(com.irrigai.mobile.data.local.entity.DashboardEntity).\n"
                  + " Expected:\n" + _infoDashboard + "\n"
                  + " Found:\n" + _existingDashboard);
        }
        final HashMap<String, TableInfo.Column> _columnsRobots = new HashMap<String, TableInfo.Column>(5);
        _columnsRobots.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRobots.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRobots.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRobots.put("batteryLevel", new TableInfo.Column("batteryLevel", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRobots.put("location", new TableInfo.Column("location", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRobots = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRobots = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRobots = new TableInfo("robots", _columnsRobots, _foreignKeysRobots, _indicesRobots);
        final TableInfo _existingRobots = TableInfo.read(db, "robots");
        if (!_infoRobots.equals(_existingRobots)) {
          return new RoomOpenHelper.ValidationResult(false, "robots(com.irrigai.mobile.data.local.entity.RobotEntity).\n"
                  + " Expected:\n" + _infoRobots + "\n"
                  + " Found:\n" + _existingRobots);
        }
        final HashMap<String, TableInfo.Column> _columnsWorkers = new HashMap<String, TableInfo.Column>(7);
        _columnsWorkers.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkers.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkers.put("role", new TableInfo.Column("role", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkers.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkers.put("inDuty", new TableInfo.Column("inDuty", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkers.put("remark", new TableInfo.Column("remark", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkers.put("assignedTask", new TableInfo.Column("assignedTask", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWorkers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWorkers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWorkers = new TableInfo("workers", _columnsWorkers, _foreignKeysWorkers, _indicesWorkers);
        final TableInfo _existingWorkers = TableInfo.read(db, "workers");
        if (!_infoWorkers.equals(_existingWorkers)) {
          return new RoomOpenHelper.ValidationResult(false, "workers(com.irrigai.mobile.data.local.entity.WorkerEntity).\n"
                  + " Expected:\n" + _infoWorkers + "\n"
                  + " Found:\n" + _existingWorkers);
        }
        final HashMap<String, TableInfo.Column> _columnsWeather = new HashMap<String, TableInfo.Column>(12);
        _columnsWeather.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("temperature", new TableInfo.Column("temperature", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("windSpeed", new TableInfo.Column("windSpeed", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("condition", new TableInfo.Column("condition", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("icon", new TableInfo.Column("icon", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("humidity", new TableInfo.Column("humidity", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("rainPrediction", new TableInfo.Column("rainPrediction", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("irrigationSuggestion", new TableInfo.Column("irrigationSuggestion", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("isDay", new TableInfo.Column("isDay", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("tomorrowTemp", new TableInfo.Column("tomorrowTemp", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("tomorrowCondition", new TableInfo.Column("tomorrowCondition", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("tomorrowRainChance", new TableInfo.Column("tomorrowRainChance", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWeather = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWeather = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWeather = new TableInfo("weather", _columnsWeather, _foreignKeysWeather, _indicesWeather);
        final TableInfo _existingWeather = TableInfo.read(db, "weather");
        if (!_infoWeather.equals(_existingWeather)) {
          return new RoomOpenHelper.ValidationResult(false, "weather(com.irrigai.mobile.data.local.entity.WeatherEntity).\n"
                  + " Expected:\n" + _infoWeather + "\n"
                  + " Found:\n" + _existingWeather);
        }
        final HashMap<String, TableInfo.Column> _columnsStats = new HashMap<String, TableInfo.Column>(7);
        _columnsStats.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStats.put("label", new TableInfo.Column("label", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStats.put("temperature", new TableInfo.Column("temperature", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStats.put("wind", new TableInfo.Column("wind", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStats.put("rain", new TableInfo.Column("rain", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStats.put("activeValves", new TableInfo.Column("activeValves", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStats.put("irrigationDuration", new TableInfo.Column("irrigationDuration", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStats = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesStats = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoStats = new TableInfo("stats", _columnsStats, _foreignKeysStats, _indicesStats);
        final TableInfo _existingStats = TableInfo.read(db, "stats");
        if (!_infoStats.equals(_existingStats)) {
          return new RoomOpenHelper.ValidationResult(false, "stats(com.irrigai.mobile.data.local.entity.StatsEntity).\n"
                  + " Expected:\n" + _infoStats + "\n"
                  + " Found:\n" + _existingStats);
        }
        final HashMap<String, TableInfo.Column> _columnsFarms = new HashMap<String, TableInfo.Column>(3);
        _columnsFarms.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarms.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarms.put("ownerId", new TableInfo.Column("ownerId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFarms = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFarms = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFarms = new TableInfo("farms", _columnsFarms, _foreignKeysFarms, _indicesFarms);
        final TableInfo _existingFarms = TableInfo.read(db, "farms");
        if (!_infoFarms.equals(_existingFarms)) {
          return new RoomOpenHelper.ValidationResult(false, "farms(com.irrigai.mobile.data.local.entity.FarmEntity).\n"
                  + " Expected:\n" + _infoFarms + "\n"
                  + " Found:\n" + _existingFarms);
        }
        final HashMap<String, TableInfo.Column> _columnsFarmPoints = new HashMap<String, TableInfo.Column>(5);
        _columnsFarmPoints.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarmPoints.put("farmId", new TableInfo.Column("farmId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarmPoints.put("pointOrder", new TableInfo.Column("pointOrder", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarmPoints.put("x", new TableInfo.Column("x", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarmPoints.put("y", new TableInfo.Column("y", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFarmPoints = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFarmPoints = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFarmPoints = new TableInfo("farm_points", _columnsFarmPoints, _foreignKeysFarmPoints, _indicesFarmPoints);
        final TableInfo _existingFarmPoints = TableInfo.read(db, "farm_points");
        if (!_infoFarmPoints.equals(_existingFarmPoints)) {
          return new RoomOpenHelper.ValidationResult(false, "farm_points(com.irrigai.mobile.data.local.entity.FarmPointEntity).\n"
                  + " Expected:\n" + _infoFarmPoints + "\n"
                  + " Found:\n" + _existingFarmPoints);
        }
        final HashMap<String, TableInfo.Column> _columnsFarmTrees = new HashMap<String, TableInfo.Column>(5);
        _columnsFarmTrees.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarmTrees.put("farmId", new TableInfo.Column("farmId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarmTrees.put("x", new TableInfo.Column("x", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarmTrees.put("y", new TableInfo.Column("y", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarmTrees.put("rotationAngle", new TableInfo.Column("rotationAngle", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFarmTrees = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFarmTrees = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFarmTrees = new TableInfo("farm_trees", _columnsFarmTrees, _foreignKeysFarmTrees, _indicesFarmTrees);
        final TableInfo _existingFarmTrees = TableInfo.read(db, "farm_trees");
        if (!_infoFarmTrees.equals(_existingFarmTrees)) {
          return new RoomOpenHelper.ValidationResult(false, "farm_trees(com.irrigai.mobile.data.local.entity.TreeEntity).\n"
                  + " Expected:\n" + _infoFarmTrees + "\n"
                  + " Found:\n" + _existingFarmTrees);
        }
        final HashMap<String, TableInfo.Column> _columnsFarmValves = new HashMap<String, TableInfo.Column>(6);
        _columnsFarmValves.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarmValves.put("farmId", new TableInfo.Column("farmId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarmValves.put("x", new TableInfo.Column("x", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarmValves.put("y", new TableInfo.Column("y", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarmValves.put("rotationAngle", new TableInfo.Column("rotationAngle", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarmValves.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFarmValves = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFarmValves = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFarmValves = new TableInfo("farm_valves", _columnsFarmValves, _foreignKeysFarmValves, _indicesFarmValves);
        final TableInfo _existingFarmValves = TableInfo.read(db, "farm_valves");
        if (!_infoFarmValves.equals(_existingFarmValves)) {
          return new RoomOpenHelper.ValidationResult(false, "farm_valves(com.irrigai.mobile.data.local.entity.ValveEntity).\n"
                  + " Expected:\n" + _infoFarmValves + "\n"
                  + " Found:\n" + _existingFarmValves);
        }
        final HashMap<String, TableInfo.Column> _columnsFarmSensors = new HashMap<String, TableInfo.Column>(7);
        _columnsFarmSensors.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarmSensors.put("farmId", new TableInfo.Column("farmId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarmSensors.put("x", new TableInfo.Column("x", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarmSensors.put("y", new TableInfo.Column("y", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarmSensors.put("type", new TableInfo.Column("type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarmSensors.put("status", new TableInfo.Column("status", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarmSensors.put("lastValue", new TableInfo.Column("lastValue", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFarmSensors = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFarmSensors = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFarmSensors = new TableInfo("farm_sensors", _columnsFarmSensors, _foreignKeysFarmSensors, _indicesFarmSensors);
        final TableInfo _existingFarmSensors = TableInfo.read(db, "farm_sensors");
        if (!_infoFarmSensors.equals(_existingFarmSensors)) {
          return new RoomOpenHelper.ValidationResult(false, "farm_sensors(com.irrigai.mobile.data.local.entity.SensorEntity).\n"
                  + " Expected:\n" + _infoFarmSensors + "\n"
                  + " Found:\n" + _existingFarmSensors);
        }
        final HashMap<String, TableInfo.Column> _columnsFarmZones = new HashMap<String, TableInfo.Column>(4);
        _columnsFarmZones.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarmZones.put("farmId", new TableInfo.Column("farmId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarmZones.put("color", new TableInfo.Column("color", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarmZones.put("pointsJson", new TableInfo.Column("pointsJson", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFarmZones = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFarmZones = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFarmZones = new TableInfo("farm_zones", _columnsFarmZones, _foreignKeysFarmZones, _indicesFarmZones);
        final TableInfo _existingFarmZones = TableInfo.read(db, "farm_zones");
        if (!_infoFarmZones.equals(_existingFarmZones)) {
          return new RoomOpenHelper.ValidationResult(false, "farm_zones(com.irrigai.mobile.data.local.entity.ZoneAreaEntity).\n"
                  + " Expected:\n" + _infoFarmZones + "\n"
                  + " Found:\n" + _existingFarmZones);
        }
        final HashMap<String, TableInfo.Column> _columnsFarmWorkerCrossRef = new HashMap<String, TableInfo.Column>(2);
        _columnsFarmWorkerCrossRef.put("farmId", new TableInfo.Column("farmId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFarmWorkerCrossRef.put("workerUserId", new TableInfo.Column("workerUserId", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFarmWorkerCrossRef = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFarmWorkerCrossRef = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFarmWorkerCrossRef = new TableInfo("farm_worker_cross_ref", _columnsFarmWorkerCrossRef, _foreignKeysFarmWorkerCrossRef, _indicesFarmWorkerCrossRef);
        final TableInfo _existingFarmWorkerCrossRef = TableInfo.read(db, "farm_worker_cross_ref");
        if (!_infoFarmWorkerCrossRef.equals(_existingFarmWorkerCrossRef)) {
          return new RoomOpenHelper.ValidationResult(false, "farm_worker_cross_ref(com.irrigai.mobile.data.local.entity.FarmWorkerCrossRefEntity).\n"
                  + " Expected:\n" + _infoFarmWorkerCrossRef + "\n"
                  + " Found:\n" + _existingFarmWorkerCrossRef);
        }
        final HashMap<String, TableInfo.Column> _columnsRobotTasks = new HashMap<String, TableInfo.Column>(7);
        _columnsRobotTasks.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRobotTasks.put("robotId", new TableInfo.Column("robotId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRobotTasks.put("farmId", new TableInfo.Column("farmId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRobotTasks.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRobotTasks.put("status", new TableInfo.Column("status", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRobotTasks.put("startedAtMillis", new TableInfo.Column("startedAtMillis", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRobotTasks.put("completedAtMillis", new TableInfo.Column("completedAtMillis", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRobotTasks = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRobotTasks = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRobotTasks = new TableInfo("robot_tasks", _columnsRobotTasks, _foreignKeysRobotTasks, _indicesRobotTasks);
        final TableInfo _existingRobotTasks = TableInfo.read(db, "robot_tasks");
        if (!_infoRobotTasks.equals(_existingRobotTasks)) {
          return new RoomOpenHelper.ValidationResult(false, "robot_tasks(com.irrigai.mobile.data.local.entity.RobotTaskEntity).\n"
                  + " Expected:\n" + _infoRobotTasks + "\n"
                  + " Found:\n" + _existingRobotTasks);
        }
        final HashMap<String, TableInfo.Column> _columnsMaintenanceLogs = new HashMap<String, TableInfo.Column>(7);
        _columnsMaintenanceLogs.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaintenanceLogs.put("robotId", new TableInfo.Column("robotId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaintenanceLogs.put("farmId", new TableInfo.Column("farmId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaintenanceLogs.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaintenanceLogs.put("maintenanceAtMillis", new TableInfo.Column("maintenanceAtMillis", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaintenanceLogs.put("hoursWorked", new TableInfo.Column("hoursWorked", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMaintenanceLogs.put("performedBy", new TableInfo.Column("performedBy", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMaintenanceLogs = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMaintenanceLogs = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMaintenanceLogs = new TableInfo("maintenance_logs", _columnsMaintenanceLogs, _foreignKeysMaintenanceLogs, _indicesMaintenanceLogs);
        final TableInfo _existingMaintenanceLogs = TableInfo.read(db, "maintenance_logs");
        if (!_infoMaintenanceLogs.equals(_existingMaintenanceLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "maintenance_logs(com.irrigai.mobile.data.local.entity.MaintenanceLogEntity).\n"
                  + " Expected:\n" + _infoMaintenanceLogs + "\n"
                  + " Found:\n" + _existingMaintenanceLogs);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "f477f4bafdd3e813ade3f8699be35dcf", "e7a97effda27a95e1e1966bd327cd9c7");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "users","dashboard","robots","workers","weather","stats","farms","farm_points","farm_trees","farm_valves","farm_sensors","farm_zones","farm_worker_cross_ref","robot_tasks","maintenance_logs");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `users`");
      _db.execSQL("DELETE FROM `dashboard`");
      _db.execSQL("DELETE FROM `robots`");
      _db.execSQL("DELETE FROM `workers`");
      _db.execSQL("DELETE FROM `weather`");
      _db.execSQL("DELETE FROM `stats`");
      _db.execSQL("DELETE FROM `farms`");
      _db.execSQL("DELETE FROM `farm_points`");
      _db.execSQL("DELETE FROM `farm_trees`");
      _db.execSQL("DELETE FROM `farm_valves`");
      _db.execSQL("DELETE FROM `farm_sensors`");
      _db.execSQL("DELETE FROM `farm_zones`");
      _db.execSQL("DELETE FROM `farm_worker_cross_ref`");
      _db.execSQL("DELETE FROM `robot_tasks`");
      _db.execSQL("DELETE FROM `maintenance_logs`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(FarmDao.class, FarmDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(DashboardDao.class, DashboardDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RobotDao.class, RobotDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(FarmWorkerDao.class, FarmWorkerDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RobotTaskDao.class, RobotTaskDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MaintenanceLogDao.class, MaintenanceLogDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(WorkerDao.class, WorkerDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(WeatherDao.class, WeatherDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(StatsDao.class, StatsDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(FarmPointDao.class, FarmPointDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TreeDao.class, TreeDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ValveDao.class, ValveDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SensorDao.class, SensorDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ZoneAreaDao.class, ZoneAreaDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public FarmDao farmDao() {
    if (_farmDao != null) {
      return _farmDao;
    } else {
      synchronized(this) {
        if(_farmDao == null) {
          _farmDao = new FarmDao_Impl(this);
        }
        return _farmDao;
      }
    }
  }

  @Override
  public DashboardDao dashboardDao() {
    if (_dashboardDao != null) {
      return _dashboardDao;
    } else {
      synchronized(this) {
        if(_dashboardDao == null) {
          _dashboardDao = new DashboardDao_Impl(this);
        }
        return _dashboardDao;
      }
    }
  }

  @Override
  public RobotDao robotDao() {
    if (_robotDao != null) {
      return _robotDao;
    } else {
      synchronized(this) {
        if(_robotDao == null) {
          _robotDao = new RobotDao_Impl(this);
        }
        return _robotDao;
      }
    }
  }

  @Override
  public FarmWorkerDao farmWorkerDao() {
    if (_farmWorkerDao != null) {
      return _farmWorkerDao;
    } else {
      synchronized(this) {
        if(_farmWorkerDao == null) {
          _farmWorkerDao = new FarmWorkerDao_Impl(this);
        }
        return _farmWorkerDao;
      }
    }
  }

  @Override
  public RobotTaskDao robotTaskDao() {
    if (_robotTaskDao != null) {
      return _robotTaskDao;
    } else {
      synchronized(this) {
        if(_robotTaskDao == null) {
          _robotTaskDao = new RobotTaskDao_Impl(this);
        }
        return _robotTaskDao;
      }
    }
  }

  @Override
  public MaintenanceLogDao maintenanceLogDao() {
    if (_maintenanceLogDao != null) {
      return _maintenanceLogDao;
    } else {
      synchronized(this) {
        if(_maintenanceLogDao == null) {
          _maintenanceLogDao = new MaintenanceLogDao_Impl(this);
        }
        return _maintenanceLogDao;
      }
    }
  }

  @Override
  public WorkerDao workerDao() {
    if (_workerDao != null) {
      return _workerDao;
    } else {
      synchronized(this) {
        if(_workerDao == null) {
          _workerDao = new WorkerDao_Impl(this);
        }
        return _workerDao;
      }
    }
  }

  @Override
  public WeatherDao weatherDao() {
    if (_weatherDao != null) {
      return _weatherDao;
    } else {
      synchronized(this) {
        if(_weatherDao == null) {
          _weatherDao = new WeatherDao_Impl(this);
        }
        return _weatherDao;
      }
    }
  }

  @Override
  public StatsDao statsDao() {
    if (_statsDao != null) {
      return _statsDao;
    } else {
      synchronized(this) {
        if(_statsDao == null) {
          _statsDao = new StatsDao_Impl(this);
        }
        return _statsDao;
      }
    }
  }

  @Override
  public FarmPointDao farmPointDao() {
    if (_farmPointDao != null) {
      return _farmPointDao;
    } else {
      synchronized(this) {
        if(_farmPointDao == null) {
          _farmPointDao = new FarmPointDao_Impl(this);
        }
        return _farmPointDao;
      }
    }
  }

  @Override
  public TreeDao treeDao() {
    if (_treeDao != null) {
      return _treeDao;
    } else {
      synchronized(this) {
        if(_treeDao == null) {
          _treeDao = new TreeDao_Impl(this);
        }
        return _treeDao;
      }
    }
  }

  @Override
  public ValveDao valveDao() {
    if (_valveDao != null) {
      return _valveDao;
    } else {
      synchronized(this) {
        if(_valveDao == null) {
          _valveDao = new ValveDao_Impl(this);
        }
        return _valveDao;
      }
    }
  }

  @Override
  public SensorDao sensorDao() {
    if (_sensorDao != null) {
      return _sensorDao;
    } else {
      synchronized(this) {
        if(_sensorDao == null) {
          _sensorDao = new SensorDao_Impl(this);
        }
        return _sensorDao;
      }
    }
  }

  @Override
  public ZoneAreaDao zoneAreaDao() {
    if (_zoneAreaDao != null) {
      return _zoneAreaDao;
    } else {
      synchronized(this) {
        if(_zoneAreaDao == null) {
          _zoneAreaDao = new ZoneAreaDao_Impl(this);
        }
        return _zoneAreaDao;
      }
    }
  }
}
