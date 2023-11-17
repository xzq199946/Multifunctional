package com.example.myapplicationcalculator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class DaysViewModel extends ViewModel {
    private MutableLiveData<List<LateDateWeatherBean>> mutableDayList;

    public LiveData<List<LateDateWeatherBean>> getDaysLiveData() {
        if (null == mutableDayList) {
            mutableDayList = new MutableLiveData<>();
        }

        return mutableDayList;
    }

    public void setDays(List<LateDateWeatherBean> days) {
        if (null == mutableDayList) {
            mutableDayList = new MutableLiveData<>();
        }

        mutableDayList.setValue(days);
    }
}
