package com.irrigai.mobile.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.irrigai.mobile.data.local.entity.StatsEntity;
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
public final class StatsDao_Impl implements StatsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<StatsEntity> __insertionAdapterOfStatsEntity;

  public StatsDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStatsEntity = new EntityInsertionAdapter<StatsEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `stats` (`id`,`label`,`temperature`,`wind`,`rain`,`activeValves`,`irrigationDuration`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final StatsEntity entity) {
        statement.bindLong(1, entity.id);
        if (entity.label == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.label);
        }
        statement.bindDouble(3, entity.temperature);
        statement.bindDouble(4, entity.wind);
        statement.bindDouble(5, entity.rain);
        statement.bindLong(6, entity.activeValves);
        statement.bindDouble(7, entity.irrigationDuration);
      }
    };
  }

  @Override
  public void insertAll(final List<StatsEntity> stats) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfStatsEntity.insert(stats);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<StatsEntity> getAllStats() {
    final String _sql = "SELECT * FROM stats ORDER BY id";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
      final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
      final int _cursorIndexOfWind = CursorUtil.getColumnIndexOrThrow(_cursor, "wind");
      final int _cursorIndexOfRain = CursorUtil.getColumnIndexOrThrow(_cursor, "rain");
      final int _cursorIndexOfActiveValves = CursorUtil.getColumnIndexOrThrow(_cursor, "activeValves");
      final int _cursorIndexOfIrrigationDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "irrigationDuration");
      final List<StatsEntity> _result = new ArrayList<StatsEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final StatsEntity _item;
        _item = new StatsEntity();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfLabel)) {
          _item.label = null;
        } else {
          _item.label = _cursor.getString(_cursorIndexOfLabel);
        }
        _item.temperature = _cursor.getDouble(_cursorIndexOfTemperature);
        _item.wind = _cursor.getDouble(_cursorIndexOfWind);
        _item.rain = _cursor.getDouble(_cursorIndexOfRain);
        _item.activeValves = _cursor.getInt(_cursorIndexOfActiveValves);
        _item.irrigationDuration = _cursor.getDouble(_cursorIndexOfIrrigationDuration);
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
    final String _sql = "SELECT COUNT(*) FROM stats";
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
