package de.slikey.game.arena;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import de.slikey.game.exception.InvalidArenaConfigFileException;

/**
 * {@link Arena} in the geometry of a cuboid
 * 
 * @author Kevin Carstens
 * @since 29.04.2014
 */
public class CuboidArena extends Arena {

	private static final long serialVersionUID = 1L;

	protected double minX, minY, minZ, maxX, maxY, maxZ;

	public CuboidArena(int id, Plugin plugin, World world, Location spawn) {
		super(id, plugin, world, spawn);
	}

	public static final CuboidArena load(Plugin plugin, int id) throws InvalidArenaConfigFileException {
		// Get path of file
		File file = new File(plugin.getDataFolder() + File.separator + "arena" + File.separator + "arena_" + id
				+ ".yml");
		// Interpret file as YAML
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		// Check if all keys are set.
		Collection<String> keys = Arrays.asList(new String[] { "id", "version", "coordinates", "coordinates.min",
				"coordinates.min.x", "coordinates.min.y", "coordinates.min.z", "coordinates.max.x",
				"coordinates.max.y", "coordinates.max.z", "coordinates.spawn", "coordinates.spawn.x",
				"coordinates.spawn.y", "coordinates.spawn.z", "coordinates.spawn.pitch", "coordinates.spawn.yaw" });
		Collection<String> configKeys = cfg.getConfigurationSection("").getKeys(true);
		// Throw Exception if key is not found
		for (String s : keys)
			if (!configKeys.contains(s))
				throw new InvalidArenaConfigFileException("Key not set: " + s);

		// Checking version id
		if (cfg.getLong("version") != serialVersionUID)
			throw new InvalidArenaConfigFileException("Incompatible version");

		// Get World
		World world = Bukkit.getWorld(cfg.getString("world"));
		if (world == null)
			throw new InvalidArenaConfigFileException("World not found");

		// Get Spawn
		Location spawn = new Location(world, cfg.getDouble("spawn.x"), cfg.getDouble("spawn.y"),
				cfg.getDouble("spawn.z"), (float) cfg.getDouble("spawn.yaw"), (float) cfg.getDouble("spawn.pitch"));

		// Setting up instance of arena
		CuboidArena arena = new CuboidArena(id, plugin, world, spawn);

		// Get coordinates for the bounds
		arena.minX = cfg.getInt("coordinates.min.x");
		arena.minY = cfg.getInt("coordinates.min.y");
		arena.minZ = cfg.getInt("coordinates.min.z");
		arena.maxX = cfg.getInt("coordinates.max.x");
		arena.maxY = cfg.getInt("coordinates.max.y");
		arena.maxZ = cfg.getInt("coordinates.max.z");

		return arena;
	}

	/**
	 * Saves the arena to a 
	 */
	@Override
	public void save() throws IOException {
		// Set up file path
		File file = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "arena" + File.separator
				+ "arena_" + id + ".yml");
		// Interpret file as YAML
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		// Save ID
		cfg.set("id", id);
		// Set version to ensure correct parsing
		cfg.set("version", serialVersionUID);
		// Set type of arena
		cfg.set("type", ArenaType.CUBOID);
		// Set world
		cfg.set("world", world.getName());
		// Set coordinates
		cfg.set("coordinates.min.x", minX);
		cfg.set("coordinates.min.y", minY);
		cfg.set("coordinates.min.z", minZ);
		cfg.set("coordinates.max.x", maxX);
		cfg.set("coordinates.max.y", maxY);
		cfg.set("coordinates.max.z", maxZ);
		// Set spawn
		cfg.set("coordinates.spawn.x", spawn.getX());
		cfg.set("coordinates.spawn.y", spawn.getY());
		cfg.set("coordinates.spawn.z", spawn.getZ());
		cfg.set("coordinates.spawn.pitch", spawn.getPitch());
		cfg.set("coordinates.spawn.yaw", spawn.getYaw());

		// Save file
		cfg.save(file);
	}

	@Override
	public boolean contains(double x, double y, double z) {
		if (x < minX || x > maxX)
			return false;
		if (y < minY || y > maxY)
			return false;
		if (z < minZ || z > maxZ)
			return false;
		return true;
	}

}
