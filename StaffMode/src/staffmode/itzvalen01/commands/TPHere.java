package staffmode.itzvalen01.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import staffmode.itzvalen01.Main;

public class TPHere implements CommandExecutor{

    private Main plugin;

    public TPHere(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou cannot execute this command from the console."));
            return true;
        }else {
            Player p = (Player) sender;
            FileConfiguration messages = plugin.getMessages();
            if(p.hasPermission("staffmode.tphere")) {
                if(args.length == 1) {
                    Player t = Bukkit.getPlayer(args[0]);
                    if(t != null) {
                        t.teleport(p);
                        String tp = PlaceholderAPI.setPlaceholders(t, messages.getString("Teleport.tphere"));
                        String send = PlaceholderAPI.setPlaceholders(t, messages.getString("Teleport.tphere_send"));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', tp
                                .replaceAll("%player%", t.getName())));
                        t.sendMessage(ChatColor.translateAlternateColorCodes('&', send
                                .replaceAll("%player%", p.getName())));
                        return true;
                    }else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Messages.player_offline")));
                        return true;
                    }
                }

                if(args.length == 0 ||args.length >= 2) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("CorrectUsages.tphere")));
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
