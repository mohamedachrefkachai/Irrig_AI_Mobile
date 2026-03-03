package com.irrigai.mobile.ui.worker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.irrigai.mobile.model.Robot;
import com.irrigai.mobile.util.StorageHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkerRobotsFragment extends Fragment {
    private RecyclerView recyclerView;
    private WorkerRobotAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private StorageHelper storageHelper;
    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_worker_robots, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        storageHelper = new StorageHelper(requireContext());
        apiService = ApiClient.getApiService();

        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new WorkerRobotAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        if (swipeRefresh != null) {
            swipeRefresh.setOnRefreshListener(this::loadRobots);
        }
        loadRobots();
    }

    private void loadRobots() {
        String farmId = storageHelper.getFarmId();
        if (farmId == null) {
            Toast.makeText(requireContext(), "Farm not selected", Toast.LENGTH_SHORT).show();
            if (swipeRefresh != null) swipeRefresh.setRefreshing(false);
            return;
        }

        String token = storageHelper.getToken();
        String authHeader = token != null ? "Bearer " + token : "";

        apiService.getFarmData(farmId, authHeader).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (swipeRefresh != null) swipeRefresh.setRefreshing(false);
                if (!isAdded()) return;

                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(requireContext(), "Erreur chargement robots", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Robot> robots = new ArrayList<>();
                Object robotsObj = response.body().get("robots");
                if (robotsObj instanceof List<?>) {
                    for (Object item : (List<?>) robotsObj) {
                        if (item instanceof Map<?, ?>) {
                            Map<?, ?> robotMap = (Map<?, ?>) item;
                            Robot robot = new Robot();
                            Object id = robotMap.get("_id");
                            robot.setId(id != null ? String.valueOf(id) : "");
                            Object name = robotMap.get("name");
                            robot.setName(name != null ? String.valueOf(name) : "Robot");
                            Object status = robotMap.get("status");
                            robot.setStatus(status != null ? String.valueOf(status) : "OFFLINE");
                            Object battery = robotMap.get("battery_level");
                            robot.setBatteryLevel(battery instanceof Number ? ((Number) battery).intValue() : 0);
                            Object health = robotMap.get("health_status");
                            robot.setHealthStatus(health != null ? String.valueOf(health) : "OK");
                            robots.add(robot);
                        }
                    }
                }
                adapter.updateRobots(robots);
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
