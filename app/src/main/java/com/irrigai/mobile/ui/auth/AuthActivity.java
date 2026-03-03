package com.irrigai.mobile.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.irrigai.mobile.R;
import com.irrigai.mobile.api.ApiClient;
import com.irrigai.mobile.api.ApiService;
import com.irrigai.mobile.model.AuthResponse;
import com.irrigai.mobile.ui.worker.WorkerMainActivity;
import com.irrigai.mobile.util.StorageHelper;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity {
    private boolean isSignUp = false;

    private LinearLayout layoutName;
    private EditText etName, etEmail, etPassword;
    private Button btnSubmit;
    private TextView tvToggleAuth;
    private StorageHelper storageHelper;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        storageHelper = new StorageHelper(this);
        apiService = ApiClient.getApiService();
        initViews();
        setupListeners();
    }

    private void initViews() {
        layoutName = findViewById(R.id.layoutName);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvToggleAuth = findViewById(R.id.tvToggleAuth);

        // Hide owner/worker toggle and farm name field
        View layoutOwnerWorker = findViewById(R.id.layoutOwnerWorker);
        if (layoutOwnerWorker != null) layoutOwnerWorker.setVisibility(View.GONE);
        
        View layoutFarmName = findViewById(R.id.layoutFarmName);
        if (layoutFarmName != null) layoutFarmName.setVisibility(View.GONE);

        updateUI();
    }

    private void setupListeners() {
        btnSubmit.setOnClickListener(v -> handleSubmit());

        tvToggleAuth.setOnClickListener(v -> {
            isSignUp = !isSignUp;
            updateUI();
        });
    }

    private void updateUI() {
        layoutName.setVisibility(isSignUp ? View.VISIBLE : View.GONE);
        btnSubmit.setText(isSignUp ? R.string.create_account : R.string.sign_in);
        tvToggleAuth.setText(isSignUp ? R.string.already_have_account : R.string.dont_have_account);
    }

    private void handleSubmit() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isSignUp) {
            String name = etName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Veuillez entrer votre nom", Toast.LENGTH_SHORT).show();
                return;
            }
            signUp(email, password, name);
        } else {
            login(email, password);
        }
    }

    private void signUp(String email, String password, String name) {
        btnSubmit.setEnabled(false);
        Map<String, String> data = new HashMap<>();
        data.put("email", email);
        data.put("password", password);
        data.put("name", name);

        apiService.signup(data).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                btnSubmit.setEnabled(true);
                if (!response.isSuccessful() || response.body() == null || response.body().getUser() == null) {
                    if (response.code() == 409) {
                        Toast.makeText(AuthActivity.this, "Email déjà utilisé. Connectez-vous.", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {
                        Toast.makeText(AuthActivity.this, "Vérifiez les champs saisis", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AuthActivity.this, "Inscription échouée", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }

                handleAuthSuccess(response.body());
                Toast.makeText(AuthActivity.this, "Compte créé avec succès", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                btnSubmit.setEnabled(true);
                Toast.makeText(AuthActivity.this, "Erreur réseau: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login(String email, String password) {
        btnSubmit.setEnabled(false);
        Map<String, String> data = new HashMap<>();
        data.put("email", email);
        data.put("password", password);

        apiService.login(data).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                btnSubmit.setEnabled(true);
                if (!response.isSuccessful() || response.body() == null || response.body().getUser() == null) {
                    if (response.code() == 401) {
                        Toast.makeText(AuthActivity.this, "Mot de passe incorrect", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {
                        Toast.makeText(AuthActivity.this, "Owners must use web dashboard", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 404) {
                        Toast.makeText(AuthActivity.this, "Compte introuvable. Créez un compte.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AuthActivity.this, "Identifiants invalides", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }

                handleAuthSuccess(response.body());
                Toast.makeText(AuthActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                btnSubmit.setEnabled(true);
                Toast.makeText(AuthActivity.this, "Erreur réseau: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleAuthSuccess(AuthResponse response) {
        if (response.getToken() != null) {
            storageHelper.saveToken(response.getToken());
        }
        if (response.getUser() != null) {
            storageHelper.saveUserId(response.getUser().getId());
            storageHelper.saveUserEmail(response.getUser().getEmail());
            storageHelper.saveUserName(response.getUser().getName());
            storageHelper.saveUserType("worker");
            
            // Save user role for future reference
            if (response.getUser().getRole() != null) {
                storageHelper.saveUserRole(response.getUser().getRole());
            }
        }
        
        // Fetch worker info to get farm ID
        fetchWorkerInfoAndRedirect();
    }

    private void fetchWorkerInfoAndRedirect() {
        String token = storageHelper.getToken();
        if (token == null) {
            goToWorkerDashboard(); // Fallback if no token
            return;
        }
        
        // Call API to get worker info including farm_id
        apiService.getWorkerInfo(token).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        Map<String, Object> data = response.body();
                        
                        // Extract farm ID from worker info
                        if (data.containsKey("farm")) {
                            Map<String, Object> farm = (Map<String, Object>) data.get("farm");
                            if (farm != null && farm.containsKey("id")) {
                                String farmId = (String) farm.get("id");
                                storageHelper.saveFarmId(farmId);
                            }
                        }
                        
                        // Extract farm name
                        if (data.containsKey("farm")) {
                            Map<String, Object> farm = (Map<String, Object>) data.get("farm");
                            if (farm != null && farm.containsKey("name")) {
                                String farmName = (String) farm.get("name");
                                storageHelper.saveFarmName(farmName);
                            }
                        }
                    } catch (Exception e) {
                        Log.e("AuthActivity", "Error parsing worker info: " + e.getMessage());
                    }
                }
                goToWorkerDashboard();
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Log.e("AuthActivity", "Error fetching worker info: " + t.getMessage());
                goToWorkerDashboard();
            }
        });
    }

    private void goToWorkerDashboard() {
        Intent intent = new Intent(this, WorkerMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
