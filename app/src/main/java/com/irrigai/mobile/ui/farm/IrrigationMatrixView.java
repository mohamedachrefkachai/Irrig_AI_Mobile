package com.irrigai.mobile.ui.farm;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.irrigai.mobile.R;

/**
 * Simple visual irrigation board:
 * - 99 trees (3 columns x 33 rows) as green circles
 * - 10 valves on the left that toggle horizontal water lines
 */
public class IrrigationMatrixView extends View {

    private static final int TREE_COLUMNS = 3;
    private static final int TREE_ROWS = 33;
    private static final int VALVE_COUNT = 10;

    private final Paint treePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint valveOffPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint valveOnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint lineOffPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint lineOnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float[] valveCenterX = new float[VALVE_COUNT];
    private float[] valveCenterY = new float[VALVE_COUNT];
    private float valveRadius;
    private boolean[] valveOn = new boolean[VALVE_COUNT];
    private float[] lineProgress = new float[VALVE_COUNT];

    private float leftPadding;
    private float topPadding;
    private float rightPadding;
    private float bottomPadding;

    public IrrigationMatrixView(Context context) {
        super(context);
        init(context);
    }

    public IrrigationMatrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IrrigationMatrixView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        treePaint.setColor(getColorCompat(context, R.color.primary_green));
        treePaint.setStyle(Paint.Style.FILL);

        valveOffPaint.setColor(getColorCompat(context, R.color.gray_600));
        valveOffPaint.setStyle(Paint.Style.FILL);

        valveOnPaint.setColor(getColorCompat(context, R.color.status_blue));
        valveOnPaint.setStyle(Paint.Style.FILL);

        lineOffPaint.setColor(getColorCompat(context, R.color.gray_200));
        lineOffPaint.setStrokeWidth(dp(3));

        lineOnPaint.setColor(getColorCompat(context, R.color.status_blue));
        lineOnPaint.setStrokeWidth(dp(4));

        leftPadding = dp(56);   // space for valves
        rightPadding = dp(16);
        topPadding = dp(24);
        bottomPadding = dp(24);

        for (int i = 0; i < VALVE_COUNT; i++) {
            valveOn[i] = false;
            lineProgress[i] = 0f;
        }
    }

    private int getColorCompat(Context ctx, int resId) {
        return ctx.getResources().getColor(resId, ctx.getTheme());
    }

    private float dp(float v) {
        return v * getResources().getDisplayMetrics().density;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();

        if (width <= 0 || height <= 0) return;

        float contentLeft = leftPadding;
        float contentRight = width - rightPadding;
        float contentTop = topPadding;
        float contentBottom = height - bottomPadding;

        // Draw tree grid (3 columns x 33 rows)
        float gridWidth = contentRight - contentLeft;
        float gridHeight = contentBottom - contentTop;

        float colSpacing = gridWidth / (TREE_COLUMNS + 1);
        float rowSpacing = gridHeight / (TREE_ROWS + 1);
        float treeRadius = Math.min(colSpacing, rowSpacing) * 0.35f;

        for (int row = 0; row < TREE_ROWS; row++) {
            float cy = contentTop + (row + 1) * rowSpacing;
            for (int col = 0; col < TREE_COLUMNS; col++) {
                float cx = contentLeft + (col + 1) * colSpacing;
                canvas.drawCircle(cx, cy, treeRadius, treePaint);
            }
        }

        // Compute valves positions
        valveRadius = Math.min(leftPadding * 0.4f, rowSpacing * 0.8f) * 0.4f;
        float valveAreaCenterX = leftPadding * 0.5f;

        for (int i = 0; i < VALVE_COUNT; i++) {
            float fraction = (i + 0.5f) / VALVE_COUNT;
            float cy = contentTop + fraction * gridHeight;
            valveCenterX[i] = valveAreaCenterX;
            valveCenterY[i] = cy;
        }

        // Draw valves + water lines
        for (int i = 0; i < VALVE_COUNT; i++) {
            boolean on = valveOn[i];
            Paint valvePaint = on ? valveOnPaint : valveOffPaint;

            // Valve circle
            canvas.drawCircle(valveCenterX[i], valveCenterY[i], valveRadius, valvePaint);

            // Water line
            float startX = contentLeft;
            float endX = contentRight;

            if (on) {
                float progress = Math.max(0f, Math.min(1f, lineProgress[i]));
                float currentEnd = startX + (endX - startX) * progress;
                canvas.drawLine(startX, valveCenterY[i], currentEnd, valveCenterY[i], lineOnPaint);
            } else {
                // Subtle grey line to show the pipe when off
                canvas.drawLine(startX, valveCenterY[i], endX, valveCenterY[i], lineOffPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();

            for (int i = 0; i < VALVE_COUNT; i++) {
                float dx = x - valveCenterX[i];
                float dy = y - valveCenterY[i];
                float dist2 = dx * dx + dy * dy;
                float hitRadius = valveRadius * 1.6f;
                if (dist2 <= hitRadius * hitRadius) {
                    toggleValve(i);
                    return true;
                }
            }
        }
        return true;
    }

    private void toggleValve(int index) {
        boolean newState = !valveOn[index];
        valveOn[index] = newState;

        if (newState) {
            // Animate line extension
            ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
            animator.setDuration(400);
            animator.addUpdateListener(a -> {
                lineProgress[index] = (float) a.getAnimatedValue();
                invalidate();
            });
            animator.start();
        } else {
            // Instantly remove water (keep grey pipe)
            lineProgress[index] = 0f;
            invalidate();
        }
    }
}

