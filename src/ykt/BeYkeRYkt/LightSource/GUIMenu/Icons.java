package ykt.BeYkeRYkt.LightSource.GUIMenu;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ykt.BeYkeRYkt.LightSource.LightSource;

public class Icons{

	public static ItemStack getWorlds(){
		  ItemStack adve = new ItemStack(Material.MAP);
		  ItemMeta meta = adve.getItemMeta();
		  meta.setDisplayName("Worlds");
		  
		  ArrayList<String> list = new ArrayList<String>();
		  list.add(ChatColor.GOLD + "Click here to change");
		  meta.setLore(list);
		  adve.setItemMeta(meta);
		  return adve;
	}
	
	public static ItemStack getReload(){
		  ItemStack adve = new ItemStack(Material.BEDROCK);
		  ItemMeta meta = adve.getItemMeta();
		  meta.setDisplayName("Reload");
		  
		  ArrayList<String> list = new ArrayList<String>();
		  list.add(ChatColor.GOLD + "Reload plugin");
		  meta.setLore(list);
		  adve.setItemMeta(meta);
		  return adve;
	}
	
	public static ItemStack getEntityLight(){
		  ItemStack adve = new ItemStack(Material.SKULL_ITEM, 1, (short) 2);
		  ItemMeta meta = adve.getItemMeta();
		  meta.setDisplayName("Advanced Entity Light");
		  
		  ArrayList<String> list = new ArrayList<String>();
		  list.add(ChatColor.DARK_RED + "WARNING!");
		  list.add(ChatColor.RED + "This option can cause colossal lags!");
		  list.add(ChatColor.GOLD + "Status: ");
		  list.add(String.valueOf(LightSource.getInstance().getDB().isEntityLight()));
		  meta.setLore(list);
		  adve.setItemMeta(meta);
		  return adve;
	}
	
	public static ItemStack getPlayerLight(){
		  ItemStack adve = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		  ItemMeta meta = adve.getItemMeta();
		  meta.setDisplayName("Player Light");
		  
		  ArrayList<String> list = new ArrayList<String>();
		  list.add(ChatColor.GOLD + "Status: ");
		  list.add(String.valueOf(LightSource.getInstance().getDB().isPlayerLight()));
		  meta.setLore(list);
		  adve.setItemMeta(meta);
		  return adve;
	}
	
	public static ItemStack getItemLight(){
		  ItemStack adve = new ItemStack(Material.TORCH);
		  ItemMeta meta = adve.getItemMeta();
		  meta.setDisplayName("Advanced Item Light");
		  
		  ArrayList<String> list = new ArrayList<String>();
		  list.add(ChatColor.DARK_RED + "WARNING!");
		  list.add(ChatColor.RED + "This option can cause colossal lags!");
		  list.add(ChatColor.GOLD + "Status: ");
		  list.add(String.valueOf(LightSource.getInstance().getDB().isItemLight()));
		  meta.setLore(list);
		  adve.setItemMeta(meta);
		  return adve;
	}
	
	public static ItemStack getUpdate(){
		  ItemStack adve = new ItemStack(Material.DIAMOND);
		  ItemMeta meta = adve.getItemMeta();
		  meta.setDisplayName("Update");
		  
		  ArrayList<String> list = new ArrayList<String>();
		  list.add(ChatColor.GOLD + "Check and download update");
		  meta.setLore(list);
		  adve.setItemMeta(meta);
		  return adve;
	}
	
	public static ItemStack getCreate(){
		  ItemStack adve = new ItemStack(Material.GLOWSTONE);
		  ItemMeta meta = adve.getItemMeta();
		  meta.setDisplayName("Create light");
		  
		  ArrayList<String> list = new ArrayList<String>();
		  list.add(ChatColor.GOLD + "Creating a light in your location");
		  meta.setLore(list);
		  adve.setItemMeta(meta);
		  return adve;
	}
	
	public static ItemStack getDelete(){
		  ItemStack adve = new ItemStack(Material.REDSTONE_LAMP_OFF);
		  ItemMeta meta = adve.getItemMeta();
		  meta.setDisplayName("Delete light");
		  
		  ArrayList<String> list = new ArrayList<String>();
		  list.add(ChatColor.GOLD + "Deleting a light in your location");
		  meta.setLore(list);
		  adve.setItemMeta(meta);
		  return adve;
	}
	
	
	public static ItemStack getLightLevel(int level){
		  ItemStack adve = new ItemStack(Material.GLOWSTONE_DUST);
		  ItemMeta meta = adve.getItemMeta();
		  meta.setDisplayName(String.valueOf(level));
		  
		  ArrayList<String> list = new ArrayList<String>();
		  list.add(ChatColor.GOLD + "Light level");
		  meta.setLore(list);
		  adve.setAmount(level);
		  adve.setItemMeta(meta);
		  return adve;
	}
}