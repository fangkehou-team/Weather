package org.eu.fangkehou.weather.service.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.eu.fangkehou.weather.model.bean.LocationData;

import java.util.ArrayList;
import java.util.List;

public class LocationDatabase {

    private static LocationDatabase instance;
    public static synchronized LocationDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new LocationDatabase(context);
        }

        return instance;
    }

    private LocationDatabaseHelper locationDatabaseHelper;
    private LocationDatabase(Context context) {
        locationDatabaseHelper = new LocationDatabaseHelper(context, "location_data", null, 1);
    }

    public LocationData getLocation(int id){
        SQLiteDatabase database = locationDatabaseHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from location where id = ?", new String[]{String.valueOf(id)});

        if (cursor.moveToNext()) {
            try {
                return LocationData.builder()
                        .id(cursor.getInt(cursor.getColumnIndexOrThrow("id")))
                        .name(cursor.getString(cursor.getColumnIndexOrThrow("name")))
                        .latitude(cursor.getString(cursor.getColumnIndexOrThrow("latitude")))
                        .longitude(cursor.getString(cursor.getColumnIndexOrThrow("longitude")))
                        .build();
            } catch (Exception ignored){}
            finally {
                cursor.close();
            }

        }

        return null;

    }

    public List<LocationData> getAllLocations(){
        ArrayList<LocationData> locationDataArrayList = new ArrayList<>();

        SQLiteDatabase database = locationDatabaseHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from location", new String[]{});

        while (cursor.moveToNext()) {
            try {
                locationDataArrayList.add(LocationData.builder()
                        .id(cursor.getInt(cursor.getColumnIndexOrThrow("id")))
                        .name(cursor.getString(cursor.getColumnIndexOrThrow("name")))
                        .latitude(cursor.getString(cursor.getColumnIndexOrThrow("latitude")))
                        .longitude(cursor.getString(cursor.getColumnIndexOrThrow("longitude")))
                        .build());
            } catch (Exception ignored){}
        }

        cursor.close();

        return locationDataArrayList;

    }

    public int insertLocation(LocationData locationData) {
        SQLiteDatabase database = locationDatabaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", locationData.getName());
        contentValues.put("latitude", locationData.getLatitude());
        contentValues.put("longitude", locationData.getLongitude());

        return (int) database.insert("location", null, contentValues);
    }

    public int updateLocation(LocationData locationData) {
        SQLiteDatabase database = locationDatabaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", locationData.getId());
        contentValues.put("name", locationData.getName());
        contentValues.put("latitude", locationData.getLatitude());
        contentValues.put("longitude", locationData.getLongitude());

        return database.update("location", contentValues, "id = ?", new String[]{String.valueOf(locationData.getId())});
    }

    public int saveLocation(LocationData locationData) {
        if (locationData.getId() == null || getLocation(locationData.getId()) == null) {
            return insertLocation(locationData);
        }

        return updateLocation(locationData);
    }

    public int deleteLocation(LocationData locationData) {
        SQLiteDatabase database = locationDatabaseHelper.getWritableDatabase();

        return database.delete("location", "id = ?", new String[]{String.valueOf(locationData.getId())});
    }

    private static class LocationDatabaseHelper extends SQLiteOpenHelper {

        public LocationDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public LocationDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createDBString = "create table if not exists location(" +
                    "id Integer primary key," +
                    "name text," +
                    "latitude text," +
                    "longitude text" +
                    ")";

            db.execSQL(createDBString);

            String createFirstLocation = "insert into location(id, name, latitude, longitude)" +
                    "values (0, 'fangkehou', 'fangkehou', 'fangkehou');";

            db.execSQL(createFirstLocation);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
