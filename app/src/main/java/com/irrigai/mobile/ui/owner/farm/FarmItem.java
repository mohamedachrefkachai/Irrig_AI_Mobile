package com.irrigai.mobile.ui.owner.farm;

import android.graphics.PointF;

import java.util.List;

/** In-memory models for canvas. IDs match Room entities for sync. */
public class FarmItem {

    public static class Tree {
        public long id;
        public float x, y;
        public float rotationAngle;
        public Tree(long id, float x, float y, float rotationAngle) {
            this.id = id; this.x = x; this.y = y; this.rotationAngle = rotationAngle;
        }
    }

    public static class Valve {
        public long id;
        public float x, y;
        public float rotationAngle;
        public boolean isActive;
        public Valve(long id, float x, float y, float rotationAngle, boolean isActive) {
            this.id = id; this.x = x; this.y = y; this.rotationAngle = rotationAngle; this.isActive = isActive;
        }
    }

    public static class Sensor {
        public long id;
        public float x, y;
        public String type;
        public String lastValue;
        public String status;
        public Sensor(long id, float x, float y, String type, String lastValue, String status) {
            this.id = id; this.x = x; this.y = y; this.type = type;
            this.lastValue = lastValue != null ? lastValue : "";
            this.status = status != null ? status : "ok";
        }
    }

    public static class Zone {
        public long id;
        public int color;
        public List<float[]> points;
        public Zone(long id, int color, List<float[]> points) {
            this.id = id; this.color = color; this.points = points;
        }
    }
}
