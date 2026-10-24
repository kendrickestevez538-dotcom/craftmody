package com.kendrick.kendrickcraft;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = KendrickCraft.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public final class ClientEvents {
    /** Estado visual local del tren de aterrizaje. */
    public static boolean landingGearDown = true;

    @SubscribeEvent
    public static void clientTick(ClientTickEvent.Post event) {
        while (ClientSetup.TOGGLE_GEAR.consumeClick()) {
            Player player = net.minecraft.client.Minecraft.getInstance().player;
            if (player == null) return;
            if (player.getVehicle() instanceof PlaneEntity) {
                landingGearDown = !landingGearDown;
                player.displayClientMessage(Component.literal(landingGearDown
                        ? "Tren de aterrizaje: ABIERTO" : "Tren de aterrizaje: RETRAÍDO"), true);
            } else {
                player.displayClientMessage(Component.literal("Súbete al avión para usar G."), true);
            }
        }
    }

    private ClientEvents() {}
}
