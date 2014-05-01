package de.slikey.game;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import de.slikey.game.event.GamePauseEvent;
import de.slikey.game.event.GameStartEvent;
import de.slikey.game.event.GameStopEvent;
import de.slikey.game.procedure.Loop;

/**
 * The Game class is meant to be extended or used as is. It might be senseful to
 * override methods like onStart(), onPause() and onStop(). Start a game after
 * setting the Loop.
 * 
 * @author Kevin Carstens
 * @since 02.05.2014
 */
public class Game {

	protected final JavaPlugin plugin;

	public GameState gamestate;
	public Loop loop;
	private BukkitTask task;

	public Game(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	public final void start() {
		Validate.notNull(loop, "Loop cannot be null!");
		task = Bukkit.getScheduler().runTaskTimer(plugin, loop, 0, 1);
		Bukkit.getPluginManager().callEvent(new GameStartEvent(this));
	}

	public final void pause(boolean paused) {
		GamePauseEvent event = new GamePauseEvent(this, paused);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled())
			return;
		loop.setPause(event.isPaused());
		onPause(event.isPaused());
	}

	public final void stop() {
		Validate.notNull(task, "Task cannot be null!");
		task.cancel();
		Bukkit.getPluginManager().callEvent(new GameStopEvent(this));
	}

	protected void onStart() {

	}

	protected void onPause(boolean paused) {

	}

	protected void onStop() {

	}

	public GameState getGameState() {
		return gamestate;
	}

}
