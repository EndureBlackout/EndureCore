package me.endureblackout.EndureCore.Utilities;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

import me.endureblackout.EndureCore.EndureCore;

public class PermissionsHandler implements Listener {
  private static HashMap<UUID, PermissionAttachment> playerPermissions = new HashMap<UUID, PermissionAttachment>();

  public static void setupPermissions(Player p) {
    PermissionAttachment attachment = p.addAttachment(EndureCore.core);
    playerPermissions.put(p.getUniqueId(), attachment);
    permissionsSetter(p.getUniqueId());
  }

  public static void permissionsSetter(UUID uuid) {
    PermissionAttachment attachment = playerPermissions.get(uuid);

    File ranksFile = new File(EndureCore.core.getDataFolder(), "ranks.yml");
    YamlConfiguration ranksConfig = YamlConfiguration.loadConfiguration(ranksFile);

    File playerFile = new File(EndureCore.core.getDataFolder(), "playerRanks.yml");
    YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

    for (String ranks : ranksConfig.getConfigurationSection("ranks").getKeys(false)) {
      System.out.println(ranks);
      if (playerConfig.getString("ranks." + uuid + ".rank").equalsIgnoreCase(ranks)) {
        for (String permission : ranksConfig.getStringList("ranks." + ranks + ".permissions")) {
          System.out.println(permission);
          attachment.setPermission(permission, true);
        }
      }
    }
    
    Bukkit.getServer().getPlayer(uuid).recalculatePermissions();
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
    setupPermissions(e.getPlayer());
    RanksHandler.getUserRank(e.getPlayer());
  }

  @EventHandler
  public void onPlayerLeave(PlayerQuitEvent e) {
    e.getPlayer().removeAttachment(playerPermissions.get(e.getPlayer().getUniqueId()));
    playerPermissions.remove(e.getPlayer().getUniqueId());
  }
  
  public static void removeAttachment(Player p) {
    p.removeAttachment(playerPermissions.get(p.getUniqueId()));
    playerPermissions.remove(p.getUniqueId());
  }
  
  public static void clearCache() {
    for(Entry<UUID, PermissionAttachment> entry : playerPermissions.entrySet()) {
      Bukkit.getServer().getPlayer(entry.getKey()).removeAttachment(entry.getValue());
    }
    
    playerPermissions.clear();    
  }
}
