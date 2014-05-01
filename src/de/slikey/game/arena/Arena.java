package de.slikey.game.arena;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import de.slikey.game.event.ArenaJoinEvent;
import de.slikey.game.event.ArenaLeaveEvent;

/**
 * Arena, that handles player teleporting and location bounding.
 * 
 * @author Kevin Carstens
 * @since 29.04.2014
 * @version 1.0
 */
public abstract class Arena {

	/**
	 * Metadata Identifier to save the arena in the players Metadata
	 */
	public static final String META_IDENTIFIER = "gArena";

	/**
	 * ID of the Arena, to allow saving as unique file.
	 */
	protected final int id;

	/**
	 * Plugin which uses the API
	 */
	protected final Plugin plugin;

	/**
	 * List of players that are in the arena
	 */
	protected final List<Player> players;

	/**
	 * World, which contains the arena
	 */
	protected final World world;

	/**
	 * Location to spawn players at
	 */
	protected Location spawn;

	/**
	 * Creates a new Arena which is able to hold players.
	 * 
	 * @param plugin
	 *            Plugin which uses the API
	 */
	public Arena(int id, Plugin plugin, World world, Location spawn) {
		this.id = id;
		this.plugin = plugin;
		this.world = world;
		players = new ArrayList<Player>();
	}

	/**
	 * Adds a player to the arena and calls an ArenaJoinEvent.
	 * 
	 * @param player
	 *            Player to add
	 */
	public final synchronized void addPlayer(Player player) {
		// Check if player is already in arena
		if (player.hasMetadata(META_IDENTIFIER)) {
			// Get Metdata for the identifier
			List<MetadataValue> values = player.getMetadata(META_IDENTIFIER);
			// Cycle through values
			for (MetadataValue value : values)
				// Check if value is instance of Arena
				if (value.value() instanceof Arena)
					// Remove player from arena
					((Arena) value.value()).removePlayer(player);
		}
		// Add player to this arena
		players.add(player);
		// Update Metadata of player
		player.setMetadata(META_IDENTIFIER, new FixedMetadataValue(plugin, this));

		// Call the ArenaJoinEvent
		Bukkit.getPluginManager().callEvent(new ArenaJoinEvent(player, this));
	}

	/**
	 * Removes a player from the arena and calls an ArenaLeaveEvent.
	 * 
	 * @param player
	 *            Player to remove
	 */
	public final synchronized void removePlayer(Player player) {
		// Remove players Metadata entry
		player.removeMetadata(META_IDENTIFIER, plugin);
		// Remove player from arena
		players.remove(player);

		// Call the ArenaLeaveEvent
		Bukkit.getPluginManager().callEvent(new ArenaLeaveEvent(player, this));
	}

	/**
	 * Gets a list of all players in this arena.
	 * 
	 * @return
	 */
	public final synchronized Player[] getPlayers() {
		return (Player[]) players.toArray();
	}

	/**
	 * Checks if a Location is inside the Arena.
	 * 
	 * @param location
	 *            Location to check
	 * @return TRUE, if Location is in Arena
	 */
	public boolean contains(Location location) {
		return location.getWorld() == world && contains(location.getX(), location.getY(), location.getZ());
	}

	/**
	 * Checks if a Vector is inside the Arena
	 * 
	 * @param vector
	 *            Vector to check
	 * @return TRUE, if Vector is in Arena
	 */
	public boolean contains(Vector vector) {
		return contains(vector.getX(), vector.getY(), vector.getZ());
	}

	/**
	 * Checks if x, y and z coordinate is in Arena.
	 * 
	 * @param x
	 *            Position on x-axis
	 * @param y
	 *            Position on y-axis
	 * @param z
	 *            Position on z-axis
	 * @return TRUE, if coordinates are in Arena
	 */
	public abstract boolean contains(double x, double y, double z);

	/**
	 * Saves the Arena to a file
	 * 
	 * @param id
	 */
	public abstract void save() throws IOException;

}
