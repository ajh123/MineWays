package tk.minersonline.mineways.setup;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import tk.minersonline.mineways.MineWaysMod;

public class Registration {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MineWaysMod.MOD_ID, Registry.ITEM_KEY);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(MineWaysMod.MOD_ID, Registry.BLOCK_KEY);
    public static final DeferredRegister<BlockEntityType<?>> BLOCKS_ENTITIES = DeferredRegister.create(MineWaysMod.MOD_ID, Registry.BLOCK_ENTITY_TYPE_KEY);
    

	public static void init() {
		ITEMS.register();
        BLOCKS.register();
        BLOCKS_ENTITIES.register();

        ModItems.init();
        ModBlocks.init();
        ModBlockEntities.init();
	}
}
