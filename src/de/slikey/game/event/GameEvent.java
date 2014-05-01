package de.slikey.game.event;

import org.bukkit.event.Event;

import de.slikey.game.Game;

public abstract class GameEvent extends Event {

	protected final Game game;
	protected boolean cancelled;

	public GameEvent(final Game game) {
		super();
		this.game = game;
	}

	/**
	 * Gets the Game
	 * 
	 * @return string join message
	 */
	public Game getGame() {
		return game;
	}

}