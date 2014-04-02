package ykt.BeYkeRYkt.LightSource;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ykt.BeYkeRYkt.LightSource.GUIMenu.CustomGUIMenu;

public class LightCommand implements CommandExecutor{
	@Override
	  public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		
		if(sender instanceof Player){
		Player player = (Player) sender;
			if(command.getName().equalsIgnoreCase("light")){
				if(!LightSource.getInstance().getGUI()){
				if(args.length == 0){
					player.sendMessage(ChatColor.GOLD + "#========#" + ChatColor.GREEN + LightSource.getInstance().getName() + ChatColor.GOLD + "#========#");
					player.sendMessage("- /ls info" + ChatColor.YELLOW +"- Information about plugin.");
					
					if(player.hasPermission("lightsource.hidden")){
						player.sendMessage("- /light create" + ChatColor.YELLOW +"- Creating a light in your location.");
						player.sendMessage("- /light delete" + ChatColor.YELLOW +"- Deleting a light in your location.");
					}
				}else if(args.length == 1){
					if(player.hasPermission("lightsource.hidden")){
						if(args[0].equalsIgnoreCase("create")){
							  player.sendMessage(ChatColor.RED + "Need more arguments!");
							  player.sendMessage(ChatColor.YELLOW + "Description: " + ChatColor.WHITE + "Creating a light in your location");
						}else if(args[0].equalsIgnoreCase("delete")){
							LightAPI.deleteLightSourceStatic(player.getLocation());
							player.sendMessage(ChatColor.GREEN + "Light successfully deleted!");
						}else{
							  player.sendMessage(ChatColor.RED +"Unknown command.");
						}
					}else{
						  player.sendMessage(ChatColor.RED + "You do not have permission");
					}
				}else if(args.length == 2){
					if(player.hasPermission("lightsource.hidden")){
						if(args[0].equalsIgnoreCase("create")){
							int lightblock = player.getLocation().getBlock().getLightLevel();
							
							LightAPI.createLightSourceStatic(player.getLocation(), Integer.parseInt(args[1]));
							player.sendMessage(ChatColor.GREEN + "Light successfully created!");
						}else if(args[0].equalsIgnoreCase("delete")){
							  player.sendMessage(ChatColor.RED + "Too many arguments!");
							  player.sendMessage(ChatColor.YELLOW + "Description: " + ChatColor.WHITE + "Deleting a light in your location");
						}else{
							  player.sendMessage(ChatColor.RED +"Unknown command.");
						}
					}else{
						  player.sendMessage(ChatColor.RED + "You do not have permission");
					}
				}else{
					if(player.hasPermission("lightsource.hidden")){
					player.sendMessage(ChatColor.RED + "Too many arguments.");
					}else{
						 player.sendMessage(ChatColor.RED + "You do not have permission");
					}
				}
			}else if(LightSource.getInstance().getGUI()){
				
				  CustomGUIMenu menu = new CustomGUIMenu("LightCreator", 9);
				  
				  if(player.hasPermission("lightsource.hidden")){
					  menu.addItem(getCreate(), 0);
					  menu.addItem(getDelete(), 1);
					  
					  player.openInventory(menu.getInventory());	
				  }else{
						 player.sendMessage(ChatColor.RED + "You do not have permission");
					}
			}
			}
		}
		return true;
	}
	
	public ItemStack getCreate(){
		  ItemStack adve = new ItemStack(Material.REDSTONE_LAMP_ON);
		  ItemMeta meta = adve.getItemMeta();
		  meta.setDisplayName("Create light");
		  
		  ArrayList list = new ArrayList();
		  list.add(ChatColor.GOLD + "Creating a light in your location");
		  meta.setLore(list);
		  adve.setItemMeta(meta);
		  return adve;
	}
	
	public ItemStack getDelete(){
		  ItemStack adve = new ItemStack(Material.REDSTONE_LAMP_OFF);
		  ItemMeta meta = adve.getItemMeta();
		  meta.setDisplayName("Delete light");
		  
		  ArrayList list = new ArrayList();
		  list.add(ChatColor.GOLD + "Deleting a light in your location");
		  meta.setLore(list);
		  adve.setItemMeta(meta);
		  return adve;
	}
	
	
	public ItemStack getLightLevel(int level){
		  ItemStack adve = new ItemStack(Material.GLOWSTONE_DUST);
		  ItemMeta meta = adve.getItemMeta();
		  meta.setDisplayName(String.valueOf(level));
		  
		  ArrayList list = new ArrayList();
		  list.add(ChatColor.GOLD + "Light level");
		  meta.setLore(list);
		  adve.setAmount(level);
		  adve.setItemMeta(meta);
		  return adve;
	}
}