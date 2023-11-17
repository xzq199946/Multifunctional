package com.example.myapplicationcalculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationcalculator.databinding.HourItemBinding;

import java.util.List;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourHolder> {

    List<HourBean> hourBeanList;

    public HourAdapter(List<HourBean> hourBeanList) {
        this.hourBeanList = hourBeanList;
    }

    @NonNull
    @Override
    public HourHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hour_item, parent, false);
        return new HourHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HourHolder holder, int position) {
        int hourResourceInt = hourBeanList.get(position).getConditionImageResource();
        holder.hourItemBinding.ivHourOneImage.setImageResource(hourResourceInt);
        holder.bind(hourBeanList.get(position));
    }

    @Override
    public int getItemCount() {
        return hourBeanList.size();
    }

    static class HourHolder extends RecyclerView.ViewHolder {

        private HourItemBinding hourItemBinding;
        public HourHolder(@NonNull View itemView) {
            super(itemView);
            hourItemBinding = HourItemBinding.bind(itemView);
        }

        public void bind(HourBean hourBean) {
            hourItemBinding.setHour(hourBean);
        }


    }
}
