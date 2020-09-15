package me.endureblackout.EndureCore.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.endureblackout.EndureCore.EndureCore;

public class Spawn implements CommandExecutor {
	
	EndureCore core;
	
	public Spawn(EndureCore core) {
		this.core = core;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player && cmd.getName().equalsIgnoreCase("spawn")) {
			Player p = (Player) sender;
			
			if(args.length == 0) {
				YamlConfiguration config = (YamlConfiguration) core.getConfig();
				
				if(!config.contains("spawn")) {
					p.sendMessage(ChatColor.RED + "Sorry, but there is not currently a spawn location set.");
					
					if(p.hasPermission("EndureCore.setspawn")) {
						p.sendMessage(ChatColor.YELLOW + "To set a spawn, use the /spawnset command.");
					}
					
					return true;
				}
				
				World spawnWorld = Bukkit.getWorld(config.getString("spawn.world"));
				int x = config.getInt("spawn.x");
				int y = config.getInt("spawn.y");
				int z = config.getInt("spawn.z");
				float pitch = config.getLong("spawn.pitch");
				float yaw = config.getLong("spawn.yaw");
				
				Location spawnLocation = new Location(spawnWorld, x, y, z);
				spawnLocation.setPitch(pitch);
				spawnLocation.setYaw(yaw);
				
				p.teleport(spawnLocation);
				p.sendMessage(ChatColor.GOLD + "Whoosh! You have been teleported to spawn!");
			}
		}
		
		return true;
	}
	
}
