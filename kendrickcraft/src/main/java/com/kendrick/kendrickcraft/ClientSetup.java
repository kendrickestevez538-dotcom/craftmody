package com.kendrick.kendrickcraft;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;
@EventBusSubscriber(modid = KendrickCraft.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
 public static final KeyMapping TOGGLE_GEAR = new KeyMapping("key.kendrickcraft.toggle_gear", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, "key.categories.kendrickcraft");
 public static final KeyMapping OPEN_ORBIT_MAP = new KeyMapping("key.kendrickcraft.orbit_map", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_M, "key.categories.kendrickcraft");
 @SubscribeEvent public static void renderers(EntityRenderersEvent.RegisterRenderers e) {
  e.registerEntityRenderer(ModEntities.CAR.get(), VehicleRenderer::new);
  e.registerEntityRenderer(ModEntities.PLANE.get(), VehicleRenderer::new);
 }
 @SubscribeEvent public static void registerKeys(RegisterKeyMappingsEvent e) { e.register(TOGGLE_GEAR); e.register(OPEN_ORBIT_MAP); }
}