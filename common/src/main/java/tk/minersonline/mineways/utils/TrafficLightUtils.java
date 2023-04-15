package tk.minersonline.mineways.utils;

import net.minecraft.util.StringIdentifiable;

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

		LightState(String name) {
			this.name = name;
		}

		@Override
		public String asString() {
			return name;
		}
	}
}
