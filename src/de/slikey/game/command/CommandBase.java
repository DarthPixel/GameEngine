package de.slikey.game.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * CommandBase is an extended CommandExecutor from the BukkitAPI. It handles
 * permission, sender and activation checks.
 * 
 * @author Kevin Carstnes
 * @since 01.05.2014
 */
public abstract class CommandBase implements CommandExecutor {

	public static final String DECLINE_COMMANDTYPE = "You cannot run that command as ";
	public static final String DECLINE_PERMISSION = "You don't have the permission!";
	public static final String DECLINE_DISABLED = "Command is currently disabled.";

	protected final JavaPlugin plugin;
	protected final PluginCommand command;
	protected final List<CommandType> commandtype;

	protected boolean disabled;

	public CommandBase(JavaPlugin plugin, PluginCommand command) {
		this.plugin = plugin;
		this.command = command;
		this.commandtype = new ArrayList<CommandType>(4);
		disabled = true;
	}

	/**
	 * Checks if a CommandSender is allowed to use the command.
	 * 
	 * @param sender
	 *            CommandSender issued the command
	 * @param opAccess
	 *            Should an OP have default access?
	 * @return TRUE, wenn das Kommando ausgeführt wird.
	 */
	protected boolean checkAccess(CommandSender sender, boolean opAccess) {
		if (disabled)
			declineDisabled(sender);

		for (CommandType ct : CommandType.values()) {
			if (ct.getSenderType().isInstance(sender)) {
				if (!commandtype.contains(ct)) {
					declineCommandType(sender);
					return false;
				}
				break;
			}
		}

		if (command.getPermission().isEmpty())
			return true;
		if (sender instanceof Player) {
			if ((opAccess && !sender.isOp()) && !sender.hasPermission(command.getPermission())) {
				declinePermission(sender);
				return false;
			}
		}
		return true;
	}

	/**
	 * Responds, that the sender is not on the list of possible CommandSenders.
	 * 
	 * @param sender
	 */
	protected void declineCommandType(CommandSender sender) {
		sender.sendMessage(DECLINE_COMMANDTYPE + sender.getName());
	}

	/**
	 * Responds, that the sender has not the permission to run the command.
	 * 
	 * @param sender
	 */
	protected void declinePermission(CommandSender sender) {
		sender.sendMessage(DECLINE_PERMISSION);
	}

	/**
	 * Responds, that the command is currently disabled
	 * 
	 * @param sender
	 */
	protected void declineDisabled(CommandSender sender) {
		sender.sendMessage(DECLINE_DISABLED);
	}

	/**
	 * Returns the label of the command.
	 * 
	 * @return Name des Kommandos
	 */
	public final String getLabel() {
		return command.getLabel();
	}

	/**
	 * Sets this object as CommandExector
	 * 
	 * @return this
	 */
	public final CommandBase register() {
		command.setExecutor(this);
		return this;
	}

	/**
	 * Enables Command
	 * 
	 * @return this
	 */
	public final CommandBase enable() {
		disabled = false;
		return this;
	}

	/**
	 * Disables Command
	 * 
	 * @return this
	 */
	public final CommandBase disable() {
		disabled = true;
		return this;
	}

}
