package com.irrigai.mobile.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.irrigai.mobile.data.local.entity.WeatherEntity;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WeatherDao_Impl implements WeatherDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WeatherEntity> __insertionAdapterOfWeatherEntity;

  public WeatherDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWeatherEntity = new EntityInsertionAdapter<WeatherEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `weather` (`id`,`temperature`,`windSpeed`,`condition`,`icon`,`humidity`,`rainPrediction`,`irrigationSuggestion`,`isDay`,`tomorrowTemp`,`tomorrowCondition`,`tomorrowRainChance`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final WeatherEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindDouble(2, entity.temperature);
        statement.bindDouble(3, entity.windSpeed);
        if (entity.condition == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.condition);
        }
        if (entity.icon == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.icon);
        }
        statement.bindDouble(6, entity.humidity);
        if (entity.rainPrediction == null) {
          statement.bindNull(7);
        } else {
          statement.bindDouble(7, entity.rainPrediction);
        }
        if (entity.irrigationSuggestion == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.irrigationSuggestion);
        }
        final int _tmp = entity.isDay ? 1 : 0;
        statement.bindLong(9, _tmp);
        statement.bindDouble(10, entity.tomorrowTemp);
        if (entity.tomorrowCondition == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.tomorrowCondition);
        }
        if (entity.tomorrowRainChance == null) {
          statement.bindNull(12);
        } else {
          statement.bindDouble(12, entity.tomorrowRainChance);
        }
      }
    };
  }

  @Override
  public void insert(final WeatherEntity weather) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfWeatherEntity.insert(weather);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public WeatherEntity getWeather() {
    final String _sql = "SELECT * FROM weather WHERE id = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
      final int _cursorIndexOfWindSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "windSpeed");
      final int _cursorIndexOfCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "condition");
      final int _cursorIndexOfIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "icon");
      final int _cursorIndexOfHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "humidity");
      final int _cursorIndexOfRainPrediction = CursorUtil.getColumnIndexOrThrow(_cursor, "rainPrediction");
      final int _cursorIndexOfIrrigationSuggestion = CursorUtil.getColumnIndexOrThrow(_cursor, "irrigationSuggestion");
      final int _cursorIndexOfIsDay = CursorUtil.getColumnIndexOrThrow(_cursor, "isDay");
      final int _cursorIndexOfTomorrowTemp = CursorUtil.getColumnIndexOrThrow(_cursor, "tomorrowTemp");
      final int _cursorIndexOfTomorrowCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "tomorrowCondition");
      final int _cursorIndexOfTomorrowRainChance = CursorUtil.getColumnIndexOrThrow(_cursor, "tomorrowRainChance");
      final WeatherEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new WeatherEntity();
        _result.id = _cursor.getInt(_cursorIndexOfId);
        _result.temperature = _cursor.getDouble(_cursorIndexOfTemperature);
        _result.windSpeed = _cursor.getDouble(_cursorIndexOfWindSpeed);
        if (_cursor.isNull(_cursorIndexOfCondition)) {
          _result.condition = null;
        } else {
          _result.condition = _cursor.getString(_cursorIndexOfCondition);
        }
        if (_cursor.isNull(_cursorIndexOfIcon)) {
          _result.icon = null;
        } else {
          _result.icon = _cursor.getString(_cursorIndexOfIcon);
        }
        _result.humidity = _cursor.getDouble(_cursorIndexOfHumidity);
        if (_cursor.isNull(_cursorIndexOfRainPrediction)) {
          _result.rainPrediction = null;
        } else {
          _result.rainPrediction = _cursor.getDouble(_cursorIndexOfRainPrediction);
        }
        if (_cursor.isNull(_cursorIndexOfIrrigationSuggestion)) {
          _result.irrigationSuggestion = null;
        } else {
          _result.irrigationSuggestion = _cursor.getString(_cursorIndexOfIrrigationSuggestion);
        }
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsDay);
        _result.isDay = _tmp != 0;
        _result.tomorrowTemp = _cursor.getDouble(_cursorIndexOfTomorrowTemp);
        if (_cursor.isNull(_cursorIndexOfTomorrowCondition)) {
          _result.tomorrowCondition = null;
        } else {
          _result.tomorrowCondition = _cursor.getString(_cursorIndexOfTomorrowCondition);
        }
        if (_cursor.isNull(_cursorIndexOfTomorrowRainChance)) {
          _result.tomorrowRainChance = null;
        } else {
          _result.tomorrowRainChance = _cursor.getDouble(_cursorIndexOfTomorrowRainChance);
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
  public int getCount() {
    final String _sql = "SELECT COUNT(*) FROM weather";
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
