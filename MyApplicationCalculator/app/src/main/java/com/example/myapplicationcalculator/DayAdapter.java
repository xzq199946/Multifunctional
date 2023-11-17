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
