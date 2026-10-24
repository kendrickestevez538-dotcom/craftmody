package com.kendrick.kendrickcraft;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, KendrickCraft.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<CarEntity>> CAR = ENTITIES.register("car",
            () -> EntityType.Builder.<CarEntity>of(CarEntity::new, MobCategory.MISC).sized(1.8f, 1.2f).clientTrackingRange(10).build("car"));
    public static final DeferredHolder<EntityType<?>, EntityType<PlaneEntity>> PLANE = ENTITIES.register("plane",
            () -> EntityType.Builder.<PlaneEntity>of(PlaneEntity::new, MobCategory.MISC).sized(2.4f, 1.4f).clientTrackingRange(16).build("plane"));
}
