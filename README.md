# rebbxyz client — Minecraft 1.21.11 Fabric

A clean client-side Fabric starter with:

- `rebbxyz client` branding
- Dark, compact ClickGUI inspired by the general layout language of PvP clients
- Right Shift opens the ClickGUI
- Left click a module = enable/disable
- Right click a module = open settings
- `StorageFinder`
  - Chests
  - Trapped chests
  - All shulker colors
  - Droppers
  - Per-category outline color
  - Per-category opacity
  - Per-category tracer toggle
  - Range and line-width settings
- `SpawnerFinder`
  - Monster spawners
  - Color + opacity
  - Tracer toggle
  - Range and line-width settings

## Build requirements

Minecraft 1.21.11 uses Java 21. Fabric's 1.21.11 documentation recommends JDK 21, and the 1.21.11 release notes identify Loom 1.14-era tooling. See the official docs before building.

## Build

From this folder:

    gradle build

or, after adding a Gradle wrapper:

    ./gradlew build

The resulting remapped JAR is placed in:

    build/libs/

## Important

This is intentionally a clean-room implementation. It does not include Vape source, assets, branding, or copied proprietary code.

The scanner is client-side and only highlights blocks that are already present in the client world. It does not send packets or modify the server world.

## Next upgrades

Good next steps are:

1. Save settings to JSON.
2. Add actual RGB sliders and opacity sliders.
3. Add animated ClickGUI transitions.
4. Add category tabs/search.
5. Add render-distance culling and chunk-based scanning for better performance.
6. Add a dedicated tracer render layer that can optionally ignore depth.
7. Add a polished color picker.
8. Add module keybinds.
