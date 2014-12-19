package ykt.BeYkeRYkt.LightSource.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Icon {

    private Material material;
    private String name;
    private List<String> lore = new ArrayList<String>();
    private int amount = 1;

    private String id;

    public Icon(String id, Material material) {
        this.id = id;
        this.material = material;
    }

    public Icon(String id, ItemStack item) {
        this.id = id;
        this.material = item.getType();
        if (item.getItemMeta().hasDisplayName()) {
            this.name = item.getItemMeta().getDisplayName();
        }

        if (item.getItemMeta().hasLore()) {
            this.lore = item.getItemMeta().getLore();
        }
    }

    public String getId() {
        return id;
    }

    public Material getMaterial() {
        return material;
    }

    public Icon setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public String getName() {
        return name;
    }

    public Icon setName(String name) {
        this.name = name;
        return this;
    }

    public Icon setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public List<String> getLore() {
        return lore;
    }

    public int getAmount() {
        return amount;
    }

    public Icon setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemStack getItemStack() {
        ItemStack item = new ItemStack(material);
        item.setAmount(amount);
        ItemMeta meta = item.getItemMeta();

        if (name != null) {
            meta.setDisplayName(name);
        }

        if (!lore.isEmpty()) {
            meta.setLore(lore);
        }

        item.setItemMeta(meta);
        return item;
    }

    public abstract void onItemClick(InventoryClickEvent event);
}