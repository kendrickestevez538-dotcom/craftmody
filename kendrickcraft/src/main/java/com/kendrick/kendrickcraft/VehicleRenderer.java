package com.kendrick.kendrickcraft;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/** Renderizado de los vehículos y del tren de aterrizaje visual. */
public class VehicleRenderer extends EntityRenderer<DrivableEntity> {
    public VehicleRenderer(EntityRendererProvider.Context ctx) { super(ctx); }

    @Override
    public ResourceLocation getTextureLocation(DrivableEntity e) {
        return ResourceLocation.withDefaultNamespace("textures/block/stone.png");
    }

    private void box(PoseStack ps, MultiBufferSource b, int light, BlockState st,
                     double x, double y, double z, double sx, double sy, double sz) {
        ps.pushPose();
        ps.translate(x, y, z);
        ps.scale((float) sx, (float) sy, (float) sz);
        ps.translate(-0.5, 0, -0.5);
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(st, ps, b, light, OverlayTexture.NO_OVERLAY);
        ps.popPose();
    }

    @Override
    public void render(DrivableEntity e, float yaw, float pt, PoseStack ps, MultiBufferSource b, int light) {
        ps.pushPose();
        ps.mulPose(Axis.YP.rotationDegrees(-yaw));
        ps.mulPose(Axis.XP.rotationDegrees(e.getXRot()));
        if (e instanceof PlaneEntity) {
            BlockState white = Blocks.WHITE_CONCRETE.defaultBlockState(), red = Blocks.RED_CONCRETE.defaultBlockState(),
                    gray = Blocks.GRAY_CONCRETE.defaultBlockState(), glass = Blocks.LIGHT_BLUE_STAINED_GLASS.defaultBlockState(),
                    black = Blocks.BLACK_CONCRETE.defaultBlockState();
            // Fuselaje, alas, cola, cabina y hélice.
            box(ps, b, light, white, 0, 0.3, 0, 0.7, 0.7, 3.2);
            box(ps, b, light, gray, 0, 0.4, 1.8, 0.5, 0.5, 0.4);
            box(ps, b, light, red, 0, 0.55, 0.2, 5.0, 0.12, 0.9);
            box(ps, b, light, red, 0, 0.8, -1.5, 0.12, 0.9, 0.7);
            box(ps, b, light, red, 0, 0.75, -1.5, 1.6, 0.1, 0.5);
            box(ps, b, light, glass, 0, 0.9, 0.5, 0.55, 0.4, 0.9);
            ps.pushPose();
            ps.translate(0, 0.65, 2.05);
            ps.mulPose(Axis.ZP.rotationDegrees((e.tickCount + pt) * 60f));
            ps.translate(0, -0.04, 0);
            box(ps, b, light, black, 0, 0, 0, 1.4, 0.08, 0.08);
            box(ps, b, light, black, 0, 0, 0, 0.08, 0.08, 1.4);
            ps.popPose();
            if (ClientEvents.landingGearDown) {
                // Dos ruedas principales y una rueda delantera: se ocultan al pulsar G.
                box(ps, b, light, black, -0.65, -0.12, 0.75, 0.22, 0.42, 0.22);
                box(ps, b, light, black, 0.65, -0.12, 0.75, 0.22, 0.42, 0.22);
                box(ps, b, light, black, 0, -0.12, 1.55, 0.18, 0.32, 0.18);
            }
        } else {
            BlockState body = Blocks.RED_CONCRETE.defaultBlockState(), black = Blocks.BLACK_CONCRETE.defaultBlockState(),
                    glass = Blocks.LIGHT_BLUE_STAINED_GLASS.defaultBlockState(), lamp = Blocks.YELLOW_CONCRETE.defaultBlockState();
            box(ps, b, light, body, 0, 0.25, 0, 1.5, 0.4, 2.4);
            box(ps, b, light, glass, 0, 0.65, -0.2, 1.3, 0.45, 1.2);
            box(ps, b, light, black, 0, 1.1, -0.2, 1.3, 0.08, 1.2);
            for (int sx = -1; sx <= 1; sx += 2) for (int sz = -1; sz <= 1; sz += 2)
                box(ps, b, light, black, sx * 0.8, 0, sz * 0.8, 0.2, 0.5, 0.5);
            box(ps, b, light, lamp, -0.5, 0.4, 1.2, 0.3, 0.15, 0.05);
            box(ps, b, light, lamp, 0.5, 0.4, 1.2, 0.3, 0.15, 0.05);
        }
        ps.popPose();
        super.render(e, yaw, pt, ps, b, light);
    }
}
