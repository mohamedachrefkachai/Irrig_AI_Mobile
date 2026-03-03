package com.irrigai.mobile.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.irrigai.mobile.data.local.entity.RobotTaskEntity;
import java.lang.Class;
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
public final class RobotTaskDao_Impl implements RobotTaskDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RobotTaskEntity> __insertionAdapterOfRobotTaskEntity;

  public RobotTaskDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRobotTaskEntity = new EntityInsertionAdapter<RobotTaskEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `robot_tasks` (`id`,`robotId`,`farmId`,`title`,`status`,`startedAtMillis`,`completedAtMillis`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final RobotTaskEntity entity) {
        statement.bindLong(1, entity.id);
        if (entity.robotId == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.robotId);
        }
        statement.bindLong(3, entity.farmId);
        if (entity.title == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.title);
        }
        if (entity.status == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.status);
        }
        if (entity.startedAtMillis == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.startedAtMillis);
        }
        if (entity.completedAtMillis == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.completedAtMillis);
        }
      }
    };
  }

  @Override
  public long insert(final RobotTaskEntity task) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfRobotTaskEntity.insertAndReturnId(task);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<RobotTaskEntity> getTasksForRobot(final String robotId) {
    final String _sql = "SELECT * FROM robot_tasks WHERE robotId = ? ORDER BY id DESC";
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
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfStartedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAtMillis");
      final int _cursorIndexOfCompletedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAtMillis");
      final List<RobotTaskEntity> _result = new ArrayList<RobotTaskEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final RobotTaskEntity _item;
        final String _tmpRobotId;
        if (_cursor.isNull(_cursorIndexOfRobotId)) {
          _tmpRobotId = null;
        } else {
          _tmpRobotId = _cursor.getString(_cursorIndexOfRobotId);
        }
        final long _tmpFarmId;
        _tmpFarmId = _cursor.getLong(_cursorIndexOfFarmId);
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final String _tmpStatus;
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _tmpStatus = null;
        } else {
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        }
        final Long _tmpStartedAtMillis;
        if (_cursor.isNull(_cursorIndexOfStartedAtMillis)) {
          _tmpStartedAtMillis = null;
        } else {
          _tmpStartedAtMillis = _cursor.getLong(_cursorIndexOfStartedAtMillis);
        }
        final Long _tmpCompletedAtMillis;
        if (_cursor.isNull(_cursorIndexOfCompletedAtMillis)) {
          _tmpCompletedAtMillis = null;
        } else {
          _tmpCompletedAtMillis = _cursor.getLong(_cursorIndexOfCompletedAtMillis);
        }
        _item = new RobotTaskEntity(_tmpRobotId,_tmpFarmId,_tmpTitle,_tmpStatus,_tmpStartedAtMillis,_tmpCompletedAtMillis);
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
  public RobotTaskEntity getLastTaskForRobot(final String robotId) {
    final String _sql = "SELECT * FROM robot_tasks WHERE robotId = ? ORDER BY id DESC LIMIT 1";
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
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfStartedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "startedAtMillis");
      final int _cursorIndexOfCompletedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAtMillis");
      final RobotTaskEntity _result;
      if (_cursor.moveToFirst()) {
        final String _tmpRobotId;
        if (_cursor.isNull(_cursorIndexOfRobotId)) {
          _tmpRobotId = null;
        } else {
          _tmpRobotId = _cursor.getString(_cursorIndexOfRobotId);
        }
        final long _tmpFarmId;
        _tmpFarmId = _cursor.getLong(_cursorIndexOfFarmId);
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final String _tmpStatus;
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _tmpStatus = null;
        } else {
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        }
        final Long _tmpStartedAtMillis;
        if (_cursor.isNull(_cursorIndexOfStartedAtMillis)) {
          _tmpStartedAtMillis = null;
        } else {
          _tmpStartedAtMillis = _cursor.getLong(_cursorIndexOfStartedAtMillis);
        }
        final Long _tmpCompletedAtMillis;
        if (_cursor.isNull(_cursorIndexOfCompletedAtMillis)) {
          _tmpCompletedAtMillis = null;
        } else {
          _tmpCompletedAtMillis = _cursor.getLong(_cursorIndexOfCompletedAtMillis);
        }
        _result = new RobotTaskEntity(_tmpRobotId,_tmpFarmId,_tmpTitle,_tmpStatus,_tmpStartedAtMillis,_tmpCompletedAtMillis);
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
