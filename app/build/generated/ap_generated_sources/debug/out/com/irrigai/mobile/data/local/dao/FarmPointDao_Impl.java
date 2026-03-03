package com.irrigai.mobile.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.irrigai.mobile.data.local.entity.FarmPointEntity;
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
public final class FarmPointDao_Impl implements FarmPointDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FarmPointEntity> __insertionAdapterOfFarmPointEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByFarmId;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public FarmPointDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFarmPointEntity = new EntityInsertionAdapter<FarmPointEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `farm_points` (`id`,`farmId`,`pointOrder`,`x`,`y`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final FarmPointEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.farmId);
        statement.bindLong(3, entity.pointOrder);
        statement.bindDouble(4, entity.x);
        statement.bindDouble(5, entity.y);
      }
    };
    this.__preparedStmtOfDeleteByFarmId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM farm_points WHERE farmId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM farm_points";
        return _query;
      }
    };
  }

  @Override
  public void insertAll(final List<FarmPointEntity> points) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfFarmPointEntity.insert(points);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public long insert(final FarmPointEntity point) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfFarmPointEntity.insertAndReturnId(point);
      __db.setTransactionSuccessful();
      return _result;
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
  public List<FarmPointEntity> getPointsByFarmId(final long farmId) {
    final String _sql = "SELECT * FROM farm_points WHERE farmId = ? ORDER BY pointOrder ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, farmId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfFarmId = CursorUtil.getColumnIndexOrThrow(_cursor, "farmId");
      final int _cursorIndexOfPointOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "pointOrder");
      final int _cursorIndexOfX = CursorUtil.getColumnIndexOrThrow(_cursor, "x");
      final int _cursorIndexOfY = CursorUtil.getColumnIndexOrThrow(_cursor, "y");
      final List<FarmPointEntity> _result = new ArrayList<FarmPointEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final FarmPointEntity _item;
        _item = new FarmPointEntity();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        _item.farmId = _cursor.getLong(_cursorIndexOfFarmId);
        _item.pointOrder = _cursor.getInt(_cursorIndexOfPointOrder);
        _item.x = _cursor.getFloat(_cursorIndexOfX);
        _item.y = _cursor.getFloat(_cursorIndexOfY);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<FarmPointEntity> getAllPoints() {
    final String _sql = "SELECT * FROM farm_points ORDER BY pointOrder ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfFarmId = CursorUtil.getColumnIndexOrThrow(_cursor, "farmId");
      final int _cursorIndexOfPointOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "pointOrder");
      final int _cursorIndexOfX = CursorUtil.getColumnIndexOrThrow(_cursor, "x");
      final int _cursorIndexOfY = CursorUtil.getColumnIndexOrThrow(_cursor, "y");
      final List<FarmPointEntity> _result = new ArrayList<FarmPointEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final FarmPointEntity _item;
        _item = new FarmPointEntity();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        _item.farmId = _cursor.getLong(_cursorIndexOfFarmId);
        _item.pointOrder = _cursor.getInt(_cursorIndexOfPointOrder);
        _item.x = _cursor.getFloat(_cursorIndexOfX);
        _item.y = _cursor.getFloat(_cursorIndexOfY);
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
    final String _sql = "SELECT COUNT(*) FROM farm_points";
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
