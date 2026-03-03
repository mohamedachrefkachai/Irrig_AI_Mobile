package com.irrigai.mobile.ui.worker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.irrigai.mobile.R;
import com.irrigai.mobile.data.local.AppDatabase;
import com.irrigai.mobile.data.local.entity.RobotEntity;
import com.irrigai.mobile.data.local.entity.WeatherEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class WorkerVizFragment extends androidx.fragment.app.Fragment {

    private TextView tvTemperature, tvCondition, tvWeatherIcon, tvDayNight, tvWind, tvHumidity, tvSuggestion, tvTomorrow;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, android.os.Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_worker_viz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, android.os.Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTemperature = view.findViewById(R.id.tvTemperature);
        tvCondition = view.findViewById(R.id.tvCondition);
        tvWeatherIcon = view.findViewById(R.id.tvWeatherIcon);
        tvDayNight = view.findViewById(R.id.tvDayNight);
        tvWind = view.findViewById(R.id.tvWind);
        tvHumidity = view.findViewById(R.id.tvHumidity);
        tvSuggestion = view.findViewById(R.id.tvSuggestion);
        tvTomorrow = view.findViewById(R.id.tvTomorrow);

        Context appContext = getContext() != null ? getContext().getApplicationContext() : null;
        if (appContext != null) {
            loadWeather(appContext);
        }
    }

    private void loadWeather(Context appContext) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                WeatherEntity w = AppDatabase.getDatabase(appContext).weatherDao().getWeather();
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        if (tvTemperature != null) {
                            if (w != null) {
                                tvTemperature.setText(String.format("%.1f°C", w.temperature));
                                if (tvCondition != null) tvCondition.setText(w.condition != null ? w.condition : "—");
                                if (tvWeatherIcon != null) tvWeatherIcon.setText(weatherIcon(w.icon, w.condition));
                                if (tvDayNight != null) tvDayNight.setText(w.isDay ? "☀️ Jour" : "🌙 Nuit");
                                if (tvWind != null) tvWind.setText(String.format("💨 Vent: %.1f km/h", w.windSpeed));
                                if (tvHumidity != null) tvHumidity.setText(String.format("💧 Humidité: %.0f%%", w.humidity));
                                if (tvSuggestion != null) tvSuggestion.setText("🌱 " + (w.irrigationSuggestion != null ? w.irrigationSuggestion : "Irrigation normale"));
                                if (tvTomorrow != null) {
                                    String tomorrow = String.format("%.1f°C, %s", w.tomorrowTemp, w.tomorrowCondition != null ? w.tomorrowCondition : "—");
                                    if (w.tomorrowRainChance != null) tomorrow += String.format(" (%.0f%% pluie)", w.tomorrowRainChance * 100);
                                    tvTomorrow.setText(tomorrow);
                                }
                            } else {
                                tvTemperature.setText("—°C");
                                if (tvCondition != null) tvCondition.setText("—");
                                if (tvWeatherIcon != null) tvWeatherIcon.setText("☀️");
                                if (tvDayNight != null) tvDayNight.setText("—");
                                if (tvWind != null) tvWind.setText("💨 Vent: —");
                                if (tvHumidity != null) tvHumidity.setText("💧 Humidité: —");
                                if (tvSuggestion != null) tvSuggestion.setText("🌱 —");
                                if (tvTomorrow != null) tvTomorrow.setText("—");
                            }
                        }
                    });
                }
            } catch (Exception e) { e.printStackTrace(); }
        });
    }

    private static String weatherIcon(String icon, String condition) {
        if (icon != null) {
            if (icon.contains("rain") || icon.contains("storm")) return "🌧️";
            if (icon.contains("cloud")) return "☁️";
            if (icon.contains("partly")) return "⛅";
            if (icon.contains("night")) return "🌙";
        }
        if (condition != null) {
            String c = condition.toLowerCase();
            if (c.contains("rain") || c.contains("storm")) return "🌧️";
            if (c.contains("cloud")) return "☁️";
            if (c.contains("partly")) return "⛅";
        }
        return "☀️";
    }

    // All robot and valve controls have been moved to other tabs (Robots / Control).
}
