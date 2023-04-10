package tk.minersonline.mineways.quilt;

import tk.minersonline.mineways.fabriclike.MineWaysModFabricLike;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class MineWaysModQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        MineWaysModFabricLike.init();
    }
}
