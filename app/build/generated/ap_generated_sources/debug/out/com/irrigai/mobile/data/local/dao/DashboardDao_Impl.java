package com.irrigai.mobile.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.irrigai.mobile.data.local.entity.DashboardEntity;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class DashboardDao_Impl implements DashboardDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DashboardEntity> __insertionAdapterOfDashboardEntity;

  public DashboardDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDashboardEntity = new EntityInsertionAdapter<DashboardEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `dashboard` (`id`,`temperature`,`windSpeed`,`isDay`,`workersCount`,`robotsCount`,`activeValves`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final DashboardEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindDouble(2, entity.temperature);
        statement.bindDouble(3, entity.windSpeed);
        final int _tmp = entity.isDay ? 1 : 0;
        statement.bindLong(4, _tmp);
        statement.bindLong(5, entity.workersCount);
        statement.bindLong(6, entity.robotsCount);
        statement.bindLong(7, entity.activeValves);
      }
    };
  }

  @Override
  public void insert(final DashboardEntity dashboard) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDashboardEntity.insert(dashboard);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public DashboardEntity getDashboard() {
    final String _sql = "SELECT * FROM dashboard WHERE id = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
      final int _cursorIndexOfWindSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "windSpeed");
      final int _cursorIndexOfIsDay = CursorUtil.getColumnIndexOrThrow(_cursor, "isDay");
      final int _cursorIndexOfWorkersCount = CursorUtil.getColumnIndexOrThrow(_cursor, "workersCount");
      final int _cursorIndexOfRobotsCount = CursorUtil.getColumnIndexOrThrow(_cursor, "robotsCount");
      final int _cursorIndexOfActiveValves = CursorUtil.getColumnIndexOrThrow(_cursor, "activeValves");
      final DashboardEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new DashboardEntity();
        _result.id = _cursor.getInt(_cursorIndexOfId);
        _result.temperature = _cursor.getDouble(_cursorIndexOfTemperature);
        _result.windSpeed = _cursor.getDouble(_cursorIndexOfWindSpeed);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsDay);
        _result.isDay = _tmp != 0;
        _result.workersCount = _cursor.getInt(_cursorIndexOfWorkersCount);
        _result.robotsCount = _cursor.getInt(_cursorIndexOfRobotsCount);
        _result.activeValves = _cursor.getInt(_cursorIndexOfActiveValves);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getCount() {
    final String _sql = "SELECT COUNT(*) FROM dashboard";
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
