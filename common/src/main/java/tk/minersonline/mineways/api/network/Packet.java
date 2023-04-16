package tk.minersonline.mineways.api.network;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class Packet {
	private final Identifier identifier;
	private final NbtCompound data;

	public Packet(Identifier identifier, NbtCompound  data) {
		this.identifier = identifier;
		this.data = data;
	}

	public NbtCompound  getData() {
		return data;
	}

	public Identifier getIdentifier() {
		return identifier;
	}
}
