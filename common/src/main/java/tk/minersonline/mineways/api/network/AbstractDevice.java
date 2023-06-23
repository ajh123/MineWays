package tk.minersonline.mineways.api.network;

import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.UUID;

public class AbstractDevice {
	private final UUID id;
	private final ArrayList<Packet> buffer;
	private final DeviceProvider provider;

	private AbstractDevice(DeviceProvider provider, UUID id) {
		this.id = id;
		this.provider = provider;
		buffer = new ArrayList<>();
	}

	public static AbstractDevice create(DeviceProvider provider) {
		AbstractDevice device = new AbstractDevice(provider, UUID.randomUUID());
		NetworkManager.getInstance().registerDevice(device);
		return device;
	}

	public static AbstractDevice fromNbt(DeviceProvider provider, NbtCompound nbt) {
		AbstractDevice device = new AbstractDevice(provider, nbt.getUuid("id"));
		NetworkManager.getInstance().registerDevice(device);
		return device;
	}

	public NbtCompound writeNbt() {
		NbtCompound compound = new NbtCompound();
		compound.putUuid("id", this.id);
		return compound;
	}

	protected void receivePacket(Packet packet) {
		buffer.add(packet);
	}

	public void processPackets() {
		for (Packet packet : buffer) {
			this.provider.processPacket(packet);
		}
		buffer.clear();
	}

	public UUID getId() {
		return id;
	}
}
