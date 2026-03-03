package com.irrigai.mobile.ui.worker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.irrigai.mobile.R;
import com.irrigai.mobile.api.ApiClient;
import com.irrigai.mobile.api.ApiService;
import com.irrigai.mobile.util.StorageHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkerHomeFragment extends Fragment {
    private StorageHelper storageHelper;
    private ApiService apiService;
    private TextView tvFarmName;
    private TextView tvFarmLocation;
    private TextView tvTemperature;
    private TextView tvHumidity;
    private TextView tvWeatherCondition;
    private FarmMapView farmMapView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_worker_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        storageHelper = new StorageHelper(requireContext());
        apiService = ApiClient.getApiService();

        // Initialize views
        tvFarmName = view.findViewById(R.id.tvFarmName);
        tvFarmLocation = view.findViewById(R.id.tvFarmLocation);
        tvTemperature = view.findViewById(R.id.tvTemperature);
        tvHumidity = view.findViewById(R.id.tvHumidity);
        tvWeatherCondition = view.findViewById(R.id.tvWeatherCondition);
        farmMapView = view.findViewById(R.id.farmMapView);

        loadWorkerInfo();
        loadWeather();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadWorkerInfo();
        loadWeather();
    }

    private void loadWorkerInfo() {
        String token = storageHelper.getToken();
        if (token == null) {
            Toast.makeText(requireContext(), "Token not found", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.getWorkerInfo(token).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (!isAdded()) return;

                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(requireContext(), "Erreur chargement infos", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    Map<String, Object> data = response.body();
                    
                    // Get farm info
                    Object farmObj = data.get("farm");
                    if (farmObj instanceof Map<?, ?>) {
                        Map<?, ?> farm = (Map<?, ?>) farmObj;
                        String farmName = farm.get("name") != null ? String.valueOf(farm.get("name")) : "N/A";
                        String farmLocation = farm.get("location") != null ? String.valueOf(farm.get("location")) : "";
                        double farmWidth = getDoubleValue(farm.get("longueur"), 100.0);
                        double farmLength = getDoubleValue(farm.get("largeur"), 60.0);
                        
                        tvFarmName.setText(farmName);
                        tvFarmLocation.setText(farmLocation);
                        if (farmMapView != null) {
                            farmMapView.setFarmDimensions(farmWidth, farmLength);
                        }
                    }

                    // Get zones for map
                    List<Map<String, Object>> zones = new ArrayList<>();
                    Object zonesObj = data.get("zones");
                    if (zonesObj instanceof List<?>) {
                        for (Object zoneItem : (List<?>) zonesObj) {
                            if (zoneItem instanceof Map<?, ?>) {
                                Map<String, Object> zone = (Map<String, Object>) zoneItem;
                                zones.add(zone);
                            }
                        }
                    }
                    
                    if (farmMapView != null) {
                        farmMapView.setZones(zones);
                    }
                } catch (Exception e) {
                    Toast.makeText(requireContext(), "Erreur parsing: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                if (!isAdded()) return;
                Toast.makeText(requireContext(), "Erreur réseau: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

    private void loadWeather() {
        String token = storageHelper.getToken();
        if (token == null) {
            return;
        }

        apiService.getWeather(token).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (!isAdded()) return;

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        Map<String, Object> weather = response.body();
                        
                        Object temp = weather.get("temperature");
                        Object humidity = weather.get("humidity");
                        Object condition = weather.get("condition");
                        
                        if (temp != null) {
                            double tempValue = temp instanceof Number ? ((Number) temp).doubleValue() : 0.0;
                            tvTemperature.setText(String.format("%.1f°C", tempValue));
                        } else {
                            tvTemperature.setText("N/A");
                        }
                        
                        if (humidity != null) {
                            double humidityValue = humidity instanceof Number ? ((Number) humidity).doubleValue() : 0.0;
                            tvHumidity.setText(String.format("%.0f%%", humidityValue));
                        } else {
                            tvHumidity.setText("N/A");
                        }
                        
                        if (condition != null) {
                            tvWeatherCondition.setText(String.valueOf(condition));
                        } else {
                            tvWeatherCondition.setText("N/A");
                        }
                    } catch (Exception e) {
                        // Silently handle weather errors - it's optional data
                        tvTemperature.setText("N/A");
                        tvHumidity.setText("N/A");
                        tvWeatherCondition.setText("N/A");
                    }
                } else {
                    // Set default values if weather unavailable
                    tvTemperature.setText("N/A");
                    tvHumidity.setText("N/A");
                    tvWeatherCondition.setText("N/A");
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                if (!isAdded()) return;
                // Weather is optional, set default values
                tvTemperature.setText("N/A");
                tvHumidity.setText("N/A");
                tvWeatherCondition.setText("N/A");
            }
        });
    }
}
