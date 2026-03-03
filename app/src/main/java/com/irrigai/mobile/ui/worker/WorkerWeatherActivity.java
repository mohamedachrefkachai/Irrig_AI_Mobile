package com.irrigai.mobile.ui.worker;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.irrigai.mobile.R;
import com.irrigai.mobile.data.local.AppDatabase;
import com.irrigai.mobile.data.local.entity.WeatherEntity;
import java.util.concurrent.Executors;

public class WorkerWeatherActivity extends AppCompatActivity {
    private TextView tvTemperature, tvCondition;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_weather);

        tvTemperature = findViewById(R.id.tvTemperature);
        tvCondition = findViewById(R.id.tvCondition);
        swipeRefresh = findViewById(R.id.swipeRefresh);

        swipeRefresh.setOnRefreshListener(this::loadWeather);
        loadWeather();
    }

    private void loadWeather() {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                WeatherEntity w = AppDatabase.getDatabase(this).weatherDao().getWeather();
                runOnUiThread(() -> {
                    swipeRefresh.setRefreshing(false);
                    if (w != null) {
                        tvTemperature.setText(String.format("%.1f°C", w.temperature));
                        tvCondition.setText(w.condition != null ? w.condition : "Clear");
                    } else {
                        Toast.makeText(this, R.string.loading, Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    swipeRefresh.setRefreshing(false);
                    Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}
