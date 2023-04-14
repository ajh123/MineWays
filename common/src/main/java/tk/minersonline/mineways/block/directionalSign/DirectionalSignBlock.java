package tk.minersonline.mineways.block;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class DirectionalSignBlock extends SignBlock implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    protected static final float SHAPE_SIZE = 4.0f;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(SHAPE_SIZE, 0.0, SHAPE_SIZE, 12.0, 16.0, 12.0);
    private final SignType type;
    public static final IntProperty ROTATION = Properties.ROTATION;

    public DirectionalSignBlock(SignType signType, Block settingsBase) {
        super(Settings.copy(settingsBase), signType);
        this.type = signType;
        this.setDefaultState(this.stateManager.getDefaultState().with(ROTATION, 0).with(WATERLOGGED, false));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (direction == Direction.DOWN && !this.canPlaceAt(state, world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean canMobSpawnInside() {
        return true;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DirectionalSignBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        boolean isHoldingDye = item instanceof DyeItem;
        boolean isHoldingGlowInkSack = itemStack.isOf(Items.GLOW_INK_SAC);
        boolean isHoldingInkSac = itemStack.isOf(Items.INK_SAC);
        boolean canPlayerModify = (isHoldingGlowInkSack || isHoldingDye || isHoldingInkSac) && player.getAbilities().allowModifyWorld;
        if (world.isClient) {
            return canPlayerModify ? ActionResult.SUCCESS : ActionResult.CONSUME;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof DirectionalSignBlockEntity signBlockEntity) {
            boolean isGlowingText = signBlockEntity.isGlowingText();
            if (isHoldingGlowInkSack && isGlowingText || isHoldingInkSac && !isGlowingText) {
                return ActionResult.PASS;
            }
            if (canPlayerModify) {
                boolean nowHasGlowingText;
                if (isHoldingGlowInkSack) {
                    world.playSound(null, pos, SoundEvents.ITEM_GLOW_INK_SAC_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    nowHasGlowingText = signBlockEntity.setGlowingText(true);
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity)player, pos, itemStack);
                    }
                } else if (isHoldingInkSac) {
                    world.playSound(null, pos, SoundEvents.ITEM_INK_SAC_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    nowHasGlowingText = signBlockEntity.setGlowingText(false);
                } else {
                    world.playSound(null, pos, SoundEvents.ITEM_DYE_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    nowHasGlowingText = signBlockEntity.setTextColor(((DyeItem)item).getColor());
                }
                if (nowHasGlowingText) {
                    if (!player.isCreative()) {
                        itemStack.decrement(1);
                    }
                    player.incrementStat(Stats.USED.getOrCreateStat(item));
                }
            }
            return signBlockEntity.onActivate((ServerPlayerEntity)player) ? ActionResult.SUCCESS : ActionResult.PASS;
        }
        return ActionResult.PASS;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    public SignType getSignType() {
        return this.type;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).getMaterial().isSolid();
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(ROTATION, MathHelper.floor((double)((180.0f + ctx.getPlayerYaw()) * 16.0f / 360.0f) + 0.5) & 0xF).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(ROTATION, rotation.rotate(state.get(ROTATION), 16));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(ROTATION, mirror.mirror(state.get(ROTATION), 16));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ROTATION, WATERLOGGED);
    }
}
