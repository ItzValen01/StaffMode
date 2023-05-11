package staffmode.itzvalen01.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import staffmode.itzvalen01.Main;

public class TPHereAll implements CommandExecutor{

    private Main plugin;

    public TPHereAll(Main plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou cannot execute this command from the console."));
            return true;
        }else {
            Player p = (Player) sender;
            FileConfiguration messages = plugin.getMessages();
            ArrayList<Player> online = new ArrayList<Player>();
            if(p.hasPermission("staffmode.tphereall")) {
                if(args.length == 0) {
                    for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                        online.add(all);
                        if(online.contains(p)) {
                            online.remove(p);
                        }
                    }

                    if(online.size() == 0) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Messages.not_enought_players")));
                    }else {
                        Player ps = (Player) online.get(online.size());
                        ps.teleport(p);
                        String send = PlaceholderAPI.setPlaceholders(p, messages.getString("Teleport.tphere_send"));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Teleport.tphereall")));
                        ps.sendMessage(ChatColor.translateAlternateColorCodes('&', send
                                .replaceAll("%player%", p.getName())));
                        return true;
                    }
                }

                if(args.length >= 1) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("CorrectUsages.tphereall")));
                    return true;
                }
            }else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Messages.no_permissions")));
                return true;
            }
        }
        return false;
    }

}
