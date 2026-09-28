package com.rebbxyz.client.module;

public enum StorageType {
    CHEST("Chests"),
    SHULKER("Shulkers"),
    DROPPER("Droppers");

    private final String display;

    StorageType(String display) {
        this.display = display;
    }

    public String display() {
        return display;
    }
}
