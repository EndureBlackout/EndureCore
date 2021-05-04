package me.endureblackout.EndureCore.Utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import me.endureblackout.EndureCore.EndureCore;

public class Rank {
  private String name;
  private String prefix;
  private String suffix;
  private List<String> permissions = new ArrayList<String>();
  private EndureCore core;

  public Rank(String name, EndureCore core) {
    this.name = name;
    this.prefix = "";
    this.suffix = "";
    this.core = core;
  }

  public Rank(EndureCore core) {
    this.core = core;
    this.prefix = "";
    this.suffix = "";
  }

  public Rank(String name, String prefix, String suffix) {
    this.name = name;
    this.prefix = prefix;
    this.suffix = suffix;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }

  public String getName() {
    return this.name;
  }

  public String getPrefix() {
    return this.prefix;
  }

  public String getSuffix() {
    return this.suffix;
  }

  public String addPermission(String permission) {
    if (permissions == null) {
      permissions = new ArrayList<String>();
    }

    if (this.permissions != null && this.permissions.contains(permission)) {
      return getName() + " already has that permission";
    } else {
      this.permissions.add(permission);
      return "Permission has been granted for " + getName();
    }
  }
  
  public String removePermission(String permission) {
    if (!this.permissions.contains(permission)) {
      return getName() + " does not have that permission";
    } else {
      for(int i = 0; i < permissions.size(); i++) {
        if(permission.equalsIgnoreCase(permissions.get(i))) {
          permissions.remove(i);
        }
      }
      
      return "Permission has been removed from " + getName();
    }
  }

  public void retrieveRank(String name) throws IllegalArgumentException {
    File file = new File(core.getDataFolder(), "ranks.yml");
    YamlConfiguration ranksConfig = YamlConfiguration.loadConfiguration(file);

    if (ranksConfig.contains("ranks." + name)) {
      this.name = name;
      this.prefix = ranksConfig.getString("ranks." + name + ".prefix");
      this.suffix = ranksConfig.getString("ranks." + name + ".suffix");
      this.permissions = ranksConfig.getStringList("ranks." + name + ".permissions");
    } else {
      throw new IllegalArgumentException("Rank by the name " + name + " was not found");
    }

  }

  public void saveRank() {
    File file = new File(core.getDataFolder(), "ranks.yml");
    YamlConfiguration ranksConfig = YamlConfiguration.loadConfiguration(file);

    if (ranksConfig.contains("ranks." + name)) {
      ranksConfig.set("ranks." + name + ".prefix", this.prefix);
      ranksConfig.set("ranks." + name + ".suffix", this.suffix);

      ranksConfig.set("ranks." + name + ".permissions", permissions);

      try {
        ranksConfig.save(file);
        RanksHandler.updatePermissions(name);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      ranksConfig.createSection("ranks." + name);

      ranksConfig.set("ranks." + name + ".prefix", this.prefix);
      ranksConfig.set("ranks." + name + ".suffix", this.suffix);
      ranksConfig.createSection("ranks." + name + ".permissions");

      for (String permission : permissions) {
        ranksConfig.addDefault("ranks." + name + ".permissions", permission);
      }

      try {
        ranksConfig.save(file);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }
}
