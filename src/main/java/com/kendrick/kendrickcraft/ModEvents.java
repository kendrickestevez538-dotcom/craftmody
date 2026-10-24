package com.kendrick.kendrickcraft;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = KendrickCraft.MODID)
public class ModEvents {

    // ---------- /go to sol  y  /go to tierra ----------
    @SubscribeEvent
    public static void commands(RegisterCommandsEvent e) {
        CommandDispatcher<CommandSourceStack> d = e.getDispatcher();
        d.register(Commands.literal("go").then(Commands.literal("to")
                .then(Commands.literal("sol").executes(c -> go(c.getSource(), Dims.SOL)))
                .then(Commands.literal("tierra").executes(c -> go(c.getSource(), Dims.TIERRA)))));
    }

    private static int go(CommandSourceStack src, ResourceKey<Level> dim) throws CommandSyntaxException {
        ServerPlayer p = src.getPlayerOrException();
        ServerLevel lvl = src.getServer().getLevel(dim);
        if (lvl == null) {
            src.sendFailure(Component.literal("La dimensión no está cargada. ¿Está bien instalado el mod?"));
            return 0;
        }
        double y = dim == Dims.SOL ? 2 : lvl.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, 0, 0) + 1;
        p.teleportTo(lvl, 0.5, y, 0.5, p.getYRot(), p.getXRot());
        if (dim == Dims.SOL) {
            if (p.isCreative() || p.isSpectator()) p.sendSystemMessage(Component.literal("Estás en el Sol. En creativo no te pasa nada."));
            else p.kill();          // supervivencia: muerte instantánea
        }
        return 1;
    }

    // ---------- Sol mortal + mundo Tierra continuo ----------
    @SubscribeEvent
    public static void tick(PlayerTickEvent.Post e) {
        if (!(e.getEntity() instanceof ServerPlayer p)) return;
        ResourceKey<Level> dim = p.level().dimension();
        if (dim.equals(Dims.SOL)) {
            if (p.isAlive() && !p.isCreative() && !p.isSpectator()) p.kill();
        } else if (dim.equals(Dims.TIERRA)) {
            wrap(p);
        }
    }

    private static void wrap(ServerPlayer p) {
        double x = p.getX(), z = p.getZ(), nx = x, nz = z;
        boolean move = false;
        if (Math.abs(z) > Dims.HALF_Z) {      // cruzar un polo: apareces en el lado opuesto
            nz = Math.signum(z) * (Dims.HALF_Z - 2);
            nx = x + (x > 0 ? -Dims.HALF_X : Dims.HALF_X);
            move = true;
        } else if (x > Dims.HALF_X) { nx = -Dims.HALF_X + 2; move = true; }
        else if (x < -Dims.HALF_X) { nx = Dims.HALF_X - 2; move = true; }
        if (!move) return;
        Entity v = p.getVehicle();
        if (v != null) v.ejectPassengers();
        p.teleportTo(nx, p.getY(), nz);
        if (v != null) { v.setPos(nx, v.getY(), nz); p.startRiding(v, true); }
    }
}
