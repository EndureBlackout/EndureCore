package me.endureblackout.EndureCore.Utilities;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.endureblackout.EndureCore.EndureCore;

public class RanksHandler {
  
  private static HashMap<UUID, String> playerRanks = new HashMap<UUID, String>();
  
  public static String getUserRank(Player p) {
    String rank = null;
    
    if(playerRanks.containsKey(p.getUniqueId())) {
      rank = playerRanks.get(p.getUniqueId());
    } else {
      File file = new File(EndureCore.core.getDataFolder(), "playerRanks.yml");
      YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(file);
      
      for(String uuid : playerConfig.getConfigurationSection("ranks").getKeys(false)) {
        if(p.getUniqueId().equals(UUID.fromString(uuid))) {
          rank = playerConfig.getString("ranks." + uuid + ".rank");
          playerRanks.put(p.getUniqueId(), rank);
        }
      }
    }
    
    return rank;
  }
  
  public static void setUserRank(String username, String rank) {
    Player p = Bukkit.getServer().getPlayer(username);
    
    File playerFile = new File(EndureCore.core.getDataFolder(), "playerRanks.yml");
    YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
    
    File ranksFile = new File(EndureCore.core.getDataFolder(), "ranks.yml");
    YamlConfiguration ranksConfig = YamlConfiguration.loadConfiguration(ranksFile);
    
    if(playerConfig.contains("ranks." + p.getUniqueId()) && ranksConfig.contains("ranks." + rank)) {
      playerConfig.set("ranks." + p.getUniqueId() + ".rank", rank);
      
      try {
        playerConfig.save(playerFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
    if(!playerConfig.contains("ranks." + p.getUniqueId()) && ranksConfig.contains("ranks." + rank)) {
      playerConfig.createSection("ranks." + p.getUniqueId());
      playerConfig.set("ranks." + p.getUniqueId() + ".rank", rank);
      
      try {
        playerConfig.save(playerFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  
  public static void updatePermissions(String rank) {
    for(Entry<UUID, String> entry : playerRanks.entrySet()) {
      System.out.println(entry.getValue());
      if(entry.getValue().equalsIgnoreCase(rank)) {
        Player p = Bukkit.getServer().getPlayer(entry.getKey());
        PermissionsHandler.removeAttachment(p);
        
        PermissionsHandler.setupPermissions(p);
      }
    }
  }
}
