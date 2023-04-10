package tk.minersonline.mineways.fabric;

import tk.minersonline.mineways.MineWaysExpectPlatform;
import org.quiltmc.loader.api.QuiltLoader;

import java.nio.file.Path;

public class MineWaysExpectPlatformImpl {
    /**
     * This is our actual method to {@link MineWaysExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return QuiltLoader.getConfigDir();
    }
}
