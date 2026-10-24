package com.kendrick.kendrickcraft;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, KendrickCraft.MODID);

    public static final net.neoforged.neoforge.registries.DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN =
            TABS.register("main", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.kendrickcraft"))
                    .icon(() -> new ItemStack(ModItems.RIFLE.get()))
                    .displayItems((p, out) -> ModItems.ITEMS.getEntries().forEach(i -> out.accept(i.get())))
                    .build());
}
