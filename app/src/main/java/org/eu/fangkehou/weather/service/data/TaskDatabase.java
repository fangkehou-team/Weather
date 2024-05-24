package org.eu.fangkehou.weather.service.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import org.eu.fangkehou.weather.model.bean.TaskBean;
import org.eu.fangkehou.weather.model.bean.WeatherData;

import java.util.ArrayList;
import java.util.List;

public class TaskDatabase {

    private TaskDatabaseHelper taskDatabaseHelper;

    private static TaskDatabase instance;

    private TaskDatabase(Context context) {
        taskDatabaseHelper = new TaskDatabaseHelper(context, "task_data", null, 1);
    }

    public static synchronized TaskDatabase getInstance(Context context){
        if (instance == null){
            instance = new TaskDatabase(context);
        }

        return instance;
    }

    public TaskBean getTask(int id) {
        SQLiteDatabase database = taskDatabaseHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from task where id = ?", new String[]{String.valueOf(id)});

        if (cursor.moveToNext()) {
            try {
                return TaskBean.builder()
                        .id(cursor.getInt(cursor.getColumnIndexOrThrow("id")))
                        .name(cursor.getString(cursor.getColumnIndexOrThrow("name")))
                        .date(cursor.getString(cursor.getColumnIndexOrThrow("date")))
                        .time(cursor.getString(cursor.getColumnIndexOrThrow("time")))
                        .build();

            } catch (Exception ignored) {}
            finally {
                cursor.close();
            }
        }

        return null;
    }

    public List<TaskBean> getDateTasks(String date) {
        ArrayList<TaskBean> taskBeanArrayList = new ArrayList<>();

        SQLiteDatabase database = taskDatabaseHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from task where date = ?", new String[]{date});

        while (cursor.moveToNext()) {
            try {
                taskBeanArrayList.add(TaskBean.builder()
                        .id(cursor.getInt(cursor.getColumnIndexOrThrow("id")))
                        .name(cursor.getString(cursor.getColumnIndexOrThrow("name")))
                        .date(cursor.getString(cursor.getColumnIndexOrThrow("date")))
                        .time(cursor.getString(cursor.getColumnIndexOrThrow("time")))
                        .build());
            } catch (Exception ignored) {}
        }

        cursor.close();

        return taskBeanArrayList;
    }

    public int insertTask(TaskBean taskBean) {
        SQLiteDatabase database = taskDatabaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", taskBean.getName());
        contentValues.put("date", taskBean.getDate());
        contentValues.put("time", taskBean.getTime());

        return (int) database.insert("task", null, contentValues);
    }

    public int updateTask(TaskBean taskBean) {
        SQLiteDatabase database = taskDatabaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", taskBean.getId());
        contentValues.put("name", taskBean.getName());
        contentValues.put("date", taskBean.getDate());
        contentValues.put("time", taskBean.getTime());

        return database.update("task", contentValues, "id = ?", new String[]{String.valueOf(taskBean.getId())});
    }

    public int saveTask(TaskBean taskBean) {
        if (taskBean.getId() == null || getTask(taskBean.getId()) == null) {
            return insertTask(taskBean);
        }

        return updateTask(taskBean);
    }

    private static class TaskDatabaseHelper extends SQLiteOpenHelper {

        public TaskDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public TaskDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createDBString = "create table if not exists task(" +
                    "id Integer primary key," +
                    "name text," +
                    "date text," +
                    "time text" +
                    ")";

            db.execSQL(createDBString);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
