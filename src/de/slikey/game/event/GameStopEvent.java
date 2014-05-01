package de.slikey.game.event;

import org.bukkit.event.HandlerList;

import de.slikey.game.Game;

public class GameStopEvent extends GameEvent {

	private static final HandlerList handlers = new HandlerList();

	public GameStopEvent(Game game) {
		super(game);
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
