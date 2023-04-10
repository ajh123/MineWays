package tk.minersonline.mineways;

import com.google.common.base.Suppliers;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.Registries;
import tk.minersonline.mineways.setup.item.ItemRegistry;

import java.util.function.Supplier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class MineWaysMod {
    public static final String MOD_ID = "mineways";
    // We can use this if we don't want to use DeferredRegister
    public static final Supplier<Registries> REGISTRIES = Suppliers.memoize(() -> Registries.get(MOD_ID));
    // Registering a new creative tab
    public static final ItemGroup MAIN_TAB = CreativeTabRegistry.create(new Identifier(MOD_ID, "mineays_main"), () ->
            new ItemStack(ItemRegistry.ROAD_BUILDER.get()));
    

    
    public static void init() {
        ItemRegistry.init();
        
        System.out.println(MineWaysExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}
