package com.chris.vanilla_expansion.util;

import com.chris.vanilla_expansion.component.CoreAffinityComponent;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class ModClientEvents {

    public static void registerTooltipEvents() {
        ItemTooltipCallback.EVENT.register((stack, context, flag, lines) -> {
            CoreAffinityComponent component = stack.get(CoreAffinityComponent.KEY);
            if (component != null) {
                // Determine safe insertion index (right under item title)
                int insertIndex = Math.min(1, lines.size());

                // Insert Title Line
                lines.add(insertIndex, Component.translatable("tooltip.vanilla_expansion.core_affinity." + component.affinity().getSerializedName())
                        .withStyle(component.affinity().getColor()));

                // Insert Effect Line directly beneath Title Line
                lines.add(insertIndex + 1, Component.translatable("tooltip.vanilla_expansion.affinity_effect." + component.affinity().getSerializedName())
                        .withStyle(ChatFormatting.GRAY));
            }
        });
    }
}