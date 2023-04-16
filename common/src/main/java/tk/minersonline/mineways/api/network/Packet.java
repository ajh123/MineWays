package tk.minersonline.mineways.api.network;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class Packet {
	private final Identifier identifier;
	private NbtCompound data = new NbtCompound();

	public Packet(Identifier identifier) {
		this.identifier = identifier;
	}

	public NbtCompound getData() {
		return data;
	}

	public void setData(NbtCompound data) {
		this.data = data;
	}

	public Identifier getIdentifier() {
		return identifier;
	}
}
