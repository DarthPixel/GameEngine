package de.slikey.game.event;

import org.bukkit.event.HandlerList;

import de.slikey.game.item.ItemBase;

/**
 * Called when an item is going to be enabled.
 * 
 * @author Kevin Carstens
 * @since 01.05.2014
 */
public class EnableItemEvent extends ItemEvent {

	private static final HandlerList handlers = new HandlerList();

	public EnableItemEvent(ItemBase item) {
		super(item);
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
