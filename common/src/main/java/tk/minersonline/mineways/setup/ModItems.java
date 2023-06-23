package tk.minersonline.mineways.setup;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.Item;
import tk.minersonline.mineways.item.RoadBuilderItem;
import tk.minersonline.mineways.item.DeviceLinkerItem;

public class ModItems {
    public static final RegistrySupplier<Item> ROAD_BUILDER = Registration.ITEMS.register("road_builder", RoadBuilderItem::new);
	 public static final RegistrySupplier<Item> DEVICE_LINKER = Registration.ITEMS.register("device_linker", DeviceLinkerItem::new);

    public static void init() {
        // Initialise the static class variables.
    }
}
