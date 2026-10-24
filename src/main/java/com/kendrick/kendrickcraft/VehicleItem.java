package com.kendrick.kendrickcraft;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class VehicleItem extends Item {
    private final Supplier<? extends EntityType<? extends DrivableEntity>> type;

    public VehicleItem(Properties p, Supplier<? extends EntityType<? extends DrivableEntity>> type) {
        super(p);
        this.type = type;
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        var level = ctx.getLevel();
        if (level.isClientSide) return InteractionResult.SUCCESS;
        BlockPos pos = ctx.getClickedPos().relative(ctx.getClickedFace());
        Entity e = type.get().create(level);
        if (e == null) return InteractionResult.FAIL;
        e.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, ctx.getRotation(), 0);
        level.addFreshEntity(e);
        if (ctx.getPlayer() == null || !ctx.getPlayer().getAbilities().instabuild) ctx.getItemInHand().shrink(1);
        return InteractionResult.CONSUME;
    }
}
