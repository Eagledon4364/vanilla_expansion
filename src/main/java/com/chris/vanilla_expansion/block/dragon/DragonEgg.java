package com.chris.vanilla_expansion.block.dragon;

import com.chris.vanilla_expansion.entity.server.DragonAnimal;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DragonEgg extends Block {

    private DragonAnimal dragon;
    private final int MAXTIME = 24000;
    private final int MINTIME = 16800;
    protected static final VoxelShape EGG_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D);

    public DragonEgg(Properties properties) {
        super(properties.noOcclusion());
    }
    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return EGG_SHAPE;
    }

    public void setDragon(DragonAnimal dragon) {
        this.dragon = dragon;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (!level.isClientSide()) {
            int duration = chooseTime(level.getRandom());
            level.scheduleTick(pos, this, duration);
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.tick(state, level, pos, random);

        if (this.dragon != null) {

            this.dragon.setBaby(true);

            this.dragon.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            this.dragon.setYRot(random.nextFloat() * 360F);

            level.addFreshEntity(this.dragon);
        }

        // Break/remove the egg block after hatching
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
    }

    public int chooseTime(RandomSource random) {
        return random.nextBoolean() ? MINTIME : MAXTIME;
    }
}