package tk.minersonline.mineways.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import tk.minersonline.mineways.api.event.EventHandler;
import tk.minersonline.mineways.api.event.Events;
import tk.minersonline.mineways.api.network.NetworkManager;
import tk.minersonline.mineways.setup.ModBlockEntities;

public class TrafficLightControllerBlock extends HorizontalFacingBlock implements BlockEntityProvider, EventHandler {
	public TrafficLightControllerBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState()
				.with(Properties.HORIZONTAL_FACING, Direction.NORTH)
		);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(Properties.HORIZONTAL_FACING);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, ctx.getPlayerFacing().getOpposite());
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new TrafficLightControllerBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		if (type != ModBlockEntities.TRAFFIC_LIGHT_CONTROLLER.get()) return null;
		return TrafficLightControllerBlockEntity::tick;
	}

	@Override
	public boolean handleEvent(Events.Event<?> event) {
		if (event.getData() instanceof Events.BlockBreakEvent breakEvent) {
			World world = breakEvent.getWorld();
			BlockPos pos = breakEvent.getPos();
			BlockEntity entity = world.getBlockEntity(pos);
			if (entity instanceof TrafficLightControllerBlockEntity blockEntity) {
				NetworkManager.getInstance().removeDevice(blockEntity.getDevice());
			}
		}
		return false;
	}
}
