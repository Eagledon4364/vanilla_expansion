package com.chris.vanilla_expansion_dragons.entity.client.renderer;

import com.chris.vanilla_expansion_dragons.entity.server.ElderWingDragonEntity;
import net.minecraft.world.entity.player.Player;
import org.joml.Quaternionf;

public interface DragonRiderRenderStateAccess {

    void vanillaExpansionDragons$setRider(
            ElderWingDragonEntity dragon,
            Player player,
            float partialTicks
    );

    ElderWingDragonEntity vanillaExpansionDragons$getDragon();

    Player vanillaExpansionDragons$getPlayer();

    float vanillaExpansionDragons$getPartialTicks();

    void vanillaExpansionDragons$setSaddleRotation(Quaternionf rotation);

    Quaternionf vanillaExpansionDragons$getSaddleRotation();

    boolean vanillaExpansionDragons$hasSaddleTransform();
}