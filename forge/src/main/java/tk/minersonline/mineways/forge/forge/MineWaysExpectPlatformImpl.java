package tk.minersonline.mineways.forge.forge;

import tk.minersonline.mineways.MineWaysExpectPlatform;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class MineWaysExpectPlatformImpl {
    /**
     * This is our actual method to {@link MineWaysExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
