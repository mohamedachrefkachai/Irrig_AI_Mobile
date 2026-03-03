package com.irrigai.mobile.ui.owner;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.irrigai.mobile.R;
import com.irrigai.mobile.ui.auth.LandingActivity;
import com.irrigai.mobile.util.StorageHelper;

public class OwnerProfileFragment extends Fragment {
    private StorageHelper storageHelper;
    private TextView tvUserName, tvUserEmail, tvFarmName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_owner_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        storageHelper = new StorageHelper(requireContext());

        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserEmail = view.findViewById(R.id.tvUserEmail);
        tvFarmName = view.findViewById(R.id.tvFarmName);
        Button btnFarm = view.findViewById(R.id.btnFarm);
        Button btnWeather = view.findViewById(R.id.btnWeather);
        Button btnLogout = view.findViewById(R.id.btnLogout);

        updateProfileInfo();

        btnFarm.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new OwnerFarmFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        btnWeather.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new OwnerWeatherFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        btnLogout.setOnClickListener(v -> {
            storageHelper.clearAll();
            Intent intent = new Intent(requireContext(), LandingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateProfileInfo();
    }

    private void updateProfileInfo() {
        String name = storageHelper.getUserName();
        String farmName = storageHelper.getFarmName();
        tvUserName.setText(name != null && !name.isEmpty() ? name : "Farm Owner");
        String email = storageHelper.getUserEmail();
        tvUserEmail.setText(email != null && !email.isEmpty() ? email : "Logged in");
        tvFarmName.setText(farmName != null && !farmName.isEmpty() ? farmName : "My Farm");
    }
}
