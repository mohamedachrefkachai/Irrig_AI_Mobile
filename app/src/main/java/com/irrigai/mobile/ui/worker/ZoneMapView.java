package com.irrigai.mobile.ui.worker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.irrigai.mobile.model.Tree;
import com.irrigai.mobile.model.Zone;
import java.util.List;

public class ZoneMapView extends View {
    private Zone zone;
    private Paint bgPaint;
    private Paint zoneFillPaint;
    private Paint zoneBorderPaint;
    private Paint treeStrokePaint;
    private Paint treeTextPaint;
    private Paint emptyTextPaint;

    public ZoneMapView(Context context) {
        super(context);
        init();
    }

    public ZoneMapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setColor(Color.parseColor("#F7F8F4"));
        bgPaint.setStyle(Paint.Style.FILL);

        zoneFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        zoneFillPaint.setColor(Color.parseColor("#A7F3D0"));
        zoneFillPaint.setStyle(Paint.Style.FILL);

        zoneBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        zoneBorderPaint.setColor(Color.parseColor("#059669"));
        zoneBorderPaint.setStyle(Paint.Style.STROKE);
        zoneBorderPaint.setStrokeWidth(4f);

        treeStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        treeStrokePaint.setColor(Color.parseColor("#047857"));
        treeStrokePaint.setStyle(Paint.Style.STROKE);
        treeStrokePaint.setStrokeWidth(1.5f);

        treeTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        treeTextPaint.setColor(Color.WHITE);
        treeTextPaint.setTextAlign(Paint.Align.CENTER);
        treeTextPaint.setFakeBoldText(true);

        emptyTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        emptyTextPaint.setColor(Color.GRAY);
        emptyTextPaint.setTextAlign(Paint.Align.CENTER);
        emptyTextPaint.setTextSize(36f);
    }

    public void setZone(Zone zone) {
        this.zone = zone;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (getWidth() == 0 || getHeight() == 0) return;

        float pad = 16f;
        RectF frameRect = new RectF(pad, pad, getWidth() - pad, getHeight() - pad);
        canvas.drawRoundRect(frameRect, 16f, 16f, bgPaint);

        RectF zoneRect = new RectF(
            frameRect.left + 12f,
            frameRect.top + 12f,
            frameRect.right - 12f,
            frameRect.bottom - 12f
        );
        canvas.drawRoundRect(zoneRect, 12f, 12f, zoneFillPaint);
        canvas.drawRoundRect(zoneRect, 12f, 12f, zoneBorderPaint);

        if (zone == null || zone.getTrees() == null || zone.getTrees().isEmpty()) {
            canvas.drawText("Aucun arbre", getWidth() / 2f, getHeight() / 2f, emptyTextPaint);
            return;
        }

        drawTrees(canvas, zoneRect, zone.getTrees());
    }

    private void drawTrees(Canvas canvas, RectF zoneRect, List<Tree> trees) {
        int maxRows = 1;
        int maxCols = 1;

        for (Tree tree : trees) {
            maxRows = Math.max(maxRows, tree.getRowNumber() + 1);
            maxCols = Math.max(maxCols, tree.getIndexInRow() + 1);
        }

        float innerLeft = zoneRect.left + 8f;
        float innerTop = zoneRect.top + 8f;
        float innerWidth = Math.max(zoneRect.width() - 16f, 8f);
        float innerHeight = Math.max(zoneRect.height() - 16f, 8f);

        float cellW = innerWidth / maxCols;
        float cellH = innerHeight / maxRows;
        float radius = Math.max(4f, Math.min(cellW, cellH) * 0.24f);
        treeTextPaint.setTextSize(Math.max(7f, radius * 0.95f));

        for (Tree tree : trees) {
            float cx = innerLeft + (tree.getIndexInRow() + 0.5f) * cellW;
            float cy = innerTop + (tree.getRowNumber() + 0.5f) * cellH;

            Paint treeFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            treeFillPaint.setStyle(Paint.Style.FILL);
            treeFillPaint.setColor(getTreeColor(tree.getHealthStatus()));

            canvas.drawCircle(cx, cy, radius, treeFillPaint);
            canvas.drawCircle(cx, cy, radius, treeStrokePaint);

            String treeCode = tree.getTreeCode() != null ? tree.getTreeCode() : "T";
            String label = treeCode.isEmpty() ? "T" : treeCode.substring(0, 1).toUpperCase();
            canvas.drawText(label, cx, cy + (treeTextPaint.getTextSize() * 0.35f), treeTextPaint);
        }
    }

    private int getTreeColor(String status) {
        if (status == null) return Color.parseColor("#10b981");
        if ("DISEASE".equalsIgnoreCase(status)) return Color.parseColor("#ef4444");
        if ("STRESS".equalsIgnoreCase(status)) return Color.parseColor("#f59e0b");
        return Color.parseColor("#10b981");
    }
}
