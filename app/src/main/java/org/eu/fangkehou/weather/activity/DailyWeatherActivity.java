package org.eu.fangkehou.weather.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import org.eu.fangkehou.weather.R;
import org.eu.fangkehou.weather.model.view.WeatherViewModel;
import org.eu.fangkehou.weather.model.view.wether.DailyViewModel;

import java.time.format.DateTimeFormatter;

public class DailyWeatherActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    RecyclerView dailyFullRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_daily_weather);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.weather_toolbar);

        toolbar.setTitle("7天天气预报");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WeatherViewModel weatherData = (WeatherViewModel) getIntent().getSerializableExtra("data");

        dailyFullRecycler = findViewById(R.id.weather_daily_full_recycler);
        dailyFullRecycler.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View basicView = View.inflate(DailyWeatherActivity.this, R.layout.item_daily_weather_full, null);

                return new DailyFullViewHolder(basicView);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                DailyViewModel dailyViewModel = weatherData.getDailyData().get(position);
                DailyFullViewHolder dailyViewHolder = (DailyFullViewHolder) holder;

                dailyViewHolder.dailyFullTime.setText(dailyViewModel.getTime().format(DateTimeFormatter.ofPattern("MM月dd日")));
                dailyViewHolder.dailyFullIcon.setImageResource(dailyViewModel.getWeatherCode().getWeatherIconDay());
                dailyViewHolder.dailyFullWeather.setText(dailyViewModel.getWeatherCode().getWeatherText());
                dailyViewHolder.dailyFullMax.setText(dailyViewModel.getTemperatureMin());
                dailyViewHolder.dailyFullMin.setText(dailyViewModel.getTemperatureMin());
            }

            @Override
            public int getItemCount() {
                return weatherData.getDailyData().size();
            }

            class DailyFullViewHolder extends RecyclerView.ViewHolder {

                TextView dailyFullTime;
                ImageView dailyFullIcon;
                TextView dailyFullWeather;
                TextView dailyFullMax;
                TextView dailyFullMin;
                public DailyFullViewHolder(@NonNull View itemView) {
                    super(itemView);

                    dailyFullTime = itemView.findViewById(R.id.item_weather_daily_full_time);
                    dailyFullIcon = itemView.findViewById(R.id.item_weather_daily_full_icon);
                    dailyFullWeather = itemView.findViewById(R.id.item_weather_daily_full_weather);
                    dailyFullMax = itemView.findViewById(R.id.item_weather_daily_full_max);
                    dailyFullMin = itemView.findViewById(R.id.item_weather_daily_full_min);
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(DailyWeatherActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dailyFullRecycler.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}