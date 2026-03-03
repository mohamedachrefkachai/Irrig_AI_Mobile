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
import com.irrigai.mobile.data.local.entity.UserEntity;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserEntity> __insertionAdapterOfUserEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateWorkerIdentifier;

  public UserDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserEntity = new EntityInsertionAdapter<UserEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `users` (`id`,`email`,`password`,`name`,`userType`,`farmName`,`workerIdentifier`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final UserEntity entity) {
        if (entity.id == null) {
          statement.bindNull(1);
        } else {
          statement.bindLong(1, entity.id);
        }
        if (entity.email == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.email);
        }
        if (entity.password == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.password);
        }
        if (entity.name == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.name);
        }
        if (entity.userType == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.userType);
        }
        if (entity.farmName == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.farmName);
        }
        if (entity.workerIdentifier == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.workerIdentifier);
        }
      }
    };
    this.__preparedStmtOfUpdateWorkerIdentifier = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET workerIdentifier = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public long insertUser(final UserEntity user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfUserEntity.insertAndReturnId(user);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateWorkerIdentifier(final int userId, final String identifier) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateWorkerIdentifier.acquire();
    int _argIndex = 1;
    if (identifier == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, identifier);
    }
    _argIndex = 2;
    _stmt.bindLong(_argIndex, userId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfUpdateWorkerIdentifier.release(_stmt);
    }
  }

  @Override
  public UserEntity getUserByEmailAndPassword(final String email, final String password) {
    final String _sql = "SELECT * FROM users WHERE email = ? AND password = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    _argIndex = 2;
    if (password == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, password);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfUserType = CursorUtil.getColumnIndexOrThrow(_cursor, "userType");
      final int _cursorIndexOfFarmName = CursorUtil.getColumnIndexOrThrow(_cursor, "farmName");
      final int _cursorIndexOfWorkerIdentifier = CursorUtil.getColumnIndexOrThrow(_cursor, "workerIdentifier");
      final UserEntity _result;
      if (_cursor.moveToFirst()) {
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        final String _tmpPassword;
        if (_cursor.isNull(_cursorIndexOfPassword)) {
          _tmpPassword = null;
        } else {
          _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
        }
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpUserType;
        if (_cursor.isNull(_cursorIndexOfUserType)) {
          _tmpUserType = null;
        } else {
          _tmpUserType = _cursor.getString(_cursorIndexOfUserType);
        }
        final String _tmpFarmName;
        if (_cursor.isNull(_cursorIndexOfFarmName)) {
          _tmpFarmName = null;
        } else {
          _tmpFarmName = _cursor.getString(_cursorIndexOfFarmName);
        }
        final String _tmpWorkerIdentifier;
        if (_cursor.isNull(_cursorIndexOfWorkerIdentifier)) {
          _tmpWorkerIdentifier = null;
        } else {
          _tmpWorkerIdentifier = _cursor.getString(_cursorIndexOfWorkerIdentifier);
        }
        _result = new UserEntity(_tmpEmail,_tmpPassword,_tmpName,_tmpUserType,_tmpFarmName,_tmpWorkerIdentifier);
        if (_cursor.isNull(_cursorIndexOfId)) {
          _result.id = null;
        } else {
          _result.id = _cursor.getInt(_cursorIndexOfId);
        }
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
  public UserEntity getUserById(final int userId) {
    final String _sql = "SELECT * FROM users WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfUserType = CursorUtil.getColumnIndexOrThrow(_cursor, "userType");
      final int _cursorIndexOfFarmName = CursorUtil.getColumnIndexOrThrow(_cursor, "farmName");
      final int _cursorIndexOfWorkerIdentifier = CursorUtil.getColumnIndexOrThrow(_cursor, "workerIdentifier");
      final UserEntity _result;
      if (_cursor.moveToFirst()) {
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        final String _tmpPassword;
        if (_cursor.isNull(_cursorIndexOfPassword)) {
          _tmpPassword = null;
        } else {
          _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
        }
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpUserType;
        if (_cursor.isNull(_cursorIndexOfUserType)) {
          _tmpUserType = null;
        } else {
          _tmpUserType = _cursor.getString(_cursorIndexOfUserType);
        }
        final String _tmpFarmName;
        if (_cursor.isNull(_cursorIndexOfFarmName)) {
          _tmpFarmName = null;
        } else {
          _tmpFarmName = _cursor.getString(_cursorIndexOfFarmName);
        }
        final String _tmpWorkerIdentifier;
        if (_cursor.isNull(_cursorIndexOfWorkerIdentifier)) {
          _tmpWorkerIdentifier = null;
        } else {
          _tmpWorkerIdentifier = _cursor.getString(_cursorIndexOfWorkerIdentifier);
        }
        _result = new UserEntity(_tmpEmail,_tmpPassword,_tmpName,_tmpUserType,_tmpFarmName,_tmpWorkerIdentifier);
        if (_cursor.isNull(_cursorIndexOfId)) {
          _result.id = null;
        } else {
          _result.id = _cursor.getInt(_cursorIndexOfId);
        }
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
  public UserEntity getUserByEmail(final String email) {
    final String _sql = "SELECT * FROM users WHERE email = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfUserType = CursorUtil.getColumnIndexOrThrow(_cursor, "userType");
      final int _cursorIndexOfFarmName = CursorUtil.getColumnIndexOrThrow(_cursor, "farmName");
      final int _cursorIndexOfWorkerIdentifier = CursorUtil.getColumnIndexOrThrow(_cursor, "workerIdentifier");
      final UserEntity _result;
      if (_cursor.moveToFirst()) {
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        final String _tmpPassword;
        if (_cursor.isNull(_cursorIndexOfPassword)) {
          _tmpPassword = null;
        } else {
          _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
        }
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpUserType;
        if (_cursor.isNull(_cursorIndexOfUserType)) {
          _tmpUserType = null;
        } else {
          _tmpUserType = _cursor.getString(_cursorIndexOfUserType);
        }
        final String _tmpFarmName;
        if (_cursor.isNull(_cursorIndexOfFarmName)) {
          _tmpFarmName = null;
        } else {
          _tmpFarmName = _cursor.getString(_cursorIndexOfFarmName);
        }
        final String _tmpWorkerIdentifier;
        if (_cursor.isNull(_cursorIndexOfWorkerIdentifier)) {
          _tmpWorkerIdentifier = null;
        } else {
          _tmpWorkerIdentifier = _cursor.getString(_cursorIndexOfWorkerIdentifier);
        }
        _result = new UserEntity(_tmpEmail,_tmpPassword,_tmpName,_tmpUserType,_tmpFarmName,_tmpWorkerIdentifier);
        if (_cursor.isNull(_cursorIndexOfId)) {
          _result.id = null;
        } else {
          _result.id = _cursor.getInt(_cursorIndexOfId);
        }
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
  public UserEntity getWorkerByIdentifierAndPassword(final String identifier,
      final String password) {
    final String _sql = "SELECT * FROM users WHERE workerIdentifier = ? AND password = ? AND userType = 'worker' LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (identifier == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, identifier);
    }
    _argIndex = 2;
    if (password == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, password);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfUserType = CursorUtil.getColumnIndexOrThrow(_cursor, "userType");
      final int _cursorIndexOfFarmName = CursorUtil.getColumnIndexOrThrow(_cursor, "farmName");
      final int _cursorIndexOfWorkerIdentifier = CursorUtil.getColumnIndexOrThrow(_cursor, "workerIdentifier");
      final UserEntity _result;
      if (_cursor.moveToFirst()) {
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        final String _tmpPassword;
        if (_cursor.isNull(_cursorIndexOfPassword)) {
          _tmpPassword = null;
        } else {
          _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
        }
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpUserType;
        if (_cursor.isNull(_cursorIndexOfUserType)) {
          _tmpUserType = null;
        } else {
          _tmpUserType = _cursor.getString(_cursorIndexOfUserType);
        }
        final String _tmpFarmName;
        if (_cursor.isNull(_cursorIndexOfFarmName)) {
          _tmpFarmName = null;
        } else {
          _tmpFarmName = _cursor.getString(_cursorIndexOfFarmName);
        }
        final String _tmpWorkerIdentifier;
        if (_cursor.isNull(_cursorIndexOfWorkerIdentifier)) {
          _tmpWorkerIdentifier = null;
        } else {
          _tmpWorkerIdentifier = _cursor.getString(_cursorIndexOfWorkerIdentifier);
        }
        _result = new UserEntity(_tmpEmail,_tmpPassword,_tmpName,_tmpUserType,_tmpFarmName,_tmpWorkerIdentifier);
        if (_cursor.isNull(_cursorIndexOfId)) {
          _result.id = null;
        } else {
          _result.id = _cursor.getInt(_cursorIndexOfId);
        }
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
