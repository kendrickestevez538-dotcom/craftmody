# KendrickCraft — Fabric 26.3 port

This is a separate Fabric-targeted project so the existing NeoForge 1.21.1 release remains untouched.

## Target
- Minecraft Java 26.3
- Fabric Loader 0.19.5
- Fabric API 0.163.0+26.3
- Java 25
- Fabric Loom 1.18-SNAPSHOT

Minecraft 26.1+ uses unobfuscated game code; this project therefore uses the non-remapping Loom plugin and does not declare Yarn mappings.

## Port status
- [x] Separate Fabric 26.3 Gradle project and metadata
- [x] Fabric entrypoint
- [ ] Reimplement non-weapon features against the 26.3 APIs: orbital map, vehicle entities, airport items/blocks, interactive computer, and dimension/world travel
- [ ] Add client-side map screen and key binding
- [ ] Run a full Gradle build and test in a 26.3 Fabric client

The previous NeoForge source is not binary/API-compatible with Fabric and Minecraft 26.3. Features must be ported, not just have their version numbers changed. This branch is a migration workspace, not a finished playable release yet.
