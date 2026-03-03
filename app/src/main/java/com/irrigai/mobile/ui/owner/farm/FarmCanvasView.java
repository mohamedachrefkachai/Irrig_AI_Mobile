package com.irrigai.mobile.ui.owner.farm;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.irrigai.mobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Full smart farm canvas: perimeter, trees, valves, sensors, zones.
 * Modes: DRAW, ADD_TREE, ADD_VALVE, ADD_SENSOR, DELETE, ROTATE, SIMULATION, VIEW.
 * Drag & drop, delete, rotation, irrigation animation.
 */
public class FarmCanvasView extends View {

    public enum FarmMode {
        DRAW_MODE, ADD_TREE_MODE, ADD_VALVE_MODE, ADD_SENSOR_MODE,
        DELETE_MODE, ROTATE_MODE, SIMULATION_MODE, VIEW_MODE
    }

    private FarmMode mode = FarmMode.VIEW_MODE;
    private final List<PointF> perimeter = new ArrayList<>();
    private final List<FarmItem.Tree> trees = new ArrayList<>();
    private final List<FarmItem.Valve> valves = new ArrayList<>();
    private final List<FarmItem.Sensor> sensors = new ArrayList<>();
    private final List<FarmItem.Zone> zones = new ArrayList<>();

    private final Paint perimeterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint treePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint valvePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint sensorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint zonePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint waterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static final float TOUCH_SLOP = 0.04f;
    private static final float HIT_RADIUS = 0.04f;
    private static final float ICON_RADIUS = 0.032f;
    private float layoutRotationAngle = 0f;

    private Object draggingItem;
    private float lastTouchX, lastTouchY;
    private GestureDetector gestureDetector;
    private ValueAnimator irrigationAnimator;
    private float simulationRadius = 0f;
    private boolean simulationRunning = false;

    private int primaryGreen, darkGreen, statusBlue;
    private int[] zoneColors;

    public interface OnFarmChangeListener {
        void onFarmChanged();
        void onBeforeModify();
        void onSensorTapped(FarmItem.Sensor sensor);
        void onRequestAddTree(float x, float y);
        void onRequestAddValve(float x, float y);
        void onRequestAddSensor(float x, float y);
        void onTreeRemoved(long id);
        void onValveRemoved(long id);
        void onSensorRemoved(long id);
    }
    private OnFarmChangeListener listener;

    public FarmCanvasView(Context context) {
        super(context);
        init(context);
    }

    public FarmCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FarmCanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        primaryGreen = context.getColor(R.color.primary_green);
        darkGreen = context.getColor(R.color.dark_green);
        statusBlue = context.getColor(R.color.status_blue);
        zoneColors = new int[] {
            Color.parseColor("#ECFDF5"),
            Color.parseColor("#DBEAFE"),
            Color.parseColor("#FEF3C7"),
            Color.parseColor("#FCE7F3"),
            Color.parseColor("#E0E7FF")
        };

        perimeterPaint.setColor(primaryGreen);
        perimeterPaint.setStyle(Paint.Style.STROKE);
        perimeterPaint.setStrokeWidth(4f);
        fillPaint.setColor(Color.argb(55, 16, 185, 129));
        fillPaint.setStyle(Paint.Style.FILL);
        pointPaint.setColor(primaryGreen);
        pointPaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(Color.argb(200, 16, 185, 129));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(3f);
        treePaint.setColor(darkGreen);
        treePaint.setStyle(Paint.Style.FILL);
        valvePaint.setColor(statusBlue);
        valvePaint.setStyle(Paint.Style.FILL);
        sensorPaint.setColor(Color.parseColor("#6B7280"));
        sensorPaint.setStyle(Paint.Style.FILL);
        zonePaint.setStyle(Paint.Style.FILL);
        waterPaint.setColor(Color.argb(100, 59, 130, 246));
        waterPaint.setStyle(Paint.Style.FILL);
        setBackgroundColor(Color.argb(255, 248, 250, 252));
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                if (getWidth() <= 0 || getHeight() <= 0) return;
                float nx = e.getX() / getWidth();
                float ny = e.getY() / getHeight();
                if (mode != FarmMode.VIEW_MODE && mode != FarmMode.SIMULATION_MODE && mode != FarmMode.DRAW_MODE && mode != FarmMode.DELETE_MODE) {
                    for (FarmItem.Tree t : trees)
                        if (hitTest(nx, ny, t.x, t.y)) {
                            if (listener != null) listener.onBeforeModify();
                            draggingItem = t; lastTouchX = nx; lastTouchY = ny; return;
                        }
                    for (FarmItem.Valve v : valves)
                        if (hitTest(nx, ny, v.x, v.y)) {
                            if (listener != null) listener.onBeforeModify();
                            draggingItem = v; lastTouchX = nx; lastTouchY = ny; return;
                        }
                    for (FarmItem.Sensor s : sensors)
                        if (hitTest(nx, ny, s.x, s.y)) {
                            if (listener != null) listener.onBeforeModify();
                            draggingItem = s; lastTouchX = nx; lastTouchY = ny; return;
                        }
                }
            }
        });
    }

    public void setMode(FarmMode m) {
        mode = m;
        stopSimulation();
        draggingItem = null;
        postInvalidateOnAnimation();
    }

    public FarmMode getMode() {
        return mode;
    }

    public void setOnFarmChangeListener(OnFarmChangeListener l) {
        listener = l;
    }

    // --- Load from normalized data (after DB load) ---
    public void setPerimeterFromNormalized(List<float[]> points) {
        perimeter.clear();
        for (float[] p : points) perimeter.add(new PointF(p[0], p[1]));
        postInvalidateOnAnimation();
    }

    public void setTreesFromNormalized(List<Object[]> data) {
        trees.clear();
        if (data == null) return;
        for (Object[] d : data) {
            long id = ((Number) d[0]).longValue();
            float x = ((Number) d[1]).floatValue();
            float y = ((Number) d[2]).floatValue();
            float rot = d.length > 3 ? ((Number) d[3]).floatValue() : 0f;
            trees.add(new FarmItem.Tree(id, x, y, rot));
        }
        postInvalidateOnAnimation();
    }

    public void setValvesFromNormalized(List<Object[]> data) {
        valves.clear();
        for (Object[] d : data) {
            float x = ((Number)d[1]).floatValue();
            float y = ((Number)d[2]).floatValue();
            float rot = d.length > 3 ? ((Number)d[3]).floatValue() : 0f;
            boolean active = d.length > 4 && (Boolean)d[4];
            valves.add(new FarmItem.Valve(((Number)d[0]).longValue(), x, y, rot, active));
        }
        postInvalidateOnAnimation();
    }

    public void setSensorsFromNormalized(List<Object[]> data) {
        sensors.clear();
        for (Object[] d : data) {
            long id = ((Number)d[0]).longValue();
            float x = ((Number)d[1]).floatValue();
            float y = ((Number)d[2]).floatValue();
            String type = d.length > 3 ? (String)d[3] : "moisture";
            String lastVal = d.length > 4 ? (String)d[4] : "";
            String status = d.length > 5 ? (String)d[5] : "ok";
            sensors.add(new FarmItem.Sensor(id, x, y, type, lastVal, status));
        }
        postInvalidateOnAnimation();
    }

    public void setZonesFromNormalized(List<Object[]> data) {
        zones.clear();
        for (Object[] d : data) {
            int color = ((Number)d[1]).intValue();
            @SuppressWarnings("unchecked")
            List<float[]> pts = (List<float[]>) d[2];
            zones.add(new FarmItem.Zone(((Number)d[0]).longValue(), color, pts));
        }
        postInvalidateOnAnimation();
    }

    // --- Export for save ---
    public List<float[]> getPerimeterNormalized() {
        List<float[]> out = new ArrayList<>();
        for (PointF p : perimeter) out.add(new float[]{ p.x, p.y });
        return out;
    }

    public List<FarmItem.Tree> getTrees() { return new ArrayList<>(trees); }
    public List<FarmItem.Valve> getValves() { return new ArrayList<>(valves); }
    public List<FarmItem.Sensor> getSensors() { return new ArrayList<>(sensors); }
    public List<FarmItem.Zone> getZones() { return new ArrayList<>(zones); }

    public void addTree(long id, float x, float y) {
        trees.add(new FarmItem.Tree(id, x, y, 0f));
        postInvalidateOnAnimation();
    }

    public void addValve(long id, float x, float y) {
        valves.add(new FarmItem.Valve(id, x, y, 0f, true));
        postInvalidateOnAnimation();
    }

    public void addSensor(long id, float x, float y, String type) {
        sensors.add(new FarmItem.Sensor(id, x, y, type, "", "ok"));
        postInvalidateOnAnimation();
    }

    public void addZone(long id, int color, List<float[]> points) {
        zones.add(new FarmItem.Zone(id, color, new ArrayList<>(points)));
        postInvalidateOnAnimation();
    }

    public void setLayoutRotation(float degrees) {
        layoutRotationAngle = degrees;
        postInvalidateOnAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = getWidth();
        int h = getHeight();
        if (w <= 0 || h <= 0) return;
        float scale = Math.min(w, h);

        if (layoutRotationAngle != 0) {
            canvas.save();
            canvas.rotate(layoutRotationAngle, w / 2f, h / 2f);
        }

        // 1. Zones
        for (FarmItem.Zone z : zones) {
            if (z.points == null || z.points.size() < 3) continue;
            Path path = new Path();
            path.moveTo(z.points.get(0)[0] * w, z.points.get(0)[1] * h);
            for (int i = 1; i < z.points.size(); i++)
                path.lineTo(z.points.get(i)[0] * w, z.points.get(i)[1] * h);
            path.close();
            zonePaint.setColor(z.color);
            canvas.drawPath(path, zonePaint);
        }

        // 2. Farm perimeter
        if (perimeter.size() >= 3) {
            Path path = new Path();
            path.moveTo(perimeter.get(0).x * w, perimeter.get(0).y * h);
            for (int i = 1; i < perimeter.size(); i++)
                path.lineTo(perimeter.get(i).x * w, perimeter.get(i).y * h);
            path.close();
            canvas.drawPath(path, fillPaint);
            canvas.drawPath(path, perimeterPaint);
        }
        for (PointF p : perimeter) {
            float px = p.x * w, py = p.y * h;
            canvas.drawCircle(px, py, scale * 0.018f, pointPaint);
        }
        if (mode == FarmMode.DRAW_MODE && perimeter.size() >= 2) {
            float x0 = perimeter.get(0).x * w, y0 = perimeter.get(0).y * h;
            float x1 = perimeter.get(perimeter.size() - 1).x * w, y1 = perimeter.get(perimeter.size() - 1).y * h;
            canvas.drawLine(x1, y1, x0, y0, linePaint);
        }

        // 3. Irrigation animation (expanding circles from valves)
        if (simulationRunning && simulationRadius > 0) {
            for (FarmItem.Valve v : valves) {
                if (!v.isActive) continue;
                float vx = v.x * w, vy = v.y * h;
                float r = simulationRadius * scale;
                waterPaint.setAlpha((int)(120 * (1f - simulationRadius / 0.5f)));
                canvas.drawCircle(vx, vy, r, waterPaint);
            }
        }

        // 4. Trees
        float ir = scale * ICON_RADIUS;
        for (FarmItem.Tree t : trees) {
            float tx = t.x * w, ty = t.y * h;
            canvas.save();
            canvas.rotate(t.rotationAngle, tx, ty);
            canvas.drawCircle(tx, ty - ir * 0.3f, ir * 0.6f, treePaint);
            canvas.drawCircle(tx, ty + ir * 0.4f, ir, treePaint);
            canvas.restore();
        }

        // 5. Valves (blink when simulating)
        for (FarmItem.Valve v : valves) {
            float vx = v.x * w, vy = v.y * h;
            if (simulationRunning && v.isActive)
                valvePaint.setAlpha(128 + (int)(127 * Math.sin(System.currentTimeMillis() / 200.0)));
            else
                valvePaint.setAlpha(255);
            canvas.save();
            canvas.rotate(v.rotationAngle, vx, vy);
            canvas.drawCircle(vx, vy, ir, valvePaint);
            canvas.drawCircle(vx, vy, ir * 0.4f, sensorPaint);
            canvas.restore();
        }

        // 6. Sensors
        for (FarmItem.Sensor s : sensors) {
            float sx = s.x * w, sy = s.y * h;
            canvas.save();
            canvas.drawCircle(sx, sy, ir * 0.9f, sensorPaint);
            canvas.restore();
        }

        if (layoutRotationAngle != 0) canvas.restore();
    }

    private boolean hitTest(float nx, float ny, float ox, float oy) {
        return Math.hypot(nx - ox, ny - oy) < HIT_RADIUS;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        int w = getWidth();
        int h = getHeight();
        if (w <= 0 || h <= 0) return true;
        float nx = event.getX() / w;
        float ny = event.getY() / h;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchX = nx;
                lastTouchY = ny;
                if (mode == FarmMode.DELETE_MODE) {
                    FarmItem.Tree hitTree = null;
                    for (FarmItem.Tree t : trees)
                        if (hitTest(nx, ny, t.x, t.y)) { hitTree = t; break; }
                    if (hitTree != null) {
                        if (listener != null) listener.onBeforeModify();
                        long id = hitTree.id;
                        trees.remove(hitTree);
                        if (listener != null) listener.onTreeRemoved(id);
                        postInvalidateOnAnimation();
                        return true;
                    }
                    FarmItem.Valve hitValve = null;
                    for (FarmItem.Valve v : valves)
                        if (hitTest(nx, ny, v.x, v.y)) { hitValve = v; break; }
                    if (hitValve != null) {
                        if (listener != null) listener.onBeforeModify();
                        long id = hitValve.id;
                        valves.remove(hitValve);
                        if (listener != null) listener.onValveRemoved(id);
                        postInvalidateOnAnimation();
                        return true;
                    }
                    FarmItem.Sensor hitSensor = null;
                    for (FarmItem.Sensor s : sensors)
                        if (hitTest(nx, ny, s.x, s.y)) { hitSensor = s; break; }
                    if (hitSensor != null) {
                        if (listener != null) listener.onBeforeModify();
                        long id = hitSensor.id;
                        sensors.remove(hitSensor);
                        if (listener != null) listener.onSensorRemoved(id);
                        postInvalidateOnAnimation();
                        return true;
                    }
                } else if (mode == FarmMode.VIEW_MODE || mode == FarmMode.SIMULATION_MODE) {
                    for (FarmItem.Sensor s : sensors)
                        if (hitTest(nx, ny, s.x, s.y)) {
                            if (listener != null) listener.onSensorTapped(s);
                            return true;
                        }
                } else if (draggingItem == null) {
                    switch (mode) {
                    case DRAW_MODE:
                        if (perimeter.size() >= 3) {
                            PointF first = perimeter.get(0);
                            if (Math.hypot(nx - first.x, ny - first.y) < TOUCH_SLOP) {
                                if (listener != null) listener.onBeforeModify();
                                postInvalidateOnAnimation();
                                notifyChange();
                                return true;
                            }
                        }
                        if (listener != null) listener.onBeforeModify();
                        perimeter.add(new PointF(nx, ny));
                        notifyChange();
                        break;
                    case ADD_TREE_MODE:
                        if (listener != null) listener.onRequestAddTree(nx, ny);
                        break;
                    case ADD_VALVE_MODE:
                        if (listener != null) listener.onRequestAddValve(nx, ny);
                        break;
                    case ADD_SENSOR_MODE:
                        if (listener != null) listener.onRequestAddSensor(nx, ny);
                        break;
                    default:
                        break;
                    }
                }
                postInvalidateOnAnimation();
                break;

            case MotionEvent.ACTION_MOVE:
                if (draggingItem != null) {
                    float dx = nx - lastTouchX;
                    float dy = ny - lastTouchY;
                    lastTouchX = nx;
                    lastTouchY = ny;
                    if (draggingItem instanceof FarmItem.Tree) {
                        FarmItem.Tree t = (FarmItem.Tree) draggingItem;
                        t.x = Math.max(0, Math.min(1f, t.x + dx));
                        t.y = Math.max(0, Math.min(1f, t.y + dy));
                    } else if (draggingItem instanceof FarmItem.Valve) {
                        FarmItem.Valve v = (FarmItem.Valve) draggingItem;
                        v.x = Math.max(0, Math.min(1f, v.x + dx));
                        v.y = Math.max(0, Math.min(1f, v.y + dy));
                    } else if (draggingItem instanceof FarmItem.Sensor) {
                        FarmItem.Sensor s = (FarmItem.Sensor) draggingItem;
                        s.x = Math.max(0, Math.min(1f, s.x + dx));
                        s.y = Math.max(0, Math.min(1f, s.y + dy));
                    }
                    postInvalidateOnAnimation();
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (draggingItem != null) {
                    notifyChange();
                    draggingItem = null;
                }
                break;
        }
        return true;
    }

    public void startIrrigationSimulation() {
        stopSimulation();
        mode = FarmMode.SIMULATION_MODE;
        simulationRunning = true;
        simulationRadius = 0f;
        irrigationAnimator = ValueAnimator.ofFloat(0f, 0.5f);
        irrigationAnimator.setDuration(2500);
        irrigationAnimator.setInterpolator(new LinearInterpolator());
        irrigationAnimator.addUpdateListener(animation -> {
            simulationRadius = (float) animation.getAnimatedValue();
            postInvalidateOnAnimation();
        });
        irrigationAnimator.start();
    }

    public void stopSimulation() {
        simulationRunning = false;
        if (irrigationAnimator != null) {
            irrigationAnimator.cancel();
            irrigationAnimator = null;
        }
        simulationRadius = 0f;
        postInvalidateOnAnimation();
    }

    private void notifyChange() {
        if (listener != null) listener.onFarmChanged();
    }

    public boolean hasClosedPerimeter() {
        return perimeter.size() >= 3;
    }

    public void clearPerimeter() {
        perimeter.clear();
        postInvalidateOnAnimation();
        notifyChange();
    }

    public int getZoneColor(int index) {
        return index >= 0 && index < zoneColors.length ? zoneColors[index] : zoneColors[0];
    }

    /** Clears perimeter, trees, valves, sensors, zones to empty state. */
    public void clearAllState() {
        perimeter.clear();
        trees.clear();
        valves.clear();
        sensors.clear();
        zones.clear();
        stopSimulation();
        postInvalidateOnAnimation();
        notifyChange();
    }
}
