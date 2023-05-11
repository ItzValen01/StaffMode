package staffmode.itzvalen01.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.clip.placeholderapi.PlaceholderAPI;
import staffmode.itzvalen01.Main;
import staffmode.itzvalen01.commands.AdminChat;
import staffmode.itzvalen01.commands.ModMode;
import staffmode.itzvalen01.commands.StaffChat;
import staffmode.itzvalen01.commands.VanishMode;

public class StaffJoin implements Listener{

    private Main plugin;

    public StaffJoin(Main plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onConnected(PlayerJoinEvent e) {
        FileConfiguration messages = plugin.getMessages();
        FileConfiguration config = plugin.getConfig();
        if(config.getString("Config.Modules.StaffJoin").equals("true")) {
            if(e.getPlayer().hasPermission("staffmode.staff")) {
                e.setJoinMessage(null);
                for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                    if(all.hasPermission("staffmode.staff")) {
                        String msg = PlaceholderAPI.setPlaceholders(e.getPlayer(), messages.getString("StaffJoin.connected"));
                        all.sendMessage(ChatColor.translateAlternateColorCodes('&', msg
                                .replaceAll("%staff%", e.getPlayer().getName())));
                    }
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onDisconnected(PlayerQuitEvent e) {
        FileConfiguration messages = plugin.getMessages();
        FileConfiguration config = plugin.getConfig();
        if(config.getString("Config.Modules.StaffJoin").equals("true")) {
            if(e.getPlayer().hasPermission("staffmode.staff")) {
                e.setQuitMessage(null);
                for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                    if(all.hasPermission("staffmode.staff")) {
                        String msg = PlaceholderAPI.setPlaceholders(e.getPlayer(), messages.getString("StaffJoin.disconnected"));
                        all.sendMessage(ChatColor.translateAlternateColorCodes('&', msg
                                .replaceAll("%staff%", e.getPlayer().getName())));
                    }
                }
            }
        }
        Player p = e.getPlayer();
        if(ModMode.modmode.contains(p)){
            VanishMode.vanishmode.remove(p);
            for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                all.showPlayer(p);
            }
            ModMode.modmode.remove(p);
            StaffChat.staffchat.remove(p);
            AdminChat.adminchat.remove(p);

            if(ModMode.pitems.containsKey(p)) {
                p.getInventory().setContents(ModMode.pitems.get(p));
            }
            if(ModMode.parmor.containsKey(p)) {
                p.getInventory().setArmorContents(ModMode.parmor.get(p));
            }

        }

    }

}
