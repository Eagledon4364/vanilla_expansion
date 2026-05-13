package com.chris.vanilla_expansion.item;


import com.chris.vanilla_expansion.util.ModTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;

public class ModToolMaterials {
    public static ToolMaterial PAXEL_MATERIAL = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            6000, 20.0f, 5.0f, 30, ModTags.Items.PAXEL_REPAIR);



    public static ToolMaterial STEEL = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            2500, 15, 5.0f, 26, ModTags.Items.STEEL_REPAIRABLE);

}
