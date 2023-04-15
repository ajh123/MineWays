package tk.minersonline.mineways.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

public class TrafficLightControllerBlock extends HorizontalFacingBlock implements BlockEntityProvider {
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

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		BlockEntity blockentity = world.getBlockEntity(pos);
		if (blockentity instanceof TrafficLightControllerBlockEntity) {
			((TrafficLightControllerBlockEntity)blockentity).onScheduledTick();
		}
	}
}
