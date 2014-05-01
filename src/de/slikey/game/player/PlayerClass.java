package de.slikey.game.player;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import de.slikey.game.event.DisablePlayerClassEvent;
import de.slikey.game.event.EnablePlayerClassEvent;
import de.slikey.game.item.ItemBase;

/**
 * PlayerClass is a possibility to manage a players cooldowns and inventorys. It
 * is possible to set Items and register Events inside this class.
 * {@link #enable(Plugin)}.
 * 
 * @author Kevin Carstens
 * @since 01.05.2014
 */
public abstract class PlayerClass implements Listener {

	public static final String META_IDENTIFIER = "gClass";

	/**
	 * Boolean which is flagged if Listener is registered
	 */
	private boolean enabled;

	/**
	 * Player owning this object
	 */
	protected final Player player;

	/**
	 * List of all items contained in this object.
	 */
	protected final List<ItemBase> items;

	/**
	 * List of all entities / pets contained in this object.
	 */
	protected final List<Entity> pets;

	/**
	 * Constructs a new PlayerClass, which is owned by a Player.
	 * 
	 * @param player
	 *            Player owning the class
	 */
	public PlayerClass(Player player) {
		Validate.notNull(player, "Player cannot be null.");
		enabled = false;
		this.player = player;
		items = new ArrayList<ItemBase>();
		pets = new ArrayList<Entity>();
	}

	/**
	 * Add an Entity as pet of the player.
	 * 
	 * @param entity
	 *            Pet
	 */
	public final synchronized void addPet(Entity entity) {
		Validate.notNull(entity, "Entity cannot be null.");
		pets.add(entity);
	}

	/**
	 * Applies the PlayerClass to a player's metadata.
	 * 
	 * @param plugin
	 *            Owning plugin
	 */
	public final void apply(Plugin plugin) {
		if (player.hasMetadata(META_IDENTIFIER)) {
			Object obj = player.getMetadata(META_IDENTIFIER).get(0).value();
			Validate.isTrue(obj instanceof PlayerClass, "Metadata (" + META_IDENTIFIER
					+ ") is no instance of PlayerClass!");
			((PlayerClass) player).remove(plugin);
		}
		player.setMetadata(META_IDENTIFIER, new FixedMetadataValue(plugin, this));
	}

	/**
	 * Removes a this object from Metadata of player.
	 * 
	 * @param plugin
	 *            Owning plugin
	 */
	public final void remove(Plugin plugin) {
		Validate.isTrue(player.hasMetadata(META_IDENTIFIER), "Player has no playerclass applied.");
		Object obj = player.getMetadata(META_IDENTIFIER).get(0).value();
		Validate.isTrue(obj instanceof PlayerClass, "Metadata (" + META_IDENTIFIER + ") is no instance of PlayerClass!");
		Validate.isTrue(obj == this,
				"PlayerClass cannot be remove from another PlayerClass. Call remove() on active PlayerClass");
		player.removeMetadata(META_IDENTIFIER, plugin);
	}

	/**
	 * Disables the object as Listener, if called event is not cancelled.
	 */
	public final void disable() {
		DisablePlayerClassEvent event = new DisablePlayerClassEvent(player, this);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled())
			return;
		HandlerList.unregisterAll(this);
		enabled = false;
		onDisable();
	}

	/**
	 * Enables the object as Listener, if called event is not cancelled.
	 * 
	 * @param plugin
	 *            Owning plugin
	 */
	public final void enable(Plugin plugin) {
		Validate.notNull(plugin, "Plugin cannot be null!");
		EnablePlayerClassEvent event = new EnablePlayerClassEvent(player, this);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled())
			return;
		enabled = true;
		Bukkit.getPluginManager().registerEvents(this, plugin);
		onEnable();
	}

	/**
	 * Returns the armor of the player.
	 * 
	 * @return Armor of player
	 */
	public ItemStack[] getArmor() {
		return null;
	}

	/**
	 * Returns all Entities / Pets that are bound to this object.
	 * 
	 * @return List of pets
	 */
	public final synchronized List<Entity> getPets() {
		return pets;
	}

	/**
	 * Returns, if this object is registered as Listener.
	 * 
	 * @return TRUE, if registered.
	 */
	public final boolean isEnabled() {
		return enabled;
	}

	/**
	 * Called after successful disabling.
	 */
	public abstract void onDisable();

	/**
	 * Called after successful enabling.
	 */
	public abstract void onEnable();

	/**
	 * Removes an entity / pet from this object.
	 * 
	 * @param entity
	 *            Pet of player
	 */
	public final synchronized void removePet(Entity entity) {
		entity.remove();
		pets.remove(entity);
	}

	/**
	 * Removes all pets from an object.
	 */
	public final synchronized void removePets() {
		for (Entity entity : pets)
			entity.remove();
		pets.clear();
	}

}
