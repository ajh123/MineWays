package tk.minersonline.mineways.api.event;

public interface EventHandler {
	boolean handleEvent(Events.Event<?> event);
}
