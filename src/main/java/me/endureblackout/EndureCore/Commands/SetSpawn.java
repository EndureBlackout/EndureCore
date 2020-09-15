package me.endureblackout.EndureCore.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.endureblackout.EndureCore.EndureCore;

public class SetSpawn implements CommandExecutor {

	EndureCore core;
	
	public SetSpawn(EndureCore core) {
		this.core = core;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(p.hasPermission("EndureCore.setspawn")) {				
				String worldName = p.getWorld().getName();
				int x = p.getLocation().getBlockX();
				int y = p.getLocation().getBlockY();
				int z = p.getLocation().getBlockZ();
				float pitch = p.getLocation().getPitch();
				float yaw = p.getLocation().getYaw();
				
				core.getConfig().set("spawn.world", worldName);
				core.getConfig().set("spawn.x", x);
				core.getConfig().set("spawn.y", y);
				core.getConfig().set("spawn.z", z);
				core.getConfig().set("spawn.pitch", pitch);
				core.getConfig().set("spawn.yaw", yaw);
				
				core.saveConfig();
				
				p.sendMessage(ChatColor.GREEN + "You have successfully set the spawn location for the server!");
			} else {
				p.sendMessage(ChatColor.RED + "You don't have permission to perform this command!");
			}
		}
		
		return true;
	}
	
	
}
