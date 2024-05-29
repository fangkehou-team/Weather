package org.eu.fangkehou.weather.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.eu.fangkehou.weather.R;
import org.eu.fangkehou.weather.activity.DailyWeatherActivity;
import org.eu.fangkehou.weather.model.bean.LocationData;
import org.eu.fangkehou.weather.model.bean.WeatherData;
import org.eu.fangkehou.weather.model.enums.BackgroundEnums;
import org.eu.fangkehou.weather.model.view.WeatherViewModel;
import org.eu.fangkehou.weather.model.view.wether.DailyViewModel;
import org.eu.fangkehou.weather.model.view.wether.HourlyViewModel;
import org.eu.fangkehou.weather.service.api.ApiCallback;
import org.eu.fangkehou.weather.service.data.LocationDatabase;
import org.eu.fangkehou.weather.service.fetcher.LocationFetcher;
import org.eu.fangkehou.weather.service.fetcher.WeatherFetcher;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {

    private SwipeRefreshLayout weatherFragmentSwipeLayout;
    private ImageView weatherFragmentBackImage;
    private ImageView weatherFragmentLoadingImage;
    private ImageView weatherFragmentWeatherIcon;
    private TextView weatherFragmentCityName;
    private TextView weatherFragmentUpdateText;
    private TextView weatherFragmentTemperatureText;
    private TextView weatherFragmentDetailText;
    private NestedScrollView weatherFragmentScrollView;

    private RecyclerView weatherFragmentHourlyRecycler;
    private ListView weatherFragmentDailyList;

    private TextView weatherFragmentAdditionalApparentText;
    private TextView weatherFragmentAdditionalHumidityText;
    private TextView weatherFragmentAdditionalWindSpeedText;
    private TextView weatherFragmentAdditionalCloudText;
    private TextView weatherFragmentAdditionalUvText;
    private TextView weatherFragmentAdditionalRainText;
    private TextView weatherFragmentAdditionalSunriseText;
    private TextView weatherFragmentAdditionalSunsetText;
    private TextView weatherFragmentAdditionalPressureText;

    private Button weatherFragmentDailyButton;

    private int id;

    public WeatherFragment() {
        // Required empty public constructor
    }

    public static WeatherFragment newInstance(int id) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vg = inflater.inflate(R.layout.fragment_weather, container, false);

        ((ImageView) vg.findViewById(R.id.weather_fragment_more_background)).setColorFilter(Color.parseColor("#F7F7F7"));
        ((ImageView) vg.findViewById(R.id.weather_fragment_loading_image)).setColorFilter(Color.parseColor("#F7F7F7"));
        setupView(vg);
        setupData();

        return vg;
    }

    private void setupView(View vg) {
        weatherFragmentSwipeLayout = vg.findViewById(R.id.weather_fragment_refresh_layout);
        weatherFragmentBackImage = vg.findViewById(R.id.weather_fragment_back);
        weatherFragmentLoadingImage = vg.findViewById(R.id.weather_fragment_loading_image);
        weatherFragmentWeatherIcon = vg.findViewById(R.id.weather_fragment_weather_icon);
        weatherFragmentCityName = vg.findViewById(R.id.weather_fragment_city_name_text);
        weatherFragmentUpdateText = vg.findViewById(R.id.weather_fragment_update_text);
        weatherFragmentTemperatureText = vg.findViewById(R.id.weather_fragment_temperature_text);
        weatherFragmentDetailText = vg.findViewById(R.id.weather_fragment_detail_text);
        weatherFragmentScrollView = vg.findViewById(R.id.weather_fragment_scroll_view);

        weatherFragmentAdditionalApparentText = vg.findViewById(R.id.weather_fragment_additional_value_apparent);
        weatherFragmentAdditionalHumidityText = vg.findViewById(R.id.weather_fragment_additional_value_humidity);
        weatherFragmentAdditionalWindSpeedText = vg.findViewById(R.id.weather_fragment_additional_value_wind_speed);
        weatherFragmentAdditionalCloudText = vg.findViewById(R.id.weather_fragment_additional_value_cloud);
        weatherFragmentAdditionalUvText = vg.findViewById(R.id.weather_fragment_additional_value_uv);
        weatherFragmentAdditionalRainText = vg.findViewById(R.id.weather_fragment_additional_value_rain);
        weatherFragmentAdditionalSunriseText = vg.findViewById(R.id.weather_fragment_additional_value_sunrise);
        weatherFragmentAdditionalSunsetText = vg.findViewById(R.id.weather_fragment_additional_value_sunset);
        weatherFragmentAdditionalPressureText = vg.findViewById(R.id.weather_fragment_additional_value_pressure);

        weatherFragmentDailyList = vg.findViewById(R.id.weather_fragment_daily_list);
        weatherFragmentHourlyRecycler = vg.findViewById(R.id.weather_fragment_hourly_recycler);

        weatherFragmentDailyButton = vg.findViewById(R.id.weather_fragment_daily_button);

        weatherFragmentScrollView.setOnScrollChangeListener((NestedScrollView v, int i, int scrollY, int i2, int i3) -> {
            Log.i("TAG", scrollY + "onScrollChange: " + (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()));

            if (scrollY == 0) {//监听滚动条到顶部
                weatherFragmentSwipeLayout.setEnabled(true);
            } else {//滚动中
                if (!weatherFragmentSwipeLayout.isRefreshing())//在这里加个判断就可以了
                    weatherFragmentSwipeLayout.setEnabled(false);
            }
        });

        weatherFragmentSwipeLayout.setOnRefreshListener(this::prepareForCurrentCity);

        weatherFragmentDailyList.setEnabled(false);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SettingsFragment.SETTING_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences != null && sharedPreferences.contains("back_image")) {
            weatherFragmentBackImage.setImageResource(BackgroundEnums.getBackById(sharedPreferences.getString("back_image", "100")).getResId());
        }

    }

    private void setupData() {
        weatherFragmentSwipeLayout.setRefreshing(true);

        prepareForCurrentCity();
    }

    private void prepareForCurrentCity() {
        weatherFragmentWeatherIcon.setImageDrawable(null);
        weatherFragmentScrollView.setVisibility(View.GONE);
        weatherFragmentLoadingImage.setVisibility(View.VISIBLE);

        if (id == 0) {
            onGettingLocation();
            getLocationAndWeather();
        } else {
            LocationData locationData = LocationDatabase.getInstance(this.getContext()).getLocation(id);

            getWeather(id, locationData);
        }
    }

    private void onGetLocationFailed() {
        weatherFragmentCityName.setText("");
        weatherFragmentUpdateText.setText("");
        weatherFragmentTemperatureText.setText("定位失败");
        weatherFragmentDetailText.setText("");
        weatherFragmentSwipeLayout.setRefreshing(false);
    }

    private void onGettingLocation() {
        weatherFragmentCityName.setText("");
        weatherFragmentUpdateText.setText("");
        weatherFragmentTemperatureText.setText("正在定位");
        weatherFragmentDetailText.setText("");
    }

    private void onGettingWeather(LocationData locationData) {
        weatherFragmentCityName.setText(locationData.getName());
        weatherFragmentUpdateText.setText("");
        weatherFragmentTemperatureText.setText("正在获取天气");
        weatherFragmentDetailText.setText("");
    }

    private void onGetWeatherFailed() {
        weatherFragmentUpdateText.setText("");
        weatherFragmentTemperatureText.setText("获取失败");
        weatherFragmentDetailText.setText("");
        weatherFragmentSwipeLayout.setRefreshing(false);
    }

    private void onGetWeatherSucceed(WeatherViewModel weatherData) {
        if (weatherData.getCurrentData() == null) {
            return;
        }

        weatherFragmentUpdateText.setText(weatherData.getCurrentData().getUpdateTime().format(new DateTimeFormatterBuilder()
                .appendPattern("u")
                .appendLiteral("-")
                .appendValue(ChronoField.MONTH_OF_YEAR)
                .appendLiteral("-")
                .appendValue(ChronoField.DAY_OF_MONTH)
                .appendLiteral(" ")
                .appendValue(ChronoField.HOUR_OF_DAY)
                .appendLiteral(":")
                .appendText(ChronoField.MINUTE_OF_HOUR, TextStyle.NARROW_STANDALONE)
                .appendLiteral(" 更新").toFormatter()));

        weatherFragmentTemperatureText.setText(weatherData.getCurrentData().getTemperature());
        weatherFragmentDetailText.setText(weatherData.getCurrentData().getWeatherCode().getWeatherText()
                + " 最高" + weatherData.getDailyData().get(0).getTemperatureMax()
                + " 最低" + weatherData.getDailyData().get(0).getTemperatureMin());

        weatherFragmentWeatherIcon.setImageResource(weatherData.getCurrentData().getIsDay() ?
                weatherData.getCurrentData().getWeatherCode().getWeatherIconDay() :
                weatherData.getCurrentData().getWeatherCode().getWeatherIconNight());

        weatherFragmentSwipeLayout.setRefreshing(false);

        weatherFragmentScrollView.setVisibility(View.VISIBLE);
        weatherFragmentLoadingImage.setVisibility(View.GONE);

        weatherFragmentDailyButton.setOnClickListener((v)-> {
            Intent i = new Intent(getActivity(), DailyWeatherActivity.class);
            i.putExtra("data", weatherData);
            startActivity(i);
        });

        setupHourlyRecycler(weatherData);
        setupDailyList(weatherData);
        setupAdditionalData(weatherData);

    }

    private void setupAdditionalData(WeatherViewModel weatherData) {
        weatherFragmentAdditionalApparentText.setText(weatherData.getHourlyData().get(0).getApparentTemperature());
        weatherFragmentAdditionalHumidityText.setText(weatherData.getHourlyData().get(0).getRelativeHumidity());
        weatherFragmentAdditionalWindSpeedText.setText(weatherData.getCurrentData().getWindSpeed() + " " + weatherData.getCurrentData().getWindDirection().getDirectionArrow());
        weatherFragmentAdditionalCloudText.setText(weatherData.getHourlyData().get(0).getCloudCover());
        weatherFragmentAdditionalUvText.setText(String.valueOf(weatherData.getHourlyData().get(0).getUvIndex()));
        weatherFragmentAdditionalRainText.setText(weatherData.getDailyData().get(0).getPrecipitationProbability());
        weatherFragmentAdditionalSunriseText.setText(weatherData.getDailyData().get(0).getSunrise().format(DateTimeFormatter.ofPattern("HH:mm")));
        weatherFragmentAdditionalSunsetText.setText(weatherData.getDailyData().get(0).getSunset().format(DateTimeFormatter.ofPattern("HH:mm")));
        weatherFragmentAdditionalPressureText.setText(weatherData.getHourlyData().get(0).getSurfacePressure());
    }

    private void setupDailyList(WeatherViewModel weatherData) {
        weatherFragmentDailyList.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Object getItem(int position) {
                return weatherData.getDailyData().get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                DailyViewModel dailyViewModel = weatherData.getDailyData().get(position);

                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_daily_weather, parent, false);

                TextView dailyDateText = view.findViewById(R.id.item_daily_date);
                ImageView dailyWeatherCode = view.findViewById(R.id.item_daily_icon);
                TextView dailyWeatherText = view.findViewById(R.id.item_daily_temperature);

                String dayText = "";
                switch (position) {
                    case 0:
                        dayText = "今天";
                        break;
                    case 1:
                        dayText = "明天";
                        break;
                    case 2:
                        dayText = "后天";
                        break;
                }

//                dailyDateText.setText(dailyViewModel.getTime().format(DateTimeFormatter.ofPattern("MM月dd日")));
                dailyDateText.setText(dayText);
                dailyWeatherCode.setImageResource(dailyViewModel.getWeatherCode().getWeatherIconDay());
                dailyWeatherText.setText("最高" + dailyViewModel.getTemperatureMax() + "/最低" + dailyViewModel.getTemperatureMin());

                return view;
            }
        });
    }

    private void setupHourlyRecycler(WeatherViewModel weatherData) {
        weatherFragmentHourlyRecycler.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View basicView = View.inflate(getContext(), R.layout.item_hourly_weather, null);

                return new HourlyViewHolder(basicView);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                HourlyViewModel hourlyViewModel = weatherData.getHourlyData().get(position);
                HourlyViewHolder hourlyViewHolder = (HourlyViewHolder) holder;

                hourlyViewHolder.itemHourlyTimeText.setText(hourlyViewModel.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                hourlyViewHolder.itemHourlyWeatherCode.setImageResource(hourlyViewModel.getIsDay() ?
                        hourlyViewModel.getWeatherCode().getWeatherIconDay() :
                        hourlyViewModel.getWeatherCode().getWeatherIconNight());
                hourlyViewHolder.itemHourlyTemperatureText.setText(hourlyViewModel.getTemperature());
                hourlyViewHolder.itemHourlyWindSpeedText.setText(hourlyViewModel.getPrecipitationProbability() + "可能降雨");

            }

            @Override
            public int getItemCount() {
                return 24;
            }

            class HourlyViewHolder extends RecyclerView.ViewHolder {

                TextView itemHourlyTimeText;
                ImageView itemHourlyWeatherCode;
                TextView itemHourlyTemperatureText;
                TextView itemHourlyWindSpeedText;

                public HourlyViewHolder(@NonNull View itemView) {
                    super(itemView);

                    itemHourlyTimeText = itemView.findViewById(R.id.item_hourly_time);
                    itemHourlyWeatherCode = itemView.findViewById(R.id.item_hourly_icon);
                    itemHourlyTemperatureText = itemView.findViewById(R.id.item_hourly_tempreature);
                    itemHourlyWindSpeedText = itemView.findViewById(R.id.item_hourly_wind_speed);
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        weatherFragmentHourlyRecycler.setLayoutManager(layoutManager);
    }

    private void getLocationAndWeather() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        String providerName = "";
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            providerName = LocationManager.NETWORK_PROVIDER;
        } else if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            providerName = LocationManager.GPS_PROVIDER;
        } else {
            Toast.makeText(this.getContext(), "定位失败，请检查权限设置", Toast.LENGTH_SHORT).show();
//            runOnUiThread(this::onGetLocationFailed);
            getActivity().runOnUiThread(this::onGetLocationFailed);
            return;
        }
        // 权限复验
        if (ActivityCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            getActivity().runOnUiThread(this::onGetLocationFailed);
            Toast.makeText(this.getContext(), "定位失败，请检查权限设置", Toast.LENGTH_SHORT).show();
            return;
        }
        Location location = locationManager.getLastKnownLocation(providerName);
        if (location != null) {
            final double longitude = location.getLongitude();// 经度
            final double latitude = location.getLatitude();// 纬度
            Log.i("TAG", "longitude = " + longitude);
            Log.i("TAG", "latitude = " + latitude);

            LocationFetcher.fetchAndCacheLocationData(getContext(), 0, String.valueOf(latitude), String.valueOf(longitude), new ApiCallback<LocationData>() {
                @Override
                public void onFailure(IOException e) {
                    getActivity().runOnUiThread(() -> onGetLocationFailed());
                    Toast.makeText(getContext(), "定位失败，请检查权限设置", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(LocationData result) {
                    getWeather(0, result);
                }
            });
        } else {
            getActivity().runOnUiThread(this::onGetLocationFailed);
            Toast.makeText(this.getContext(), "定位失败，请检查权限设置", Toast.LENGTH_SHORT).show();
        }
    }

    private void getWeather(int id, LocationData locationData) {
        getActivity().runOnUiThread(() -> onGettingWeather(locationData));
        WeatherData weatherData = WeatherFetcher.getCachedLocationDataAsync(this.getContext(), id);
        if (weatherData != null) {
            getActivity().runOnUiThread(() -> onGetWeatherSucceed(WeatherViewModel.fromWeatherData(weatherData)));
        }

        WeatherFetcher.fetchAndCacheWeatherData(getContext(), id, locationData.getLatitude(), locationData.getLongitude(), new ApiCallback<WeatherData>() {
            @Override
            public void onFailure(IOException e) {
                getActivity().runOnUiThread(() -> onGetWeatherFailed());
                Toast.makeText(getContext(), "获取失败，请检查网络设置", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(WeatherData result) {
                getActivity().runOnUiThread(() -> onGetWeatherSucceed(WeatherViewModel.fromWeatherData(result)));
            }
        });
    }
}