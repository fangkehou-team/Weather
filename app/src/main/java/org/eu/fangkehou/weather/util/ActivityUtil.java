package org.eu.fangkehou.weather.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityUtil{

    private ActivityUtil() {
    }

    private static ActivityUtil instance = new ActivityUtil();
    private static List<Activity> activityStack = new ArrayList<Activity>();

    public static ActivityUtil getInstance() {
        return instance;
    }

    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    public void removeActivity(Activity activity) {
        activityStack.remove(activity);
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            activityStack.get(i).finish();
        }
        activityStack.clear();
    }

}
