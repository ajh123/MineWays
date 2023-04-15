package tk.minersonline.mineways.api.device;

import net.minecraft.nbt.NbtCompound;
import tk.minersonline.mineways.api.network.NetworkManager;
import tk.minersonline.mineways.api.network.Packet;

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

	public void receivePacket(Packet packet) {
		buffer.add(packet);
		System.out.println("Packet received by " + id);
	}

	public void processPackets() {
		for (Packet packet : buffer) {
			System.out.println("Processing packet " + packet.getData() + " at " + id);
			this.provider.processPacket(packet);
		}
		buffer.clear();
	}

	public UUID getId() {
		return id;
	}
}
