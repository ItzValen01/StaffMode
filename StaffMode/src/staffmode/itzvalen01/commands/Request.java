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

public class Request implements CommandExecutor{

    private Main plugin;

    public Request(Main plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou cannot execute this command from the console."));
            return true;
        }else {
            Player p = (Player) sender;
            FileConfiguration config = plugin.getConfig();
            FileConfiguration messages = plugin.getMessages();
            if(config.getString("Config.Modules.Request").equals("true")) {
                if(args.length >= 1) {
                    for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                        String msg = "";
                        for(int i=0;i<args.length;i++) {
                            msg = msg + args[i] + " ";
                        }

                        if(all.hasPermission("staffmode.request")) {
                            String text = PlaceholderAPI.setPlaceholders(p, messages.getString("Request.notify"));
                            all.sendMessage(ChatColor.translateAlternateColorCodes('&', text
                                    .replaceAll("%player%", p.getName())
                                    .replaceAll("%message%", msg)));
                        }
                    }
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Request.successfully_sent")));
                    return true;
                }

                if(args.length <= 0) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("CorrectUsages.request")));
                    return true;
                }
            }
        }
        return false;
    }

}
