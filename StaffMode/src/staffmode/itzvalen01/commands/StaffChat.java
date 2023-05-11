package staffmode.itzvalen01.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import staffmode.itzvalen01.Main;

public class StaffChat implements CommandExecutor{

    private Main plugin;

    public StaffChat(Main plugin) {
        this.plugin = plugin;
    }

    public static ArrayList<Player> staffchat = new ArrayList<Player>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou cannot execute this command from the console."));
            return true;
        }else {
            Player p = (Player) sender;
            FileConfiguration config = plugin.getConfig();
            FileConfiguration messages = plugin.getMessages();
            if(config.getString("Config.Modules.StaffChat").equals("true")) {
                if(p.hasPermission("staffmode.chat.staff")) {
                    if(args.length == 0) {
                        if(!staffchat.contains(p) && !AdminChat.adminchat.contains(p)) {
                            staffchat.add(p);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Chats.Staff.activated")));
                            return true;
                        }else if(staffchat.contains(p)) {
                            staffchat.remove(p);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Chats.Staff.desactivated")));
                            return true;
                        }else if(!staffchat.contains(p) && AdminChat.adminchat.contains(p)) {
                            AdminChat.adminchat.remove(p);
                            staffchat.add(p);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Chats.Staff.activated")));
                            return true;
                        }
                    }

                    if(args.length >= 1) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("CorrectUsages.staffchat")));
                        return true;
                    }
                }else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Messages.no_permissions")));
                    return true;
                }
            }
        }
        return false;
    }

}
