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
import com.irrigai.mobile.data.local.entity.TreeEntity;
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
public final class TreeDao_Impl implements TreeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TreeEntity> __insertionAdapterOfTreeEntity;

  private final EntityDeletionOrUpdateAdapter<TreeEntity> __deletionAdapterOfTreeEntity;

  private final EntityDeletionOrUpdateAdapter<TreeEntity> __updateAdapterOfTreeEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByFarmId;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public TreeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTreeEntity = new EntityInsertionAdapter<TreeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `farm_trees` (`id`,`farmId`,`x`,`y`,`rotationAngle`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final TreeEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.farmId);
        statement.bindDouble(3, entity.x);
        statement.bindDouble(4, entity.y);
        statement.bindDouble(5, entity.rotationAngle);
      }
    };
    this.__deletionAdapterOfTreeEntity = new EntityDeletionOrUpdateAdapter<TreeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `farm_trees` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final TreeEntity entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfTreeEntity = new EntityDeletionOrUpdateAdapter<TreeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `farm_trees` SET `id` = ?,`farmId` = ?,`x` = ?,`y` = ?,`rotationAngle` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final TreeEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.farmId);
        statement.bindDouble(3, entity.x);
        statement.bindDouble(4, entity.y);
        statement.bindDouble(5, entity.rotationAngle);
        statement.bindLong(6, entity.id);
      }
    };
    this.__preparedStmtOfDeleteByFarmId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM farm_trees WHERE farmId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM farm_trees";
        return _query;
      }
    };
  }

  @Override
  public long insert(final TreeEntity tree) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfTreeEntity.insertAndReturnId(tree);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final List<TreeEntity> trees) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTreeEntity.insert(trees);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final TreeEntity tree) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfTreeEntity.handle(tree);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final TreeEntity tree) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfTreeEntity.handle(tree);
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
  public List<TreeEntity> getTreesByFarmId(final long farmId) {
    final String _sql = "SELECT * FROM farm_trees WHERE farmId = ?";
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
      final List<TreeEntity> _result = new ArrayList<TreeEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final TreeEntity _item;
        _item = new TreeEntity();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        _item.farmId = _cursor.getLong(_cursorIndexOfFarmId);
        _item.x = _cursor.getFloat(_cursorIndexOfX);
        _item.y = _cursor.getFloat(_cursorIndexOfY);
        _item.rotationAngle = _cursor.getFloat(_cursorIndexOfRotationAngle);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<TreeEntity> getAllTrees() {
    final String _sql = "SELECT * FROM farm_trees";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfFarmId = CursorUtil.getColumnIndexOrThrow(_cursor, "farmId");
      final int _cursorIndexOfX = CursorUtil.getColumnIndexOrThrow(_cursor, "x");
      final int _cursorIndexOfY = CursorUtil.getColumnIndexOrThrow(_cursor, "y");
      final int _cursorIndexOfRotationAngle = CursorUtil.getColumnIndexOrThrow(_cursor, "rotationAngle");
      final List<TreeEntity> _result = new ArrayList<TreeEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final TreeEntity _item;
        _item = new TreeEntity();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        _item.farmId = _cursor.getLong(_cursorIndexOfFarmId);
        _item.x = _cursor.getFloat(_cursorIndexOfX);
        _item.y = _cursor.getFloat(_cursorIndexOfY);
        _item.rotationAngle = _cursor.getFloat(_cursorIndexOfRotationAngle);
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
    final String _sql = "SELECT COUNT(*) FROM farm_trees";
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
