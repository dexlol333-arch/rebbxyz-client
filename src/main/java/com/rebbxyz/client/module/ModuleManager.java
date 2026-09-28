package com.rebbxyz.client.module;

import java.util.List;

public final class ModuleManager {
    private final StorageFinder storageFinder = new StorageFinder();
    private final SpawnerFinder spawnerFinder = new SpawnerFinder();

    public List<Module> all() {
        return List.of(storageFinder, spawnerFinder);
    }

    public StorageFinder storageFinder() { return storageFinder; }
    public SpawnerFinder spawnerFinder() { return spawnerFinder; }
}
