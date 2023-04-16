package tk.minersonline.mineways.api.network;

import tk.minersonline.mineways.api.network.AbstractDevice;
import tk.minersonline.mineways.api.network.Packet;

public interface DeviceProvider {
	void processPacket(Packet packet);
	AbstractDevice getDevice();
}
