package com.kendrick.kendrickcraft;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CarEntity extends DrivableEntity {
    public CarEntity(EntityType<? extends CarEntity> t, Level l) { super(t, l); }

    @Override public float maxUpStep() { return 1.0f; }
    @Override protected Item dropItem() { return ModItems.CAR.get(); }

    @Override
    protected void drive(LivingEntity pilot) {
        float fwd = pilot != null ? pilot.zza : 0, side = pilot != null ? pilot.xxa : 0;
        if (fwd > 0) speed += 0.03; else if (fwd < 0) speed -= 0.025; else speed *= 0.92;
        speed = Mth.clamp(speed * 0.98, -0.3, 1.0);
        if (horizontalCollision) speed *= 0.5;
        if (Math.abs(speed) > 0.02) setYRot(getYRot() - side * 4f * (speed >= 0 ? 1 : -1));
        setXRot(0);
        Vec3 dir = Vec3.directionFromRotation(0, getYRot());
        double vy = onGround() ? -0.05 : getDeltaMovement().y - 0.08;
        setDeltaMovement(dir.x * speed, vy, dir.z * speed);
    }
}
