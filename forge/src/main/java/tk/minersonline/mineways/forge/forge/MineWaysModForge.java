package tk.minersonline.mineways.forge.forge;

import dev.architectury.platform.forge.EventBuses;
import tk.minersonline.mineways.MineWaysMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MineWaysMod.MOD_ID)
public class MineWaysModForge {
    public MineWaysModForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(MineWaysMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        MineWaysMod.init();
    }
}
