package com.irrigai.mobile.ui.worker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.irrigai.mobile.R;
import com.irrigai.mobile.api.ApiClient;
import com.irrigai.mobile.api.ApiService;
import com.irrigai.mobile.model.Task;
import com.irrigai.mobile.util.StorageHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.text.ParseException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkerTasksFragment extends Fragment {
    private RecyclerView recyclerView;
    private WorkerTaskAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private Spinner spinnerPriority;
    private Button btnPickDate;
    private Button btnClearDate;
    private StorageHelper storageHelper;
    private ApiService apiService;

    private final List<Task> allTasks = new ArrayList<>();
    private String selectedPriority = "ALL";
    private Long selectedDateMillis = null;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_worker_tasks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        storageHelper = new StorageHelper(requireContext());
        apiService = ApiClient.getApiService();

        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        spinnerPriority = view.findViewById(R.id.spinnerPriority);
        btnPickDate = view.findViewById(R.id.btnPickDate);
        btnClearDate = view.findViewById(R.id.btnClearDate);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new WorkerTaskAdapter(new ArrayList<>(), storageHelper);
        recyclerView.setAdapter(adapter);

        setupFilters();

        if (swipeRefresh != null) {
            swipeRefresh.setOnRefreshListener(this::loadTasks);
        }
        loadTasks();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTasks();
    }

    private void setupFilters() {
        List<String> priorities = new ArrayList<>();
        priorities.add("ALL");
        priorities.add("LOW");
        priorities.add("MEDIUM");
        priorities.add("HIGH");

        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                priorities
        );
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(priorityAdapter);

        spinnerPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPriority = priorities.get(position);
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        btnPickDate.setOnClickListener(v -> showDatePicker());
        btnClearDate.setOnClickListener(v -> {
            selectedDateMillis = null;
            btnPickDate.setText("Date");
            applyFilters();
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        if (selectedDateMillis != null) {
            calendar.setTimeInMillis(selectedDateMillis);
        }

        DatePickerDialog dialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    Calendar selected = Calendar.getInstance();
                    selected.set(year, month, dayOfMonth, 0, 0, 0);
                    selected.set(Calendar.MILLISECOND, 0);
                    selectedDateMillis = selected.getTimeInMillis();
                    btnPickDate.setText(dateFormat.format(new Date(selectedDateMillis)));
                    applyFilters();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    private void applyFilters() {
        List<Task> filtered = new ArrayList<>();
        for (Task task : allTasks) {
            boolean matchesPriority = "ALL".equals(selectedPriority)
                    || selectedPriority.equalsIgnoreCase(task.getPriority());

            boolean matchesDate = true;
            if (selectedDateMillis != null) {
                long taskDate = task.getDueDate() > 0 ? task.getDueDate() : task.getCreatedAt();
                if (taskDate > 0) {
                    matchesDate = isSameDay(taskDate, selectedDateMillis);
                } else {
                    matchesDate = false;
                }
            }

            if (matchesPriority && matchesDate) {
                filtered.add(task);
            }
        }

        adapter.updateTasks(filtered);
    }

    private boolean isSameDay(long firstMillis, long secondMillis) {
        Calendar first = Calendar.getInstance();
        first.setTimeInMillis(firstMillis);

        Calendar second = Calendar.getInstance();
        second.setTimeInMillis(secondMillis);

        return first.get(Calendar.YEAR) == second.get(Calendar.YEAR)
                && first.get(Calendar.DAY_OF_YEAR) == second.get(Calendar.DAY_OF_YEAR);
    }

    private long parseDateValue(Object value) {
        if (value == null) return 0L;
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }

        String text = String.valueOf(value);
        if (text.isEmpty()) return 0L;

        try {
            return Long.parseLong(text);
        } catch (NumberFormatException ignored) { }

        try {
            // Try ISO 8601 format
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date parsed = isoFormat.parse(text);
            return parsed.getTime();
        } catch (ParseException ignored) {
            return 0L;
        }
    }

    private void loadTasks() {
        String token = storageHelper.getToken();
        if (token == null) {
            Toast.makeText(requireContext(), "Token not found", Toast.LENGTH_SHORT).show();
            if (swipeRefresh != null) swipeRefresh.setRefreshing(false);
            return;
        }

        apiService.getWorkerInfo(token).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (swipeRefresh != null) swipeRefresh.setRefreshing(false);
                if (!isAdded()) return;

                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(requireContext(), "Erreur chargement tasks", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Task> tasks = new ArrayList<>();
                try {
                    Object tasksObj = response.body().get("tasks");
                    if (tasksObj instanceof List<?>) {
                        for (Object item : (List<?>) tasksObj) {
                            if (item instanceof Map<?, ?>) {
                                Map<?, ?> taskMap = (Map<?, ?>) item;
                                Task task = new Task();
                                Object id = taskMap.get("id");
                                task.setId(id != null ? String.valueOf(id) : "");
                                Object title = taskMap.get("title");
                                task.setTitle(title != null ? String.valueOf(title) : "-");
                                Object description = taskMap.get("description");
                                task.setDescription(description != null ? String.valueOf(description) : "");
                                Object priority = taskMap.get("priority");
                                task.setPriority(priority != null ? String.valueOf(priority) : "MEDIUM");
                                Object status = taskMap.get("status");
                                task.setStatus(status != null ? String.valueOf(status) : "TODO");

                                task.setDueDate(parseDateValue(taskMap.get("due_date")));
                                task.setCreatedAt(parseDateValue(taskMap.get("created_at")));
                                tasks.add(task);
                            }
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(requireContext(), "Erreur parsing tasks: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                allTasks.clear();
                allTasks.addAll(tasks);
                applyFilters();

                if (tasks.isEmpty()) {
                    Toast.makeText(requireContext(), "Aucune tâche assignée pour le moment", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                if (swipeRefresh != null) swipeRefresh.setRefreshing(false);
                if (!isAdded()) return;
                Toast.makeText(requireContext(), "Erreur réseau: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
