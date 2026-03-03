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
import com.irrigai.mobile.data.local.entity.WorkerEntity;
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
public final class WorkerDao_Impl implements WorkerDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WorkerEntity> __insertionAdapterOfWorkerEntity;

  private final EntityDeletionOrUpdateAdapter<WorkerEntity> __updateAdapterOfWorkerEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateTaskAndRemark;

  private final SharedSQLiteStatement __preparedStmtOfUpdateStatus;

  public WorkerDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWorkerEntity = new EntityInsertionAdapter<WorkerEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `workers` (`id`,`name`,`role`,`status`,`inDuty`,`remark`,`assignedTask`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final WorkerEntity entity) {
        if (entity.id == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.id);
        }
        if (entity.name == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.name);
        }
        if (entity.role == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.role);
        }
        if (entity.status == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.status);
        }
        final int _tmp = entity.inDuty ? 1 : 0;
        statement.bindLong(5, _tmp);
        if (entity.remark == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.remark);
        }
        if (entity.assignedTask == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.assignedTask);
        }
      }
    };
    this.__updateAdapterOfWorkerEntity = new EntityDeletionOrUpdateAdapter<WorkerEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `workers` SET `id` = ?,`name` = ?,`role` = ?,`status` = ?,`inDuty` = ?,`remark` = ?,`assignedTask` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final WorkerEntity entity) {
        if (entity.id == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.id);
        }
        if (entity.name == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.name);
        }
        if (entity.role == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.role);
        }
        if (entity.status == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.status);
        }
        final int _tmp = entity.inDuty ? 1 : 0;
        statement.bindLong(5, _tmp);
        if (entity.remark == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.remark);
        }
        if (entity.assignedTask == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.assignedTask);
        }
        if (entity.id == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.id);
        }
      }
    };
    this.__preparedStmtOfUpdateTaskAndRemark = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE workers SET assignedTask = ?, remark = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE workers SET status = ?, inDuty = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public void insertAll(final List<WorkerEntity> workers) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfWorkerEntity.insert(workers);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insert(final WorkerEntity worker) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfWorkerEntity.insert(worker);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final WorkerEntity worker) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfWorkerEntity.handle(worker);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateTaskAndRemark(final String workerId, final String task, final String remark) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateTaskAndRemark.acquire();
    int _argIndex = 1;
    if (task == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, task);
    }
    _argIndex = 2;
    if (remark == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, remark);
    }
    _argIndex = 3;
    if (workerId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, workerId);
    }
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfUpdateTaskAndRemark.release(_stmt);
    }
  }

  @Override
  public void updateStatus(final String workerId, final String status, final boolean inDuty) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateStatus.acquire();
    int _argIndex = 1;
    if (status == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, status);
    }
    _argIndex = 2;
    final int _tmp = inDuty ? 1 : 0;
    _stmt.bindLong(_argIndex, _tmp);
    _argIndex = 3;
    if (workerId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, workerId);
    }
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfUpdateStatus.release(_stmt);
    }
  }

  @Override
  public WorkerEntity getWorkerById(final String workerId) {
    final String _sql = "SELECT * FROM workers WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (workerId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, workerId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfInDuty = CursorUtil.getColumnIndexOrThrow(_cursor, "inDuty");
      final int _cursorIndexOfRemark = CursorUtil.getColumnIndexOrThrow(_cursor, "remark");
      final int _cursorIndexOfAssignedTask = CursorUtil.getColumnIndexOrThrow(_cursor, "assignedTask");
      final WorkerEntity _result;
      if (_cursor.moveToFirst()) {
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpRole;
        if (_cursor.isNull(_cursorIndexOfRole)) {
          _tmpRole = null;
        } else {
          _tmpRole = _cursor.getString(_cursorIndexOfRole);
        }
        final String _tmpStatus;
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _tmpStatus = null;
        } else {
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        }
        final boolean _tmpInDuty;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfInDuty);
        _tmpInDuty = _tmp != 0;
        final String _tmpRemark;
        if (_cursor.isNull(_cursorIndexOfRemark)) {
          _tmpRemark = null;
        } else {
          _tmpRemark = _cursor.getString(_cursorIndexOfRemark);
        }
        final String _tmpAssignedTask;
        if (_cursor.isNull(_cursorIndexOfAssignedTask)) {
          _tmpAssignedTask = null;
        } else {
          _tmpAssignedTask = _cursor.getString(_cursorIndexOfAssignedTask);
        }
        _result = new WorkerEntity(_tmpId,_tmpName,_tmpRole,_tmpStatus,_tmpInDuty,_tmpRemark,_tmpAssignedTask);
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
  public List<WorkerEntity> getAllWorkers() {
    final String _sql = "SELECT * FROM workers ORDER BY name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfInDuty = CursorUtil.getColumnIndexOrThrow(_cursor, "inDuty");
      final int _cursorIndexOfRemark = CursorUtil.getColumnIndexOrThrow(_cursor, "remark");
      final int _cursorIndexOfAssignedTask = CursorUtil.getColumnIndexOrThrow(_cursor, "assignedTask");
      final List<WorkerEntity> _result = new ArrayList<WorkerEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final WorkerEntity _item;
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpRole;
        if (_cursor.isNull(_cursorIndexOfRole)) {
          _tmpRole = null;
        } else {
          _tmpRole = _cursor.getString(_cursorIndexOfRole);
        }
        final String _tmpStatus;
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _tmpStatus = null;
        } else {
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        }
        final boolean _tmpInDuty;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfInDuty);
        _tmpInDuty = _tmp != 0;
        final String _tmpRemark;
        if (_cursor.isNull(_cursorIndexOfRemark)) {
          _tmpRemark = null;
        } else {
          _tmpRemark = _cursor.getString(_cursorIndexOfRemark);
        }
        final String _tmpAssignedTask;
        if (_cursor.isNull(_cursorIndexOfAssignedTask)) {
          _tmpAssignedTask = null;
        } else {
          _tmpAssignedTask = _cursor.getString(_cursorIndexOfAssignedTask);
        }
        _item = new WorkerEntity(_tmpId,_tmpName,_tmpRole,_tmpStatus,_tmpInDuty,_tmpRemark,_tmpAssignedTask);
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
    final String _sql = "SELECT COUNT(*) FROM workers";
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

  @Override
  public int getOnDutyCount() {
    final String _sql = "SELECT COUNT(*) FROM workers WHERE status = 'ON_DUTY'";
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
