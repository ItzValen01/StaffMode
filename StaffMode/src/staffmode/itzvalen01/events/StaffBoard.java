package staffmode.itzvalen01.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import me.clip.placeholderapi.PlaceholderAPI;
import staffmode.itzvalen01.Main;
import staffmode.itzvalen01.commands.ModMode;

public class StaffBoard {

    private Main plugin;
    int taskID;

    public StaffBoard(Main plugin) {
        this.plugin = plugin;
    }

    public void createBoard(int ticks) {
        BukkitScheduler schedule = Bukkit.getServer().getScheduler();
        taskID = schedule.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @SuppressWarnings("deprecation")
            public void run() {
                FileConfiguration config = plugin.getConfig();
                for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                    updateBoard(all,config);
                }
            }
        }, 0, ticks);
    }

    public void updateBoard(Player p, FileConfiguration config) {
        if(config.getString("ScoreBoard.enabled").equals("true")) {
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard scoreboard = manager.getNewScoreboard();
            Objective objective = scoreboard.registerNewObjective("staffmode", "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("ScoreBoard.title")));
            List<String> lines = config.getStringList("ScoreBoard.lines");
            for(int i=0;i<lines.size();i++) {
                String text = PlaceholderAPI.setPlaceholders(p, lines.get(i));
                Score score = objective.getScore(ChatColor.translateAlternateColorCodes('&', text));
                score.setScore(lines.size()-(i));
                if(ModMode.modmode.contains(p)) {
                    p.setScoreboard(scoreboard);
                }else {
                    p.setScoreboard(manager.getNewScoreboard());
                }
            }
        }
    }

}
