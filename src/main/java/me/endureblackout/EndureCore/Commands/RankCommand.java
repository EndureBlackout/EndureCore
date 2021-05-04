package me.endureblackout.EndureCore.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.endureblackout.EndureCore.EndureCore;
import me.endureblackout.EndureCore.Utilities.Rank;
import me.endureblackout.EndureCore.Utilities.RanksHandler;

public class RankCommand implements CommandExecutor {

  private EndureCore core;

  public RankCommand(EndureCore core) {
    this.core = core;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("rank")) {
      if (args.length == 0) {
        sender.sendMessage("Rank Help:");
        sender.sendMessage("/rank create <name> - Creates a new rank with the specified name");
        sender.sendMessage("/rank prefix <name> <prefix> - Sets the prefix for the rank with the specified name");
        sender.sendMessage("/rank suffix <name> <suffix> - Sets the suffix for the rank with the speciifed name");
        sender.sendMessage("/rank permission add <name> <permission> - Adds a permission to the ranks permission list");
        sender.sendMessage("/rank permission remove <name> <permission> - Removes a permission from the ranks permission list");
        sender.sendMessage("/rank set <player> <rank> - Set a user's rank");
      }

      if (args.length == 2) {
        if (args[0].equalsIgnoreCase("create")) {
          Rank rank = new Rank(args[1], this.core);

          rank.saveRank();
          sender.sendMessage(ChatColor.GREEN + rank.getName() + " was created successfully!");
        }
      }

      if (args.length == 3) {

        if (args[0].equalsIgnoreCase("prefix")) {
          Rank rank = new Rank(core);

          try {
            rank.retrieveRank(args[1]);

            rank.setPrefix(args[2]);
            rank.saveRank();

            sender.sendMessage(ChatColor.GREEN + "Prefix for " + args[0] + " set to: "
                + ChatColor.translateAlternateColorCodes('&', args[2]));
          } catch (IllegalArgumentException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
          }
        }

        if (args[0].equalsIgnoreCase("suffix")) {
          Rank rank = new Rank(core);

          try {
            rank.retrieveRank(args[1]);

            rank.setSuffix(args[2]);
            rank.saveRank();

            sender.sendMessage(ChatColor.GREEN + "Suffix for " + args[0] + " set to: "
                + ChatColor.translateAlternateColorCodes('&', args[2]));
          } catch (IllegalArgumentException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
          }
        }
        
        if(args[0].equalsIgnoreCase("set")) {
          RanksHandler.setUserRank(args[1], args[2]);
          
          sender.sendMessage(ChatColor.GREEN + args[1] + " added to " + args[2]);
        }
      }

      if (args.length == 4) {
        if (args[0].equalsIgnoreCase("permission")) {
          if (args[1].equalsIgnoreCase("add")) {
            Rank rank = new Rank(core);

            try {
              rank.retrieveRank(args[2]);

              sender.sendMessage(rank.addPermission(args[3]));
              rank.saveRank();
              RanksHandler.updatePermissions(args[2]);
            } catch (IllegalArgumentException e) {
              sender.sendMessage(ChatColor.RED + e.getMessage());
            }
          }
          
          if(args[1].equalsIgnoreCase("remove")) {
            Rank rank = new Rank(core);
            try {
              rank.retrieveRank(args[2]);

              sender.sendMessage(rank.removePermission(args[3]));
              rank.saveRank();
              RanksHandler.updatePermissions(args[2]);
            } catch (IllegalArgumentException e) {
              sender.sendMessage(ChatColor.RED + e.getMessage());
            }
          }
        }
      }
    }

    return true;
  }

}
