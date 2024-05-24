package org.eu.fangkehou.weather.model.enums;

public enum WindDirection {
    NORTH("↑"),
    NORTHEAST("↖"),
    EAST("←"),
    SOUTHEAST("↙"),
    SOUTH("↓"),
    SOUTHWEST("↘"),
    WEST("→"),
    NORTHWEST("↗");

    private final String directionArrow;

    WindDirection(String directionArrow) {
        this.directionArrow = directionArrow;
    }

    public String getDirectionArrow() {
        return directionArrow;
    }

    public static WindDirection find(int windDirection) {
        if(windDirection >= 337.5 || windDirection <= 22.5)return WindDirection.NORTH;
        else if(windDirection > 292.5)return WindDirection.NORTHWEST;
        else if(windDirection >= 247.5)return WindDirection.WEST;
        else if(windDirection > 202.5)return WindDirection.SOUTHWEST;
        else if(windDirection > 157.5)return WindDirection.SOUTH;
        else if(windDirection > 112.5)return WindDirection.SOUTHEAST;
        else if(windDirection > 67.5)return WindDirection.EAST;
        else if(windDirection > 22.5)return WindDirection.NORTHEAST;
        return null;
    }
}
