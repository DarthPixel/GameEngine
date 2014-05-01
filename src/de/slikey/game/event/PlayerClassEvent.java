package de.slikey.game.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerEvent;

import de.slikey.game.player.PlayerClass;

public abstract class PlayerClassEvent extends PlayerEvent implements Cancellable{

	protected PlayerClass playerclass;
	protected boolean cancelled;

	public PlayerClassEvent(final Player player, final PlayerClass playerclass) {
		super(player);
		this.playerclass = playerclass;
		this.cancelled = false;
	}

	/**
	 * Gets the PlayerAttribute
	 * 
	 * @return string join message
	 */
	public PlayerClass getPlayerClass() {
		return playerclass;
	}
	
	/**
	 * Sets the PlayerAttribute
	 * 
	 * @param attribute New PlayerAttribute
	 */
	public void setPlayerClass(final PlayerClass playerclass) {
		this.playerclass = playerclass;
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