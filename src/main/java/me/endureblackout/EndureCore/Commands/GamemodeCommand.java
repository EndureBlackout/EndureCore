package me.endureblackout.EndureCore.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.endureblackout.EndureCore.EndureCore;
import me.endureblackout.EndureCore.Menus.GamemodeMenu;
import me.endureblackout.EndureCore.Utilities.GamemodeSwitcher;

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
					if(args.length == 1) {
						try {
							GamemodeSwitcher gmSwitch = new GamemodeSwitcher();
							
							int modeNum = Integer.parseInt(args[0]);
							
							gmSwitch.setGamemode(modeNum, p);
							
							return true;
						} catch (NumberFormatException e) {
							p.sendMessage(ChatColor.RED + "Usage: /gamemode <int>");
							
							return true;
						}
					}
					
					GamemodeMenu menu = new GamemodeMenu(core);
					
					p.openInventory(menu.getMenu());
				}
			}
			
		}
		return true;
	}
}
