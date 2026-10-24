package com.kendrick.kendrickcraft;

import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(KendrickCraft.MODID);

    public static final DeferredItem<Item> BULLET = ITEMS.registerSimpleItem("bullet", new Item.Properties());
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

    public static final DeferredItem<BlockItem> COMPUTER = ITEMS.register("computer",
            () -> new BlockItem(ModBlocks.COMPUTER.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> RUNWAY = ITEMS.register("runway",
            () -> new BlockItem(ModBlocks.RUNWAY.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> TAXIWAY = ITEMS.register("taxiway",
            () -> new BlockItem(ModBlocks.TAXIWAY.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> HANGAR_PANEL = ITEMS.register("hangar_panel",
            () -> new BlockItem(ModBlocks.HANGAR_PANEL.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> AIRPORT_LIGHT = ITEMS.register("airport_light",
            () -> new BlockItem(ModBlocks.AIRPORT_LIGHT.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> HELIPAD = ITEMS.register("helipad",
            () -> new BlockItem(ModBlocks.HELIPAD.get(), new Item.Properties()));
}
