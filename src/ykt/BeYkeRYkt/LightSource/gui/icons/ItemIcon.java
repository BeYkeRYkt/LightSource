package ykt.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ykt.BeYkeRYkt.LightSource.LightAPI;
import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.editor.PlayerEditor;
import ykt.BeYkeRYkt.LightSource.gui.Icon;
import ykt.BeYkeRYkt.LightSource.gui.Menu;
import ykt.BeYkeRYkt.LightSource.items.ItemManager;
import ykt.BeYkeRYkt.LightSource.items.LightItem;

public class ItemIcon extends Icon {

    private LightItem item;

    public ItemIcon(LightItem item) {
        super("item_" + item.getId(), item.getMaterial());
        this.item = item;
        setName(ChatColor.GREEN + item.getId());
        getLore().add(ChatColor.GOLD + "Settings: ");
        getLore().add(ChatColor.WHITE + "Name: " + ChatColor.GREEN + item.getName());
        getLore().add(ChatColor.WHITE + "Material: " + ChatColor.GREEN + item.getMaterial());
        getLore().add(ChatColor.WHITE + "Light level: " + ChatColor.YELLOW + item.getLevelLight());
        getLore().add(ChatColor.WHITE + "BurnTime: " + ChatColor.YELLOW + item.getMaxBurnTime());
    }

    @Override
    public void onItemClick(InventoryClickEvent event) {
        if (LightAPI.getEditorManager().getEditor(event.getWhoClicked().getName()) == null) {
            Player player = (Player) event.getWhoClicked();
            PlayerEditor editor = new PlayerEditor(player.getName(), item);
            LightAPI.getEditorManager().addEditor(editor);

            Menu menu = LightSource.getAPI().getGUIManager().getMenuFromId("editorMenu");
            LightSource.getAPI().getGUIManager().openMenu(player, menu);
        }
    }

    @Override
    public void onMenuOpen(Menu menu, Player player) {
        LightItem refresh = null;
        for (LightItem item : ItemManager.getList()) {
            if (item.getId().equals(this.item.getId())) {
                refresh = item;
            }
        }
        getLore().set(1, ChatColor.WHITE + "Name: " + ChatColor.GREEN + refresh.getName());
        getLore().set(2, ChatColor.WHITE + "Material: " + ChatColor.GREEN + refresh.getMaterial());
        getLore().set(3, ChatColor.WHITE + "Light level: " + ChatColor.YELLOW + refresh.getLevelLight());
        getLore().set(4, ChatColor.WHITE + "BurnTime: " + ChatColor.YELLOW + refresh.getMaxBurnTime());
    }
}