package tk.minersonline.mineways;

import dev.architectury.registry.CreativeTabRegistry;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import tk.minersonline.mineways.setup.ModItems;
import tk.minersonline.mineways.setup.Registration;

public class MineWaysMod {
    public static final String MOD_ID = "mineways";
    // We can use this if we don't want to use DeferredRegister
    //public static final Supplier<Registries> REGISTRIES = Suppliers.memoize(() -> Registries.get(MOD_ID));
    // Registering a new creative tab
    public static final ItemGroup MAIN_TAB = CreativeTabRegistry.create(new Identifier(MOD_ID, "mineays_main"), () ->
            new ItemStack(ModItems.ROAD_BUILDER.get()));

    public static void init() {
        Registration.init();
        System.out.println(MineWaysExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}
