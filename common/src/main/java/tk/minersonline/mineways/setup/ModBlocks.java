package tk.minersonline.mineways.setup;

import java.util.function.Supplier;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import tk.minersonline.mineways.MineWaysMod;
import tk.minersonline.mineways.block.DirectionalSignBlock;
import tk.minersonline.mineways.item.DirectionalSignItem;

public class ModBlocks {
    public static final RegistrySupplier<Block> OAK_DIRECTIONAL_SIGN = registerNoItem(
            "oak_directional_sign", () -> new DirectionalSignBlock(SignType.OAK, Blocks.OAK_SIGN)
    );
    public static final RegistrySupplier<Item> OAK_DIRECTIONAL_SIGN_ITEM = Registration.ITEMS.register(
            "oak_directional_sign", () -> new DirectionalSignItem(ModBlocks.OAK_DIRECTIONAL_SIGN.get())
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
