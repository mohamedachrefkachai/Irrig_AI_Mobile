package com.irrigai.mobile.ui.owner;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.irrigai.mobile.R;
import com.irrigai.mobile.data.local.DatabaseInitializer;
import com.irrigai.mobile.util.StorageHelper;

public class OwnerMainActivity extends AppCompatActivity {
    
    private BottomNavigationView bottomNav;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_main);
        DatabaseInitializer.seedIfEmpty(this);

        // Ensure a farm is selected (from FarmSelectionActivity)
        StorageHelper storageHelper = new StorageHelper(this);
        String farmIdStr = storageHelper.getFarmId();
        long farmId = 0;
        try {
            farmId = farmIdStr != null ? Long.parseLong(farmIdStr) : 0;
        } catch (NumberFormatException e) {
            farmId = 0;
        }
        if (farmId <= 0) {
            android.content.Intent intent = new android.content.Intent(this, com.irrigai.mobile.ui.farm.FarmSelectionActivity.class);
            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }
        
        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int itemId = item.getItemId();
            
            if (itemId == R.id.nav_home) fragment = new OwnerHomeFragment();
            else if (itemId == R.id.nav_stats) fragment = new OwnerStatsFragment();
            else if (itemId == R.id.nav_robots) fragment = new OwnerRobotsFragment();
            else if (itemId == R.id.nav_workers) fragment = new OwnerWorkersFragment();
            else if (itemId == R.id.nav_profile) fragment = new OwnerProfileFragment();
            
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .commit();
                return true;
            }
            return false;
        });
        
        // Load default fragment: dashboard home
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new OwnerHomeFragment())
                    .commit();
            bottomNav.setSelectedItemId(R.id.nav_home);
        }
    }
}

