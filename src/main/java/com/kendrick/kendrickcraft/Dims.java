package com.kendrick.kendrickcraft;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class Dims {
    public static final ResourceKey<Level> SOL = key("sol");
    public static final ResourceKey<Level> TIERRA = key("tierra");
    /** Mitad del ancho y del alto del mundo Tierra, en bloques. */
    public static final double HALF_X = 10000, HALF_Z = 5000;

    private static ResourceKey<Level> key(String n) {
        return ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(KendrickCraft.MODID, n));
    }
}
