package org.eu.fangkehou.weather.model.enums;

import org.eu.fangkehou.weather.R;

public enum BackgroundEnums {
    BACK_100("100",R.mipmap.back_100d),
    BACK_101("101", R.mipmap.back_101d),
    BACK_104("104", R.mipmap.back_104d),
    BACK_300("300", R.mipmap.back_300d),
    BACK_302("302", R.mipmap.back_302d);

    private final String code;
    private final int resId;

    BackgroundEnums(String code, int resId) {
        this.code = code;
        this.resId = resId;
    }

    public String getCode() {
        return code;
    }

    public int getResId() {
        return resId;
    }

    public static BackgroundEnums getBackById(String id) {
        switch (Integer.valueOf(id)) {
            case 101:
                return BACK_101;
            case 104:
                return BACK_104;
            case 300:
                return BACK_300;
            case 302:
                return BACK_302;
            case 100:
            default:
                return BACK_100;
        }
    }
}
