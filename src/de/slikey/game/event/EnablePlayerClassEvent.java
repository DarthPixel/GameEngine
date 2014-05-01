package de.slikey.game.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import de.slikey.game.player.PlayerClass;

/**
 * Called when a PlayerClass is enabled for a player.
 * 
 * @author Kevin Carstens
 * @since 01.05.2014
 */
public class EnablePlayerClassEvent extends PlayerClassEvent {

	private static final HandlerList handlers = new HandlerList();

	public EnablePlayerClassEvent(final Player player, final PlayerClass playerclass) {
		super(player, playerclass);
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}