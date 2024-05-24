package org.eu.fangkehou.weather.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.eu.fangkehou.weather.BaseActivity;
import org.eu.fangkehou.weather.R;
import org.eu.fangkehou.weather.citypicker.db.DBManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SplashActivity extends BaseActivity {

    final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    ActivityResultLauncher<String[]> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        launcher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), o -> {
            Log.i("SplashActivity", o.toString());

            boolean result = o.entrySet().stream().allMatch(Map.Entry::getValue);

            runOnUiThread(() -> {
                if (result) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    return;
                }
                AlertDialog alertDialog = new MaterialAlertDialogBuilder(this)
                        .setTitle("无法获取必要权限")
                        .setMessage("请授予必要权限再继续")
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("重试", (dialogInterface, i) -> runOnUiThread(this::getPermissions))
                        .setNegativeButton("退出", (dialogInterface, i) -> runOnUiThread(this::finish))
                        .create();
                alertDialog.show();
            });
        });

        getPermissions();
//        setupDatabase();
    }

//    private void setupDatabase() {
//
//        AssetManager assetManager = this.getApplicationContext().getAssets();
//
//        InputStream inputStream = null;
//        try {
//            inputStream = assetManager.open("china_city_data.json");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//
//        Type type = new TypeToken<ArrayList<Province>>(){}.getType();
//        List<Province> provincesList = new Gson().fromJson(new JsonReader(new BufferedReader(new InputStreamReader(inputStream))), type);
//
//        Log.i("Province List", String.valueOf(provincesList.size()));
//
//        DBManager dbManager = new DBManager(this);
//        SQLiteDatabase db = dbManager.getDatabase();
//
//        db.execSQL("create table if not exists new_cities(" +
//                "n_id Integer primary key," +
//                "n_c_name text," +
//                "n_c_code text," +
//                "n_c_province text" +
//                ")");
//
//        for (Province province: provincesList) {
//            if (province.getCityList() != null && !province.getCityList().isEmpty()) {
//                for(City cityItem: province.getCityList()) {
//                    writeDatabase(province.getName(), cityItem, db);
//                }
//            }
//        }
//    }

//    private void writeDatabase(String province, City city, SQLiteDatabase db) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("n_c_name", city.getName());
//        contentValues.put("n_c_code", city.getId());
//        contentValues.put("n_c_province", province);
//
//        db.insert("new_cities", null, contentValues);
//
//        if (city.getCityList() != null && !city.getCityList().isEmpty()) {
//            for(City cityItem: city.getCityList()) {
//                writeDatabase(province, cityItem, db);
//            }
//        }
//    }

    private void getPermissions() {
        launcher.launch(PERMISSIONS);
    }
}