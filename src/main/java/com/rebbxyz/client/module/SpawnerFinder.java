package com.rebbxyz.client.module;

public final class SpawnerFinder extends Module {
    private final ColorSetting color = new ColorSetting(255, 70, 70, 0.90f);
    private boolean tracer;
    private int range = 64;
    private float lineWidth = 2.0f;

    public SpawnerFinder() {
        super("SpawnerFinder", "Highlights monster spawners.");
    }

    public ColorSetting color() { return color; }
    public boolean tracer() { return tracer; }
    public void toggleTracer() { tracer = !tracer; }

    public int range() { return range; }
    public void setRange(int range) { this.range = Math.max(8, Math.min(128, range)); }

    public float lineWidth() { return lineWidth; }
    public void setLineWidth(float width) { this.lineWidth = Math.max(1.0f, Math.min(5.0f, width)); }
}
