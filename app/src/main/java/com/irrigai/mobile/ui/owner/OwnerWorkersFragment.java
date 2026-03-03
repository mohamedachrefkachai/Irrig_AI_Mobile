package com.irrigai.mobile.ui.owner;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.irrigai.mobile.R;
import com.irrigai.mobile.api.ApiClient;
import com.irrigai.mobile.api.ApiService;
import com.irrigai.mobile.data.local.AppDatabase;
import com.irrigai.mobile.data.local.entity.WorkerEntity;
import com.irrigai.mobile.util.StorageHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerWorkersFragment extends Fragment implements WorkerAdapter.OnWorkerActionListener {
    private RecyclerView recyclerView;
    private WorkerAdapter adapter;
    private ApiService apiService;
    private StorageHelper storageHelper;
    private Button btnAddWorker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_owner_workers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        apiService = ApiClient.getApiService();
        storageHelper = new StorageHelper(requireContext());
        
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new WorkerAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);
        
        // Add button to show add worker dialog
        btnAddWorker = view.findViewById(R.id.btnAddWorker);
        if (btnAddWorker != null) {
            btnAddWorker.setOnClickListener(v -> showAddWorkerDialog());
        }
        
        loadWorkers();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadWorkers();
    }

    private void loadWorkers() {
        // First try to load from local database
        Executors.newSingleThreadExecutor().execute(() -> {
            List<WorkerEntity> workers = AppDatabase.getDatabase(requireContext()).workerDao().getAllWorkers();
            requireActivity().runOnUiThread(() -> adapter.updateWorkers(workers));
        });
        
        // Also fetch from server if available
        String ownerId = storageHelper.getUserId();
        if (ownerId != null) {
            fetchWorkersFromServer(ownerId);
        }
    }

    private void fetchWorkersFromServer(String ownerId) {
        String token = storageHelper.getToken();
        if (token == null) return;
        
        // Call API to list workers
        // Note: The actual implementation depends on your API structure
        // This is a placeholder for future enhancement
    }

    private void showAddWorkerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add New Worker");
        
        // Create layout for the dialog
        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(48, 24, 48, 24);
        
        // Email field
        EditText emailInput = new EditText(requireContext());
        emailInput.setHint("Worker Email");
        emailInput.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        layout.addView(emailInput);
        
        // Name field
        EditText nameInput = new EditText(requireContext());
        nameInput.setHint("Worker Name (optional)");
        layout.addView(nameInput);
        
        builder.setView(layout);
        builder.setPositiveButton("Add Worker", (dialog, which) -> {
            String email = emailInput.getText().toString().trim();
            String name = nameInput.getText().toString().trim();
            
            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            
            addWorkerViaAPI(email, name);
        });
        
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void addWorkerViaAPI(String email, String name) {
        String ownerId = storageHelper.getUserId();
        String token = storageHelper.getToken();
        
        if (ownerId == null || token == null) {
            Toast.makeText(requireContext(), "Authentication required", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (btnAddWorker != null) {
            btnAddWorker.setEnabled(false);
        }
        
        Map<String, String> workerData = new HashMap<>();
        workerData.put("owner_id", ownerId);
        workerData.put("farm_id", ""); // Will be updated when farms are loaded
        workerData.put("worker_email", email);
        workerData.put("worker_name", name.isEmpty() ? "Unknown Worker" : name);
        
        apiService.addWorker(token, workerData).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (btnAddWorker != null) {
                    btnAddWorker.setEnabled(true);
                }
                
                if (!response.isSuccessful()) {
                    Toast.makeText(requireContext(), "Failed to add worker", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                Toast.makeText(requireContext(), "Worker added successfully! Default password: azerty", Toast.LENGTH_LONG).show();
                loadWorkers();
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                if (btnAddWorker != null) {
                    btnAddWorker.setEnabled(true);
                }
                Toast.makeText(requireContext(), "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAssignTask(WorkerEntity worker) {
        EditText input = new EditText(requireContext());
        input.setHint("Enter task");
        input.setText(worker.assignedTask != null ? worker.assignedTask : "");
        input.setPadding(48, 48, 48, 48);
        new AlertDialog.Builder(requireContext())
                .setTitle("Assign Task")
                .setMessage("Task for " + worker.name)
                .setView(input)
                .setPositiveButton("Save", (d, w) -> {
                    String task = input.getText().toString().trim();
                    saveTaskAndRemark(worker.id, task, worker.remark);
                    Toast.makeText(requireContext(), "Task saved", Toast.LENGTH_SHORT).show();
                    loadWorkers();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onAddRemark(WorkerEntity worker) {
        EditText input = new EditText(requireContext());
        input.setHint("Enter remark");
        input.setText(worker.remark != null ? worker.remark : "");
        input.setPadding(48, 48, 48, 48);
        new AlertDialog.Builder(requireContext())
                .setTitle("Add Remark")
                .setMessage("Remark for " + worker.name)
                .setView(input)
                .setPositiveButton("Save", (d, w) -> {
                    String remark = input.getText().toString().trim();
                    saveTaskAndRemark(worker.id, worker.assignedTask != null ? worker.assignedTask : "", remark);
                    Toast.makeText(requireContext(), "Remark saved", Toast.LENGTH_SHORT).show();
                    loadWorkers();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void saveTaskAndRemark(String workerId, String task, String remark) {
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase.getDatabase(requireContext()).workerDao().updateTaskAndRemark(workerId, task, remark);
            requireActivity().runOnUiThread(this::loadWorkers);
        });
    }
}
