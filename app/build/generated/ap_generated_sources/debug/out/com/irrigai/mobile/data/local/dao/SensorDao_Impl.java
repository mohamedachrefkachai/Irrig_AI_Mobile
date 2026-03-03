package com.irrigai.mobile.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.irrigai.mobile.data.local.entity.SensorEntity;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SensorDao_Impl implements SensorDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SensorEntity> __insertionAdapterOfSensorEntity;

  private final EntityDeletionOrUpdateAdapter<SensorEntity> __deletionAdapterOfSensorEntity;

  private final EntityDeletionOrUpdateAdapter<SensorEntity> __updateAdapterOfSensorEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByFarmId;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public SensorDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSensorEntity = new EntityInsertionAdapter<SensorEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `farm_sensors` (`id`,`farmId`,`x`,`y`,`type`,`status`,`lastValue`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final SensorEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.farmId);
        statement.bindDouble(3, entity.x);
        statement.bindDouble(4, entity.y);
        if (entity.type == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.type);
        }
        if (entity.status == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.status);
        }
        if (entity.lastValue == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.lastValue);
        }
      }
    };
    this.__deletionAdapterOfSensorEntity = new EntityDeletionOrUpdateAdapter<SensorEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `farm_sensors` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final SensorEntity entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfSensorEntity = new EntityDeletionOrUpdateAdapter<SensorEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `farm_sensors` SET `id` = ?,`farmId` = ?,`x` = ?,`y` = ?,`type` = ?,`status` = ?,`lastValue` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final SensorEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.farmId);
        statement.bindDouble(3, entity.x);
        statement.bindDouble(4, entity.y);
        if (entity.type == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.type);
        }
        if (entity.status == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.status);
        }
        if (entity.lastValue == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.lastValue);
        }
        statement.bindLong(8, entity.id);
      }
    };
    this.__preparedStmtOfDeleteByFarmId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM farm_sensors WHERE farmId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM farm_sensors";
        return _query;
      }
    };
  }

  @Override
  public long insert(final SensorEntity sensor) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfSensorEntity.insertAndReturnId(sensor);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final List<SensorEntity> sensors) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfSensorEntity.insert(sensors);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final SensorEntity sensor) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfSensorEntity.handle(sensor);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final SensorEntity sensor) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfSensorEntity.handle(sensor);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteByFarmId(final long farmId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByFarmId.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, farmId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteByFarmId.release(_stmt);
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public List<SensorEntity> getSensorsByFarmId(final long farmId) {
    final String _sql = "SELECT * FROM farm_sensors WHERE farmId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, farmId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfFarmId = CursorUtil.getColumnIndexOrThrow(_cursor, "farmId");
      final int _cursorIndexOfX = CursorUtil.getColumnIndexOrThrow(_cursor, "x");
      final int _cursorIndexOfY = CursorUtil.getColumnIndexOrThrow(_cursor, "y");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfLastValue = CursorUtil.getColumnIndexOrThrow(_cursor, "lastValue");
      final List<SensorEntity> _result = new ArrayList<SensorEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final SensorEntity _item;
        _item = new SensorEntity();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        _item.farmId = _cursor.getLong(_cursorIndexOfFarmId);
        _item.x = _cursor.getFloat(_cursorIndexOfX);
        _item.y = _cursor.getFloat(_cursorIndexOfY);
        if (_cursor.isNull(_cursorIndexOfType)) {
          _item.type = null;
        } else {
          _item.type = _cursor.getString(_cursorIndexOfType);
        }
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _item.status = null;
        } else {
          _item.status = _cursor.getString(_cursorIndexOfStatus);
        }
        if (_cursor.isNull(_cursorIndexOfLastValue)) {
          _item.lastValue = null;
        } else {
          _item.lastValue = _cursor.getString(_cursorIndexOfLastValue);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<SensorEntity> getAllSensors() {
    final String _sql = "SELECT * FROM farm_sensors";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfFarmId = CursorUtil.getColumnIndexOrThrow(_cursor, "farmId");
      final int _cursorIndexOfX = CursorUtil.getColumnIndexOrThrow(_cursor, "x");
      final int _cursorIndexOfY = CursorUtil.getColumnIndexOrThrow(_cursor, "y");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfLastValue = CursorUtil.getColumnIndexOrThrow(_cursor, "lastValue");
      final List<SensorEntity> _result = new ArrayList<SensorEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final SensorEntity _item;
        _item = new SensorEntity();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        _item.farmId = _cursor.getLong(_cursorIndexOfFarmId);
        _item.x = _cursor.getFloat(_cursorIndexOfX);
        _item.y = _cursor.getFloat(_cursorIndexOfY);
        if (_cursor.isNull(_cursorIndexOfType)) {
          _item.type = null;
        } else {
          _item.type = _cursor.getString(_cursorIndexOfType);
        }
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _item.status = null;
        } else {
          _item.status = _cursor.getString(_cursorIndexOfStatus);
        }
        if (_cursor.isNull(_cursorIndexOfLastValue)) {
          _item.lastValue = null;
        } else {
          _item.lastValue = _cursor.getString(_cursorIndexOfLastValue);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getCount() {
    final String _sql = "SELECT COUNT(*) FROM farm_sensors";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
