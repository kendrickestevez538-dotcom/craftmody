package com.kendrick.kendrickcraft;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(KendrickCraft.MODID);

    public static final DeferredBlock<Block> COMPUTER = BLOCKS.register("computer",
            () -> new ComputerBlock(BlockBehaviour.Properties.of().strength(2.5f).sound(SoundType.METAL).noOcclusion()));
    public static final DeferredBlock<Block> RUNWAY = BLOCKS.register("runway",
            () -> new Block(BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE)));
    public static final DeferredBlock<Block> TAXIWAY = BLOCKS.register("taxiway",
            () -> new Block(BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE)));
    public static final DeferredBlock<Block> HANGAR_PANEL = BLOCKS.register("hangar_panel",
            () -> new Block(BlockBehaviour.Properties.of().strength(3.0f).sound(SoundType.METAL)));
    public static final DeferredBlock<Block> AIRPORT_LIGHT = BLOCKS.register("airport_light",
            () -> new Block(BlockBehaviour.Properties.of().strength(1.0f).lightLevel(s -> 15).sound(SoundType.METAL)));
    public static final DeferredBlock<Block> HELIPAD = BLOCKS.register("helipad",
            () -> new Block(BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE)));

    private ModBlocks() {}
}
