package me.endureblackout.EndureCore.Menus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GamemodeMenu implements Listener {
	
	Inventory menuInv;
	
	ItemStack survival = new ItemStack(Material.DIAMOND_PICKAXE);
	ItemMeta survivalMeta = survival.getItemMeta();
	ItemStack creative = new ItemStack(Material.GOLD_INGOT);
	ItemMeta creativeMeta = creative.getItemMeta();
	ItemStack adventure = new ItemStack(Material.COOKIE);
	ItemMeta advMeta = adventure.getItemMeta();
	ItemStack spectator = new ItemStack(Material.BARRIER);
	ItemMeta specMeta = spectator.getItemMeta();
	
	public GamemodeMenu() {
		survivalMeta.setDisplayName(ChatColor.RED + "Survival");
		creativeMeta.setDisplayName(ChatColor.GOLD + "Creative");
		advMeta.setDisplayName(ChatColor.DARK_BLUE + "Adventure");
		specMeta.setDisplayName(ChatColor.DARK_AQUA + "Spectator");
		
		survival.setItemMeta(survivalMeta);
		creative.setItemMeta(creativeMeta);
		adventure.setItemMeta(advMeta);
		spectator.setItemMeta(specMeta);		
		
		this.menuInv = createMenu();
	}
	
	@EventHandler
	public void MenuClickEvent(InventoryClickEvent e) {
		Inventory clickedInv = e.getClickedInventory();
		
		if(e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			
			if(clickedInv != null && e.getView().getTitle().equalsIgnoreCase(ChatColor.RED + "Gamemode Menu")) {
				e.setCancelled(true);
				
				if(e.getClick().isShiftClick()) {
					e.setCancelled(true);
				}
				
				if(e.getCurrentItem().getType().equals(survival.getType())) {
					p.setGameMode(GameMode.SURVIVAL);
				}
				
				if(e.getCurrentItem().getType().equals(creative.getType())) {
					p.setGameMode(GameMode.CREATIVE);
				}
				
				if(e.getCurrentItem().getType().equals(adventure.getType())) {
					p.setGameMode(GameMode.ADVENTURE);
				}
				
				if(e.getCurrentItem().getType().equals(spectator.getType())) {
					p.setGameMode(GameMode.SPECTATOR);
				}
				
				p.closeInventory();
			}	
		}
	}
	
	private Inventory createMenu() {	
		Inventory gamemodeInv = Bukkit.createInventory(null, 9, ChatColor.RED + "Gamemode Menu");
		
		gamemodeInv.setItem(0, survival);
		gamemodeInv.setItem(2, creative);
		gamemodeInv.setItem(5, adventure);
		gamemodeInv.setItem(8, spectator);
		
		return gamemodeInv;
	}
	
	public Inventory getMenu() {
		if(menuInv == null) {
			createMenu();
		}
		
		return menuInv;
	}
}
