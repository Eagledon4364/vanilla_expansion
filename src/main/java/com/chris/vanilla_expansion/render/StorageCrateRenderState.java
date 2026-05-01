package com.chris.vanilla_expansion.render;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

public class StorageCrateRenderState extends BlockEntityRenderState {
    public Direction facing = Direction.NORTH;
    public final ItemStackRenderState[] items = new ItemStackRenderState[3];
}