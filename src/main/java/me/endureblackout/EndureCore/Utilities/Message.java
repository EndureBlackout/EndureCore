package me.endureblackout.EndureCore.Utilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Message {
  private String message;
  private Player sender;
  private Rank rank;
  
  public Message(String message, Player sender, Rank rank) {
    this.message = message;
    this.sender = sender;
    this.rank = rank;
  }
  
  public String formatMessage() {
    //TODO: format message according to config
    return "[" + ChatColor.translateAlternateColorCodes('&', rank.getPrefix()) + "] " + sender.getName() + ">> " + message;
  }
}
