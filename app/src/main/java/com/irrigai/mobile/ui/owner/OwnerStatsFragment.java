package com.irrigai.mobile.ui.owner;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.irrigai.mobile.R;
import com.irrigai.mobile.data.local.AppDatabase;
import com.irrigai.mobile.data.local.entity.StatsEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class OwnerStatsFragment extends Fragment {
    private LineChart chartTemperature, chartWind, chartRain, chartValves, chartIrrigation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_owner_stats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chartTemperature = view.findViewById(R.id.chartTemperature);
        chartWind = view.findViewById(R.id.chartWind);
        chartRain = view.findViewById(R.id.chartRain);
        chartValves = view.findViewById(R.id.chartValves);
        chartIrrigation = view.findViewById(R.id.chartIrrigation);
        loadStats();
    }

    private void loadStats() {
        android.content.Context ctx = requireContext().getApplicationContext();
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                List<StatsEntity> stats = AppDatabase.getDatabase(ctx).statsDao().getAllStats();
                android.app.Activity act = getActivity();
                if (act != null && isAdded()) {
                    act.runOnUiThread(() -> {
                        if (!isAdded()) return;
                        if (stats.isEmpty()) {
                            if (getContext() != null) Toast.makeText(getContext(), R.string.loading, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        setupCharts(stats);
                    });
                }
            } catch (Exception e) {
                android.app.Activity act = getActivity();
                if (act != null && isAdded()) {
                    act.runOnUiThread(() -> {
                        if (getContext() != null) Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    private void setupCharts(List<StatsEntity> stats) {
        List<String> labels = new ArrayList<>();
        for (StatsEntity s : stats) labels.add(s.label);

        setupLineChart(chartTemperature, labels, toEntries(stats, s -> (float) s.temperature),
                getColor(R.color.status_red), "°C");
        setupLineChart(chartWind, labels, toEntries(stats, s -> (float) s.wind),
                getColor(R.color.status_blue), " km/h");
        setupLineChart(chartRain, labels, toEntries(stats, s -> (float) s.rain),
                getColor(R.color.primary_green), " mm");
        setupLineChart(chartValves, labels, toEntries(stats, s -> (float) s.activeValves),
                getColor(R.color.primary_emerald), "");
        setupLineChart(chartIrrigation, labels, toEntries(stats, s -> (float) s.irrigationDuration),
                getColor(R.color.dark_green), " min");
    }

    private interface ValueExtractor {
        float get(StatsEntity s);
    }

    private List<Entry> toEntries(List<StatsEntity> stats, ValueExtractor extractor) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < stats.size(); i++) {
            entries.add(new Entry(i, extractor.get(stats.get(i))));
        }
        return entries;
    }

    private void setupLineChart(LineChart chart, List<String> labels, List<Entry> entries, int color, String suffix) {
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.DKGRAY);

        chart.getAxisLeft().setTextColor(Color.DKGRAY);
        chart.getAxisRight().setEnabled(false);

        LineDataSet dataSet = new LineDataSet(entries, "");
        dataSet.setColor(color);
        dataSet.setCircleColor(color);
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(4f);
        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(9f);
        dataSet.setValueTextColor(Color.DKGRAY);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        chart.setData(new LineData(dataSet));
        chart.invalidate();
    }

    private int getColor(int resId) {
        return requireContext().getColor(resId);
    }
}
