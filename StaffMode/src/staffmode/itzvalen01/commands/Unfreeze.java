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

public class Unfreeze implements CommandExecutor{

    private Main plugin;

    public Unfreeze(Main plugin) {
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
            if(p.hasPermission("staffmode.freeze")) {
                if(args.length == 1) {
                    Player t = Bukkit.getPlayer(args[0]);
                    if(t != null) {
                        if(Freeze.ss_players.contains(t)) {
                            Freeze.ss_players.remove(t);
                            for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                                if(all.hasPermission("staffmode.freeze")) {
                                    String msg = PlaceholderAPI.setPlaceholders(t, messages.getString("Freeze.unfrozen"));
                                    all.sendMessage(ChatColor.translateAlternateColorCodes('&', msg
                                            .replaceAll("%player%", t.getName())
                                            .replaceAll("%staff%", p.getName())));
                                }
                            }
                            return true;
                        }else {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Freeze.already_unfrozen")));
                            return true;
                        }
                    }else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Messages.player_offline")));
                        return true;
                    }
                }

                if(args.length == 0 ||args.length >= 2) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("CorrectUsages.unfreeze")));
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
