package org.eu.fangkehou.weather.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

import org.eu.fangkehou.weather.BaseActivity;
import org.eu.fangkehou.weather.R;
import org.eu.fangkehou.weather.fragment.WeatherFragment;
import org.eu.fangkehou.weather.model.bean.LocationData;
import org.eu.fangkehou.weather.service.data.LocationDatabase;
import org.eu.fangkehou.weather.util.ActivityUtil;

import java.util.List;

public class MainActivity extends BaseActivity {

    private ImageView mainSettingsButton;
    private ImageView mainAddButton;
    private ImageView mainCalenderButton;
    private ViewPager2 mainFragmentPager;
    private List<LocationData> locationDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupView();
        setupData();

    }

    private void setupView() {
        mainSettingsButton = findViewById(R.id.weather_main_settings_button);
        mainAddButton = findViewById(R.id.weather_main_add_button);
        mainCalenderButton = findViewById(R.id.weather_main_calender_button);
        mainFragmentPager = findViewById(R.id.weather_main_fragment_pager);

        mainSettingsButton.setOnClickListener((v) -> {
            Intent i = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);
        });

        mainAddButton.setOnClickListener((v) -> {
            Intent i = new Intent(MainActivity.this, CityChooseActivity.class);
            startActivity(i);
        });

        mainCalenderButton.setOnClickListener((v) -> {
            Intent i = new Intent(MainActivity.this, CalendarActivity.class);
            startActivity(i);
        });

    }

    private void setupData() {
        locationDataList = LocationDatabase.getInstance(this).getAllLocations();

        updateFragmentPager();
    }

    private void updateFragmentPager() {
        mainFragmentPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return WeatherFragment.newInstance(locationDataList.get(position).getId());
            }

            @Override
            public int getItemCount() {
                return locationDataList.size();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupData();
    }

    //--------------使用onKeyUp()干掉他--------------

    //记录用户首次点击返回键的时间
    private long firstTime=0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                ActivityUtil.getInstance().finishAllActivity();
            }
        }
        return super.onKeyUp(keyCode, event);
    }
}