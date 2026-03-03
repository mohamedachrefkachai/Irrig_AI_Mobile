package com.irrigai.mobile.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.irrigai.mobile.R;
import com.irrigai.mobile.api.ApiClient;
import com.irrigai.mobile.api.ApiService;
import com.irrigai.mobile.ui.worker.WorkerMainActivity;
import com.irrigai.mobile.util.StorageHelper;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinFarmActivity extends AppCompatActivity {
    private EditText etInvitationCode;
    private Button btnJoinWithCode, btnScanQR;
    private ProgressBar progressBar;
    private StorageHelper storageHelper;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_farm);

        storageHelper = new StorageHelper(this);
        apiService = ApiClient.getApiService();
        initViews();
        setupListeners();
    }

    private void initViews() {
        etInvitationCode = findViewById(R.id.etInvitationCode);
        btnJoinWithCode = findViewById(R.id.btnJoinWithCode);
        btnScanQR = findViewById(R.id.btnScanQR);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupListeners() {
        btnJoinWithCode.setOnClickListener(v -> handleJoinWithCode());
        btnScanQR.setOnClickListener(v -> handleScanQR());
    }

    private void handleJoinWithCode() {
        String code = normalizeInvitationCode(etInvitationCode.getText().toString());
        if (code.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer un code d'invitation", Toast.LENGTH_SHORT).show();
            return;
        }

        validateAndJoinFarm(code);
    }

    private String normalizeInvitationCode(String raw) {
        if (raw == null) return "";
        String value = raw.trim();
        if (value.contains("token=")) {
            int idx = value.indexOf("token=");
            String part = value.substring(idx + 6);
            int amp = part.indexOf('&');
            if (amp >= 0) part = part.substring(0, amp);
            value = part;
        }
        return value.replaceAll("\\s+", "").toUpperCase();
    }

    private void handleScanQR() {
        // TODO: Implement QR code scanner
        Toast.makeText(this, "Scanner QR code à implémenter", Toast.LENGTH_SHORT).show();
    }

    private void validateAndJoinFarm(String code) {
        String userId = storageHelper.getUserId();
        if (userId == null || userId.isEmpty()) {
            Toast.makeText(this, "Utilisateur non connecté", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate code locally (temporary workaround for backend 404 issue)
        if (!isValidInvitationCode(code)) {
            Toast.makeText(this, "Format code invalide (6 caractères alphanumériques requis)", Toast.LENGTH_SHORT).show();
            return;
        }

        btnJoinWithCode.setEnabled(false);
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);

        // TODO: Once backend /api/invitation/validate is fixed, use actual backend validation to get farmId
        // For now, use placeholder farm ID for testing
        String testFarmId = "6999ca806b6b17e22a5958ab";
        
        // Save code and farm ID locally
        storageHelper.saveFarmInviteCode(code);
        storageHelper.saveFarmId(testFarmId);

        btnJoinWithCode.setEnabled(true);
        if (progressBar != null) progressBar.setVisibility(View.GONE);

        Toast.makeText(JoinFarmActivity.this, "Code accepté: " + code, Toast.LENGTH_SHORT).show();
        
        Intent intent = new Intent(JoinFarmActivity.this, WorkerMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private boolean isValidInvitationCode(String code) {
        // Validate: 6 characters, alphanumeric only (A-Z, 0-9)
        if (code == null || code.length() != 6) {
            return false;
        }
        return code.matches("^[A-Z0-9]{6}$");
    }
}
