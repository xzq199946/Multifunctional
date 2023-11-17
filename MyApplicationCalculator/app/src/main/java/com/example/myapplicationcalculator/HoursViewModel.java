package com.example.myapplicationcalculator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class HoursViewModel extends ViewModel {
    private MutableLiveData<List<HourBean>> mutableHourList;

    public LiveData<List<HourBean>> getDaysLiveData() {
        if (null == mutableHourList) {
            mutableHourList = new MutableLiveData<>();
        }

        return mutableHourList;
    }

    public void setDays(List<HourBean> days) {
        if (null == mutableHourList) {
            mutableHourList = new MutableLiveData<>();
        }

        mutableHourList.setValue(days);
    }
}
