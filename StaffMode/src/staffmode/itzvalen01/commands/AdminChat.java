package staffmode.itzvalen01.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import staffmode.itzvalen01.Main;

public class AdminChat implements CommandExecutor{

    private Main plugin;

    public AdminChat(Main plugin) {
        this.plugin = plugin;
    }

    public static ArrayList<Player> adminchat = new ArrayList<Player>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou cannot execute this command from the console."));
            return true;
        }else {
            Player p = (Player) sender;
            FileConfiguration config = plugin.getConfig();
            FileConfiguration messages = plugin.getMessages();
            if(config.getString("Config.Modules.AdminChat").equals("true")) {
                if(p.hasPermission("staffmode.chat.admin")) {
                    if(args.length == 0) {
                        if(!adminchat.contains(p) && !StaffChat.staffchat.contains(p)) {
                            adminchat.add(p);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Chats.Admin.activated")));
                            return true;
                        }else if(adminchat.contains(p)) {
                            adminchat.remove(p);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Chats.Admin.desactivated")));
                            return true;
                        }else if(!adminchat.contains(p) && StaffChat.staffchat.contains(p)) {
                            StaffChat.staffchat.remove(p);
                            adminchat.add(p);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Chats.Admin.activated")));
                            return true;
                        }
                    }

                    if(args.length >= 1) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("CorrectUsages.adminchat")));
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
