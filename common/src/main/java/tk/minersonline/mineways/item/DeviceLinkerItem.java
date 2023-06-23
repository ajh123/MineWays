package tk.minersonline.mineways.item;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tk.minersonline.mineways.MineWaysMod;
import tk.minersonline.mineways.api.network.DeviceProvider;
import tk.minersonline.mineways.api.network.NetworkManager;

public class DeviceLinkerItem extends AbstractBlockConnectingItem {
	public DeviceLinkerItem() {
		super(new Item.Settings().group(MineWaysMod.MAIN_TAB));
	}

	@Override
	protected void onStartClick(ItemUsageContext context, NbtCompound compoundTag) {

	}

	@Override
	protected void onEndClick(ItemUsageContext context, BlockPos posEnd, NbtCompound compoundTag) {
		final World world = context.getWorld();
		final BlockPos posStart = context.getBlockPos();
		final BlockEntity blockStart = world.getBlockEntity(posStart);
		final BlockEntity blockEnd = world.getBlockEntity(posEnd);
		if (blockStart instanceof DeviceProvider start && blockEnd instanceof DeviceProvider end) {
			if (!NetworkManager.getInstance().devicesLinked(start.getDevice(), end.getDevice())) {
				NetworkManager.getInstance().connectDevices(start.getDevice(), end.getDevice());
			} else {
				NetworkManager.getInstance().disconnectDevices(start.getDevice(), end.getDevice());
			}
		}
	}

	@Override
	protected boolean clickCondition(ItemUsageContext context) {
		final BlockEntity block = context.getWorld().getBlockEntity(context.getBlockPos());
		return block instanceof DeviceProvider;
	}
}
