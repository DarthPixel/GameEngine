package de.slikey.game.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import de.slikey.game.Game;

public class GamePauseEvent extends GameEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled = false;

	protected boolean paused;

	public GamePauseEvent(Game game, boolean paused) {
		super(game);
		paused = true;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public boolean isPaused() {
		return paused;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
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
