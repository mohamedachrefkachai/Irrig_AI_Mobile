package com.irrigai.mobile.ui.owner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.irrigai.mobile.R;

import java.util.List;

public class OwnerHourlyWeatherAdapter extends RecyclerView.Adapter<OwnerHourlyWeatherAdapter.ViewHolder> {

    public static class HourItem {
        public final String time;
        public final String icon;
        public final String temp;

        public HourItem(String time, String icon, String temp) {
            this.time = time;
            this.icon = icon;
            this.temp = temp;
        }
    }

    private final List<HourItem> items;

    public OwnerHourlyWeatherAdapter(List<HourItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hourly_weather, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HourItem item = items.get(position);
        holder.tvHour.setText(item.time);
        holder.tvIcon.setText(item.icon);
        holder.tvTemp.setText(item.temp);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHour, tvIcon, tvTemp;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHour = itemView.findViewById(R.id.tvHour);
            tvIcon = itemView.findViewById(R.id.tvIcon);
            tvTemp = itemView.findViewById(R.id.tvTemp);
        }
    }

    public static void setupHorizontalRecycler(RecyclerView rv, List<HourItem> data) {
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv.setAdapter(new OwnerHourlyWeatherAdapter(data));
    }
}

