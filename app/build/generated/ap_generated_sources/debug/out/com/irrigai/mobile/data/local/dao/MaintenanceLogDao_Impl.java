package com.irrigai.mobile.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.irrigai.mobile.data.local.entity.MaintenanceLogEntity;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MaintenanceLogDao_Impl implements MaintenanceLogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MaintenanceLogEntity> __insertionAdapterOfMaintenanceLogEntity;

  public MaintenanceLogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMaintenanceLogEntity = new EntityInsertionAdapter<MaintenanceLogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `maintenance_logs` (`id`,`robotId`,`farmId`,`description`,`maintenanceAtMillis`,`hoursWorked`,`performedBy`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final MaintenanceLogEntity entity) {
        statement.bindLong(1, entity.id);
        if (entity.robotId == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.robotId);
        }
        statement.bindLong(3, entity.farmId);
        if (entity.description == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.description);
        }
        if (entity.maintenanceAtMillis == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.maintenanceAtMillis);
        }
        if (entity.hoursWorked == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.hoursWorked);
        }
        if (entity.performedBy == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.performedBy);
        }
      }
    };
  }

  @Override
  public long insert(final MaintenanceLogEntity log) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfMaintenanceLogEntity.insertAndReturnId(log);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<MaintenanceLogEntity> getLogsForRobot(final String robotId) {
    final String _sql = "SELECT * FROM maintenance_logs WHERE robotId = ? ORDER BY maintenanceAtMillis DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (robotId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, robotId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfRobotId = CursorUtil.getColumnIndexOrThrow(_cursor, "robotId");
      final int _cursorIndexOfFarmId = CursorUtil.getColumnIndexOrThrow(_cursor, "farmId");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfMaintenanceAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "maintenanceAtMillis");
      final int _cursorIndexOfHoursWorked = CursorUtil.getColumnIndexOrThrow(_cursor, "hoursWorked");
      final int _cursorIndexOfPerformedBy = CursorUtil.getColumnIndexOrThrow(_cursor, "performedBy");
      final List<MaintenanceLogEntity> _result = new ArrayList<MaintenanceLogEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final MaintenanceLogEntity _item;
        final String _tmpRobotId;
        if (_cursor.isNull(_cursorIndexOfRobotId)) {
          _tmpRobotId = null;
        } else {
          _tmpRobotId = _cursor.getString(_cursorIndexOfRobotId);
        }
        final long _tmpFarmId;
        _tmpFarmId = _cursor.getLong(_cursorIndexOfFarmId);
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        final Long _tmpMaintenanceAtMillis;
        if (_cursor.isNull(_cursorIndexOfMaintenanceAtMillis)) {
          _tmpMaintenanceAtMillis = null;
        } else {
          _tmpMaintenanceAtMillis = _cursor.getLong(_cursorIndexOfMaintenanceAtMillis);
        }
        final Integer _tmpHoursWorked;
        if (_cursor.isNull(_cursorIndexOfHoursWorked)) {
          _tmpHoursWorked = null;
        } else {
          _tmpHoursWorked = _cursor.getInt(_cursorIndexOfHoursWorked);
        }
        final String _tmpPerformedBy;
        if (_cursor.isNull(_cursorIndexOfPerformedBy)) {
          _tmpPerformedBy = null;
        } else {
          _tmpPerformedBy = _cursor.getString(_cursorIndexOfPerformedBy);
        }
        _item = new MaintenanceLogEntity(_tmpRobotId,_tmpFarmId,_tmpDescription,_tmpMaintenanceAtMillis,_tmpHoursWorked,_tmpPerformedBy);
        _item.id = _cursor.getLong(_cursorIndexOfId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public MaintenanceLogEntity getLastLogForRobot(final String robotId) {
    final String _sql = "SELECT * FROM maintenance_logs WHERE robotId = ? ORDER BY maintenanceAtMillis DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (robotId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, robotId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfRobotId = CursorUtil.getColumnIndexOrThrow(_cursor, "robotId");
      final int _cursorIndexOfFarmId = CursorUtil.getColumnIndexOrThrow(_cursor, "farmId");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfMaintenanceAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "maintenanceAtMillis");
      final int _cursorIndexOfHoursWorked = CursorUtil.getColumnIndexOrThrow(_cursor, "hoursWorked");
      final int _cursorIndexOfPerformedBy = CursorUtil.getColumnIndexOrThrow(_cursor, "performedBy");
      final MaintenanceLogEntity _result;
      if (_cursor.moveToFirst()) {
        final String _tmpRobotId;
        if (_cursor.isNull(_cursorIndexOfRobotId)) {
          _tmpRobotId = null;
        } else {
          _tmpRobotId = _cursor.getString(_cursorIndexOfRobotId);
        }
        final long _tmpFarmId;
        _tmpFarmId = _cursor.getLong(_cursorIndexOfFarmId);
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        final Long _tmpMaintenanceAtMillis;
        if (_cursor.isNull(_cursorIndexOfMaintenanceAtMillis)) {
          _tmpMaintenanceAtMillis = null;
        } else {
          _tmpMaintenanceAtMillis = _cursor.getLong(_cursorIndexOfMaintenanceAtMillis);
        }
        final Integer _tmpHoursWorked;
        if (_cursor.isNull(_cursorIndexOfHoursWorked)) {
          _tmpHoursWorked = null;
        } else {
          _tmpHoursWorked = _cursor.getInt(_cursorIndexOfHoursWorked);
        }
        final String _tmpPerformedBy;
        if (_cursor.isNull(_cursorIndexOfPerformedBy)) {
          _tmpPerformedBy = null;
        } else {
          _tmpPerformedBy = _cursor.getString(_cursorIndexOfPerformedBy);
        }
        _result = new MaintenanceLogEntity(_tmpRobotId,_tmpFarmId,_tmpDescription,_tmpMaintenanceAtMillis,_tmpHoursWorked,_tmpPerformedBy);
        _result.id = _cursor.getLong(_cursorIndexOfId);
      } else {
        _result = null;
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
