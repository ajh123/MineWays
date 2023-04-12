package tk.minersonline.mineways.setup.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import tk.minersonline.mineways.MineWaysMod;
import tk.minersonline.mineways.item.ManualItem;
import tk.minersonline.mineways.item.RoadBuilderItem;

public class ItemRegistry {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MineWaysMod.MOD_ID, Registry.ITEM_KEY);
    public static final RegistrySupplier<Item> ROAD_BUILDER = ITEMS.register("road_builder", RoadBuilderItem::new);
//	 public static final RegistrySupplier<Item> MANUAL_MAIN = ITEMS.register("manual_main", ManualItem::new);
	
	public static void init() {
		// Register our items, and initalise the static class variables.
		ITEMS.register();
	}
}
