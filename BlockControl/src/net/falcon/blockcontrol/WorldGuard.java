package net.falcon.blockcontrol;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;


public class WorldGuard implements Listener {
    //WorldGuard Integration

    public static ArrayList<ProtectedRegion> getRegions(Location loc) {

        ArrayList<ProtectedRegion> pr = new ArrayList<>();
        for (ProtectedRegion r : WGBukkit.getRegionManager(loc.getWorld()).getApplicableRegions(loc)) {
            pr.add(r);


        }
        return pr;

    }

    @SuppressWarnings("unused")
    public static WorldGuardPlugin getWorldGuard() {

        Plugin plugins = BlockMain.plugin.getServer().getPluginManager().getPlugin("WorldGuard");

        if (plugins == null || !(plugins instanceof WorldGuardPlugin)) {
            return null;

        }

        return (WorldGuardPlugin) plugins;


    }
}
