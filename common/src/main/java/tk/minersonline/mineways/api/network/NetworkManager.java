package tk.minersonline.mineways.api.network;

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
		}
	}

	public void removeDevice(AbstractDevice device) {
		devices.remove(device.getId());
		connections.removeIf(connection -> connection.contains(device));
	}

	public void connectDevices(AbstractDevice device1, AbstractDevice device2) {
		connections.add(new Connection(device1, device2));
	}

	public void disconnectDevices(AbstractDevice device1, AbstractDevice device2) {
		connections.removeIf(connection -> connection.contains(device1) && connection.contains(device2));
	}

	public boolean devicesLinked(AbstractDevice device1, AbstractDevice device2) {
		for (Connection connection : connections) {
			if (connection.contains(device1, device2)) {
				return true;
			}
		}
		return false;
	}

	public Collection<AbstractDevice> getNeighbours(AbstractDevice device) {
		List<AbstractDevice> neighbours = new ArrayList<>();
		for (Connection connection : connections) {
			if (connection.contains(device)) {
				neighbours.add(connection.getOther(device));
			}
		}
		return Collections.unmodifiableList(neighbours);
	}

	public void forwardPacket(Packet packet, AbstractDevice sender, AbstractDevice recipient) {
		if (devices.containsValue(sender) && devices.containsValue(recipient)) {
			for (Connection connection : connections) {
				if (connection.contains(sender) && connection.contains(recipient)) {
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

		public AbstractDevice getOther(AbstractDevice device) {
			if (device == device1) {
				return device2;
			} else if (device == device2) {
				return  device1;
			} else {
				return null;
			}
		}
	}
}