package com.example.myapplicationcalculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationcalculator.databinding.DayItemBinding;

import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayHolder> {

    List<LateDateWeatherBean> mDays;

    public DayAdapter(List<LateDateWeatherBean> mDays) {
        this.mDays = mDays;
    }

    @NonNull
    @Override
    public DayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_item, parent, false);
        return new DayHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DayHolder holder, int position) {
        int resId = mDays.get(position).getConditionImageResource();
        holder.dayItemBinding.ivDayAfterOneDayImageView.setImageResource(resId);
        holder.bind(mDays.get(position));
    }

    @Override
    public int getItemCount() {
        return mDays.size();
    }

    /*
     *替换imageView图标
     */
    public  void displayiv(String weathercondition, ImageView view){//显示天气图标
        System.out.println("displayiv "+weathercondition);
        switch (weathercondition){
            case "阴":
                view.setImageResource(R.drawable.ying);
                break;
            case "晴":
                view.setImageResource(R.drawable.qing);
                break;
            case "多云":
                view.setImageResource(R.drawable.duoyun);
                break;
            case "小雨":
                view.setImageResource(R.drawable.xiaoyu);
                break;
            case "中雨":
                view.setImageResource(R.drawable.zhongyu);
                break;
            case "大雨":
                view.setImageResource(R.drawable.dayu);
                break;
            case "暴雨":
                view.setImageResource(R.drawable.baoyu);
                break;
            case "大暴雨":
                view.setImageResource(R.drawable.dabaoyu);
                break;
            case "特大暴雨":
                view.setImageResource(R.drawable.tedabaoyu);
                break;
            case "小雪":
                view.setImageResource(R.drawable.xiaoyue);
                break;
            case "中雪":
                view.setImageResource(R.drawable.zhongyue);
                break;
            case "大雪":
                view.setImageResource(R.drawable.dayue);
                break;
            case "暴雪":
                view.setImageResource(R.drawable.dabaoyue);
                break;
            default:
                view.setImageResource(R.drawable.qing);
        }
    }

    static class DayHolder extends RecyclerView.ViewHolder {
        private DayItemBinding dayItemBinding;
        public DayHolder(@NonNull View itemView) {
            super(itemView);
            dayItemBinding = DayItemBinding.bind(itemView);
        }

        public void bind(LateDateWeatherBean day) {
            dayItemBinding.setDay(day);
        }
    }
}
