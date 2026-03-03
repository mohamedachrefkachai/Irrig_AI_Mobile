package com.irrigai.mobile.ui.worker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class FarmMapView extends View {
    private List<Map<String, Object>> zones = new ArrayList<>();
    private double farmWidthMeters = 100.0;
    private double farmLengthMeters = 60.0;
    private Paint farmPaint;
    private Paint farmBorderPaint;
    private Paint zonePaint;
    private Paint zoneBorderPaint;
    private Paint textPaint;
    private Paint framePaint;
    private Paint frameBorderPaint;
    private Paint emptyTextPaint;
    private Paint treeStrokePaint;
    private Paint treeTextPaint;

    public FarmMapView(Context context) {
        super(context);
        init();
    }

    public FarmMapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        framePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        framePaint.setColor(Color.parseColor("#F7F8F4"));
        framePaint.setStyle(Paint.Style.FILL);

        frameBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        frameBorderPaint.setColor(Color.parseColor("#059669"));
        frameBorderPaint.setStyle(Paint.Style.STROKE);
        frameBorderPaint.setStrokeWidth(4f);

        farmPaint = new Paint();
        farmPaint.setColor(Color.parseColor("#A7F3D0"));
        farmPaint.setStyle(Paint.Style.FILL);

        farmBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        farmBorderPaint.setColor(Color.parseColor("#059669"));
        farmBorderPaint.setStyle(Paint.Style.STROKE);
        farmBorderPaint.setStrokeWidth(6f);

        zonePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        zonePaint.setStyle(Paint.Style.FILL);
        zonePaint.setColor(Color.parseColor("#FDE68A"));
        zonePaint.setAlpha(242);

        zoneBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        zoneBorderPaint.setColor(Color.parseColor("#F59E42"));
        zoneBorderPaint.setStyle(Paint.Style.STROKE);
        zoneBorderPaint.setStrokeWidth(4f);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.parseColor("#B45309"));
        textPaint.setTextSize(32f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(true);

        emptyTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        emptyTextPaint.setColor(Color.GRAY);
        emptyTextPaint.setTextSize(40f);
        emptyTextPaint.setTextAlign(Paint.Align.CENTER);

        treeStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        treeStrokePaint.setColor(Color.parseColor("#047857"));
        treeStrokePaint.setStyle(Paint.Style.STROKE);
        treeStrokePaint.setStrokeWidth(1.5f);

        treeTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        treeTextPaint.setColor(Color.WHITE);
        treeTextPaint.setTextAlign(Paint.Align.CENTER);
        treeTextPaint.setFakeBoldText(true);
    }

    public void setZones(List<Map<String, Object>> zones) {
        this.zones = zones;
        invalidate();
    }

    public void setFarmDimensions(double farmWidthMeters, double farmLengthMeters) {
        if (farmWidthMeters > 0) {
            this.farmWidthMeters = farmWidthMeters;
        }
        if (farmLengthMeters > 0) {
            this.farmLengthMeters = farmLengthMeters;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (getWidth() == 0 || getHeight() == 0) return;

        float framePadding = 20f;
        RectF frameRect = new RectF(
            framePadding,
            framePadding,
            getWidth() - framePadding,
            getHeight() - framePadding
        );
        canvas.drawRoundRect(frameRect, 24f, 24f, framePaint);
        canvas.drawRoundRect(frameRect, 24f, 24f, frameBorderPaint);

        double safeFarmWidth = farmWidthMeters > 0 ? farmWidthMeters : 100.0;
        double safeFarmLength = farmLengthMeters > 0 ? farmLengthMeters : 60.0;
        float scale = (float) Math.min(
            frameRect.width() / safeFarmWidth,
            frameRect.height() / safeFarmLength
        );

        float farmPixelWidth = (float) (safeFarmWidth * scale);
        float farmPixelHeight = (float) (safeFarmLength * scale);
        float farmLeft = frameRect.left + (frameRect.width() - farmPixelWidth) / 2f;
        float farmTop = frameRect.top + (frameRect.height() - farmPixelHeight) / 2f;
        RectF farmRect = new RectF(
            farmLeft,
            farmTop,
            farmLeft + farmPixelWidth,
            farmTop + farmPixelHeight
        );

        canvas.drawRoundRect(farmRect, 18f, 18f, farmPaint);
        canvas.drawRoundRect(farmRect, 18f, 18f, farmBorderPaint);

        if (zones.isEmpty()) {
            canvas.drawText("Aucune zone", getWidth() / 2f, getHeight() / 2f, emptyTextPaint);
            return;
        }

        float innerInset = 8f;
        for (int i = 0; i < zones.size(); i++) {
            Map<String, Object> zone = zones.get(i);
            String zoneName = zone.get("name") != null ? String.valueOf(zone.get("name")) : "Zone " + (i + 1);

            float zoneX = getFloat(zone.get("x"), 0f);
            float zoneY = getFloat(zone.get("y"), 0f);
            float zoneWidth = Math.max(getFloat(zone.get("width"), 10f), 1f);
            float zoneLength = Math.max(getFloat(zone.get("length"), 10f), 1f);

            float left = farmRect.left + (zoneX * scale) + innerInset;
            float top = farmRect.top + (zoneY * scale) + innerInset;
            float right = left + (zoneWidth * scale) - (2f * innerInset);
            float bottom = top + (zoneLength * scale) - (2f * innerInset);

            right = Math.min(right, farmRect.right - innerInset);
            bottom = Math.min(bottom, farmRect.bottom - innerInset);
            if (right <= left + 4f || bottom <= top + 4f) {
                continue;
            }
            RectF zoneRect = new RectF(left, top, right, bottom);

            canvas.drawRoundRect(zoneRect, 12f, 12f, zonePaint);
            canvas.drawRoundRect(zoneRect, 12f, 12f, zoneBorderPaint);

            Object treesObj = zone.get("trees");
            if (treesObj instanceof List<?>) {
                drawTrees(canvas, zoneRect, (List<?>) treesObj);
            }

            float centerX = zoneRect.centerX();
            float centerY = zoneRect.centerY();

            canvas.drawText(zoneName, centerX, centerY + 10, textPaint);
        }
    }

    private void drawTrees(Canvas canvas, RectF zoneRect, List<?> trees) {
        if (trees == null || trees.isEmpty()) {
            return;
        }

        List<Map<?, ?>> parsedTrees = new ArrayList<>();
        int maxRows = 1;
        int maxCols = 1;

        for (Object item : trees) {
            if (!(item instanceof Map<?, ?>)) {
                continue;
            }
            Map<?, ?> tree = (Map<?, ?>) item;
            parsedTrees.add(tree);

            int row = (int) Math.max(getFloat(tree.get("row_number"), 0f), 0f);
            int col = (int) Math.max(getFloat(tree.get("index_in_row"), 0f), 0f);
            maxRows = Math.max(maxRows, row + 1);
            maxCols = Math.max(maxCols, col + 1);
        }

        if (parsedTrees.isEmpty()) {
            return;
        }

        float innerLeft = zoneRect.left + 8f;
        float innerTop = zoneRect.top + 8f;
        float innerWidth = Math.max(zoneRect.width() - 16f, 8f);
        float innerHeight = Math.max(zoneRect.height() - 16f, 8f);
        float cellW = innerWidth / maxCols;
        float cellH = innerHeight / maxRows;
        float radius = Math.max(3f, Math.min(cellW, cellH) * 0.26f);
        treeTextPaint.setTextSize(Math.max(7f, radius * 0.9f));

        for (Map<?, ?> tree : parsedTrees) {
            int row = (int) Math.max(getFloat(tree.get("row_number"), 0f), 0f);
            int col = (int) Math.max(getFloat(tree.get("index_in_row"), 0f), 0f);

            float cx = innerLeft + (col + 0.5f) * cellW;
            float cy = innerTop + (row + 0.5f) * cellH;

            Paint treePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            treePaint.setStyle(Paint.Style.FILL);
            treePaint.setColor(getTreeColor(tree.get("health_status")));

            canvas.drawCircle(cx, cy, radius, treePaint);
            canvas.drawCircle(cx, cy, radius, treeStrokePaint);

            String treeCode = tree.get("tree_code") != null ? String.valueOf(tree.get("tree_code")) : "T";
            String label = treeCode.isEmpty() ? "T" : treeCode.substring(0, 1).toUpperCase();
            canvas.drawText(label, cx, cy + (treeTextPaint.getTextSize() * 0.35f), treeTextPaint);
        }
    }

    private int getTreeColor(Object statusObj) {
        String status = statusObj != null ? String.valueOf(statusObj) : "OK";
        if ("DISEASE".equalsIgnoreCase(status)) {
            return Color.parseColor("#ef4444");
        }
        if ("STRESS".equalsIgnoreCase(status)) {
            return Color.parseColor("#f59e0b");
        }
        return Color.parseColor("#10b981");
    }

    private float getFloat(Object value, float defaultValue) {
        if (value instanceof Number) {
            return ((Number) value).floatValue();
        }
        if (value instanceof String) {
            try {
                return Float.parseFloat((String) value);
            } catch (NumberFormatException ignored) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = Math.min(width, 800); // Max height of 800dp
        setMeasuredDimension(width, height);
    }
}
