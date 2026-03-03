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
import com.irrigai.mobile.data.local.entity.ValveEntity;
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
public final class ValveDao_Impl implements ValveDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ValveEntity> __insertionAdapterOfValveEntity;

  private final EntityDeletionOrUpdateAdapter<ValveEntity> __deletionAdapterOfValveEntity;

  private final EntityDeletionOrUpdateAdapter<ValveEntity> __updateAdapterOfValveEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByFarmId;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public ValveDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfValveEntity = new EntityInsertionAdapter<ValveEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `farm_valves` (`id`,`farmId`,`x`,`y`,`rotationAngle`,`isActive`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ValveEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.farmId);
        statement.bindDouble(3, entity.x);
        statement.bindDouble(4, entity.y);
        statement.bindDouble(5, entity.rotationAngle);
        final int _tmp = entity.isActive ? 1 : 0;
        statement.bindLong(6, _tmp);
      }
    };
    this.__deletionAdapterOfValveEntity = new EntityDeletionOrUpdateAdapter<ValveEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `farm_valves` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ValveEntity entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfValveEntity = new EntityDeletionOrUpdateAdapter<ValveEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `farm_valves` SET `id` = ?,`farmId` = ?,`x` = ?,`y` = ?,`rotationAngle` = ?,`isActive` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ValveEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.farmId);
        statement.bindDouble(3, entity.x);
        statement.bindDouble(4, entity.y);
        statement.bindDouble(5, entity.rotationAngle);
        final int _tmp = entity.isActive ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindLong(7, entity.id);
      }
    };
    this.__preparedStmtOfDeleteByFarmId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM farm_valves WHERE farmId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM farm_valves";
        return _query;
      }
    };
  }

  @Override
  public long insert(final ValveEntity valve) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfValveEntity.insertAndReturnId(valve);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final List<ValveEntity> valves) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfValveEntity.insert(valves);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final ValveEntity valve) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfValveEntity.handle(valve);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final ValveEntity valve) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfValveEntity.handle(valve);
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
  public List<ValveEntity> getValvesByFarmId(final long farmId) {
    final String _sql = "SELECT * FROM farm_valves WHERE farmId = ?";
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
      final int _cursorIndexOfRotationAngle = CursorUtil.getColumnIndexOrThrow(_cursor, "rotationAngle");
      final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
      final List<ValveEntity> _result = new ArrayList<ValveEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ValveEntity _item;
        _item = new ValveEntity();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        _item.farmId = _cursor.getLong(_cursorIndexOfFarmId);
        _item.x = _cursor.getFloat(_cursorIndexOfX);
        _item.y = _cursor.getFloat(_cursorIndexOfY);
        _item.rotationAngle = _cursor.getFloat(_cursorIndexOfRotationAngle);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsActive);
        _item.isActive = _tmp != 0;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<ValveEntity> getAllValves() {
    final String _sql = "SELECT * FROM farm_valves";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfFarmId = CursorUtil.getColumnIndexOrThrow(_cursor, "farmId");
      final int _cursorIndexOfX = CursorUtil.getColumnIndexOrThrow(_cursor, "x");
      final int _cursorIndexOfY = CursorUtil.getColumnIndexOrThrow(_cursor, "y");
      final int _cursorIndexOfRotationAngle = CursorUtil.getColumnIndexOrThrow(_cursor, "rotationAngle");
      final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
      final List<ValveEntity> _result = new ArrayList<ValveEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ValveEntity _item;
        _item = new ValveEntity();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        _item.farmId = _cursor.getLong(_cursorIndexOfFarmId);
        _item.x = _cursor.getFloat(_cursorIndexOfX);
        _item.y = _cursor.getFloat(_cursorIndexOfY);
        _item.rotationAngle = _cursor.getFloat(_cursorIndexOfRotationAngle);
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsActive);
        _item.isActive = _tmp != 0;
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
    final String _sql = "SELECT COUNT(*) FROM farm_valves";
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
