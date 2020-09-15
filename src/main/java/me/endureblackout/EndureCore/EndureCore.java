package me.endureblackout.EndureCore;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.endureblackout.EndureCore.Commands.GamemodeCommand;
import me.endureblackout.EndureCore.Commands.SetSpawn;
import me.endureblackout.EndureCore.Commands.Spawn;
import me.endureblackout.EndureCore.Menus.GamemodeMenu;

public class EndureCore extends JavaPlugin {
	
    @Override
    public void onEnable() {
    	if (!getDataFolder().exists()) {
    		getConfig().options().copyDefaults(true);
    	}
    	
    	YamlConfiguration config = (YamlConfiguration) getConfig();
    	
    	// Register Events Here
    	getServer().getPluginManager().registerEvents(new GamemodeMenu(this), this);
    	
    	// Register Commands Here
    	getCommand("spawn").setExecutor(new Spawn(this));
    	getCommand("setspawn").setExecutor(new SetSpawn(this));
    	getCommand("gamemode").setExecutor(new GamemodeCommand(this));
    }
}
