package de.slikey.game.player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import de.slikey.game.event.AddPlayerAttributeEvent;
import de.slikey.game.event.RemovePlayerAttributeEvent;
import de.slikey.game.exception.PlayerAttributeStoreOverwriteException;

/**
 * A PlayerAttributeStore stores PlayerAttributes. They help identifying if a
 * player matches certain conditions. It is sort of an replacement for the
 * Metadata of the player.
 * 
 * @author Kevin Carstens
 * @since 30.04.2014
 */
public class PlayerAttributeStore {

	/**
	 * Metadata Identifier to save the store in the players Metadata.
	 */
	public static final String META_IDENTIFIER = "gAttribute";

	/**
	 * Owner of the store.
	 */
	protected final Player player;

	/**
	 * List of all attributes, that are added to the store.
	 */
	protected List<PlayerAttribute> attributes;

	/**
	 * Constructs a PlayerAttributeStore with a player, owning the store.
	 * 
	 * @param player
	 *            Owner of the store
	 */
	public PlayerAttributeStore(final Player player) {
		this.player = player;
		attributes = new ArrayList<PlayerAttribute>(2);
	}

	/**
	 * Clears the store from all attributes, while calling events, that are
	 * cancellable. If you want to force cleaning the store use
	 * {@link #forceClear()}.
	 */
	public final synchronized void clear() {
		for (Iterator<PlayerAttribute> iter = attributes.iterator(); iter.hasNext();) {
			RemovePlayerAttributeEvent event = new RemovePlayerAttributeEvent(player, iter.next());
			Bukkit.getPluginManager().callEvent(event);
			if (!event.isCancelled())
				iter.remove();
		}
	}

	/**
	 * Clears the store from all attribute, without calling events.
	 */
	public final synchronized void forceClear() {
		attributes.clear();
	}

	/**
	 * Checks if store contains the instance of a PlayerAttribute.
	 * 
	 * @param attribute
	 *            PlayerAttribute to check
	 * @return TRUE, if the instance of PlayerAttribute is found
	 */
	public final synchronized boolean has(PlayerAttribute attribute) {
		return attributes.contains(attribute);
	}

	/**
	 * Checks if store contains an instance of a PlayerAttribute.
	 * 
	 * @param attribute
	 *            Class of PlayerAttribute to check
	 * @return TRUE, if an instance of the PlayerAttribute is found
	 */
	public final synchronized boolean has(Class<? extends PlayerAttribute> attribute) {
		for (PlayerAttribute attr : attributes)
			if (attribute.isInstance(attr))
				return true;
		return false;
	}

	/**
	 * Adds a PlayerAttribute to the store, while calling an event.
	 * 
	 * @param attribute
	 */
	public final synchronized void add(PlayerAttribute attribute) {
		AddPlayerAttributeEvent event = new AddPlayerAttributeEvent(player, attribute);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled())
			attributes.add(attribute);
	}

	/**
	 * Removes all instances of a given PlayerAttribute class, while calling
	 * events.
	 * 
	 * @param attribute
	 */
	public final synchronized void remove(Class<? extends PlayerAttribute> attribute) {
		for (Iterator<PlayerAttribute> iter = attributes.iterator(); iter.hasNext();) {
			PlayerAttribute attr = iter.next();
			if (attribute.isInstance(attr)) {
				RemovePlayerAttributeEvent event = new RemovePlayerAttributeEvent(player, attr);
				Bukkit.getPluginManager().callEvent(event);
				if (!event.isCancelled())
					iter.remove();
			}
		}
	}

	/**
	 * Removes the instance of a given PlayerAttribute.
	 * 
	 * @param attribute
	 *            PlayerAttribute to remove
	 */
	public final synchronized void remove(PlayerAttribute attribute) {
		if (!has(attribute))
			return;
		RemovePlayerAttributeEvent event = new RemovePlayerAttributeEvent(player, attribute);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled())
			attributes.remove(attribute);
	}

	/**
	 * Applies a store to a player.
	 * 
	 * @param plugin
	 *            Plugin, which owns the store
	 * @throws PlayerAttributeStoreOverwriteException
	 *             if player already has a store applied.
	 */
	public final void apply(Plugin plugin) {
		if (player.hasMetadata(META_IDENTIFIER))
			throw new PlayerAttributeStoreOverwriteException("Player (" + player.getName()
					+ ") already has a PlayerAttributeStore.");
		player.setMetadata(META_IDENTIFIER, new FixedMetadataValue(plugin, this));
	}
}
