package com.irrigai.mobile.ui.worker;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.irrigai.mobile.R;

public class WorkerControlFragment extends androidx.fragment.app.Fragment {

    private static final String PREFS_CONTROL = "worker_control";
    private Switch switchMode;
    private Button btnPompe;

    @Override
    public View onCreateView(@NonNull android.view.LayoutInflater inflater, ViewGroup container, android.os.Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_worker_control, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, android.os.Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switchMode = view.findViewById(R.id.switchMode);
        btnPompe = view.findViewById(R.id.btnPompe);

        android.content.SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_CONTROL, Context.MODE_PRIVATE);
        boolean auto = prefs.getBoolean("mode_auto", true);
        boolean pompeOn = prefs.getBoolean("pompe_on", false);
        switchMode.setChecked(auto);
        updatePompeButton(pompeOn);

        switchMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("mode_auto", isChecked).apply();
            Toast.makeText(requireContext(), isChecked ? getString(R.string.worker_mode_auto) : getString(R.string.worker_mode_manuel), Toast.LENGTH_SHORT).show();
        });

        btnPompe.setOnClickListener(v -> {
            boolean newOn = !prefs.getBoolean("pompe_on", false);
            prefs.edit().putBoolean("pompe_on", newOn).apply();
            updatePompeButton(newOn);
            Toast.makeText(requireContext(), getString(R.string.worker_pompe_puits) + " " + (newOn ? "ON" : "OFF"), Toast.LENGTH_SHORT).show();
        });
    }

    private void updatePompeButton(boolean on) {
        btnPompe.setText(on ? getString(R.string.worker_on) : getString(R.string.worker_off));
        int color = ContextCompat.getColor(requireContext(), on ? R.color.primary_green : R.color.gray_600);
        btnPompe.setBackgroundTintList(android.content.res.ColorStateList.valueOf(color));
    }
}
