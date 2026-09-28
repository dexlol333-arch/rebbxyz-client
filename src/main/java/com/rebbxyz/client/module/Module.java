package com.rebbxyz.client.module;

public abstract class Module {
    private final String name;
    private final String description;
    private boolean enabled;
    private boolean settingsOpen;

    protected Module(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String name() { return name; }
    public String description() { return description; }
    public boolean enabled() { return enabled; }
    public boolean settingsOpen() { return settingsOpen; }

    public void toggle() {
        enabled = !enabled;
    }

    public void setSettingsOpen(boolean value) {
        settingsOpen = value;
    }
}
