package de.slikey.game.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import de.slikey.game.arena.Arena;

/**
 * Called when a player joins an arena
 * 
 * @author Kevin Carstens
 * @since 01.05.2014
 */
public class ArenaJoinEvent extends PlayerEvent {

	private static final HandlerList handlers = new HandlerList();
	private Arena arena;

	public ArenaJoinEvent(final Player player, final Arena arena) {
		super(player);
		this.arena = arena;
	}

	/**
	 * Gets the arena, which the player joined
	 * 
	 * @return string join message
	 */
	public Arena getArena() {
		return arena;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}