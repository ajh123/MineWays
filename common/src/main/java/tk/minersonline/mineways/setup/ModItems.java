package tk.minersonline.mineways.setup;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.Item;
import tk.minersonline.mineways.item.RoadBuilderItem;

public class ModItems {
    public static final RegistrySupplier<Item> ROAD_BUILDER = Registration.ITEMS.register("road_builder", RoadBuilderItem::new);
//	 public static final RegistrySupplier<Item> MANUAL_MAIN = ITEMS.register("manual_main", ManualItem::new);
    
    public static void init() {
        // Initalise the static class variables.
    }
}
