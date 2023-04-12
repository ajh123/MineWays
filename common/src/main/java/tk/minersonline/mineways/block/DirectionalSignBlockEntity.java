package tk.minersonline.mineways.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.util.math.BlockPos;
import tk.minersonline.mineways.setup.ModBlockEntities;

import java.lang.reflect.Field;

public class DirectionalSignBlockEntity extends SignBlockEntity {

	public DirectionalSignBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state);
		try {
			Field hack = BlockEntity.class.getDeclaredField("type");
			hack.setAccessible(true);
			hack.set(this, ModBlockEntities.DIRECTIONAL_SIGN_BLOCK_ENTITY.get());
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}


}
