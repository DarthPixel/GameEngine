package de.slikey.game.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import de.slikey.game.item.ItemBase;

public abstract class ItemEvent extends Event implements Cancellable {

	protected ItemBase item;
	protected boolean cancelled;

	public ItemEvent(final ItemBase item) {
		super();
		this.cancelled = false;
	}

	/**
	 * Gets the Item
	 * 
	 * @return string join message
	 */
	public ItemBase getItem() {
		return item;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}