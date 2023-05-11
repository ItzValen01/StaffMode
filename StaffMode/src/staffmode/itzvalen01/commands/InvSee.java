package staffmode.itzvalen01.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import staffmode.itzvalen01.Main;

public class InvSee implements CommandExecutor {

    private Main plugin;

    public InvSee(Main plugin){
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou cannot execute this command from the console."));
            return true;
        }else {
            Player p = (Player) sender;
            FileConfiguration messages = plugin.getMessages();
            if(p.hasPermission("staffmode.invsee")){
                if(args.length == 1){
                    Player t = Bukkit.getPlayer(args[0]);
                    String text = PlaceholderAPI.setPlaceholders(t, messages.getString("Messages.invsee"));
                    if(t != null){
                        p.openInventory(t.getInventory());
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', text
                                .replaceAll("%player%", t.getName())));
                        return true;
                    }else{
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Messages.player_offline")));
                        return true;
                    }
                }

                if(args.length <= 0 || args.length >= 2){
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("CorrectUsages.invsee")));
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
