package com.kendrick.kendrickcraft;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/** W/S = acelerador. Con velocidad > 0.45 despega y vuela hacia donde mires. */
public class PlaneEntity extends DrivableEntity {
    public PlaneEntity(EntityType<? extends PlaneEntity> t, Level l) { super(t, l); }

    @Override protected Item dropItem() { return ModItems.PLANE.get(); }

    public boolean isFlying() { return speed > 0.45; }

    @Override
    protected void drive(LivingEntity pilot) {
        float fwd = pilot != null ? pilot.zza : 0;
        if (fwd > 0) speed += 0.02; else if (fwd < 0) speed -= 0.03; else speed *= 0.995;
        speed = Mth.clamp(speed, 0, 1.6);
        if (horizontalCollision) speed = 0;
        if (pilot != null) setYRot(pilot.getYRot());
        boolean flying = isFlying();
        setXRot(flying && pilot != null ? Mth.clamp(pilot.getXRot(), -45, 45) : 0);
        Vec3 dir = Vec3.directionFromRotation(getXRot(), getYRot());
        double vy = flying ? dir.y * speed : (onGround() ? -0.05 : getDeltaMovement().y - 0.08);
        setDeltaMovement(dir.x * speed, vy, dir.z * speed);
    }
}
