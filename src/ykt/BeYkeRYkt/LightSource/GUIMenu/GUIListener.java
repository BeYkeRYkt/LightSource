package ykt.BeYkeRYkt.LightSource.GUIMenu;

import java.util.ArrayList;

import net.gravitydevelopment.updater.Updater;

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

import ykt.BeYkeRYkt.LightSource.LightAPI;
import ykt.BeYkeRYkt.LightSource.LightCommand;
import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.MainCommand;
import ykt.BeYkeRYkt.LightSource.UpdateContainer;

public class GUIListener implements Listener{
	
	
	/**
	 * 
	 * FOR GUI
	 * 
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
				MainCommand cmd = new MainCommand();
				if(cmd.getAdv_Item().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					if(clicked.getItemMeta().getLore().contains("true")){
                      LightSource.getInstance().getConfig().set("Advanced-Listener.TorchLight", false);
					  LightSource.getInstance().getConfig().options().copyDefaults(true);
					  LightSource.getInstance().saveConfig();
					  LightSource.getInstance().getConfig().options().copyDefaults(false);
					  LightSource.getInstance().registerAdvancedItemListener(false);
						
					  player.closeInventory();
					  player.sendMessage(ChatColor.GREEN + "Settings are changed.");
				  }else if(clicked.getItemMeta().getLore().contains("false")){
						if(Bukkit.getPluginManager().getPlugin("BKCommonLib") != null && Bukkit.getPluginManager().getPlugin("BKCommonLib").isEnabled()){	 
					  LightSource.getInstance().getConfig().set("Advanced-Listener.TorchLight", true);
					  LightSource.getInstance().getConfig().options().copyDefaults(true);
					  LightSource.getInstance().saveConfig();
					  LightSource.getInstance().getConfig().options().copyDefaults(false);
					  LightSource.getInstance().registerAdvancedItemListener(true);
					  
					  player.closeInventory();
					  player.sendMessage(ChatColor.GREEN + "Settings are changed.");
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
						  player.sendMessage(ChatColor.GREEN + "Settings are changed.");
						  
					  }else if(clicked.getItemMeta().getLore().contains("false")){
				
						  LightSource.getInstance().getConfig().set("Debug", true);
						  LightSource.getInstance().getConfig().options().copyDefaults(true);
						  LightSource.getInstance().saveConfig();
						  LightSource.getInstance().getConfig().options().copyDefaults(false);
							
						  player.closeInventory();
						  player.sendMessage(ChatColor.GREEN + "Settings are changed.");
					  }
				}else if(cmd.getGUIEnable().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					if(clicked.getItemMeta().getLore().contains("true")){

						  LightSource.getInstance().getConfig().set("Enable-GUI", false);
						  LightSource.getInstance().getConfig().options().copyDefaults(true);
						  LightSource.getInstance().saveConfig();
						  LightSource.getInstance().getConfig().options().copyDefaults(false);
						  LightSource.getInstance().setGUI(false);
							
						  player.closeInventory();
						  player.sendMessage(ChatColor.GREEN + "Settings are changed.");
						  
					  }else if(clicked.getItemMeta().getLore().contains("false")){
		
						  LightSource.getInstance().getConfig().set("Enable-GUI", true);
						  LightSource.getInstance().getConfig().options().copyDefaults(true);
						  LightSource.getInstance().saveConfig();
						  LightSource.getInstance().getConfig().options().copyDefaults(false);
						  LightSource.getInstance().setGUI(true);
							
						  player.closeInventory();
						  player.sendMessage(ChatColor.GREEN + "Settings are changed.");
					  }
				}else if(cmd.getReload().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					  LightSource.getInstance().reloadConfig();
					  LightSource.getInstance().onDisable();
					  LightSource.getInstance().onLoad();
					  LightSource.getInstance().onEnable();
					  
					  player.closeInventory();
					  player.sendMessage(ChatColor.GREEN +"Plugin successfully restarted!");
				}else if(cmd.getUpdate().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					  if(LightSource.getInstance().getConfig().getBoolean("Enable-updater")){
						if(UpdateContainer.update){
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
			}else if("LightCreator".equals(name)){
				
				LightCommand cmd = new LightCommand();
				if(cmd.getCreate().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					player.closeInventory();
					
					CustomGUIMenu menu = new CustomGUIMenu("LightLevels", 18);
					
					menu.addItem(cmd.getLightLevel(1), 0);
					menu.addItem(cmd.getLightLevel(2), 1);
					menu.addItem(cmd.getLightLevel(3), 2);
					menu.addItem(cmd.getLightLevel(4), 3);
					menu.addItem(cmd.getLightLevel(5), 4);
					menu.addItem(cmd.getLightLevel(6), 5);
					menu.addItem(cmd.getLightLevel(7), 6);
					menu.addItem(cmd.getLightLevel(8), 7);
					menu.addItem(cmd.getLightLevel(9), 8);
					menu.addItem(cmd.getLightLevel(10), 9);
					menu.addItem(cmd.getLightLevel(11), 10);
					menu.addItem(cmd.getLightLevel(12), 11);
					menu.addItem(cmd.getLightLevel(13), 12);
					menu.addItem(cmd.getLightLevel(14), 13);
					menu.addItem(cmd.getLightLevel(15), 14);
					
					player.openInventory(menu.getInventory());
					
				}else if(cmd.getDelete().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					LightAPI.deleteLightSourceStatic(player.getLocation());
					player.closeInventory();
					player.sendMessage(ChatColor.GREEN + "Light successfully deleted!");
				}
				
				event.setCancelled(true);
				
			}else if("LightLevels".equals(name)){
				if(clicked.getType() == Material.GLOWSTONE_DUST){
				if(clicked.hasItemMeta()){
				if(clicked.getItemMeta().hasDisplayName()){

					LightAPI.createLightSourceStatic(player.getLocation(), Integer.parseInt(clicked.getItemMeta().getDisplayName()));
					
					player.closeInventory();
					player.sendMessage(ChatColor.GREEN + "Light successfully created!");
				}
				}
				}
				
				event.setCancelled(true);
			}else if("Worlds".equals(name)){
				for(World worlds : Bukkit.getWorlds()){
					if(worlds.getName().equals(clicked.getItemMeta().getDisplayName())){
						if(clicked.getItemMeta().getLore().contains("true")){
							  LightSource.getInstance().getConfig().set("Worlds." + clicked.getItemMeta().getDisplayName(), false);
							  LightSource.getInstance().getConfig().options().copyDefaults(true);
							  LightSource.getInstance().saveConfig();
							  LightSource.getInstance().getConfig().options().copyDefaults(false);
								
							  player.closeInventory();
							  player.sendMessage(ChatColor.GREEN + "Settings are changed.");
						  }else if(clicked.getItemMeta().getLore().contains("false")){
							  LightSource.getInstance().getConfig().set("Worlds." + clicked.getItemMeta().getDisplayName(), true);
							  LightSource.getInstance().getConfig().options().copyDefaults(true);
							  LightSource.getInstance().saveConfig();
							  LightSource.getInstance().getConfig().options().copyDefaults(false);
								
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