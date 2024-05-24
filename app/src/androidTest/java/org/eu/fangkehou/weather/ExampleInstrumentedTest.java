package org.eu.fangkehou.weather;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("org.eu.fangkehou.weather", appContext.getPackageName());
    }

    public void generateChinaCitiesData() throws IOException {
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//
//        AssetManager assetManager = appContext.getApplicationContext().getAssets();
//
//        InputStream inputStream = assetManager.open("china_city_data.json");
//
//
//        Type type = new TypeToken<ArrayList<Province>>(){}.getType();
//        List<Province> provincesList = new Gson().fromJson(new JsonReader(new BufferedReader(new InputStreamReader(inputStream))), type);
//
//        Log.i("Province List", String.valueOf(provincesList.size()));

    }
}