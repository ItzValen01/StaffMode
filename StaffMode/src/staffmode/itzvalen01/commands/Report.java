package staffmode.itzvalen01.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import staffmode.itzvalen01.Main;

public class Report implements CommandExecutor{

    private Main plugin;

    public Report(Main plugin) {
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
            if(config.getString("Config.Modules.Reports").equals("true")) {
                if(args.length >= 2) {
                    OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
                    String razon = "";
                    for(int i=1;i<args.length;i++) {
                        razon = razon + args[i] + " ";

                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Report.successfully_sent")));

                        List<String> text = messages.getStringList("Report.notify");
                        for(Player all : Bukkit.getServer().getOnlinePlayers()){
                            if(all.hasPermission("staffmode.reports")) {
                                for(int o=0;o<text.size();o++) {
                                    all.sendMessage(ChatColor.translateAlternateColorCodes('&', text.get(o)
                                            .replaceAll("%report_by%", p.getName())
                                            .replaceAll("%reported%", t.getName())
                                            .replaceAll("%razon%", razon)));
                                }
                                TextComponent msg = new TextComponent();
                                msg.setText(ChatColor.translateAlternateColorCodes('&', messages.getString("Report.click_message")
                                        .replaceAll("%reported%", t.getName())));
                                msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + t.getName()));
                                all.spigot().sendMessage(msg);
                                return true;
                            }
                        }
                    }
                }

                if(args.length <= 1) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("CorrectUsages.report")));
                    return true;
                }
            }
        }
        return false;
    }

}
