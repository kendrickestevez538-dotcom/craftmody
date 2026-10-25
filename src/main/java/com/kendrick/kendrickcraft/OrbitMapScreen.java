package com.kendrick.kendrickcraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

/** Mapa orbital basado en el terreno real que el cliente tiene cargado. */
public class OrbitMapScreen extends Screen {
    private static final int GRID = 96;
    private static final int UNKNOWN = 0xFF101827;
    private final int[] pixels = new int[GRID * GRID];
    private final int centerX;
    private final int centerZ;
    private int blocksPerPixel = 4;
    private int refreshTicks;
    private boolean earthMode;

    public OrbitMapScreen() {
        super(Component.literal("KendrickCraft - Mapa orbital"));
        Minecraft mc = Minecraft.getInstance();
        centerX = mc.player == null ? 0 : mc.player.blockPosition().getX();
        centerZ = mc.player == null ? 0 : mc.player.blockPosition().getZ();
    }

    @Override
    protected void init() {
        int y = height - 30;
        addRenderableWidget(Button.builder(Component.literal("- Zoom"), b -> {
            blocksPerPixel = Math.min(64, blocksPerPixel * 2);
            refreshMap();
        }).bounds(width / 2 - 150, y, 85, 20).build());
        addRenderableWidget(Button.builder(Component.literal("+ Zoom"), b -> {
            blocksPerPixel = Math.max(1, blocksPerPixel / 2);
            refreshMap();
        }).bounds(width / 2 - 60, y, 85, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Ver la Tierra"), b -> {
            earthMode = !earthMode;
            b.setMessage(Component.literal(earthMode ? "Ver mi mundo" : "Ver la Tierra"));
            refreshMap();
        }).bounds(width / 2 + 30, y, 120, 20).build());
        refreshMap();
    }

    @Override
    public void tick() {
        super.tick();
        if (!earthMode && ++refreshTicks >= 10) {
            refreshTicks = 0;
            refreshMap();
        }
    }

    private void refreshMap() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        int yCheck = mc.player == null ? 64 : mc.player.blockPosition().getY();
        for (int gz = 0; gz < GRID; gz++) {
            for (int gx = 0; gx < GRID; gx++) {
                int wx = centerX + (gx - GRID / 2) * blocksPerPixel;
                int wz = centerZ + (gz - GRID / 2) * blocksPerPixel;
                if (!mc.level.hasChunkAt(new BlockPos(wx, yCheck, wz))) {
                    pixels[gz * GRID + gx] = UNKNOWN;
                    continue;
                }
                int surfaceY = mc.level.getHeight(Heightmap.Types.WORLD_SURFACE, wx, wz);
                BlockPos surface = new BlockPos(wx, Math.max(mc.level.getMinBuildHeight(), surfaceY - 1), wz);
                BlockState state = mc.level.getBlockState(surface);
                pixels[gz * GRID + gx] = 0xFF000000 | (state.getMapColor(mc.level, surface).col & 0x00FFFFFF);
            }
        }
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        renderBackground(g, mouseX, mouseY, partialTick);
        g.fill(0, 0, width, height, 0xE908101C);
        g.drawCenteredString(font, "KENDRICKCRAFT • MAPA ORBITAL", width / 2, 12, 0xFF75E0FF);
        if (earthMode) {
            drawEarth(g);
            g.drawCenteredString(font, "TIERRA • VISTA ORBITAL", width / 2, 40, 0xFFFFFFFF);
            g.drawCenteredString(font, "Globo ilustrativo del planeta", width / 2, height - 48, 0xFFB6C8DF);
        } else {
            int mapSize = Math.max(96, Math.min(Math.min(width - 36, height - 92), 300));
            int left = (width - mapSize) / 2;
            int top = (height - mapSize) / 2 - 5;
            g.fill(left - 3, top - 3, left + mapSize + 3, top + mapSize + 3, 0xFF6A8298);
            for (int z = 0; z < GRID; z++) {
                int y1 = top + z * mapSize / GRID;
                int y2 = top + (z + 1) * mapSize / GRID + 1;
                for (int x = 0; x < GRID; x++) {
                    int x1 = left + x * mapSize / GRID;
                    int x2 = left + (x + 1) * mapSize / GRID + 1;
                    g.fill(x1, y1, x2, y2, pixels[z * GRID + x]);
                }
            }
            int mx = left + mapSize / 2, my = top + mapSize / 2;
            g.fill(mx - 4, my - 4, mx + 5, my + 5, 0xFFFFD54A);
            g.drawCenteredString(font, "MUNDO • Centro: " + centerX + ", " + centerZ + " • Escala: " + blocksPerPixel + " bloques/píxel", width / 2, 29, 0xFFFFFFFF);
            g.drawCenteredString(font, "Los chunks no cargados aparecen oscuros; explora para cartografiar más.", width / 2, height - 47, 0xFFB6C8DF);
        }
        super.render(g, mouseX, mouseY, partialTick);
    }

    private void drawEarth(GuiGraphics g) {
        int diameter = Math.min(260, Math.min(width - 50, height - 95));
        int r = diameter / 2, cx = width / 2, cy = height / 2 - 8;
        for (int dy = -r; dy <= r; dy++) {
            int half = (int) Math.sqrt((double) r * r - (double) dy * dy);
            int shade = Math.max(0, 32 - Math.abs(dy) / 2);
            g.fill(cx - half, cy + dy, cx + half, cy + dy + 1, 0xFF003C78 + (shade << 8));
        }
        g.fill(cx - r / 2, cy - r / 2, cx - r / 8, cy - r / 8, 0xFF3E8C55);
        g.fill(cx - r / 3, cy - r / 8, cx - r / 7, cy + r / 3, 0xFF438F4E);
        g.fill(cx + r / 10, cy - r / 2, cx + r / 2, cy - r / 5, 0xFF4C9959);
        g.fill(cx + r / 5, cy - r / 8, cx + r / 3, cy + r / 5, 0xFF3F884D);
        g.fill(cx + r / 5, cy + r / 4, cx + r / 3, cy + r / 2, 0xFF397C48);
        g.fill(cx - r, cy - r, cx + r, cy - r + 2, 0x6679DFFF);
    }

    @Override
    public boolean isPauseScreen() { return false; }
}