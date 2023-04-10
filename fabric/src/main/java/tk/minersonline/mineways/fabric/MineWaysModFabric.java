package tk.minersonline.mineways.fabric;

import tk.minersonline.mineways.fabriclike.MineWaysModFabricLike;
import net.fabricmc.api.ModInitializer;

public class MineWaysModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        MineWaysModFabricLike.init();
    }
}
