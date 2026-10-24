package com.kendrick.kendrickcraft;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

/** Arma de fuego de impacto instantáneo (hitscan). Gasta munición salvo en creativo. */
public class GunItem extends Item {
    private final float damage, spread;
    private final int cooldown, pellets;
    private final double range;

    public GunItem(Properties p, float damage, int cooldown, double range, int pellets, float spread) {
        super(p);
        this.damage = damage; this.cooldown = cooldown; this.range = range; this.pellets = pellets; this.spread = spread;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(this)) return InteractionResultHolder.fail(stack);
        if (!player.getAbilities().instabuild && !takeAmmo(player)) {
            if (!level.isClientSide) player.displayClientMessage(Component.literal("Sin munición"), true);
            return InteractionResultHolder.fail(stack);
        }
        if (!level.isClientSide) {
            for (int i = 0; i < pellets; i++) fire((ServerLevel) level, player);
            level.playSound(null, player.blockPosition(), SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 0.4f, 2.0f);
        }
        player.getCooldowns().addCooldown(this, cooldown);
        return InteractionResultHolder.success(stack);
    }

    private boolean takeAmmo(Player p) {
        for (int i = 0; i < p.getInventory().getContainerSize(); i++) {
            ItemStack s = p.getInventory().getItem(i);
            if (s.is(ModItems.BULLET.get())) { s.shrink(1); return true; }
        }
        return false;
    }

    private void fire(ServerLevel level, Player player) {
        var r = player.getRandom();
        Vec3 eye = player.getEyePosition();
        Vec3 dir = player.getLookAngle().add(r.nextGaussian() * spread * 0.01, r.nextGaussian() * spread * 0.01,
                r.nextGaussian() * spread * 0.01).normalize();
        Vec3 end = eye.add(dir.scale(range));
        BlockHitResult bh = level.clip(new ClipContext(eye, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        Vec3 limit = bh.getType() == HitResult.Type.MISS ? end : bh.getLocation();
        AABB box = player.getBoundingBox().expandTowards(dir.scale(range)).inflate(1);
        EntityHitResult eh = ProjectileUtil.getEntityHitResult(level, player, eye, limit, box, e -> !e.isSpectator() && e.isPickable());
        if (eh != null) {
            eh.getEntity().hurt(player.damageSources().playerAttack(player), damage);
            limit = eh.getLocation();
        }
        double len = eye.distanceTo(limit);
        for (double d = 1.5; d < len; d += 2.0) {
            Vec3 pt = eye.add(dir.scale(d));
            level.sendParticles(ParticleTypes.CRIT, pt.x, pt.y, pt.z, 1, 0, 0, 0, 0);
        }
    }
}
