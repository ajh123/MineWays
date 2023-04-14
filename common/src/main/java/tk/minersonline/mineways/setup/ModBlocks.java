package tk.minersonline.mineways.setup;

import java.util.function.Supplier;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import tk.minersonline.mineways.MineWaysMod;
import tk.minersonline.mineways.block.TrafficLightBlock;

public class ModBlocks {
    public static final RegistrySupplier<Block> TRAFFIC_LIGHT = register(
            "traffic_light", () -> new TrafficLightBlock(AbstractBlock.Settings.of(Material.METAL))
    );

    public static <T extends Block> RegistrySupplier<T> registerNoItem(String id, Supplier<? extends T> supplier) {
        return Registration.BLOCKS.register(id, supplier);
    }

    public static <T extends Block> RegistrySupplier<T> register(String id, Supplier<? extends T> supplier) {
        RegistrySupplier<T> block = registerNoItem(id, supplier);
        Registration.ITEMS.register(id, () -> new BlockItem(block.get(), new Item.Settings().group(MineWaysMod.MAIN_TAB)));
        return block;
    }

    public static void init() {
        // Initalise the static class variables.
    }
}
