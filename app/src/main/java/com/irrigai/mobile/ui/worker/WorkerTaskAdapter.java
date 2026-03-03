package com.irrigai.mobile.ui.worker;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.irrigai.mobile.R;
import com.irrigai.mobile.api.RetrofitClient;
import com.irrigai.mobile.model.Task;
import com.irrigai.mobile.util.StorageHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkerTaskAdapter extends RecyclerView.Adapter<WorkerTaskAdapter.ViewHolder> {
    private static final String TAG = "WorkerTaskAdapter";
    private List<Task> tasks;
    private final StorageHelper storageHelper;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public WorkerTaskAdapter(List<Task> tasks, StorageHelper storageHelper) {
        this.tasks = tasks;
        this.storageHelper = storageHelper;
    }

    public void updateTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_worker_task_simple, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.tvTitle.setText(task.getTitle());
        holder.tvDescription.setText(task.getDescription());
        holder.tvStatus.setText(task.getStatus());

        String meta = "Priorité: " + (task.getPriority() != null ? task.getPriority() : "MEDIUM");
        if (task.getDueDate() > 0) {
            meta += " • Date: " + dateFormat.format(new Date(task.getDueDate()));
        }
        holder.tvMeta.setText(meta);

        int statusColor = holder.itemView.getContext().getColor(R.color.text_secondary);
        if ("IN_PROGRESS".equals(task.getStatus())) {
            statusColor = holder.itemView.getContext().getColor(R.color.status_orange);
        } else if ("DONE".equals(task.getStatus())) {
            statusColor = holder.itemView.getContext().getColor(R.color.primary_green);
        }
        holder.tvStatus.setTextColor(statusColor);

        holder.btnInProgress.setEnabled(!"IN_PROGRESS".equals(task.getStatus()));
        holder.btnDone.setEnabled(!"DONE".equals(task.getStatus()));

        holder.btnInProgress.setOnClickListener(v -> updateTaskStatus(task, "IN_PROGRESS", holder));
        holder.btnDone.setOnClickListener(v -> updateTaskStatus(task, "DONE", holder));
    }

    private void updateTaskStatus(Task task, String newStatus, ViewHolder holder) {
        String token = storageHelper.getToken();
        if (token == null || token.isEmpty()) {
            Toast.makeText(holder.itemView.getContext(),
                    "Token non disponible", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> statusBody = new HashMap<>();
        statusBody.put("status", newStatus);

        RetrofitClient.getInstance()
                .getApiService()
                .updateTaskStatus("Bearer " + token, task.getId(), statusBody)
                .enqueue(new Callback<Map<String, Object>>() {
                    @Override
                    public void onResponse(Call<Map<String, Object>> call,
                                           Response<Map<String, Object>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            task.setStatus(newStatus);
                            notifyDataSetChanged();
                            Toast.makeText(holder.itemView.getContext(),
                                    "Statut mis à jour", Toast.LENGTH_SHORT).show();
                        } else {
                            String errorMsg = "Unknown error";
                            try {
                                if (response.errorBody() != null) {
                                    String errorBody = response.errorBody().string();
                                    Log.e(TAG, "Error response: " + errorBody);
                                    errorMsg = errorBody;
                                }
                            } catch (Exception e) {
                                Log.e(TAG, "Error parsing error body", e);
                            }
                            Log.e(TAG, "Update failed: " + response.code() + " - " + errorMsg);
                            Toast.makeText(holder.itemView.getContext(),
                                    "Erreur: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                        Log.e(TAG, "Update error", t);
                        Toast.makeText(holder.itemView.getContext(),
                                "Erreur: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription, tvMeta, tvStatus;
        Button btnInProgress, btnDone;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTaskTitle);
            tvDescription = itemView.findViewById(R.id.tvTaskDescription);
            tvMeta = itemView.findViewById(R.id.tvTaskMeta);
            tvStatus = itemView.findViewById(R.id.tvTaskStatus);
            btnInProgress = itemView.findViewById(R.id.btnInProgressTask);
            btnDone = itemView.findViewById(R.id.btnDoneTask);
        }
    }
}
