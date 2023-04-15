package tk.minersonline.mineways.api.network;

import tk.minersonline.mineways.api.device.AbstractDevice;

import java.util.*;

public class NetworkManager {
	private static NetworkManager instance;
	private final Map<UUID, AbstractDevice> devices;
	private final List<Connection> connections;

	private NetworkManager() {
		devices = new HashMap<>();
		connections = new ArrayList<>();
	}

	public static synchronized NetworkManager getInstance() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		return instance;
	}

	public void registerDevice(AbstractDevice device) {
		if (!devices.containsKey(device.getId())) {
			devices.put(device.getId(), device);
			System.out.println("Registered device with id " + device.getId().toString());
		}
	}

	public void removeDevice(AbstractDevice device) {
		devices.remove(device.getId());
		connections.removeIf(connection -> connection.contains(device));
		System.out.println("Removed device with id " + device.getId().toString());
	}

	public void connectDevices(AbstractDevice device1, AbstractDevice device2) {
		connections.add(new Connection(device1, device2));
	}

	public void disconnectDevices(AbstractDevice device1, AbstractDevice device2) {
		connections.removeIf(connection -> connection.contains(device1) && connection.contains(device2));
	}

	public void forwardPacket(Packet packet, AbstractDevice sender, AbstractDevice recipient) {
		if (devices.containsValue(sender) && devices.containsValue(recipient)) {
			for (Connection connection : connections) {
				if (connection.contains(sender) && connection.contains(recipient)) {
					System.out.println("Device " + sender.getId() + " sending packet to " + recipient.getId() + "...");
					recipient.receivePacket(packet);
					break;
				}
			}
		}
	}

	private static class Connection {
		private final AbstractDevice device1;
		private final AbstractDevice device2;

		public Connection(AbstractDevice device1, AbstractDevice device2) {
			this.device1 = device1;
			this.device2 = device2;
		}

		public boolean contains(AbstractDevice device) {
			return device == device1 || device == device2;
		}

		public boolean contains(AbstractDevice device1, AbstractDevice device2) {
			return contains(device1) && contains(device2);
		}
	}
}