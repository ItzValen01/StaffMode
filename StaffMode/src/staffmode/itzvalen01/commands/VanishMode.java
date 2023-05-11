package staffmode.itzvalen01.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import staffmode.itzvalen01.Main;

public class VanishMode implements CommandExecutor{

    private Main plugin;

    public VanishMode(Main plugin) {
        this.plugin = plugin;
    }

    public static ArrayList<Player> vanishmode = new ArrayList<Player>();

    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou cannot execute this command from the console."));
            return true;
        }else {
            Player p = (Player) sender;
            FileConfiguration messages = plugin.getMessages();
            if(p.hasPermission("staffmode.vanish")) {
                if(!vanishmode.contains(p)) {
                    vanishmode.add(p);
                    BukkitScheduler schedule = Bukkit.getServer().getScheduler();
                    schedule.scheduleSyncRepeatingTask(plugin, new Runnable() {
                        public void run() {
                            for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                                if(VanishMode.vanishmode.contains(p)) {
                                    if(!all.hasPermission("staffmode.vanish")) {
                                        all.hidePlayer(p);
                                    }else if(all.hasPermission("staffmode.vanish")) {
                                        all.showPlayer(p);
                                    }
                                }else {
                                    all.showPlayer(p);
                                }
                            }
                        }
                    },0,20);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("VanishMode.activated")));
                    return true;
                }else if(vanishmode.contains(p)) {
                    vanishmode.remove(p);
                    for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                        all.showPlayer(p);
                    }
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("VanishMode.desactivated")));
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
