package tk.minersonline.mineways.utils;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import tk.minersonline.mineways.MineWaysMod;
import tk.minersonline.mineways.api.network.Packet;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TrafficLightUtils {

	// | TOP(Green) | MIDDLE(Yellow) | BOTTOM(Red) | Name              |
	// | ---------- | -------------- | ----------- | ----------------- |
	// | 0          | 0              | 0           | ALL_OFF           |
	// | 0          | 0              | 1           | BOTTOM_ONLY       |
	// | 0          | 1              | 0           | MIDDLE_ONLY       |
	// | 0          | 1              | 1           | MIDDLE_AND_BOTTOM |
	// | 1          | 0              | 0           | TOP_ONLY          |
	// | 1          | 0              | 1           | TOP_AND_BOTTOM    |
	// | 1          | 1              | 0           | TOP_AND_MIDDLE    |
	// | 1          | 1              | 1           | ALL_ON            |
	public enum LightState implements StringIdentifiable {
		ALL_OFF("all_off"),                     // 0 0 0
		BOTTOM_ONLY("bottom_only"),             // 0 0 1
		MIDDLE_ONLY("middle_only"),             // 0 1 0
		MIDDLE_AND_BOTTOM("middle_and_bottom"), // 0 1 1
		TOP_ONLY("top_only"),                   // 1 0 0
		TOP_AND_BOTTOM("top_and_bottom"),       // 1 0 1
		TOP_AND_MIDDLE("top_and_middle"),       // 1 1 0
		ALL_ON("all_on")                        // 1 1 1
		;
		private final String name;

		private static final List<LightState> VALUES = List.of(values());
		private static final int SIZE = VALUES.size();
		private static final Random RANDOM = new Random();

		LightState(String name) {
			this.name = name;
		}

		@Override
		public String asString() {
			return name;
		}

		public static LightState getRandom()  {
			return VALUES.get(RANDOM.nextInt(SIZE));
		}

		public static LightState get(int index)  {
			return VALUES.get(index);
		}
	}

	public static class TrafficLightUpdatePacket extends Packet {
		public TrafficLightUpdatePacket(LightState newState) {
			super(new Identifier(MineWaysMod.MOD_ID, "traffic_light_update"));
			NbtCompound data = new NbtCompound();
			data.putInt("state", newState.ordinal());
			this.setData(data);
		}
	}
}
