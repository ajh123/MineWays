package tk.minersonline.mineways.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractBlockConnectingItem  extends Item {
	public static final String TAG_POS = "pos";

	public AbstractBlockConnectingItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		if (!context.getWorld().isClient) {
			if (clickCondition(context)) {
				final NbtCompound compoundTag = context.getStack().getOrCreateNbt();

				if (compoundTag.contains(TAG_POS)) {
					final BlockPos posEnd = BlockPos.fromLong(compoundTag.getLong(TAG_POS));
					onEndClick(context, posEnd, compoundTag);
					compoundTag.remove(TAG_POS);
				} else {
					compoundTag.putLong(TAG_POS, context.getBlockPos().asLong());
					onStartClick(context, compoundTag);
				}

				return ActionResult.SUCCESS;
			} else {
				return ActionResult.FAIL;
			}
		} else {
			return super.useOnBlock(context);
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);
		final NbtCompound compoundTag = stack.getOrCreateNbt();
		final long posLong = compoundTag.getLong(TAG_POS);
		if (posLong != 0) {
			tooltip.add(Text.translatable("gui.text.mineways.device_linker.selected_block", BlockPos.fromLong(posLong).toShortString()).setStyle(Style.EMPTY.withColor(Formatting.GOLD)));
		}
	}

	protected abstract void onStartClick(ItemUsageContext context, NbtCompound compoundTag);

	protected abstract void onEndClick(ItemUsageContext context, BlockPos posEnd, NbtCompound compoundTag);

	protected abstract boolean clickCondition(ItemUsageContext context);
}