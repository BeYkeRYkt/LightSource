package ykt.BeYkeRYkt.LightSource.GUIMenu;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.MainCommand;

public class GUIListener implements Listener{
	
	@EventHandler
	public void onStandartClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked(); 
		ItemStack clicked = event.getCurrentItem(); 
		Inventory inventory = event.getInventory(); 
		String name = inventory.getTitle();
		
		if(clicked != null && clicked.getType() != Material.AIR){
			if(LightSource.getInstance().getName().equals(name)){
				MainCommand cmd = new MainCommand();
				if(cmd.getAdv_Item().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					if(clicked.getItemMeta().getLore().contains("true")){
                      LightSource.getInstance().getConfig().set("Advanced-Listener.TorchLight", false);
					  LightSource.getInstance().getConfig().options().copyDefaults(true);
					  LightSource.getInstance().saveConfig();
					  LightSource.getInstance().getConfig().options().copyDefaults(false);
						
					  player.closeInventory();
					  player.sendMessage(ChatColor.GREEN + "Settings are changed. Restart the plugin.");
				  }else if(clicked.getItemMeta().getLore().contains("false")){
						if(Bukkit.getPluginManager().getPlugin("BKCommonLib") != null && Bukkit.getPluginManager().getPlugin("BKCommonLib").isEnabled()){	 
					  LightSource.getInstance().getConfig().set("Advanced-Listener.TorchLight", true);
					  LightSource.getInstance().getConfig().options().copyDefaults(true);
					  LightSource.getInstance().saveConfig();
					  LightSource.getInstance().getConfig().options().copyDefaults(false);
						
					  player.closeInventory();
					  player.sendMessage(ChatColor.GREEN + "Settings are changed. Restart the plugin.");
						}else{
							player.closeInventory();
							player.sendMessage(ChatColor.RED + "To work needed BKCommonLib. Advanced listener does not include.");
						}
				  }
				}else if(cmd.getAdv_Ent().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					if(clicked.getItemMeta().getLore().contains("true")){

						  LightSource.getInstance().getConfig().set("Advanced-Listener.Entity", false);
						  LightSource.getInstance().getConfig().options().copyDefaults(true);
						  LightSource.getInstance().saveConfig();
						  LightSource.getInstance().getConfig().options().copyDefaults(false);
							
						  player.closeInventory();
						  player.sendMessage(ChatColor.GREEN + "Settings are changed. Restart the plugin.");
						  
					  }else if(clicked.getItemMeta().getLore().contains("false")){
							if(Bukkit.getPluginManager().getPlugin("BKCommonLib") != null  && Bukkit.getPluginManager().getPlugin("BKCommonLib").isEnabled()){
								
						  LightSource.getInstance().getConfig().set("Advanced-Listener.Entity", true);
						  LightSource.getInstance().getConfig().options().copyDefaults(true);
						  LightSource.getInstance().saveConfig();
						  LightSource.getInstance().getConfig().options().copyDefaults(false);
							
						  player.closeInventory();
						  player.sendMessage(ChatColor.GREEN + "Settings are changed. Restart the plugin.");
							}else{
								player.closeInventory();
								player.sendMessage(ChatColor.RED + "To work needed BKCommonLib. Advanced listener does not include.");
							}
					  }
				}else if(cmd.getDebug().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					if(clicked.getItemMeta().getLore().contains("true")){

						  LightSource.getInstance().getConfig().set("Debug", false);
						  LightSource.getInstance().getConfig().options().copyDefaults(true);
						  LightSource.getInstance().saveConfig();
						  LightSource.getInstance().getConfig().options().copyDefaults(false);
							
						  player.closeInventory();
						  player.sendMessage(ChatColor.GREEN + "Settings are changed. Restart the plugin.");
						  
					  }else if(clicked.getItemMeta().getLore().contains("false")){
				
						  LightSource.getInstance().getConfig().set("Debug", true);
						  LightSource.getInstance().getConfig().options().copyDefaults(true);
						  LightSource.getInstance().saveConfig();
						  LightSource.getInstance().getConfig().options().copyDefaults(false);
							
						  player.closeInventory();
						  player.sendMessage(ChatColor.GREEN + "Settings are changed. Restart the plugin.");
					  }
				}else if(cmd.getGUIEnable().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					if(clicked.getItemMeta().getLore().contains("true")){

						  LightSource.getInstance().getConfig().set("Enable-GUI", false);
						  LightSource.getInstance().getConfig().options().copyDefaults(true);
						  LightSource.getInstance().saveConfig();
						  LightSource.getInstance().getConfig().options().copyDefaults(false);
							
						  player.closeInventory();
						  player.sendMessage(ChatColor.GREEN + "Settings are changed. Restart the plugin.");
						  
					  }else if(clicked.getItemMeta().getLore().contains("false")){
		
						  LightSource.getInstance().getConfig().set("Enable-GUI", true);
						  LightSource.getInstance().getConfig().options().copyDefaults(true);
						  LightSource.getInstance().saveConfig();
						  LightSource.getInstance().getConfig().options().copyDefaults(false);
							
						  player.closeInventory();
						  player.sendMessage(ChatColor.GREEN + "Settings are changed. Restart the plugin.");
					  }
				}else if(cmd.getReload().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					  LightSource.getInstance().reloadConfig();
					  LightSource.getInstance().onDisable();
					  LightSource.getInstance().onEnable();
					  
					  player.closeInventory();
					  player.sendMessage(ChatColor.GREEN +"Plugin successfully restarted!");
				}else if(cmd.getWorlds().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					player.closeInventory();
					
					CustomGUIMenu menu = new CustomGUIMenu("Worlds", 9 * 4);
					
					for(World worlds : Bukkit.getWorlds()){
						ItemStack map = new ItemStack(Material.MAP);
						ItemMeta meta = map.getItemMeta();
						
						meta.setDisplayName(worlds.getName());
						ArrayList list = new ArrayList();
						
						list.add(ChatColor.GOLD + "Status: ");
						list.add(String.valueOf(LightSource.getInstance().getConfig().getBoolean("Worlds." + worlds.getName())));
						
						meta.setLore(list);
						map.setItemMeta(meta);
						
						menu.addItem(map, Bukkit.getWorlds().indexOf(worlds));
						
						player.openInventory(menu.getInventory());
					}
					
				}
				
				event.setCancelled(true);
				
				//Other's gui
			}else if("Worlds".equals(name)){
				for(World worlds : Bukkit.getWorlds()){
					if(worlds.getName().equals(clicked.getItemMeta().getDisplayName())){
						if(clicked.getItemMeta().getLore().contains("true")){
							  LightSource.getInstance().getConfig().set("Worlds." + clicked.getItemMeta().getDisplayName(), false);
							  LightSource.getInstance().getConfig().options().copyDefaults(true);
							  LightSource.getInstance().saveConfig();
							  LightSource.getInstance().getConfig().options().copyDefaults(false);
								
							  player.closeInventory();
							  player.sendMessage(ChatColor.GREEN + "Settings are changed. Restart the plugin.");
						  }else if(clicked.getItemMeta().getLore().contains("false")){
							  LightSource.getInstance().getConfig().set("Worlds." + clicked.getItemMeta().getDisplayName(), true);
							  LightSource.getInstance().getConfig().options().copyDefaults(true);
							  LightSource.getInstance().saveConfig();
							  LightSource.getInstance().getConfig().options().copyDefaults(false);
								
							  player.closeInventory();
							  player.sendMessage(ChatColor.GREEN + "Settings are changed. Restart the plugin.");
						  }
					}
				}
			}
		}
	}
	
}