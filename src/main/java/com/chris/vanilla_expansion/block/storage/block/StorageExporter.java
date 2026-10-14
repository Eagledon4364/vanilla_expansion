package com.chris.vanilla_expansion.block.storage.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StorageExporter extends DirectionalBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");

    private static final VoxelShape CORE_SHAPE = makeExporterShape();

    private static final VoxelShape NORTH_SHAPE = Block.box(6.0, 6.0, 0.0, 10.0, 10.0, 6.0);
    private static final VoxelShape SOUTH_SHAPE = Block.box(6.0, 6.0, 10.0, 10.0, 10.0, 16.0);
    private static final VoxelShape WEST_SHAPE = Block.box(0.0, 6.0, 6.0, 6.0, 10.0, 10.0);
    private static final VoxelShape EAST_SHAPE = Block.box(10.0, 6.0, 6.0, 16.0, 10.0, 10.0);
    private static final VoxelShape UP_SHAPE = Block.box(6.0, 10.0, 6.0, 10.0, 16.0, 10.0);
    private static final VoxelShape DOWN_SHAPE = Block.box(6.0, 0.0, 6.0, 10.0, 6.0, 10.0);

    public StorageExporter(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(EAST, false)
                .setValue(WEST, false)
                .setValue(UP, false)
                .setValue(DOWN, false));
    }

    private static VoxelShape makeExporterShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Block.box(7.0, 7.0, 2.0, 9.0, 9.0, 3.0));
        shape = Shapes.or(shape, Block.box(6.0, 6.0, 6.0, 10.0, 10.0, 10.0));
        shape = Shapes.or(shape, Block.box(6.0, 6.0, 1.0, 10.0, 10.0, 2.0));
        shape = Shapes.or(shape, Block.box(6.0, 6.0, 3.0, 10.0, 10.0, 5.0));
        shape = Shapes.or(shape, Block.box(5.0, 5.0, 0.0, 11.0, 11.0, 1.0));
        shape = Shapes.or(shape, Block.box(4.0, 4.0, 5.0, 12.0, 12.0, 6.0));
        return shape;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<@NotNull Block, @NotNull BlockState> builder) {
        builder.add(FACING, NORTH, SOUTH, EAST, WEST, UP, DOWN);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getClickedFace().getOpposite();
        BlockGetter level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        return this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(NORTH, canConnectTo(level, pos, Direction.NORTH, facing))
                .setValue(SOUTH, canConnectTo(level, pos, Direction.SOUTH, facing))
                .setValue(EAST, canConnectTo(level, pos, Direction.EAST, facing))
                .setValue(WEST, canConnectTo(level, pos, Direction.WEST, facing))
                .setValue(UP, canConnectTo(level, pos, Direction.UP, facing))
                .setValue(DOWN, canConnectTo(level, pos, Direction.DOWN, facing));
    }

    private boolean canConnectTo(BlockGetter level, BlockPos currentPos, Direction checkDirection, Direction facing) {
        // The front/larger side matching FACING must never connect to cables or other network pieces
        if (checkDirection == facing) {
            return false;
        }

        BlockPos neighborPos = currentPos.relative(checkDirection);
        BlockState state = level.getBlockState(neighborPos);
        return state.getBlock() instanceof StorageCable ||
                state.getBlock() instanceof StorageImporter ||
                state.getBlock() instanceof StorageExporter ||
                level.getBlockEntity(neighborPos) instanceof Container;
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        Direction facing = state.getValue(FACING);
        boolean canConnect = canConnectTo(level, pos, directionToNeighbour, facing);

        switch (directionToNeighbour) {
            case NORTH: return state.setValue(NORTH, canConnect);
            case SOUTH: return state.setValue(SOUTH, canConnect);
            case EAST:  return state.setValue(EAST, canConnect);
            case WEST:  return state.setValue(WEST, canConnect);
            case UP:    return state.setValue(UP, canConnect);
            case DOWN:  return state.setValue(DOWN, canConnect);
            default:    return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = CORE_SHAPE;

        if (state.getValue(NORTH)) shape = Shapes.or(shape, NORTH_SHAPE);
        if (state.getValue(SOUTH)) shape = Shapes.or(shape, SOUTH_SHAPE);
        if (state.getValue(WEST)) shape = Shapes.or(shape, WEST_SHAPE);
        if (state.getValue(EAST)) shape = Shapes.or(shape, EAST_SHAPE);
        if (state.getValue(UP)) shape = Shapes.or(shape, UP_SHAPE);
        if (state.getValue(DOWN)) shape = Shapes.or(shape, DOWN_SHAPE);

        return shape;
    }
}