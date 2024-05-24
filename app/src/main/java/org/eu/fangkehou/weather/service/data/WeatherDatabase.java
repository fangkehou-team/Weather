package org.eu.fangkehou.weather.service.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import org.eu.fangkehou.weather.model.bean.WeatherData;

public class WeatherDatabase {

    private WeatherDatabaseHelper weatherDatabaseHelper;

    private static WeatherDatabase instance;

    private WeatherDatabase(Context context) {
        weatherDatabaseHelper = new WeatherDatabaseHelper(context, "weather_data", null, 1);
    }

    public static synchronized WeatherDatabase getInstance(Context context){
        if (instance == null){
            instance = new WeatherDatabase(context);
        }

        return instance;
    }

    public WeatherData getWeather(int id) {
        SQLiteDatabase database = weatherDatabaseHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from weather where id = ?", new String[]{String.valueOf(id)});

        int dataIndex = cursor.getColumnIndex("data");
        if (cursor.moveToNext() && dataIndex >= 0) {
            try {
                String weatherData = cursor.getString(dataIndex);
                return new Gson().fromJson(weatherData, WeatherData.class);
            } catch (Exception ignored) {}
            finally {
                cursor.close();
            }
        }

        return null;
    }

    public int insertWeather(WeatherData weatherData) {
        SQLiteDatabase database = weatherDatabaseHelper.getWritableDatabase();

        String data = new Gson().toJson(weatherData);

        ContentValues contentValues = new ContentValues();
        contentValues.put("data", data);

        return (int) database.insert("weather", null, contentValues);
    }

    public int updateWeather(int id, WeatherData weatherData) {
        SQLiteDatabase database = weatherDatabaseHelper.getWritableDatabase();

        String data = new Gson().toJson(weatherData);

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("data", data);

        return database.update("weather", contentValues, "id = ?", new String[]{String.valueOf(id)});
    }

    public int saveWeather(int id, WeatherData weatherData) {
        if (id == -1 || getWeather(id) == null) {
            return insertWeather(weatherData);
        }

        return updateWeather(id, weatherData);
    }

    private static class WeatherDatabaseHelper extends SQLiteOpenHelper {

        public WeatherDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public WeatherDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createDBString = "create table if not exists weather(" +
                    "id Integer primary key," +
                    "data text" +
                    ")";

            db.execSQL(createDBString);

            String createFirstValue = "insert into weather(id, data)" +
                    "values (0, '{}');";

            db.execSQL(createFirstValue);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
