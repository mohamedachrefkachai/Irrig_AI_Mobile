package com.irrigai.mobile.ui.worker;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.irrigai.mobile.R;
import com.irrigai.mobile.ui.auth.LandingActivity;
import com.irrigai.mobile.util.StorageHelper;

public class WorkerProfileFragment extends androidx.fragment.app.Fragment {

    @Override
    public View onCreateView(@NonNull android.view.LayoutInflater inflater, ViewGroup container, android.os.Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_worker_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, android.os.Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvName = view.findViewById(R.id.tvWorkerName);
        TextView tvEmail = view.findViewById(R.id.tvWorkerEmail);
        view.findViewById(R.id.btnLogout).setOnClickListener(v -> logout());

        StorageHelper storage = new StorageHelper(requireContext());
        String name = storage.getUserName();
        String email = storage.getUserEmail();
        tvName.setText(name != null && !name.isEmpty() ? name : "Worker");
        tvEmail.setText(email != null && !email.isEmpty() ? email : "—");
    }

    private void logout() {
        new StorageHelper(requireContext()).clearAll();
        Intent intent = new Intent(requireContext(), LandingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }
}
