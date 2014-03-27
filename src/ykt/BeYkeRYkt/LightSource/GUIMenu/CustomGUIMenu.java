package ykt.BeYkeRYkt.LightSource.GUIMenu;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CustomGUIMenu{
	
	private Inventory inv;
	
	public CustomGUIMenu(String name, int slots){
		inv = Bukkit.createInventory(null, slots, name);
	}
	
	public void addItem(ItemStack item, int slot){
		inv.setItem(slot, item);
	}
	
	public Inventory getInventory(){
		return inv;
	}
	
}