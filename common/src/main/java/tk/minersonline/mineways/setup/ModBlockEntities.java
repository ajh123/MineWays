package tk.minersonline.mineways.setup;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.entity.BlockEntityType;
import tk.minersonline.mineways.block.TrafficLightBlockEntity;
import tk.minersonline.mineways.block.TrafficLightControllerBlockEntity;

public class ModBlockEntities {
    public static final RegistrySupplier<BlockEntityType<TrafficLightBlockEntity>> TRAFFIC_LIGHT = Registration.BLOCKS_ENTITIES.register(
        "traffic_light",
        () -> BlockEntityType.Builder.create(TrafficLightBlockEntity::new, ModBlocks.TRAFFIC_LIGHT.get()
        ).build(null)
    );

     public static final RegistrySupplier<BlockEntityType<TrafficLightControllerBlockEntity>> TRAFFIC_LIGHT_CONTROLLER = Registration.BLOCKS_ENTITIES.register(
         "traffic_light_controller",
         () -> BlockEntityType.Builder.create(TrafficLightControllerBlockEntity::new, ModBlocks.TRAFFIC_LIGHT_CONTROLLER.get()
         ).build(null)
     );

    public static void init() {
        // Initialise the static class variables.
    }
}
