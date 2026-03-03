package com.irrigai.mobile.ui.owner.farm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.irrigai.mobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 2D interactive farm builder: draw perimeter polygon, place trees and valves.
 * All coordinates stored normalized (0-1) for scale-independent save and future backend sync.
 */
public class FarmBuilderView extends View {

    public static final int DRAW_MODE = 0;
    public static final int ADD_TREE_MODE = 1;
    public static final int ADD_VALVE_MODE = 2;
    public static final int VIEW_MODE = 3;

    private int mode = VIEW_MODE;

    /** Normalized 0-1 */
    private final List<PointF> perimeterPoints = new ArrayList<>();
    private final List<PointF> trees = new ArrayList<>();
    private final List<PointF> valves = new ArrayList<>();

    private final Paint perimeterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint treePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint valvePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static final float TOUCH_SLOP_NORMALIZED = 0.06f;
    private static final float POINT_RADIUS_NORMALIZED = 0.02f;
    private static final float ICON_RADIUS_NORMALIZED = 0.028f;

    private int primaryGreen;
    private int statusBlue;

    private OnFarmChangeListener listener;

    public interface OnFarmChangeListener {
        void onFarmChanged();
    }

    public FarmBuilderView(Context context) {
        super(context);
        init(context);
    }

    public FarmBuilderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FarmBuilderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        primaryGreen = context.getColor(R.color.primary_green);
        statusBlue = context.getColor(R.color.status_blue);

        perimeterPaint.setColor(primaryGreen);
        perimeterPaint.setStyle(Paint.Style.STROKE);
        perimeterPaint.setStrokeWidth(4f);

        fillPaint.setColor(Color.argb(50, 16, 185, 129));
        fillPaint.setStyle(Paint.Style.FILL);

        pointPaint.setColor(primaryGreen);
        pointPaint.setStyle(Paint.Style.FILL);

        linePaint.setColor(Color.argb(200, 16, 185, 129));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(3f);

        treePaint.setColor(context.getColor(R.color.dark_green));
        treePaint.setStyle(Paint.Style.FILL);

        valvePaint.setColor(statusBlue);
        valvePaint.setStyle(Paint.Style.FILL);

        setBackgroundColor(Color.argb(255, 248, 250, 252));
    }

    public void setMode(int mode) {
        this.mode = mode;
        invalidate();
    }

    public int getMode() {
        return mode;
    }

    public void setOnFarmChangeListener(OnFarmChangeListener listener) {
        this.listener = listener;
    }

    public void setPerimeterFromNormalized(List<float[]> points) {
        perimeterPoints.clear();
        for (float[] p : points) {
            perimeterPoints.add(new PointF(p[0], p[1]));
        }
        invalidate();
    }

    public void setTreesFromNormalized(List<float[]> points) {
        trees.clear();
        for (float[] p : points) {
            trees.add(new PointF(p[0], p[1]));
        }
        invalidate();
    }

    public void setValvesFromNormalized(List<float[]> points) {
        valves.clear();
        for (float[] p : points) {
            valves.add(new PointF(p[0], p[1]));
        }
        invalidate();
    }

    public List<float[]> getPerimeterNormalized() {
        List<float[]> out = new ArrayList<>();
        for (PointF p : perimeterPoints) {
            out.add(new float[]{ p.x, p.y });
        }
        return out;
    }

    public List<float[]> getTreesNormalized() {
        return copyNormalized(trees);
    }

    public List<float[]> getValvesNormalized() {
        return copyNormalized(valves);
    }

    private List<float[]> copyNormalized(List<PointF> points) {
        List<float[]> out = new ArrayList<>();
        for (PointF p : points) {
            out.add(new float[]{ p.x, p.y });
        }
        return out;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        if (w <= 0 || h <= 0) return;

        float pr = POINT_RADIUS_NORMALIZED * Math.min(w, h);
        float ir = ICON_RADIUS_NORMALIZED * Math.min(w, h);

        // Filled polygon + stroke
        if (perimeterPoints.size() >= 3) {
            Path path = new Path();
            path.moveTo(perimeterPoints.get(0).x * w, perimeterPoints.get(0).y * h);
            for (int i = 1; i < perimeterPoints.size(); i++) {
                path.lineTo(perimeterPoints.get(i).x * w, perimeterPoints.get(i).y * h);
            }
            path.close();
            canvas.drawPath(path, fillPaint);
            canvas.drawPath(path, perimeterPaint);
        }

        // Perimeter points
        for (PointF p : perimeterPoints) {
            float px = p.x * w;
            float py = p.y * h;
            canvas.drawCircle(px, py, pr, pointPaint);
            canvas.drawCircle(px, py, pr - 2, perimeterPaint);
        }

        // Preview line to first point when drawing
        if (mode == DRAW_MODE && perimeterPoints.size() >= 2) {
            float x0 = perimeterPoints.get(0).x * w;
            float y0 = perimeterPoints.get(0).y * h;
            float x1 = perimeterPoints.get(perimeterPoints.size() - 1).x * w;
            float y1 = perimeterPoints.get(perimeterPoints.size() - 1).y * h;
            canvas.drawLine(x1, y1, x0, y0, linePaint);
        }

        // Trees
        for (PointF p : trees) {
            drawTreeIcon(canvas, p.x * w, p.y * h, ir);
        }

        // Valves
        for (PointF p : valves) {
            drawValveIcon(canvas, p.x * w, p.y * h, ir);
        }
    }

    private void drawTreeIcon(Canvas canvas, float x, float y, float r) {
        canvas.drawCircle(x, y - r * 0.3f, r * 0.6f, treePaint);
        canvas.drawCircle(x, y + r * 0.4f, r, treePaint);
    }

    private void drawValveIcon(Canvas canvas, float x, float y, float r) {
        canvas.drawCircle(x, y, r, valvePaint);
        Paint inner = new Paint(Paint.ANTI_ALIAS_FLAG);
        inner.setColor(Color.WHITE);
        inner.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, r * 0.4f, inner);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) return true;
        int w = getWidth();
        int h = getHeight();
        if (w <= 0 || h <= 0) return true;
        float x = event.getX() / w;
        float y = event.getY() / h;

        switch (mode) {
            case DRAW_MODE:
                if (perimeterPoints.size() >= 3) {
                    PointF first = perimeterPoints.get(0);
                    if (Math.hypot(x - first.x, y - first.y) < TOUCH_SLOP_NORMALIZED) {
                        invalidate();
                        notifyChange();
                        return true;
                    }
                }
                perimeterPoints.add(new PointF(x, y));
                invalidate();
                notifyChange();
                break;
            case ADD_TREE_MODE:
                trees.add(new PointF(x, y));
                invalidate();
                notifyChange();
                break;
            case ADD_VALVE_MODE:
                valves.add(new PointF(x, y));
                invalidate();
                notifyChange();
                break;
            case VIEW_MODE:
            default:
                break;
        }
        return true;
    }

    private void notifyChange() {
        if (listener != null) listener.onFarmChanged();
    }

    public void clearPerimeter() {
        perimeterPoints.clear();
        invalidate();
        notifyChange();
    }

    public void clearTrees() {
        trees.clear();
        invalidate();
        notifyChange();
    }

    public void clearValves() {
        valves.clear();
        invalidate();
        notifyChange();
    }

    public boolean hasClosedPerimeter() {
        return perimeterPoints.size() >= 3;
    }
}
