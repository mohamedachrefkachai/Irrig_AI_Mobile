package com.irrigai.mobile.ui.farm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.irrigai.mobile.R;
import com.irrigai.mobile.data.local.AppDatabase;
import com.irrigai.mobile.data.local.entity.FarmEntity;
import com.irrigai.mobile.util.StorageHelper;
import com.irrigai.mobile.ui.owner.OwnerMainActivity;
import com.irrigai.mobile.ui.worker.WorkerMainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class FarmSelectionActivity extends AppCompatActivity implements FarmSelectionAdapter.OnFarmClickListener {

    public static final String EXTRA_USER_TYPE = "extra_user_type";

    private RecyclerView recyclerFarms;
    private ProgressBar progress;
    private TextView tvEmpty;

    private StorageHelper storageHelper;
    private final List<FarmEntity> farms = new ArrayList<>();
    private FarmSelectionAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_selection);

        storageHelper = new StorageHelper(this);

        recyclerFarms = findViewById(R.id.recyclerFarms);
        progress = findViewById(R.id.progress);
        tvEmpty = findViewById(R.id.tvEmpty);

        recyclerFarms.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new FarmSelectionAdapter(farms, this);
        recyclerFarms.setAdapter(adapter);

        loadFarms();
    }

    private void loadFarms() {
        progress.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);

        String userType = storageHelper.getUserType();
        String userIdStr = storageHelper.getUserId();
        final int userId = userIdStr != null ? Integer.parseInt(userIdStr) : -1;

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
                List<FarmEntity> result = new ArrayList<>();

                if ("owner".equals(userType) && userId > 0) {
                    // Load all farms for this owner
                    result = db.farmDao().getByOwnerId(userId);

                    // Ensure default farms Beja and Nabel exist for this owner
                    boolean hasBeja = false;
                    boolean hasNabel = false;
                    for (FarmEntity f : result) {
                        if ("Beja".equalsIgnoreCase(f.name)) hasBeja = true;
                        if ("Nabel".equalsIgnoreCase(f.name)) hasNabel = true;
                    }
                    boolean changed = false;
                    if (!hasBeja) {
                        db.farmDao().insert(new FarmEntity("Beja", userId));
                        changed = true;
                    }
                    if (!hasNabel) {
                        db.farmDao().insert(new FarmEntity("Nabel", userId));
                        changed = true;
                    }
                    if (changed) {
                        result = db.farmDao().getByOwnerId(userId);
                    }
                }

                List<FarmEntity> finalResult = result;
                runOnUiThread(() -> {
                    progress.setVisibility(View.GONE);
                    farms.clear();
                    farms.addAll(finalResult);
                    adapter.notifyDataSetChanged();
                    tvEmpty.setVisibility(farms.isEmpty() ? View.VISIBLE : View.GONE);
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    progress.setVisibility(View.GONE);
                    farms.clear();
                    adapter.notifyDataSetChanged();
                    tvEmpty.setVisibility(View.VISIBLE);
                });
            }
        });
    }

    @Override
    public void onFarmClick(FarmEntity farm) {
        storageHelper.saveFarmId(String.valueOf(farm.id));
        storageHelper.saveFarmName(farm.name);

        String userType = storageHelper.getUserType();
        Intent intent;
        if ("owner".equals(userType)) {
            intent = new Intent(this, OwnerMainActivity.class);
        } else {
            intent = new Intent(this, WorkerMainActivity.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

