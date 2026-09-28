package com.rebbxyz.client.module;

import java.util.EnumMap;
import java.util.Map;

public final class StorageFinder extends Module {
    private final Map<StorageType, ColorSetting> colors = new EnumMap<>(StorageType.class);
    private final Map<StorageType, Boolean> tracers = new EnumMap<>(StorageType.class);

    private int range = 64;
    private float lineWidth = 2.0f;

    public StorageFinder() {
        super("StorageFinder", "Highlights chests, shulkers and droppers.");
        colors.put(StorageType.CHEST, new ColorSetting(80, 170, 255, 0.85f));
        colors.put(StorageType.SHULKER, new ColorSetting(180, 90, 255, 0.85f));
        colors.put(StorageType.DROPPER, new ColorSetting(255, 170, 70, 0.85f));
        for (StorageType type : StorageType.values()) tracers.put(type, false);
    }

    public ColorSetting color(StorageType type) { return colors.get(type); }
    public boolean tracer(StorageType type) { return tracers.get(type); }

    public void toggleTracer(StorageType type) {
        tracers.put(type, !tracers.get(type));
    }

    public int range() { return range; }
    public void setRange(int range) { this.range = Math.max(8, Math.min(128, range)); }

    public float lineWidth() { return lineWidth; }
    public void setLineWidth(float width) { this.lineWidth = Math.max(1.0f, Math.min(5.0f, width)); }
}
