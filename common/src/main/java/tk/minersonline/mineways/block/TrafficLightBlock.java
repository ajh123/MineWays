package tk.minersonline.mineways.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import tk.minersonline.mineways.api.event.EventHandler;
import tk.minersonline.mineways.api.event.Events;
import tk.minersonline.mineways.api.network.NetworkManager;
import tk.minersonline.mineways.setup.ModBlockEntities;
import tk.minersonline.mineways.utils.TrafficLightUtils.LightState;

public class TrafficLightBlock extends HorizontalFacingBlock implements BlockEntityProvider, EventHandler {

    public static final EnumProperty<LightState> LIGHT_STATE = EnumProperty.of("state", LightState.class);


    public TrafficLightBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState()
            .with(Properties.HORIZONTAL_FACING, Direction.NORTH)
            .with(LIGHT_STATE, LightState.ALL_OFF)
        );
    }

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(Properties.HORIZONTAL_FACING);
        builder.add(LIGHT_STATE);
	}

    @Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
		Direction dir = state.get(FACING).getOpposite();
		return switch (dir) {
			case NORTH -> VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.5f);
			case SOUTH -> VoxelShapes.cuboid(0.0f, 0.0f, 0.5f, 1.0f, 1.0f, 1.0f);
			case EAST -> VoxelShapes.cuboid(0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
			case WEST -> VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 0.5f, 1.0f, 1.0f);
			default -> VoxelShapes.fullCube();
		};
	}

    @Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, ctx.getPlayerFacing().getOpposite());
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new TrafficLightBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		if (type != ModBlockEntities.TRAFFIC_LIGHT.get()) return null;
		return TrafficLightBlockEntity::tick;
	}

	@Override
	public boolean handleEvent(Events.Event<?> event) {
		if (event.getData() instanceof Events.BlockBreakEvent breakEvent) {
			World world = breakEvent.getWorld();
			BlockPos pos = breakEvent.getPos();
			BlockEntity entity = world.getBlockEntity(pos);
			if (entity instanceof TrafficLightBlockEntity blockEntity) {
				NetworkManager.getInstance().removeDevice(blockEntity.getDevice());
			}
		}
		return false;
	}
}
