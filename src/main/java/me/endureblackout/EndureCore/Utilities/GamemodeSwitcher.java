package me.endureblackout.EndureCore.Utilities;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GamemodeSwitcher {
	
	public GamemodeSwitcher() {
		
	}
	
	public void setGamemode(int gamemode, Player p) {
		switch(gamemode) {
			case 0:
				p.setGameMode(GameMode.SURVIVAL);
				break;
			case 1:
				p.setGameMode(GameMode.CREATIVE);
				break;
			case 2:
				p.setGameMode(GameMode.ADVENTURE);
				break;
			case 3:
				p.setGameMode(GameMode.SPECTATOR);
		}
	}
}
