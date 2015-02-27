package ru.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.gui.Icon;
import de.albionco.updater.Response;
import de.albionco.updater.Updater;
import de.albionco.updater.Version;

public class CheckUpdate extends Icon {

    public CheckUpdate() {
        super("checkUpdate", Material.DIAMOND);

        setName(ChatColor.GREEN + "Check update");
        getLore().add("");
        getLore().add(ChatColor.WHITE + "Connect to reposition Github");
        getLore().add(ChatColor.WHITE + "and check update.");
        getLore().add("");
        getLore().add(ChatColor.WHITE + "Updater created by " + ChatColor.ITALIC + ChatColor.YELLOW + "Albioncode");

    }

    @Override
    public void onItemClick(final InventoryClickEvent event) {

        Bukkit.getScheduler().runTaskAsynchronously(LightSource.getInstance(), new Runnable() {

            @Override
            public void run() {
                Player player = (Player) event.getWhoClicked();
                player.closeInventory();

                Version version = Version.parse(LightSource.getInstance().getDescription().getVersion());
                String repo = "BeYkeRYkt/LightSource";

                Updater updater;
                try {
                    updater = new Updater(version, repo);

                    Response response = updater.getResult();
                    if (response == Response.SUCCESS) {
                        LightSource.getAPI().log(player, ChatColor.GREEN + "New update is available: " + ChatColor.YELLOW + updater.getLatestVersion() + ChatColor.GREEN + "!");
                        LightSource.getAPI().log(player, ChatColor.GREEN + "Changes: ");
                        player.sendMessage(updater.getChanges());// for normal
                                                                 // view
                    } else if (response == Response.NO_UPDATE) {
                        LightSource.getAPI().log(player, "You are running the latest version of LightSource.");
                    } else if (response == Response.REPO_NOT_FOUND) {
                        LightSource.getAPI().log(player, ChatColor.RED + "Github reposity not found :C");
                    } else if (response == Response.FAILED) {
                        LightSource.getAPI().log(player, ChatColor.RED + "An error occured whilst trying to find updates.");
                    } else if (response == Response.GITHUB_DENY) {
                        LightSource.getAPI().log(player, ChatColor.RED + "GitHub denied the connection. This is most likely due to too many connections being opened to the API within a small period of time.");
                    }
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}