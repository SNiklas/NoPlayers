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
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable() {
	}

	/* Method checks if players are already hidden or not
	*  and if necessary, all players are hidden or made ​​visible again
	*/ 
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getPlayer().getItemInHand().getType() == Material.PAPER && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			if(alreadyHidden.get(e.getPlayer().getName()) == false) {
				hidePlayer(e.getPlayer());
				alreadyHidden.put(e.getPlayer().getName(), true);
			} else {
				showPlayer(e.getPlayer());
				alreadyHidden.put(e.getPlayer().getName(), false);
			}
		}
	}
	
	// Method sets the player an item to his Inventory
	@EventHandler 
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		ItemStack itemstack = new ItemStack(Material.PAPER, 1);
		List<String> ls = new ArrayList<String>();
		ls.add("§cHide all players.");
		if(!p.getInventory().contains(setName(itemstack, "§6§l§oHide", ls))) 
		{
			p.getInventory().addItem(setName(itemstack, "§6§l§oHide", ls));
		}
		alreadyHidden.put(p.getName(), false);
	}
	
	// Method hides all players
	public void hidePlayer(Player s) {
		Player[] target = s.getServer().getOnlinePlayers();
		for (int i = 0; i < target.length; i++) {
			s.hidePlayer(target[i]);
		}
		s.sendMessage("§eWooosh! Hello? Hellooo?");
	}
	
	// Method shows all players
	public void showPlayer(Player s) {
		Player[] target = s.getServer().getOnlinePlayers();
		for (int i = 0; i < target.length; i++) {
			s.showPlayer(target[i]);
		}
		s.sendMessage("§eWooosh! You can see me!");
	}
	
	// Sets the name of the Itemstack
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

