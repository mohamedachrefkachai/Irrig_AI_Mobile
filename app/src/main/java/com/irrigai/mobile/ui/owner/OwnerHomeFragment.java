package com.irrigai.mobile.ui.owner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.irrigai.mobile.R;
import com.irrigai.mobile.data.local.AppDatabase;
import com.irrigai.mobile.data.local.entity.DashboardEntity;
import java.util.concurrent.Executors;

public class OwnerHomeFragment extends Fragment {
    private TextView tvTemperature, tvWindSpeed, tvDayNight;
    private TextView tvWorkers, tvRobots, tvValves;
    private SwipeRefreshLayout swipeRefresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_owner_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTemperature = view.findViewById(R.id.tvTemperature);
        tvWindSpeed = view.findViewById(R.id.tvWindSpeed);
        tvDayNight = view.findViewById(R.id.tvDayNight);
        tvWorkers = view.findViewById(R.id.tvWorkers);
        tvRobots = view.findViewById(R.id.tvRobots);
        tvValves = view.findViewById(R.id.tvValves);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);

        if (swipeRefresh != null) {
            swipeRefresh.setOnRefreshListener(this::loadDashboard);
        }
        loadDashboard();
    }

    private void loadDashboard() {
        try {
            android.content.Context appContext = requireContext().getApplicationContext();
            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    DashboardEntity d = AppDatabase.getDatabase(appContext).dashboardDao().getDashboard();
                    android.app.Activity act = getActivity();
                    if (act != null && isAdded()) {
                        act.runOnUiThread(() -> {
                            if (!isAdded() || getView() == null) return;
                            if (swipeRefresh != null) swipeRefresh.setRefreshing(false);
                            if (d != null) {
                                updateUI(d);
                            } else {
                                android.content.Context ctx = getContext();
                                if (ctx != null) Toast.makeText(ctx, R.string.loading, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    android.app.Activity act = getActivity();
                    if (act != null && isAdded()) {
                        act.runOnUiThread(() -> {
                            if (isAdded() && swipeRefresh != null) swipeRefresh.setRefreshing(false);
                            android.content.Context ctx = getContext();
                            if (ctx != null) Toast.makeText(ctx, R.string.error, Toast.LENGTH_SHORT).show();
                        });
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUI(DashboardEntity d) {
        tvTemperature.setText(String.format("%.1f°C", d.temperature));
        tvWindSpeed.setText(String.format("Wind: %.1f km/h", d.windSpeed));
        tvDayNight.setText(d.isDay ? "☀️ Day" : "🌙 Night");
        tvWorkers.setText(String.valueOf(d.workersCount));
        tvRobots.setText(String.valueOf(d.robotsCount));
        tvValves.setText(String.valueOf(d.activeValves));
    }
}
