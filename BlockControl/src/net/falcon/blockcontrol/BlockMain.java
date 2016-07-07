package net.falcon.blockcontrol;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

/**
 * Created by Yani on 2016-07-04.
 */
public class BlockMain extends JavaPlugin implements Listener {

	// Variables, ArrayList etc (INIT)
	public static Plugin plugin;

	@Override
	public void onEnable() {

		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(this, this);
		this.saveDefaultConfig();
		plugin = this;
		System.out.println("*********************");
		System.out.println("**   BlockControl  **");
		System.out.println("**       ON        **");
		System.out.println("*********************");
		super.onEnable();
	}

	@Override
	public void onDisable() {
		System.out.println("*********************");
		System.out.println("**   BlockControl  **");
		System.out.println("**       OFF       **");
		System.out.println("*********************");

		super.onDisable();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void place(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		ArrayList<String> blocked = new ArrayList<>();
		ArrayList<ProtectedRegion> allowedregions = new ArrayList<>();
		if (!e.getPlayer().hasPermission("blockcontrol.bypass")) {
			// Create ArrayList of denied blocs
			for (String block : getConfig().getStringList("blocks")) {

						blocked.add(block);


			}

			
		}

		int i = 0;
		for (String bl : blocked) {
			//System.out.println(e.getBlock().getTypeId()+":"+e.getBlock().getData());
			//System.out.println(blocked);
			if(bl.contains(":")){
				if (bl.equals(e.getBlock().getTypeId()+":"+e.getBlock().getData())) {
					i++;
					break;
				}
			}
			
			//System.out.println(i);

		}

		// If the bloc is denied
		if (i != 0) {
			// Make ArrayList of allowed regions
			for (String region : getConfig().getStringList("regions")) {
				allowedregions
						.add(WorldGuard.getWorldGuard().getRegionManager(e.getPlayer().getWorld()).getRegion(region));
			}
			// ArrayList of regions where is bloc
			ArrayList<ProtectedRegion> pr = WorldGuard.getRegions(e.getBlock().getLocation());
			// Get how much allowed regions are there
			int numberofregions = 0;
			for (ProtectedRegion region : pr) {
				if (allowedregions.contains(region)) {
					numberofregions++;
				}
			}
			// If there is no region
			if (numberofregions == 0) {
				// Cancel place, Play smoke effect, send message
				e.setCancelled(true);
				e.getPlayer().sendMessage(getConfig().getString("message").replaceAll("&", "§"));
				p.getWorld().playEffect(e.getBlock().getLocation(), Effect.SMOKE, 0);
				return;
			}

		}

	}

}
