package tk.minersonline.mineways.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import tk.minersonline.mineways.MineWaysMod;

public class DirectionalSignItem extends BlockItem {
	public DirectionalSignItem(Block block) {
		super(block, new Settings().group(MineWaysMod.MAIN_TAB));
	}

	@Override
	protected boolean postPlacement(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
		boolean saved = super.postPlacement(pos, world, player, stack, state);
		if (!world.isClient && !saved && player != null) {
			player.openEditSignScreen((SignBlockEntity)world.getBlockEntity(pos));
		}
		return saved;
	}
}
