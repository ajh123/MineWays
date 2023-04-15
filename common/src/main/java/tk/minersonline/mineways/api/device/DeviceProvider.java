package tk.minersonline.mineways.api.device;

import tk.minersonline.mineways.api.network.Packet;

public interface DeviceProvider {
	void processPacket(Packet packet);
}
