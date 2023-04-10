package tk.minersonline.mineways.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RoadBuilderItem extends Item {

	public RoadBuilderItem(Settings properties) {
		super(properties);
	}
	

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        
		world.setBlockState(pos, Blocks.DIRT_PATH.getDefaultState());
		return ActionResult.SUCCESS;
	}
}
