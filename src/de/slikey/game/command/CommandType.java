package de.slikey.game.command;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;

public enum CommandType {

	PLAYER(Player.class), // Normaler Spieler
	CONSOLE(ConsoleCommandSender.class), // Konsole
	BLOCK(BlockCommandSender.class), // Kommando-Block
	REMOTE(RemoteConsoleCommandSender.class); // RCON Kommando

	private Class<? extends CommandSender> sender;

	private CommandType(Class<? extends CommandSender> sender) {
		this.sender = sender;
	}

	public Class<? extends CommandSender> getSenderType() {
		return sender;
	}

}
