package com.irrigai.mobile.ui.worker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.irrigai.mobile.R;
import com.irrigai.mobile.model.Robot;
import java.util.List;

public class WorkerRobotAdapter extends RecyclerView.Adapter<WorkerRobotAdapter.ViewHolder> {
    private List<Robot> robots;

    public WorkerRobotAdapter(List<Robot> robots) {
        this.robots = robots;
    }

    public void updateRobots(List<Robot> robots) {
        this.robots = robots;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_worker_robot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Robot robot = robots.get(position);
        holder.tvName.setText(robot.getName());
        holder.tvStatus.setText("Statut: " + (robot.getStatus() != null ? robot.getStatus() : "OFFLINE"));
        holder.tvHealth.setText("Santé: " + (robot.getHealthStatus() != null ? robot.getHealthStatus() : "OK"));
        holder.progressBattery.setProgress(robot.getBatteryLevel());
        holder.tvBattery.setText(robot.getBatteryLevel() + "%");

        // Color status indicator
        int statusColor = holder.itemView.getContext().getColor(R.color.text_secondary);
        if ("ONLINE".equals(robot.getStatus())) {
            statusColor = holder.itemView.getContext().getColor(R.color.primary_green);
        } else if ("BUSY".equals(robot.getStatus())) {
            statusColor = holder.itemView.getContext().getColor(R.color.status_orange);
        }
        holder.tvStatus.setTextColor(statusColor);
    }

    @Override
    public int getItemCount() {
        return robots.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvStatus, tvHealth, tvBattery;
        ProgressBar progressBattery;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvRobotName);
            tvStatus = itemView.findViewById(R.id.tvRobotStatus);
            tvHealth = itemView.findViewById(R.id.tvRobotHealth);
            tvBattery = itemView.findViewById(R.id.tvBatteryPercent);
            progressBattery = itemView.findViewById(R.id.progressBattery);
        }
    }
}
