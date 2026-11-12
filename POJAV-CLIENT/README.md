# POJAV CLIENT v0.2.0 — Fabric 1.21.11

A client-side Minecraft utility mod with a purple/blue Click GUI. This build intentionally contains utility features only; it does not include KillAura, AutoCrystal, Reach, automated attacks, or exploit modules.

## Current starter features

- Right Shift opens the POJAV Click GUI.
- Dark purple/blue custom GUI with module toggles.
- Fullbright toggle using a client-side lightmap brightness override.
- Toggle Sprint utility.
- Title-screen POJAV CLIENT branding overlay.
- Hold C for Zoom while the Zoom module is enabled.
- Live HUD: FPS, ping, coordinates, CPS, keystrokes, armor durability, totem count, and active potion effects.
- Module toggles are saved to `config/pojav-client.properties`.
- Fullbright overrides the client lightmap brightness without changing the user's gamma setting.

## Target environment

- Minecraft Java Edition 1.21.11
- Fabric Loader 0.18.x or newer
- Fabric API 0.141.1+1.21.11
- Java 21
- Yarn mappings 1.21.11+build.1

## Build on Windows

1. Install JDK 21.
2. Install Gradle 9.2 or newer and IntelliJ IDEA Community Edition.
3. Open this folder in IntelliJ IDEA and import it as a Gradle project.
4. Wait for Gradle to download Fabric/Minecraft dependencies.
5. On Windows, double-click `build.bat`, or run `gradle build` in PowerShell.
6. The mod JAR will be in `build/libs/`.

## Install in Minecraft Launcher

1. Install Fabric Loader for Minecraft 1.21.11 using the official Fabric Installer.
2. Launch the Fabric profile once, then close the game.
3. Copy the built `pojav-client-0.2.0.jar` from `build/libs/` into `%appdata%\\.minecraft\\mods`.
4. Make sure Fabric API for 1.21.11 is also in the `mods` folder.
5. Select the Fabric 1.21.11 profile in Minecraft Launcher and press Play.

## Notes

This is an updated source milestone, not a verified compiled JAR. The current environment has Java 21 but does not have Gradle installed and cannot reach dependency repositories, so compilation and in-game testing have not been performed here. Custom menu background art, editable HUD positioning/colors, and a full settings panel remain follow-up work.


## Important build status

This delivery is a **source project**, not a precompiled/install-ready JAR. A real Fabric JAR must be compiled against Minecraft/Fabric dependencies. The environment used to prepare this project does not have Gradle installed, and dependency downloads are unavailable here, so a compiled JAR and in-game test cannot honestly be provided yet.
