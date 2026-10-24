package com.kendrick.kendrickcraft;

import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(KendrickCraft.MODID);

    public static final DeferredItem<Item> BULLET = ITEMS.registerSimpleItem("bullet", new Item.Properties());
    // daño, cooldown (ticks), alcance, perdigones, dispersión
    public static final DeferredItem<Item> PISTOL = ITEMS.register("pistol",
            () -> new GunItem(new Item.Properties().stacksTo(1), 6f, 10, 64, 1, 0.6f));
    public static final DeferredItem<Item> RIFLE = ITEMS.register("rifle",
            () -> new GunItem(new Item.Properties().stacksTo(1), 5f, 4, 96, 1, 1.0f));
    public static final DeferredItem<Item> SHOTGUN = ITEMS.register("shotgun",
            () -> new GunItem(new Item.Properties().stacksTo(1), 3f, 24, 24, 8, 6.0f));
    public static final DeferredItem<Item> SNIPER = ITEMS.register("sniper",
            () -> new GunItem(new Item.Properties().stacksTo(1), 22f, 40, 256, 1, 0.0f));
    public static final DeferredItem<Item> COMBAT_KNIFE = ITEMS.register("combat_knife",
            () -> new SwordItem(Tiers.IRON, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 2, -1.8f))));
    public static final DeferredItem<Item> CAR = ITEMS.register("car",
            () -> new VehicleItem(new Item.Properties().stacksTo(1), ModEntities.CAR::get));
    public static final DeferredItem<Item> PLANE = ITEMS.register("plane",
            () -> new VehicleItem(new Item.Properties().stacksTo(1), ModEntities.PLANE::get));
}
