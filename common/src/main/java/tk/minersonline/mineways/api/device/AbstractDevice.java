package tk.minersonline.mineways.api.device;

import tk.minersonline.mineways.api.network.Packet;

import java.util.ArrayList;
import java.util.UUID;

public class AbstractDevice {
	private final UUID id;
	private final ArrayList<Packet> buffer;
	private final DeviceProvider provider;

	public AbstractDevice(DeviceProvider provider) {
		this.id = UUID.randomUUID();
		this.provider = provider;
		buffer = new ArrayList<>();
	}

	public void sendPacket(Packet packet, AbstractDevice destination) {
		System.out.println("Device " + id + " sending packet to " + destination.getId() + "...");
		destination.receivePacket(packet);
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
