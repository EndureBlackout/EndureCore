package me.endureblackout.EndureCore;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.endureblackout.EndureCore.Commands.GamemodeCommand;
import me.endureblackout.EndureCore.Commands.RankCommand;
import me.endureblackout.EndureCore.Commands.SetSpawn;
import me.endureblackout.EndureCore.Commands.Spawn;
import me.endureblackout.EndureCore.Menus.GamemodeMenu;
import me.endureblackout.EndureCore.Utilities.PermissionsHandler;

public class EndureCore extends JavaPlugin {

  public static EndureCore core;

  @Override
  public void onEnable() {
    core = this;
    
    File file = new File(getDataFolder(), "/");
    File ranksFile = new File(getDataFolder(), "ranks.yml");
    File playerRanks = new File(getDataFolder(), "playerRanks.yml");

    if (!file.exists()) {
      file.mkdir();

      try {
        ranksFile.createNewFile();
        playerRanks.createNewFile();
        saveDefaultConfig();
      } catch (IOException e) {
        e.printStackTrace();
      }
      
      YamlConfiguration ranksConfig = YamlConfiguration.loadConfiguration(ranksFile);
      YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerRanks);
      
      if (file.exists() && !ranksFile.exists()) {
        try {
          ranksFile.createNewFile();
        } catch (IOException e) {
          e.printStackTrace();
        }
        
        if (ranksConfig.getConfigurationSection("ranks") == null) {
          ranksConfig.createSection("ranks");

          try {
            ranksConfig.save(ranksFile);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
      
      if (file.exists() && !playerRanks.exists()) {
        try {
          playerRanks.createNewFile();
        } catch (IOException e) {
          e.printStackTrace();
        }
        if (playerConfig.getConfigurationSection("players") == null) {
          playerConfig.createSection("players");

          try {
            playerConfig.save(playerRanks);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }

    YamlConfiguration config = (YamlConfiguration) getConfig();

    // Register Events Here
    getServer().getPluginManager().registerEvents(new GamemodeMenu(this), this);
    getServer().getPluginManager().registerEvents(new PermissionsHandler(), this);

    // Register Commands Here
    getCommand("spawn").setExecutor(new Spawn(this));
    getCommand("setspawn").setExecutor(new SetSpawn(this));
    getCommand("gamemode").setExecutor(new GamemodeCommand(this));
    getCommand("rank").setExecutor(new RankCommand(this));
  }
  
  @Override
  public void onDisable() {
    PermissionsHandler.clearCache();
  }
}
