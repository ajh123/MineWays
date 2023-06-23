package tk.minersonline.mineways.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tk.minersonline.mineways.api.network.AbstractDevice;
import tk.minersonline.mineways.api.network.DeviceProvider;
import tk.minersonline.mineways.api.network.Packet;
import tk.minersonline.mineways.setup.ModBlockEntities;
import tk.minersonline.mineways.utils.TrafficLightUtils;

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

	@Override
	public void processPacket(Packet packet) {
		TrafficLightUtils.LightState state = TrafficLightUtils.LightState.get(packet.getData().getInt("state"));
		if (this.world != null) {
			BlockState blockState = this.world.getBlockState(this.pos);
			if (blockState.getBlock() instanceof TrafficLightBlock) {
				world.setBlockState(pos, blockState.with(TrafficLightBlock.LIGHT_STATE, state));
			}
		}
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

	public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState blockState, T t) {
		if (!world.isClient && t instanceof TrafficLightBlockEntity blockEntity) {
			blockEntity.device.processPackets();
		}
	}
}
