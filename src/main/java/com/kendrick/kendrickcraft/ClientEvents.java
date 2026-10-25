package com.kendrick.kendrickcraft;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = KendrickCraft.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public final class ClientEvents {
    @SubscribeEvent
    public static void clientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        while (ClientSetup.OPEN_ORBIT_MAP.consumeClick()) {
            if (mc.player != null && mc.level != null) {
                mc.setScreen(new OrbitMapScreen());
            }
        }
    }
    private ClientEvents() {}
}