package tk.minersonline.mineways.api.network;

import net.minecraft.nbt.NbtCompound;

public class Packet {
	private final NbtCompound data;

	public Packet(NbtCompound  data) {
		this.data = data;
	}

	public NbtCompound  getData() {
		return data;
	}
}
