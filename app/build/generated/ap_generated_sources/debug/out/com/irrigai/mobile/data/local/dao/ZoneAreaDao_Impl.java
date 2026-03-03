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
import com.irrigai.mobile.data.local.entity.ZoneAreaEntity;
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
public final class ZoneAreaDao_Impl implements ZoneAreaDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ZoneAreaEntity> __insertionAdapterOfZoneAreaEntity;

  private final EntityDeletionOrUpdateAdapter<ZoneAreaEntity> __deletionAdapterOfZoneAreaEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByFarmId;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public ZoneAreaDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfZoneAreaEntity = new EntityInsertionAdapter<ZoneAreaEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `farm_zones` (`id`,`farmId`,`color`,`pointsJson`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ZoneAreaEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.farmId);
        statement.bindLong(3, entity.color);
        if (entity.pointsJson == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.pointsJson);
        }
      }
    };
    this.__deletionAdapterOfZoneAreaEntity = new EntityDeletionOrUpdateAdapter<ZoneAreaEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `farm_zones` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ZoneAreaEntity entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__preparedStmtOfDeleteByFarmId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM farm_zones WHERE farmId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM farm_zones";
        return _query;
      }
    };
  }

  @Override
  public long insert(final ZoneAreaEntity zone) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfZoneAreaEntity.insertAndReturnId(zone);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final List<ZoneAreaEntity> zones) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfZoneAreaEntity.insert(zones);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final ZoneAreaEntity zone) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfZoneAreaEntity.handle(zone);
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
  public List<ZoneAreaEntity> getZonesByFarmId(final long farmId) {
    final String _sql = "SELECT * FROM farm_zones WHERE farmId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, farmId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfFarmId = CursorUtil.getColumnIndexOrThrow(_cursor, "farmId");
      final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
      final int _cursorIndexOfPointsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "pointsJson");
      final List<ZoneAreaEntity> _result = new ArrayList<ZoneAreaEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ZoneAreaEntity _item;
        _item = new ZoneAreaEntity();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        _item.farmId = _cursor.getLong(_cursorIndexOfFarmId);
        _item.color = _cursor.getInt(_cursorIndexOfColor);
        if (_cursor.isNull(_cursorIndexOfPointsJson)) {
          _item.pointsJson = null;
        } else {
          _item.pointsJson = _cursor.getString(_cursorIndexOfPointsJson);
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
  public List<ZoneAreaEntity> getAllZones() {
    final String _sql = "SELECT * FROM farm_zones";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfFarmId = CursorUtil.getColumnIndexOrThrow(_cursor, "farmId");
      final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
      final int _cursorIndexOfPointsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "pointsJson");
      final List<ZoneAreaEntity> _result = new ArrayList<ZoneAreaEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ZoneAreaEntity _item;
        _item = new ZoneAreaEntity();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        _item.farmId = _cursor.getLong(_cursorIndexOfFarmId);
        _item.color = _cursor.getInt(_cursorIndexOfColor);
        if (_cursor.isNull(_cursorIndexOfPointsJson)) {
          _item.pointsJson = null;
        } else {
          _item.pointsJson = _cursor.getString(_cursorIndexOfPointsJson);
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
    final String _sql = "SELECT COUNT(*) FROM farm_zones";
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
