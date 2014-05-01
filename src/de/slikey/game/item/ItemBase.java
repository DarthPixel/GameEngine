package de.slikey.game.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import de.slikey.game.event.DisableItemEvent;
import de.slikey.game.event.EnableItemEvent;

public abstract class ItemBase extends ItemStack implements Listener {

	private boolean enabled = false;;

	public ItemBase() {
	}

	public ItemBase(ItemStack stack) throws IllegalArgumentException {
		super(stack);
	}

	public ItemBase(Material type) {
		super(type);
	}

	public ItemBase(Material type, int amount) {
		super(type, amount);
	}

	public ItemBase(Material type, int amount, short damage) {
		super(type, amount, damage);
	}

	public final void disable() {
		DisableItemEvent event = new DisableItemEvent(this);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled())
			return;
		HandlerList.unregisterAll(this);
		onDisable();
	}

	public final void enable(Plugin plugin) {
		EnableItemEvent event = new EnableItemEvent(this);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled())
			return;
		Bukkit.getPluginManager().registerEvents(this, plugin);
		onEnable();
	}

	public final boolean isEnabled() {
		return enabled;
	}

	protected void onDisable() {
	}

	protected void onEnable() {
	}

}
