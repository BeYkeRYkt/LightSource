package ykt.BeYkeRYkt.LightSource.Listeners;


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

import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.UpdateContainer;
import ykt.BeYkeRYkt.LightSource.GUIMenu.Icons;
import ykt.BeYkeRYkt.LightSource.GUIMenu.Menus;
import ykt.BeYkeRYkt.LightSource.gravitydevelopment.updater.Updater;

public class GUIListener implements Listener{
	
	/**
	 * 
	 * FOR GUI
	 * 
	 */
	
	@EventHandler
	public void onStandartClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked(); 
		ItemStack clicked = event.getCurrentItem(); 
		Inventory inventory = event.getInventory(); 
		String name = inventory.getTitle();
		
		if(clicked != null && clicked.getType() != Material.AIR){
			if(LightSource.getInstance().getName().equals(name)){
				
               if(Icons.getDebug().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					if(clicked.getItemMeta().getLore().contains("true")){
						  LightSource.getInstance().getDB().setDebug(false);
							
						  player.closeInventory();
						  player.sendMessage(ChatColor.GREEN + "Settings are changed.");
						  
					  }else if(clicked.getItemMeta().getLore().contains("false")){						  
						  LightSource.getInstance().getDB().setDebug(true);
							
						  player.closeInventory();
						  player.sendMessage(ChatColor.GREEN + "Settings are changed.");
					  }
				}else if(Icons.getPlayerLight().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					if(clicked.getItemMeta().getLore().contains("true")){
						  LightSource.getInstance().getDB().setPlayerLight(false);
						  
						  player.closeInventory();
						  player.sendMessage(ChatColor.GREEN + "Settings are changed.");
						  
					  }else if(clicked.getItemMeta().getLore().contains("false")){
						  LightSource.getInstance().getDB().setPlayerLight(true);
						  
						  player.closeInventory();
						  player.sendMessage(ChatColor.GREEN + "Settings are changed.");
					  }
				}else if(Icons.getEntityLight().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					if(clicked.getItemMeta().getLore().contains("true")){
						  LightSource.getInstance().getDB().setEntityLight(false);
							
						  player.closeInventory();
						  player.sendMessage(ChatColor.GREEN + "Settings are changed.");
						  
					  }else if(clicked.getItemMeta().getLore().contains("false")){
						  LightSource.getInstance().getDB().setEntityLight(true);
						  
						  player.closeInventory();
						  player.sendMessage(ChatColor.GREEN + "Settings are changed.");
					  }
				}else if(Icons.getItemLight().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					if(clicked.getItemMeta().getLore().contains("true")){
						  LightSource.getInstance().getDB().setItemLight(false);
						  
						  player.closeInventory();
						  player.sendMessage(ChatColor.GREEN + "Settings are changed.");
						  
					  }else if(clicked.getItemMeta().getLore().contains("false")){
						  LightSource.getInstance().getDB().setItemLight(true);
						  
						  player.closeInventory();
						  player.sendMessage(ChatColor.GREEN + "Settings are changed.");
					  }
				}else if(Icons.getReload().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					  LightSource.getInstance().onDisable();
					  LightSource.getInstance().onEnable();
					  LightSource.getInstance().reloadConfig();
					  
					  player.closeInventory();
					  player.sendMessage(ChatColor.GREEN +"Plugin successfully restarted!");
				}else if(Icons.getUpdate().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					  if(LightSource.getInstance().getDB().isUpdater()){
						if(UpdateContainer.update){
						@SuppressWarnings("unused")
						Updater updater = new Updater(LightSource.getInstance(), UpdateContainer.id, UpdateContainer.file, Updater.UpdateType.NO_VERSION_CHECK, true);
						player.sendMessage(ChatColor.GREEN + "Downloading new update, check your console.");
						player.closeInventory();
						}else{
						  player.sendMessage(ChatColor.YELLOW + "No new updates");
						  player.closeInventory();
						}
						}else{
					      player.sendMessage(ChatColor.RED + "System updates disabled. Turn it in the plugin settings");
					      player.closeInventory();
						}			
				}else if(Icons.getWorlds().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					Menus.openWorldMenu(player);
				}
				
				event.setCancelled(true);
				
				//Other's gui
			}
			
			if("Worlds".equals(name)){
				for(World worlds : Bukkit.getWorlds()){
					if(worlds.getName().equals(clicked.getItemMeta().getDisplayName())){
						if(clicked.getItemMeta().getLore().contains("true")){
							LightSource.getInstance().getDB().setWorld(clicked.getItemMeta().getDisplayName(), false);
								
							  player.closeInventory();
							  player.sendMessage(ChatColor.GREEN + "Settings are changed.");
						  }else if(clicked.getItemMeta().getLore().contains("false")){
							LightSource.getInstance().getDB().setWorld(clicked.getItemMeta().getDisplayName(), true);
								
							  player.closeInventory();
							  player.sendMessage(ChatColor.GREEN + "Settings are changed.");
						  }
					}
				}
				event.setCancelled(true);
			}
		}
	}
	
}