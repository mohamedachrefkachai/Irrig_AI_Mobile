package com.irrigai.mobile.ui.owner;

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
import com.irrigai.mobile.data.local.AppDatabase;
import com.irrigai.mobile.model.Robot;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class OwnerRobotsFragment extends Fragment {
    private RecyclerView recyclerView;
    private RobotAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_owner_robots, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        if (swipeRefresh == null) {
            View parent = view.getRootView().findViewById(android.R.id.content);
            swipeRefresh = view.findViewById(R.id.swipeRefresh);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new RobotAdapter(new ArrayList<>(), robot -> {
            android.content.Context ctx = requireContext();
            android.content.Intent intent = new android.content.Intent(ctx, com.irrigai.mobile.ui.robot.RobotDetailActivity.class);
            intent.putExtra(com.irrigai.mobile.ui.robot.RobotDetailActivity.EXTRA_ROBOT_ID, robot.getId());
            ctx.startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        if (swipeRefresh != null) {
            swipeRefresh.setOnRefreshListener(this::loadRobots);
        }
        loadRobots();
    }

    private void loadRobots() {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                List<com.irrigai.mobile.data.local.entity.RobotEntity> entities =
                        AppDatabase.getDatabase(requireContext()).robotDao().getAllRobots();
                List<Robot> robots = new ArrayList<>();
                for (com.irrigai.mobile.data.local.entity.RobotEntity e : entities) {
                    Robot r = new Robot();
                    r.setId(e.id);
                    r.setName(e.name);
                    r.setStatus(e.status);
                    r.setBatteryLevel(e.batteryLevel);
                    r.setLocation(e.location);
                    robots.add(r);
                }
                requireActivity().runOnUiThread(() -> {
                    if (swipeRefresh != null) swipeRefresh.setRefreshing(false);
                    adapter.updateRobots(robots);
                });
            } catch (Exception e) {
                requireActivity().runOnUiThread(() -> {
                    if (swipeRefresh != null) swipeRefresh.setRefreshing(false);
                    Toast.makeText(requireContext(), R.string.error, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}
