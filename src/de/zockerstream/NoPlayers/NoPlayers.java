package de.zockerstream.NoPlayers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class NoPlayers extends JavaPlugin implements Listener {
	private HashMap<String, Boolean> alreadyHidden = new HashMap<String, Boolean>();
	
	/**
	 * Register the EventHandler
	 */
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	/**
	 * Handles the use of items / blocks by the player
	 * Checks if the player interacts with a paper and hides / shows the other players
	 */
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		// Is the item a paper? Right-Click?
		if (e.getPlayer().getItemInHand().getType() == Material.PAPER && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			// Players already hidden? Then make them visible again
			if (alreadyHidden.get(e.getPlayer().getName()) == false) {
				hidePlayer(e.getPlayer());
				alreadyHidden.put(e.getPlayer().getName(), true);
			} else {
				showPlayer(e.getPlayer());
				alreadyHidden.put(e.getPlayer().getName(), false);
			}
		}
	}
	
	/**
	 * Handles joining of players
	 * Gives the player his paper
	 */
	@EventHandler 
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		// Create the paper
		ItemStack itemstack = new ItemStack(Material.PAPER, 1);
		List<String> ls = new ArrayList<String>();
		ls.add("§cHide all players.");
		
		// Player already has this paper? Then don't add an other one
		if (!p.getInventory().contains(setName(itemstack, "§6§l§oHide", ls))) {
			p.getInventory().addItem(setName(itemstack, "§6§l§oHide", ls));
		}
		alreadyHidden.put(p.getName(), false);
	}
	
	/**
	 * Hides every player for the specified player
	 * @param p Player which wants to hide the others
	 */
	public void hidePlayer(Player p) {
		for (Player players : Bukkit.getOnlinePlayers()) {
			p.hidePlayer(players);
		}
		p.sendMessage("§eWooosh! Hello? Hellooo?");
	}
	
	/**
	 * Shows every player for the specified player
	 * @param p Player which wants to see the others
	 */
	public void showPlayer(Player p) {
		for (Player players : Bukkit.getOnlinePlayers()) {
			p.showPlayer(players);
		}
		p.sendMessage("§eWooosh! You can see me!");
	}
	
	/**
	 * Helper, which gives an ItemStack a (coloured) name and additional text
	 * @param is	ItemStack to modify
	 * @param name	ItemStack's name
	 * @param lore	ItemStack's info
	 */
	private ItemStack setName(ItemStack is, String name, List<String> lore) {
		ItemMeta im = is.getItemMeta();
		if (name != null) {
			im.setDisplayName(name);
		}
		if (lore != null) {
			im.setLore(lore);
			is.setItemMeta(im);
		}
		return is;
	}
}
