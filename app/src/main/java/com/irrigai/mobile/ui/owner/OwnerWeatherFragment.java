package com.irrigai.mobile.ui.owner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.irrigai.mobile.R;
import com.irrigai.mobile.data.local.AppDatabase;
import com.irrigai.mobile.data.local.entity.WeatherEntity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class OwnerWeatherFragment extends Fragment {
    private TextView tvTemperature, tvCondition, tvWind, tvRain, tvSuggestion;
    private TextView tvTomorrowTemp, tvTomorrowCondition, tvTomorrowRain;
    private TextView tvHumidity, tvWindExtra, tvUvIndex, tvPressure, tvSunrise, tvSunset;
    private RecyclerView rvHourly;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_owner_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTemperature = view.findViewById(R.id.tvTemperature);
        tvCondition = view.findViewById(R.id.tvCondition);
        tvWind = view.findViewById(R.id.tvWind);
        tvRain = view.findViewById(R.id.tvRain);
        tvSuggestion = view.findViewById(R.id.tvSuggestion);
        tvTomorrowTemp = view.findViewById(R.id.tvTomorrowTemp);
        tvTomorrowCondition = view.findViewById(R.id.tvTomorrowCondition);
        tvTomorrowRain = view.findViewById(R.id.tvTomorrowRain);
        tvHumidity = view.findViewById(R.id.tvHumidity);
        tvWindExtra = view.findViewById(R.id.tvWindExtra);
        tvUvIndex = view.findViewById(R.id.tvUvIndex);
        tvPressure = view.findViewById(R.id.tvPressure);
        tvSunrise = view.findViewById(R.id.tvSunrise);
        tvSunset = view.findViewById(R.id.tvSunset);
        rvHourly = view.findViewById(R.id.rvHourly);
        loadWeather();
    }

    private void loadWeather() {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                WeatherEntity w = AppDatabase.getDatabase(requireContext()).weatherDao().getWeather();
                requireActivity().runOnUiThread(() -> {
                    if (w != null) {
                        updateUI(w);
                    } else {
                        Toast.makeText(requireContext(), R.string.loading, Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), R.string.error, Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void updateUI(WeatherEntity w) {
        tvTemperature.setText(String.format("%.1f°C", w.temperature));
        tvCondition.setText(w.condition != null ? w.condition : "Clear");
        tvWind.setText(String.format("Wind: %.0f km/h", w.windSpeed));
        tvRain.setText(String.format("Rain: %s%%", w.rainPrediction != null ? (int)(w.rainPrediction * 100) : "0"));
        tvSuggestion.setText(w.irrigationSuggestion != null ? w.irrigationSuggestion : "Normal irrigation recommended");
        tvTomorrowTemp.setText(String.format("%.0f°C", w.tomorrowTemp));
        tvTomorrowCondition.setText(w.tomorrowCondition != null ? w.tomorrowCondition : "Clear");
        tvTomorrowRain.setText(String.format("Rain: %s%%", w.tomorrowRainChance != null ? (int)(w.tomorrowRainChance * 100) : "0"));

        if (tvHumidity != null) {
            tvHumidity.setText(String.format("%.0f%%", w.humidity));
        }
        if (tvWindExtra != null) {
            tvWindExtra.setText(String.format("%.1f km/h", w.windSpeed));
        }
        if (tvUvIndex != null) {
            tvUvIndex.setText("5"); // mock
        }
        if (tvPressure != null) {
            tvPressure.setText("1015 hPa"); // mock
        }
        if (tvSunrise != null) {
            tvSunrise.setText("06:30"); // mock
        }
        if (tvSunset != null) {
            tvSunset.setText("19:45"); // mock
        }

        if (rvHourly != null) {
            List<OwnerHourlyWeatherAdapter.HourItem> hours = new ArrayList<>();
            // Simple mock hourly data based on current temperature
            double base = w.temperature;
            String[] times = {"08:00", "10:00", "12:00", "14:00", "16:00", "18:00", "20:00"};
            for (int i = 0; i < times.length; i++) {
                double t = base - 2 + i; // small variation
                String icon = i < 4 ? "☀️" : "⛅";
                hours.add(new OwnerHourlyWeatherAdapter.HourItem(
                        times[i],
                        icon,
                        String.format("%.0f°C", t)
                ));
            }
            OwnerHourlyWeatherAdapter.setupHorizontalRecycler(rvHourly, hours);
        }
    }
}
