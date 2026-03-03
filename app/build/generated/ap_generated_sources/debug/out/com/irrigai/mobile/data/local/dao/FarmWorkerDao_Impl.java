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
import com.irrigai.mobile.data.local.entity.FarmEntity;
import com.irrigai.mobile.data.local.entity.FarmWorkerCrossRefEntity;
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
public final class FarmWorkerDao_Impl implements FarmWorkerDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FarmWorkerCrossRefEntity> __insertionAdapterOfFarmWorkerCrossRefEntity;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  public FarmWorkerDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFarmWorkerCrossRefEntity = new EntityInsertionAdapter<FarmWorkerCrossRefEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `farm_worker_cross_ref` (`farmId`,`workerUserId`) VALUES (?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final FarmWorkerCrossRefEntity entity) {
        statement.bindLong(1, entity.farmId);
        statement.bindLong(2, entity.workerUserId);
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM farm_worker_cross_ref WHERE farmId = ? AND workerUserId = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final FarmWorkerCrossRefEntity ref) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfFarmWorkerCrossRefEntity.insert(ref);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final List<FarmWorkerCrossRefEntity> refs) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfFarmWorkerCrossRefEntity.insert(refs);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final long farmId, final int workerUserId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, farmId);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, workerUserId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDelete.release(_stmt);
    }
  }

  @Override
  public List<FarmWorkerCrossRefEntity> getByWorkerUserId(final int workerUserId) {
    final String _sql = "SELECT * FROM farm_worker_cross_ref WHERE workerUserId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, workerUserId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfFarmId = CursorUtil.getColumnIndexOrThrow(_cursor, "farmId");
      final int _cursorIndexOfWorkerUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "workerUserId");
      final List<FarmWorkerCrossRefEntity> _result = new ArrayList<FarmWorkerCrossRefEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final FarmWorkerCrossRefEntity _item;
        final long _tmpFarmId;
        _tmpFarmId = _cursor.getLong(_cursorIndexOfFarmId);
        final int _tmpWorkerUserId;
        _tmpWorkerUserId = _cursor.getInt(_cursorIndexOfWorkerUserId);
        _item = new FarmWorkerCrossRefEntity(_tmpFarmId,_tmpWorkerUserId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<FarmEntity> getFarmsForWorker(final int workerUserId) {
    final String _sql = "SELECT farms.* FROM farms INNER JOIN farm_worker_cross_ref ON farms.id = farm_worker_cross_ref.farmId WHERE farm_worker_cross_ref.workerUserId = ? ORDER BY farms.id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, workerUserId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfOwnerId = CursorUtil.getColumnIndexOrThrow(_cursor, "ownerId");
      final List<FarmEntity> _result = new ArrayList<FarmEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final FarmEntity _item;
        _item = new FarmEntity();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfName)) {
          _item.name = null;
        } else {
          _item.name = _cursor.getString(_cursorIndexOfName);
        }
        _item.ownerId = _cursor.getInt(_cursorIndexOfOwnerId);
        _result.add(_item);
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
