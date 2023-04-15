package tk.minersonline.mineways.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tk.minersonline.mineways.api.device.AbstractDevice;
import tk.minersonline.mineways.api.device.DeviceProvider;
import tk.minersonline.mineways.api.network.NetworkManager;
import tk.minersonline.mineways.api.network.Packet;
import tk.minersonline.mineways.setup.ModBlockEntities;

public class TrafficLightBlockEntity extends BlockEntity implements DeviceProvider {
	private AbstractDevice device;

	public TrafficLightBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.TRAFFIC_LIGHT.get(), pos, state);
	}

	@Override
	public void setWorld(World world) {
		super.setWorld(world);
		if (!world.isClient && this.device == null) {
			this.device = AbstractDevice.create(this);
		}
	}

	public void onScheduledTick() {
		if (!this.removed) {
			this.device.processPackets();
		} else {
			NetworkManager.getInstance().removeDevice(this.getDevice());
		}
	}

	@Override
	public void processPacket(Packet packet) {

	}

	public AbstractDevice getDevice() {
		return device;
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		if (this.device != null) {
			nbt.put("device", this.device.writeNbt());
		}
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		this.device = AbstractDevice.fromNbt(this, nbt.getCompound("device"));
	}
}
