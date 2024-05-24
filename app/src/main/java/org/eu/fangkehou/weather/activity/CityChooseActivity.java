package org.eu.fangkehou.weather.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.eu.fangkehou.weather.BaseActivity;
import org.eu.fangkehou.weather.R;
import org.eu.fangkehou.weather.citypicker.CityPicker;
import org.eu.fangkehou.weather.citypicker.adapter.OnPickListener;
import org.eu.fangkehou.weather.citypicker.model.City;
import org.eu.fangkehou.weather.citypicker.model.ChosenCity;
import org.eu.fangkehou.weather.citypicker.model.LocatedCity;
import org.eu.fangkehou.weather.model.bean.LocationData;
import org.eu.fangkehou.weather.service.api.ApiCallback;
import org.eu.fangkehou.weather.service.data.LocationDatabase;
import org.eu.fangkehou.weather.service.fetcher.LocationFetcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CityChooseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_choose);

        openCityPick();
    }

    private void openCityPick() {
        List<LocationData> locationDataList = LocationDatabase.getInstance(this).getAllLocations();
        List<ChosenCity> chosenCities = new ArrayList<>();

        for (int i = 1; i < locationDataList.size(); i++) {
            chosenCities.add(new ChosenCity(locationDataList.get(i).getName(), "ch", String.valueOf(locationDataList.get(i).getId()))); //code为城市代码
        }

        String locationName = locationDataList.get(0).getName();
        if (locationName.equals("fangkehou")) {
            locationName = "定位城市";
        }

        CityPicker.from(this) //activity或者fragment
                .enableAnimation(false)	//启用动画效果，默认无
                .setLocatedCity(new LocatedCity(locationName, "lo", String.valueOf(locationDataList.get(0).getId())))  //APP自身已定位的城市，传null会自动定位（默认）
                .setChosenCities(chosenCities)	//指定已选择城市
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        if (data.getProvince().equals("lo")) {
//                            openCityPick();
                            return;
                        }

                        if (data.getProvince().equals("ch")) {
                            AlertDialog alertDialog = new MaterialAlertDialogBuilder(CityChooseActivity.this)
                                    .setTitle("删除已选城市")
                                    .setMessage("是否删除该城市？")
                                    .setIcon(R.mipmap.ic_launcher)
                                    .setPositiveButton("否", (dialogInterface, i) -> runOnUiThread(() -> {
                                        openCityPick();
                                    }))
                                    .setNegativeButton("是", (dialogInterface, i) -> runOnUiThread(() -> {
                                        LocationDatabase.getInstance(CityChooseActivity.this)
                                                .deleteLocation(LocationData.builder()
                                                        .id(Integer.valueOf(data.getCode()))
                                                        .name(data.getName())
                                                        .build());

                                        openCityPick();
                                    }))
                                    .create();
                            alertDialog.show();
                            return;
                        }

                        Toast.makeText(getApplicationContext(), "已选择" + data.getName() + ", 加载数据中", Toast.LENGTH_SHORT).show();
                        LocationFetcher.fetchAndCacheLocationData(CityChooseActivity.this, -1, data.getName(), new ApiCallback<LocationData>() {
                            @Override
                            public void onFailure(IOException e) {
                                Toast.makeText(getApplicationContext(), "添加失败，请检查网络设置", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onResponse(LocationData result) {
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onCancel(){
                        finish();
                    }

                    @Override
                    public void onLocate() {

                    }
                })
                .show();
    }
}