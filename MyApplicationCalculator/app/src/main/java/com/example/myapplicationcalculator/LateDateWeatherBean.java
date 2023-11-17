package com.example.myapplicationcalculator;

public class LateDateWeatherBean {
    private String dayTime = "";
    private String dayWeek = "";
    private int conditionImageResource = 0;
    private String tempLow = "";
    private String tempHeight = "";

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    public String getDayWeek() {
        return dayWeek;
    }

    public void setDayWeek(String dayWeek) {
        this.dayWeek = dayWeek;
    }

    public int getConditionImageResource() {
        return conditionImageResource;
    }

    public void setConditionImageResource(int conditionImageResource) {
        this.conditionImageResource = conditionImageResource;
    }

    public String getTempLow() {
        return tempLow;
    }

    public void setTempLow(String tempLow) {
        this.tempLow = tempLow;
    }

    public String getTempHeight() {
        return tempHeight;
    }

    public void setTempHeight(String tempHeight) {
        this.tempHeight = tempHeight;
    }
}
