package com.irrigai.mobile.ui.worker;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.irrigai.mobile.R;

public class WorkerMainActivity extends AppCompatActivity {

    private static final String TAG = "WorkerMainActivity";
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_worker_main);

            bottomNav = findViewById(R.id.bottomNav);
            if (bottomNav == null) {
                Log.e(TAG, "bottomNav is null");
                Toast.makeText(this, "Erreur: bottomNav null", Toast.LENGTH_LONG).show();
                finish();
                return;
            }
            bottomNav.setOnItemSelectedListener(item -> {
                Fragment fragment = null;
                int itemId = item.getItemId();
                
                if (itemId == R.id.nav_worker_home) {
                    fragment = new WorkerHomeFragment();
                } else if (itemId == R.id.nav_worker_tasks) {
                    fragment = new WorkerTasksFragment();
                } else if (itemId == R.id.nav_worker_zones) {
                    fragment = new WorkerZonesFragment();
                } else if (itemId == R.id.nav_worker_profile) {
                    fragment = new WorkerProfileFragment();
                }
                
                if (fragment != null) {
                    try {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainer, fragment)
                                .commit();
                    } catch (Exception e) {
                        Log.e(TAG, "Fragment replace failed", e);
                        Toast.makeText(this, "Erreur: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
                return false;
            });

            if (savedInstanceState == null) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    try {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainer, new WorkerHomeFragment())
                                .commit();
                        bottomNav.setSelectedItemId(R.id.nav_worker_home);
                    } catch (Exception e) {
                        Log.e(TAG, "Initial fragment load failed", e);
                        Toast.makeText(this, "Erreur: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        } catch (Exception e) {
            Log.e(TAG, "onCreate failed", e);
            Toast.makeText(this, "Erreur démarrage: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
