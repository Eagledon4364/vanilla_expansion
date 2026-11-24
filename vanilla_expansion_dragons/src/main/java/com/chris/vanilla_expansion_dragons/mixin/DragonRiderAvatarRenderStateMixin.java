package com.chris.vanilla_expansion_dragons.mixin;

import com.chris.vanilla_expansion_dragons.entity.client.renderer.DragonRiderRenderStateAccess;
import com.chris.vanilla_expansion_dragons.entity.server.ElderWingDragonEntity;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.player.Player;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AvatarRenderState.class)
public abstract class DragonRiderAvatarRenderStateMixin
        implements DragonRiderRenderStateAccess {

    @Unique
    private ElderWingDragonEntity vanillaExpansionDragons$dragon;

    @Unique
    private Player vanillaExpansionDragons$player;

    @Unique
    private float vanillaExpansionDragons$partialTicks;

    @Unique
    private Quaternionf vanillaExpansionDragons$saddleRotation =
            new Quaternionf();

    @Unique
    private boolean vanillaExpansionDragons$hasSaddleTransform;

    @Override
    public void vanillaExpansionDragons$setRider(
            ElderWingDragonEntity dragon,
            Player player,
            float partialTicks
    ) {
        this.vanillaExpansionDragons$dragon = dragon;
        this.vanillaExpansionDragons$player = player;
        this.vanillaExpansionDragons$partialTicks = partialTicks;
        this.vanillaExpansionDragons$hasSaddleTransform = false;
        this.vanillaExpansionDragons$saddleRotation.identity();
    }

    @Override
    public ElderWingDragonEntity vanillaExpansionDragons$getDragon() {
        return this.vanillaExpansionDragons$dragon;
    }

    @Override
    public Player vanillaExpansionDragons$getPlayer() {
        return this.vanillaExpansionDragons$player;
    }

    @Override
    public float vanillaExpansionDragons$getPartialTicks() {
        return this.vanillaExpansionDragons$partialTicks;
    }

    @Override
    public void vanillaExpansionDragons$setSaddleRotation(
            Quaternionf rotation
    ) {
        this.vanillaExpansionDragons$saddleRotation.set(rotation);
        this.vanillaExpansionDragons$hasSaddleTransform = true;
    }

    @Override
    public Quaternionf vanillaExpansionDragons$getSaddleRotation() {
        return this.vanillaExpansionDragons$saddleRotation;
    }

    @Override
    public boolean vanillaExpansionDragons$hasSaddleTransform() {
        return this.vanillaExpansionDragons$hasSaddleTransform;
    }
}