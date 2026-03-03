package com.irrigai.mobile.data.local.repository;

import android.content.Context;

import com.irrigai.mobile.data.local.AppDatabase;
import com.irrigai.mobile.data.local.entity.FarmEntity;
import com.irrigai.mobile.data.local.entity.FarmPointEntity;
import com.irrigai.mobile.data.local.entity.SensorEntity;
import com.irrigai.mobile.data.local.entity.TreeEntity;
import com.irrigai.mobile.data.local.entity.ValveEntity;
import com.irrigai.mobile.data.local.entity.ZoneAreaEntity;
import com.irrigai.mobile.ui.owner.farm.FarmItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Repository for farm layout. All operations are scoped to farmId (multi-user isolation).
 */
public class FarmRepository {

    private final AppDatabase db;
    private final long farmId;

    public FarmRepository(Context context) {
        this.db = AppDatabase.getDatabase(context.getApplicationContext());
        this.farmId = 0;
    }

    public FarmRepository(Context context, long farmId) {
        this.db = AppDatabase.getDatabase(context.getApplicationContext());
        this.farmId = farmId;
    }

    /** Get or create the default farm for this owner; callback receives farmId (or 0 on error). */
    public static void getOrCreateFarmForOwner(Context context, int ownerId, String farmName, OnFarmIdListener listener) {
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getDatabase(context.getApplicationContext());
            List<FarmEntity> list = db.farmDao().getByOwnerId(ownerId);
            long id;
            if (list != null && !list.isEmpty()) {
                id = list.get(0).id;
            } else {
                FarmEntity farm = new FarmEntity(farmName != null && !farmName.isEmpty() ? farmName : "My Farm", ownerId);
                id = db.farmDao().insert(farm);
            }
            long result = id;
            if (listener != null) {
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> listener.onFarmId(result));
            }
        });
    }

    public interface OnFarmIdListener {
        void onFarmId(long farmId);
    }

    public void saveFarm(List<float[]> perimeter,
                         List<FarmItem.Tree> trees,
                         List<FarmItem.Valve> valves,
                         List<FarmItem.Sensor> sensors,
                         List<FarmItem.Zone> zones,
                         Runnable onDone) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                if (farmId != 0) {
                    db.farmPointDao().deleteByFarmId(farmId);
                    db.treeDao().deleteByFarmId(farmId);
                    db.valveDao().deleteByFarmId(farmId);
                    db.sensorDao().deleteByFarmId(farmId);
                    db.zoneAreaDao().deleteByFarmId(farmId);
                } else {
                    db.farmPointDao().deleteAll();
                    db.treeDao().deleteAll();
                    db.valveDao().deleteAll();
                    db.sensorDao().deleteAll();
                    db.zoneAreaDao().deleteAll();
                }

                List<FarmPointEntity> points = new ArrayList<>();
                for (int i = 0; i < perimeter.size(); i++) {
                    float[] p = perimeter.get(i);
                    points.add(new FarmPointEntity(farmId, i, p[0], p[1]));
                }
                if (!points.isEmpty()) db.farmPointDao().insertAll(points);

                for (FarmItem.Tree t : trees) {
                    db.treeDao().insert(new TreeEntity(farmId, t.x, t.y, t.rotationAngle));
                }
                for (FarmItem.Valve v : valves) {
                    db.valveDao().insert(new ValveEntity(farmId, v.x, v.y, v.rotationAngle, v.isActive));
                }
                for (FarmItem.Sensor s : sensors) {
                    SensorEntity e = new SensorEntity(farmId, s.x, s.y, s.type);
                    e.lastValue = s.lastValue;
                    e.status = s.status;
                    db.sensorDao().insert(e);
                }
                for (FarmItem.Zone z : zones) {
                    db.zoneAreaDao().insert(new ZoneAreaEntity(farmId, z.color, pointsToJson(z.points)));
                }
                if (onDone != null) {
                    new android.os.Handler(android.os.Looper.getMainLooper()).post(onDone);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static String pointsToJson(List<float[]> points) {
        if (points == null || points.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < points.size(); i++) {
            if (i > 0) sb.append(";");
            sb.append(points.get(i)[0]).append(",").append(points.get(i)[1]);
        }
        return sb.toString();
    }

    private static List<float[]> jsonToPoints(String json) {
        List<float[]> out = new ArrayList<>();
        if (json == null || json.isEmpty()) return out;
        for (String part : json.split(";")) {
            String[] xy = part.split(",");
            if (xy.length >= 2) {
                try {
                    out.add(new float[]{ Float.parseFloat(xy[0]), Float.parseFloat(xy[1]) });
                } catch (NumberFormatException ignored) {}
            }
        }
        return out;
    }

    public void loadFarm(OnFarmLoadedListener listener) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                List<FarmPointEntity> points = farmId != 0
                        ? db.farmPointDao().getPointsByFarmId(farmId)
                        : db.farmPointDao().getAllPoints();
                List<TreeEntity> treeList = farmId != 0
                        ? db.treeDao().getTreesByFarmId(farmId)
                        : db.treeDao().getAllTrees();
                List<ValveEntity> valveList = farmId != 0
                        ? db.valveDao().getValvesByFarmId(farmId)
                        : db.valveDao().getAllValves();
                List<SensorEntity> sensorList = farmId != 0
                        ? db.sensorDao().getSensorsByFarmId(farmId)
                        : db.sensorDao().getAllSensors();
                List<ZoneAreaEntity> zoneList = farmId != 0
                        ? db.zoneAreaDao().getZonesByFarmId(farmId)
                        : db.zoneAreaDao().getAllZones();

                List<float[]> perimeter = new ArrayList<>();
                for (FarmPointEntity e : points) perimeter.add(new float[]{ e.x, e.y });

                List<long[]> trees = new ArrayList<>();
                for (TreeEntity e : treeList) trees.add(new long[]{ e.id, (long)Double.doubleToLongBits(e.x), (long)Double.doubleToLongBits(e.y), (long)Double.doubleToLongBits(e.rotationAngle) });
                List<Object[]> treesData = new ArrayList<>();
                for (TreeEntity e : treeList) treesData.add(new Object[]{ e.id, e.x, e.y, e.rotationAngle });

                List<Object[]> valvesData = new ArrayList<>();
                for (ValveEntity e : valveList) valvesData.add(new Object[]{ e.id, e.x, e.y, e.rotationAngle, e.isActive });

                List<Object[]> sensorsData = new ArrayList<>();
                for (SensorEntity e : sensorList) sensorsData.add(new Object[]{ e.id, e.x, e.y, e.type, e.lastValue, e.status });

                List<Object[]> zonesData = new ArrayList<>();
                for (ZoneAreaEntity e : zoneList) zonesData.add(new Object[]{ e.id, e.color, jsonToPoints(e.pointsJson) });

                if (listener != null) {
                    new android.os.Handler(android.os.Looper.getMainLooper()).post(() ->
                            listener.onLoaded(perimeter, treesData, valvesData, sensorsData, zonesData));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public interface OnFarmLoadedListener {
        void onLoaded(List<float[]> perimeter,
                     List<Object[]> trees,
                     List<Object[]> valves,
                     List<Object[]> sensors,
                     List<Object[]> zones);
    }

    public void insertTree(float x, float y, float rotation, OnInsertListener listener) {
        Executors.newSingleThreadExecutor().execute(() -> {
            long id = db.treeDao().insert(new TreeEntity(farmId, x, y, rotation));
            if (listener != null)
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> listener.onInserted(id));
        });
    }

    public void insertValve(float x, float y, float rotation, boolean isActive, OnInsertListener listener) {
        Executors.newSingleThreadExecutor().execute(() -> {
            long id = db.valveDao().insert(new ValveEntity(farmId, x, y, rotation, isActive));
            if (listener != null)
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> listener.onInserted(id));
        });
    }

    public void insertSensor(float x, float y, String type, OnInsertListener listener) {
        Executors.newSingleThreadExecutor().execute(() -> {
            SensorEntity e = new SensorEntity(farmId, x, y, type);
            long id = db.sensorDao().insert(e);
            if (listener != null)
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> listener.onInserted(id));
        });
    }

    public interface OnInsertListener {
        void onInserted(long id);
    }

    public void updateTree(long id, float x, float y, float rotation) {
        Executors.newSingleThreadExecutor().execute(() -> {
            TreeEntity e = new TreeEntity(farmId, x, y, rotation);
            e.id = id;
            db.treeDao().update(e);
        });
    }

    public void updateValve(long id, float x, float y, float rotation, boolean isActive) {
        Executors.newSingleThreadExecutor().execute(() -> {
            ValveEntity e = new ValveEntity(farmId, x, y, rotation, isActive);
            e.id = id;
            db.valveDao().update(e);
        });
    }

    public void updateSensor(long id, float x, float y) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<SensorEntity> all = farmId != 0 ? db.sensorDao().getSensorsByFarmId(farmId) : db.sensorDao().getAllSensors();
            for (SensorEntity e : all) {
                if (e.id == id) {
                    e.x = x; e.y = y;
                    db.sensorDao().update(e);
                    break;
                }
            }
        });
    }

    public void deleteTree(long id) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<TreeEntity> list = farmId != 0 ? db.treeDao().getTreesByFarmId(farmId) : db.treeDao().getAllTrees();
            for (TreeEntity e : list)
                if (e.id == id) { db.treeDao().delete(e); break; }
        });
    }

    public void deleteValve(long id) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<ValveEntity> list = farmId != 0 ? db.valveDao().getValvesByFarmId(farmId) : db.valveDao().getAllValves();
            for (ValveEntity e : list)
                if (e.id == id) { db.valveDao().delete(e); break; }
        });
    }

    public void deleteSensor(long id) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<SensorEntity> list = farmId != 0 ? db.sensorDao().getSensorsByFarmId(farmId) : db.sensorDao().getAllSensors();
            for (SensorEntity e : list)
                if (e.id == id) { db.sensorDao().delete(e); break; }
        });
    }

    /** Clears all farm data (points, trees, valves, sensors, zones). Call from main thread; onDone on main thread. */
    public void clearAll(Runnable onDone) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                if (farmId != 0) {
                    db.farmPointDao().deleteByFarmId(farmId);
                    db.treeDao().deleteByFarmId(farmId);
                    db.valveDao().deleteByFarmId(farmId);
                    db.sensorDao().deleteByFarmId(farmId);
                    db.zoneAreaDao().deleteByFarmId(farmId);
                } else {
                    db.farmPointDao().deleteAll();
                    db.treeDao().deleteAll();
                    db.valveDao().deleteAll();
                    db.sensorDao().deleteAll();
                    db.zoneAreaDao().deleteAll();
                }
                if (onDone != null) {
                    new android.os.Handler(android.os.Looper.getMainLooper()).post(onDone);
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (onDone != null) {
                    new android.os.Handler(android.os.Looper.getMainLooper()).post(onDone);
                }
            }
        });
    }
}
