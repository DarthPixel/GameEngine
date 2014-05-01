package de.slikey.game.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import de.slikey.game.player.PlayerAttribute;

/**
 * Called when a PlayerAttribute is added to a player's PlayerAttributeStore
 * 
 * @author Kevin Carstens
 * @since 01.05.2014
 */
public class AddPlayerAttributeEvent extends PlayerAttributeEvent {

	private static final HandlerList handlers = new HandlerList();

	public AddPlayerAttributeEvent(final Player player, final PlayerAttribute attribute) {
		super(player, attribute);
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}