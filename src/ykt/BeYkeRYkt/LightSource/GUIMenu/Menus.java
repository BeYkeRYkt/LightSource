package ykt.BeYkeRYkt.LightSource.GUIMenu;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ykt.BeYkeRYkt.LightSource.LightSource;

public class Menus{
	
	public static void openMainMenu(Player player){
	CustomGUIMenu menu = new CustomGUIMenu(LightSource.getInstance().getName(), 9);
	  
	  if(player.hasPermission("lightsource.admin") || player.isOp()){
		  menu.addItem(Icons.getPlayerLight(), 0);	  
		  menu.addItem(Icons.getEntityLight(), 1);
		  menu.addItem(Icons.getItemLight(), 2);
		  menu.addItem(Icons.getWorlds(), 3);
		  menu.addItem(Icons.getUpdate(), 4);
		  menu.addItem(Icons.getReload(), 8);
	  }
	
	  player.openInventory(menu.getInventory());
	}
	
	
	public static void openWorldMenu(Player player){
		CustomGUIMenu menu = new CustomGUIMenu("Worlds", 9 * 4);
		
		for(World worlds : Bukkit.getWorlds()){
			ItemStack map = new ItemStack(Material.MAP);
			ItemMeta meta = map.getItemMeta();
			
			meta.setDisplayName(worlds.getName());
			ArrayList<String> list = new ArrayList<String>();
			
			list.add(ChatColor.GOLD + "Status: ");
			list.add(String.valueOf(LightSource.getInstance().getConfig().getBoolean("Worlds." + worlds.getName())));
			
			meta.setLore(list);
			map.setItemMeta(meta);
			
			menu.addItem(map, Bukkit.getWorlds().indexOf(worlds));
			
			player.openInventory(menu.getInventory());
		}
	}
	
	public static void openLightCreatorMenu(Player player){
		  CustomGUIMenu menu = new CustomGUIMenu("LightCreator", 9);
		  
		  if(player.hasPermission("lightsource.admin") || player.isOp()){
			  menu.addItem(Icons.getCreate(), 0);
			  menu.addItem(Icons.getDelete(), 1);
			  
			  player.openInventory(menu.getInventory());	
		  }else{
			  player.sendMessage(ChatColor.RED + "You do not have permission");
		  }
	}
	
	public static void openLightLevelsMenu(Player player){
		CustomGUIMenu menu = new CustomGUIMenu("LightLevels", 18);
		
		menu.addItem(Icons.getLightLevel(1), 0);
		menu.addItem(Icons.getLightLevel(2), 1);
		menu.addItem(Icons.getLightLevel(3), 2);
		menu.addItem(Icons.getLightLevel(4), 3);
		menu.addItem(Icons.getLightLevel(5), 4);
		menu.addItem(Icons.getLightLevel(6), 5);
		menu.addItem(Icons.getLightLevel(7), 6);
		menu.addItem(Icons.getLightLevel(8), 7);
		menu.addItem(Icons.getLightLevel(9), 8);
		menu.addItem(Icons.getLightLevel(10), 9);
		menu.addItem(Icons.getLightLevel(11), 10);
		menu.addItem(Icons.getLightLevel(12), 11);
		menu.addItem(Icons.getLightLevel(13), 12);
		menu.addItem(Icons.getLightLevel(14), 13);
		menu.addItem(Icons.getLightLevel(15), 14);
		
		player.openInventory(menu.getInventory());
	}
}