package com.irrigai.mobile.ui.owner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.irrigai.mobile.R;
import com.irrigai.mobile.data.local.entity.WorkerEntity;
import java.util.List;

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.ViewHolder> {
    private List<WorkerEntity> workers;
    private OnWorkerActionListener listener;

    public interface OnWorkerActionListener {
        void onAssignTask(WorkerEntity worker);
        void onAddRemark(WorkerEntity worker);
    }

    public WorkerAdapter(List<WorkerEntity> workers, OnWorkerActionListener listener) {
        this.workers = workers;
        this.listener = listener;
    }

    public void updateWorkers(List<WorkerEntity> workers) {
        this.workers = workers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_worker, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkerEntity w = workers.get(position);
        holder.tvName.setText(w.name != null ? w.name : "Worker");
        holder.tvStatus.setText(w.status != null ? w.status : "OFF_DUTY");
        holder.tvStatus.setTextColor(w.status != null && w.status.equals("ON_DUTY")
                ? holder.itemView.getContext().getColor(R.color.primary_green)
                : holder.itemView.getContext().getColor(R.color.text_secondary));
        if (w.assignedTask != null && !w.assignedTask.isEmpty()) {
            holder.tvTask.setVisibility(View.VISIBLE);
            holder.tvTask.setText("Task: " + w.assignedTask);
        } else {
            holder.tvTask.setVisibility(View.GONE);
        }
        if (w.remark != null && !w.remark.isEmpty()) {
            holder.tvRemark.setVisibility(View.VISIBLE);
            holder.tvRemark.setText("Remark: " + w.remark);
        } else {
            holder.tvRemark.setVisibility(View.GONE);
        }
        holder.btnAssignTask.setOnClickListener(v -> {
            if (listener != null) listener.onAssignTask(w);
        });
        holder.btnAddRemark.setOnClickListener(v -> {
            if (listener != null) listener.onAddRemark(w);
        });
    }

    @Override
    public int getItemCount() {
        return workers.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvStatus, tvTask, tvRemark;
        Button btnAssignTask, btnAddRemark;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvWorkerName);
            tvStatus = itemView.findViewById(R.id.tvWorkerStatus);
            tvTask = itemView.findViewById(R.id.tvWorkerTask);
            tvRemark = itemView.findViewById(R.id.tvWorkerRemark);
            btnAssignTask = itemView.findViewById(R.id.btnAssignTask);
            btnAddRemark = itemView.findViewById(R.id.btnAddRemark);
        }
    }
}
