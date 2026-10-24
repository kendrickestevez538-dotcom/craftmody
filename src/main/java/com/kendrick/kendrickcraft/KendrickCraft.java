package com.kendrick.kendrickcraft;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(KendrickCraft.MODID)
public class KendrickCraft {
    public static final String MODID = "kendrickcraft";

    public KendrickCraft(IEventBus bus) {
        ModItems.ITEMS.register(bus);
        ModEntities.ENTITIES.register(bus);
        ModTabs.TABS.register(bus);
    }
}
