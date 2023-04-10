package tk.minersonline.mineways.fabric;

import tk.minersonline.mineways.MineWaysExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class MineWaysExpectPlatformImpl {
    /**
     * This is our actual method to {@link MineWaysExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
