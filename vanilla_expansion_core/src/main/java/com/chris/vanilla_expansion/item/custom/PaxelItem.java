package com.chris.vanilla_expansion.item.custom;

import com.chris.vanilla_expansion.util.ModTags;
import net.minecraft.core.Holder;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.BlockTransformers;
import net.minecraft.world.item.component.DamageResistant;

import java.util.ArrayList;
import java.util.List;

public class PaxelItem extends Item {

    public PaxelItem(ToolMaterial material, float attackDamage, float attackSpeed, Properties properties) {
        super(properties.tool(material, ModTags.Blocks.PAXEL_MINEABLE, attackDamage, attackSpeed, 1.0f)
                .delayedComponent(DataComponents.DAMAGE_RESISTANT, context ->
                        new DamageResistant(context.getOrThrow(DamageTypeTags.IS_EXPLOSION)))
                .delayedComponent(DataComponents.BLOCK_TRANSFORMER, context -> {
                    BlockTransformer shovelTransformer = context.getOrThrow(BlockTransformers.SHOVEL).value();
                    BlockTransformer axeTransformer = context.getOrThrow(BlockTransformers.AXE).value();

                    List<BlockTransformer.BlockTransformData> combinedTransforms = new ArrayList<>();
                    combinedTransforms.addAll(shovelTransformer.transforms());
                    combinedTransforms.addAll(axeTransformer.transforms());

                    return Holder.direct(new BlockTransformer(combinedTransforms));
                }));
    }
}