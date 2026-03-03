package com.irrigai.mobile.ui.robot;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.irrigai.mobile.R;
import com.irrigai.mobile.data.local.AppDatabase;
import com.irrigai.mobile.data.local.entity.MaintenanceLogEntity;
import com.irrigai.mobile.data.local.entity.RobotEntity;
import com.irrigai.mobile.data.local.entity.RobotTaskEntity;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

public class RobotDetailActivity extends AppCompatActivity {

    public static final String EXTRA_ROBOT_ID = "extra_robot_id";

    private TextView tvName, tvStatus, tvBattery, tvCurrentTask, tvHoursToday, tvTotalHours,
            tvLastMaintenance, tvNextMaintenance, tvZone;
    private ProgressBar progressBattery;
    private Button btnStart, btnStop, btnCharge, btnLogs;
    private ImageButton btnCamera;

    private RobotEntity robot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot_detail);

        tvName = findViewById(R.id.tvRobotName);
        tvStatus = findViewById(R.id.tvRobotStatus);
        tvBattery = findViewById(R.id.tvBatteryLabel);
        tvCurrentTask = findViewById(R.id.tvCurrentTask);
        tvHoursToday = findViewById(R.id.tvHoursToday);
        tvTotalHours = findViewById(R.id.tvTotalHours);
        tvLastMaintenance = findViewById(R.id.tvLastMaintenance);
        tvNextMaintenance = findViewById(R.id.tvNextMaintenance);
        tvZone = findViewById(R.id.tvZone);
        progressBattery = findViewById(R.id.progressBattery);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnCharge = findViewById(R.id.btnCharge);
        btnLogs = findViewById(R.id.btnLogs);
        btnCamera = findViewById(R.id.btnCamera);

        String robotId = getIntent().getStringExtra(EXTRA_ROBOT_ID);
        if (robotId == null) {
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadRobot(robotId);

        btnStart.setOnClickListener(v -> updateStatus("working"));
        btnStop.setOnClickListener(v -> updateStatus("idle"));
        btnCharge.setOnClickListener(v -> updateStatus("charging"));
        btnLogs.setOnClickListener(v -> Toast.makeText(this, "Logs view coming soon", Toast.LENGTH_SHORT).show());
        btnCamera.setOnClickListener(v -> openCamera());
    }

    private void loadRobot(String robotId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
                RobotEntity entity = db.robotDao().getById(robotId);
                RobotTaskEntity lastTask = db.robotTaskDao().getLastTaskForRobot(robotId);
                MaintenanceLogEntity lastLog = db.maintenanceLogDao().getLastLogForRobot(robotId);
                List<RobotTaskEntity> tasks = db.robotTaskDao().getTasksForRobot(robotId);

                runOnUiThread(() -> {
                    robot = entity;
                    if (robot == null) {
                        Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                    bindRobot(robot, lastTask, lastLog, tasks);
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        });
    }

    private void bindRobot(RobotEntity r, @Nullable RobotTaskEntity lastTask,
                           @Nullable MaintenanceLogEntity lastLog,
                           @Nullable List<RobotTaskEntity> tasks) {
        tvName.setText(r.name);
        String status = r.status != null ? r.status : "idle";
        tvStatus.setText(status.toUpperCase());
        int battery = r.batteryLevel;
        progressBattery.setProgress(battery);
        tvBattery.setText(getString(R.string.robot_battery_format, battery));
        tvZone.setText(r.location != null ? r.location : "-");

        if (lastTask != null) {
            tvCurrentTask.setText(lastTask.title);
        } else {
            tvCurrentTask.setText(getString(R.string.robot_no_task));
        }

        // Simple mock values for hours; could be computed from tasks
        tvHoursToday.setText("3h");
        tvTotalHours.setText("120h");

        if (lastLog != null && lastLog.maintenanceAtMillis != null) {
            String date = DateFormat.getDateInstance().format(new Date(lastLog.maintenanceAtMillis));
            tvLastMaintenance.setText(date);
        } else {
            tvLastMaintenance.setText("—");
        }
        tvNextMaintenance.setText("In 7 days");
    }

    private void updateStatus(String newStatus) {
        if (robot == null) return;
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                AppDatabase.getDatabase(getApplicationContext())
                        .robotDao()
                        .updateStatus(robot.id, newStatus);
                runOnUiThread(() -> {
                    robot.status = newStatus;
                    tvStatus.setText(newStatus.toUpperCase());
                    Toast.makeText(this, getString(R.string.robot_status_updated, newStatus), Toast.LENGTH_SHORT).show();
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void openCamera() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show();
        }
    }
}

