package tk.minersonline.mineways.api.event;

import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Events {
	public static abstract class EventData {

	}

	public static class Event<T extends EventData> {
		private final EventData data;

		public Event(EventData data) {
			this.data = data;
		}

		public EventData getData() {
			return data;
		}
	}

	public static class BlockBreakEvent extends EventData {
		private final World world;
		private final BlockPos pos;
		private final BlockState state;
		private final ServerPlayerEntity player;

		public BlockBreakEvent(World world, BlockPos pos, BlockState state, ServerPlayerEntity player) {
			this.world = world;
			this.pos = pos;
			this.state = state;
			this.player = player;
		}

		public World getWorld() {
			return world;
		}

		public BlockPos getPos() {
			return pos;
		}

		public BlockState getState() {
			return state;
		}

		public ServerPlayerEntity getPlayer() {
			return player;
		}
	}
}
