package com.kendrick.kendrickcraft;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/** Base de vehículos: el piloto se sube con clic derecho, W/S aceleran y A/D giran. */
public abstract class DrivableEntity extends Entity {
    protected double speed;

    protected DrivableEntity(EntityType<?> type, Level level) { super(type, level); }

    protected abstract void drive(LivingEntity pilot);
    protected abstract Item dropItem();

    @Override protected void defineSynchedData(SynchedEntityData.Builder b) {}
    @Override protected void readAdditionalSaveData(CompoundTag t) {}
    @Override protected void addAdditionalSaveData(CompoundTag t) {}
    @Override public boolean isPickable() { return !isRemoved(); }
    @Override protected boolean canAddPassenger(Entity p) { return getPassengers().isEmpty(); }

    @Override
    public LivingEntity getControllingPassenger() {
        return getFirstPassenger() instanceof LivingEntity l ? l : null;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (level().isClientSide) return InteractionResult.SUCCESS;
        return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
    }

    @Override
    public boolean hurt(DamageSource src, float amount) {
        if (!level().isClientSide && !isRemoved() && src.getEntity() instanceof Player) {
            spawnAtLocation(dropItem());
            discard();
            return true;
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (isControlledByLocalInstance()) {
            drive(getControllingPassenger());
            move(MoverType.SELF, getDeltaMovement());
        } else {
            setDeltaMovement(Vec3.ZERO);
        }
    }
}
