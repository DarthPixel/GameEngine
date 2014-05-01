package de.slikey.game.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerEvent;

import de.slikey.game.player.PlayerAttribute;

public abstract class PlayerAttributeEvent extends PlayerEvent implements Cancellable{

	protected PlayerAttribute attribute;
	protected boolean cancelled;

	public PlayerAttributeEvent(final Player player, final PlayerAttribute attribute) {
		super(player);
		this.attribute = attribute;
		this.cancelled = false;
	}

	/**
	 * Gets the PlayerAttribute
	 * 
	 * @return string join message
	 */
	public PlayerAttribute getAttribute() {
		return attribute;
	}
	
	/**
	 * Sets the PlayerAttribute
	 * 
	 * @param attribute New PlayerAttribute
	 */
	public void setAttribute(final PlayerAttribute attribute) {
		this.attribute = attribute;
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