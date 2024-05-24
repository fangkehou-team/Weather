package org.eu.fangkehou.weather;

import okhttp3.HttpUrl;
import org.eu.fangkehou.weather.model.api.AmapLocationResult;
import org.eu.fangkehou.weather.model.api.MetroLocationResult;
import org.eu.fangkehou.weather.model.api.MetroWeatherResult;
import org.eu.fangkehou.weather.model.bean.WeatherData;
import org.eu.fangkehou.weather.model.view.WeatherViewModel;
import org.eu.fangkehou.weather.util.DemoData;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ApiTest {
    @Test
    public void location_isCorrect() {
        String data0 = "{\"status\":\"1\",\"regeocode\":{\"addressComponent\":{\"city\":[],\"province\":\"北京市\",\"adcode\":\"110102\",\"district\":\"西城区\",\"towncode\":\"110102013000\",\"streetNumber\":{\"number\":\"4号楼\",\"location\":\"116.389346,39.899869\",\"direction\":\"西\",\"distance\":\"57.6563\",\"street\":\"前门西大街\"},\"country\":\"中国\",\"township\":\"大栅栏街道\",\"businessAreas\":[{\"location\":\"116.391134,39.895089\",\"name\":\"大栅栏\",\"id\":\"110102\"},{\"location\":\"116.375880,39.896727\",\"name\":\"宣武门\",\"id\":\"110102\"},{\"location\":\"116.402959,39.892171\",\"name\":\"珠市口\",\"id\":\"110101\"}],\"building\":{\"name\":[],\"type\":[]},\"neighborhood\":{\"name\":[],\"type\":[]},\"citycode\":\"010\"},\"formatted_address\":\"北京市西城区大栅栏街道前门西大街前门西大街辅路-道路停车位\"},\"info\":\"OK\",\"infocode\":\"10000\"}";
        String data1 = "{\"status\":\"1\",\"regeocode\":{\"addressComponent\":{\"city\":\"沈阳市\",\"province\":\"辽宁省\",\"adcode\":\"210112\",\"district\":\"浑南区\",\"towncode\":\"210112008000\",\"streetNumber\":{\"number\":\"6号\",\"location\":\"123.443820,41.693233\",\"direction\":\"西北\",\"distance\":\"114.324\",\"street\":\"智慧大街\"},\"country\":\"中国\",\"township\":\"白塔街道\",\"businessAreas\":[{\"location\":\"123.425795,41.687260\",\"name\":\"白塔\",\"id\":\"210112\"}],\"building\":{\"name\":[],\"type\":[]},\"neighborhood\":{\"name\":[],\"type\":[]},\"citycode\":\"024\"},\"formatted_address\":\"辽宁省沈阳市浑南区白塔街道白塔街道高深社区退役军人服务站绿地海域香庭\"},\"info\":\"OK\",\"infocode\":\"10000\"}\n";

        AmapLocationResult amr0 = AmapLocationResult.parseInstance(data0);
        AmapLocationResult amr1 = AmapLocationResult.parseInstance(data1);

        System.out.println(amr0.getCity());
        System.out.println(amr1.getCity());


        assertEquals(amr0.getCity(), "北京市");
        assertEquals(amr1.getCity(), "沈阳市");


        String data2 = "{\"results\":[{\"id\":1785738,\"name\":\"Yuncheng\",\"latitude\":35.02306,\"longitude\":110.99278,\"elevation\":361.0,\"feature_code\":\"PPL\",\"country_code\":\"CN\",\"admin1_id\":1795912,\"admin2_id\":1785736,\"timezone\":\"Asia/Shanghai\",\"population\":201950,\"country_id\":1814991,\"country\":\"中国\",\"admin1\":\"山西省\",\"admin2\":\"运城\"}],\"generationtime_ms\":0.26595592}";
        String data3 = "{\"results\":[{\"id\":2034937,\"name\":\"沈阳市\",\"latitude\":41.79222,\"longitude\":123.43278,\"elevation\":53.0,\"feature_code\":\"PPLA\",\"country_code\":\"CN\",\"admin1_id\":2036115,\"admin2_id\":2034935,\"timezone\":\"Asia/Shanghai\",\"population\":6255921,\"country_id\":1814991,\"country\":\"中国\",\"admin1\":\"辽宁省\",\"admin2\":\"沈阳\"}],\"generationtime_ms\":0.38599968}";

        MetroLocationResult mlr1 = MetroLocationResult.parseInstance(data2);
        MetroLocationResult mlr2 = MetroLocationResult.parseInstance(data3);

        assertEquals(mlr1.getResults().size(), 1);
        assertEquals(mlr2.getResults().size(), 1);

        assertEquals(mlr1.getResults().get(0).getName(), "Yuncheng");
        assertEquals(mlr2.getResults().get(0).getName(), "沈阳市");
    }

    @Test
    public void weather_isCorrect() {
        MetroWeatherResult mrt = MetroWeatherResult.parseInstance(DemoData.MetroWeatherData);
        System.out.println(mrt);

        WeatherData weatherData = WeatherData.fromMetroWeather(mrt);
        System.out.println(weatherData.getHourlyData().size());

        WeatherViewModel weatherViewModel = WeatherViewModel.fromWeatherData(weatherData);
        System.out.println(weatherViewModel.getCurrentData().getWeatherCode().getWeatherText());
    }

    @Test
    public void url_isCorrect() {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("geocoding-api.open-meteo.com")
                .addPathSegments("v1/search")
                .query("language=zh&count=1")
                .addQueryParameter("name", "沈阳市")
                .build();

        HttpUrl url1 = new HttpUrl.Builder()
                .scheme("https")
                .host("lbs.amap.com")
                .addPathSegments("AMapService/v3/geocode/regeo")
                .query("key=a4abc6f3f9cb2db7d77675e523e8b416&s=rsv3&language=zh_cn&radius=1000&output=json&" +
                        "logversion=2.0&appname=https%3A%2F%2Flbs.amap.com%2Ffn%2Fiframe%2F&csid=CEC8FA99-83A7-445C-BCAD-80C9BE69A963" +
                        "&sdkversion=1.4.26")
                .addQueryParameter("location", "123.444837,41.69254")
                .build();

        HttpUrl url2 = new HttpUrl.Builder()
                .scheme("https")
                .host("api.open-meteo.com")
                .addPathSegments("v1/forecast")
                .query("current_weather=true&timezone=auto&timeformat=unixtime" +
                        "&hourly=temperature_2m,weathercode,precipitation_probability,weathercode,winddirection_10m,apparent_temperature,relativehumidity_2m,cloudcover,surface_pressure,is_day,uv_index" +
                        "&daily=weathercode,precipitation_probability_max,temperature_2m_min,temperature_2m_max,sunrise,sunset")
                .addQueryParameter("longitude", "123.43278")
                .addQueryParameter("latitude", "41.79222")
                .build();

        System.out.println(url);
        System.out.println(url1);
        System.out.println(url2);
    }
}