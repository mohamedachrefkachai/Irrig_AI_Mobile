package com.irrigai.mobile.ui.worker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.irrigai.mobile.R;
import com.irrigai.mobile.model.Zone;
import java.util.List;

public class WorkerZoneAdapter extends RecyclerView.Adapter<WorkerZoneAdapter.ViewHolder> {
    public interface OnZoneClickListener {
        void onZoneClick(Zone zone);
    }

    private List<Zone> zones;
    private final OnZoneClickListener onZoneClickListener;

    public WorkerZoneAdapter(List<Zone> zones, OnZoneClickListener onZoneClickListener) {
        this.zones = zones;
        this.onZoneClickListener = onZoneClickListener;
    }

    public void updateZones(List<Zone> zones) {
        this.zones = zones;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_worker_zone, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Zone zone = zones.get(position);
        holder.tvName.setText(zone.getName());
        holder.tvCrop.setText("Culture: " + (zone.getCropType() != null ? zone.getCropType() : "Non spécifiée"));
        holder.tvSize.setText(String.format("Taille: %.0f x %.0f m", zone.getWidth(), zone.getLength()));
        holder.tvMode.setText("Mode: " + (zone.getMode() != null ? zone.getMode() : "AUTO"));

        holder.itemView.setOnClickListener(v -> {
            if (onZoneClickListener != null) {
                onZoneClickListener.onZoneClick(zone);
            }
        });
    }

    @Override
    public int getItemCount() {
        return zones.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCrop, tvSize, tvMode;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvZoneName);
            tvCrop = itemView.findViewById(R.id.tvZoneCrop);
            tvSize = itemView.findViewById(R.id.tvZoneSize);
            tvMode = itemView.findViewById(R.id.tvZoneMode);
        }
    }
}
