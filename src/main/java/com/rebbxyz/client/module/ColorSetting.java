package com.rebbxyz.client.module;

public final class ColorSetting {
    private int r, g, b;
    private float alpha;

    public ColorSetting(int r, int g, int b, float alpha) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.alpha = alpha;
    }

    public int r() { return r; }
    public int g() { return g; }
    public int b() { return b; }
    public float alpha() { return alpha; }

    public void setRgb(int r, int g, int b) {
        this.r = clamp(r);
        this.g = clamp(g);
        this.b = clamp(b);
    }

    public void setAlpha(float alpha) {
        this.alpha = Math.max(0.0f, Math.min(1.0f, alpha));
    }

    public int argb() {
        return ((int)(alpha * 255.0f) << 24) | (r << 16) | (g << 8) | b;
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }
}
