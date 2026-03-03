package com.irrigai.mobile.ui.owner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.irrigai.mobile.R;
import com.irrigai.mobile.model.Robot;
import java.util.List;

public class RobotAdapter extends RecyclerView.Adapter<RobotAdapter.ViewHolder> {
    public interface OnRobotClickListener {
        void onRobotClick(Robot robot);
    }

    private List<Robot> robots;
    private final OnRobotClickListener listener;

    public RobotAdapter(List<Robot> robots, OnRobotClickListener listener) {
        this.robots = robots;
        this.listener = listener;
    }

    public void updateRobots(List<Robot> robots) {
        this.robots = robots;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_robot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Robot robot = robots.get(position);
        holder.tvName.setText(robot.getName() != null ? robot.getName() : "Robot");
        holder.tvLocation.setText(robot.getLocation() != null ? robot.getLocation() : "-");
        String status = robot.getStatus() != null ? robot.getStatus() : "idle";
        holder.tvStatus.setText(status.toUpperCase());
        holder.tvBattery.setText(robot.getBatteryLevel() + "%");
        // Status colors: Red -> ERROR, Green -> WORKING, Blue -> IDLE
        int colorRes;
        switch (status.toLowerCase()) {
            case "error":
                colorRes = R.color.status_red;
                break;
            case "working":
                colorRes = R.color.status_green;
                break;
            case "idle":
            default:
                colorRes = R.color.status_blue;
                break;
        }
        holder.tvStatus.setTextColor(holder.itemView.getContext().getColor(colorRes));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onRobotClick(robot);
        });
    }

    @Override
    public int getItemCount() {
        return robots.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvLocation, tvStatus, tvBattery;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvBattery = itemView.findViewById(R.id.tvBattery);
        }
    }
}
