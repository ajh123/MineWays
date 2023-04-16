package tk.minersonline.mineways.setup;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.*;
import net.minecraft.block.Block;
import tk.minersonline.mineways.api.event.EventHandler;
import tk.minersonline.mineways.api.event.Events;

public class ModEvents {
	public static void init() {
		BlockEvent.BREAK.register((world, pos, state, player, xp) -> {
			Block block = state.getBlock();
			if (block instanceof EventHandler handler) {
				boolean cancel = handler.handleEvent(new Events.Event<>(
						new Events.BlockBreakEvent(world, pos, state, player)
				));
				if (cancel) {
					return EventResult.interrupt(true);
				}
			}
			return EventResult.pass();
		});
	}
}
