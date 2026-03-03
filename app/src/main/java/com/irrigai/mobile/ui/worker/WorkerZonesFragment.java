package com.irrigai.mobile.ui.worker;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import com.irrigai.mobile.model.Tree;
import com.irrigai.mobile.model.Zone;
import com.irrigai.mobile.util.StorageHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkerZonesFragment extends Fragment {
    private RecyclerView recyclerView;
    private WorkerZoneAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private StorageHelper storageHelper;
    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_worker_zones, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        storageHelper = new StorageHelper(requireContext());
        apiService = ApiClient.getApiService();

        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new WorkerZoneAdapter(new ArrayList<>(), this::showZoneDetailDialog);
        recyclerView.setAdapter(adapter);

        if (swipeRefresh != null) {
            swipeRefresh.setOnRefreshListener(this::loadZones);
        }
        loadZones();
    }

    private void loadZones() {
        String token = storageHelper.getToken();
        if (token == null) {
            Toast.makeText(requireContext(), "Token not found", Toast.LENGTH_SHORT).show();
            if (swipeRefresh != null) swipeRefresh.setRefreshing(false);
            return;
        }

        // Call getWorkerInfo to get zones
        apiService.getWorkerInfo(token).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (swipeRefresh != null) swipeRefresh.setRefreshing(false);
                if (!isAdded()) return;

                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(requireContext(), "Erreur chargement zones", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Zone> zones = new ArrayList<>();
                try {
                    Object zonesObj = response.body().get("zones");
                    if (zonesObj instanceof List<?>) {
                        for (Object item : (List<?>) zonesObj) {
                            if (item instanceof Map<?, ?>) {
                                Map<?, ?> zoneMap = (Map<?, ?>) item;
                                Zone zone = new Zone();
                                Object id = zoneMap.get("id");
                                zone.setId(id != null ? String.valueOf(id) : "");
                                Object name = zoneMap.get("name");
                                zone.setName(name != null ? String.valueOf(name) : "-");
                                Object cropType = zoneMap.get("crop_type");
                                zone.setCropType(cropType != null ? String.valueOf(cropType) : "-");
                                zone.setWidth(getDoubleValue(zoneMap.get("width"), 0));
                                zone.setLength(getDoubleValue(zoneMap.get("length"), 0));
                                zone.setX(getDoubleValue(zoneMap.get("x"), 0));
                                zone.setY(getDoubleValue(zoneMap.get("y"), 0));
                                Object mode = zoneMap.get("mode");
                                zone.setMode(mode != null ? String.valueOf(mode) : "AUTO");
                                zone.setMoistureThreshold(getDoubleValue(zoneMap.get("moisture_threshold"), 0));

                                List<Tree> trees = new ArrayList<>();
                                Object treesObj = zoneMap.get("trees");
                                if (treesObj instanceof List<?>) {
                                    for (Object treeItem : (List<?>) treesObj) {
                                        if (treeItem instanceof Map<?, ?>) {
                                            Map<?, ?> treeMap = (Map<?, ?>) treeItem;
                                            Tree tree = new Tree();
                                            Object treeId = treeMap.get("id");
                                            tree.setId(treeId != null ? String.valueOf(treeId) : "");
                                            Object treeCode = treeMap.get("tree_code");
                                            tree.setTreeCode(treeCode != null ? String.valueOf(treeCode) : "T");
                                            tree.setRowNumber(getIntValue(treeMap.get("row_number"), 0));
                                            tree.setIndexInRow(getIntValue(treeMap.get("index_in_row"), 0));
                                            Object healthStatus = treeMap.get("health_status");
                                            tree.setHealthStatus(healthStatus != null ? String.valueOf(healthStatus) : "OK");
                                            trees.add(tree);
                                        }
                                    }
                                }
                                zone.setTrees(trees);
                                zones.add(zone);
                            }
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(requireContext(), "Erreur parsing zones: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                adapter.updateZones(zones);
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                if (swipeRefresh != null) swipeRefresh.setRefreshing(false);
                if (!isAdded()) return;
                Toast.makeText(requireContext(), "Erreur réseau: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showZoneDetailDialog(Zone zone) {
        if (!isAdded()) return;

        View dialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_worker_zone_detail, null, false);

        TextView tvZoneName = dialogView.findViewById(R.id.tvZoneDetailName);
        TextView tvZoneMeta = dialogView.findViewById(R.id.tvZoneDetailMeta);
        TextView tvZoneTreesCount = dialogView.findViewById(R.id.tvZoneTreesCount);
        ZoneMapView zoneMapView = dialogView.findViewById(R.id.zoneMapView);

        tvZoneName.setText(zone.getName());
        tvZoneMeta.setText(String.format("%.0fm × %.0fm • Mode: %s", zone.getWidth(), zone.getLength(), zone.getMode()));
        int treesCount = zone.getTrees() != null ? zone.getTrees().size() : 0;
        tvZoneTreesCount.setText("Arbres: " + treesCount);
        zoneMapView.setZone(zone);

        new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setPositiveButton("Fermer", null)
                .show();
    }

    private double getDoubleValue(Object value, double defaultValue) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        if (value instanceof String) {
            try {
                return Double.parseDouble((String) value);
            } catch (NumberFormatException ignored) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    private int getIntValue(Object value, int defaultValue) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException ignored) {
                return defaultValue;
            }
        }
        return defaultValue;
    }
}
