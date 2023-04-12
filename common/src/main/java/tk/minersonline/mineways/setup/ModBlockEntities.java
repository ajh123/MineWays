package tk.minersonline.mineways.setup;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SignBlockEntity;
import tk.minersonline.mineways.block.DirectionalSignBlockEntity;

public class ModBlockEntities {
    public static final RegistrySupplier<BlockEntityType<SignBlockEntity>> DIRECTIONAL_SIGN_BLOCK_ENTITY = Registration.BLOCKS_ENTITIES.register(
        "directional_sign",
        () -> BlockEntityType.Builder.create(
            (pos, state) -> (SignBlockEntity) new DirectionalSignBlockEntity(pos, state),
            ModBlocks.OAK_DIRECTIONAL_SIGN.get()
        ).build(null)
    );

    public static void init() {
        // Initalise the static class variables.
    }
}
