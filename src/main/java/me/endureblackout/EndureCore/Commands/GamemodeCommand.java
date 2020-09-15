package me.endureblackout.EndureCore.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.endureblackout.EndureCore.EndureCore;
import me.endureblackout.EndureCore.Menus.GamemodeMenu;

public class GamemodeCommand implements CommandExecutor {

	EndureCore core;

	public GamemodeCommand(EndureCore core) {
		this.core = core;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (cmd.getName().equalsIgnoreCase("gamemode")) {
				if(p.hasPermission("EndureCore.gamemode")) {
					GamemodeMenu menu = new GamemodeMenu();
					
					p.openInventory(menu.getMenu());
				}
			}
		}
		return true;
	}
}
