package staffmode.itzvalen01.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.clip.placeholderapi.PlaceholderAPI;
import staffmode.itzvalen01.Main;
import staffmode.itzvalen01.commands.Freeze;

public class FreezeListener implements Listener{

    private Main plugin;
    int taskID;

    public FreezeListener(Main plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage();
        FileConfiguration config = plugin.getConfig();
        if(Freeze.ss_players.contains(p)) {
            e.setCancelled(true);
            if(config.getString("Config.frozen_chat").equals("true")) {
                String format = PlaceholderAPI.setPlaceholders(p, config.getString("Config.frozen_chat_format"));
                for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                    if(all.hasPermission("staffmode.freeze")) {
                        all.sendMessage(ChatColor.translateAlternateColorCodes('&', format
                                .replaceAll("%player%", p.getName())
                                .replaceAll("%message%", msg)));
                    }
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void LoggoutFrozen(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        FileConfiguration messages = plugin.getMessages();
        if(Freeze.ss_players.contains(p)) {
            String text = PlaceholderAPI.setPlaceholders(p, messages.getString("Freeze.loggout_frozen"));
            for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                if(all.hasPermission("staffmode.freeze")) {
                    all.sendMessage(ChatColor.translateAlternateColorCodes('&', text
                            .replaceAll("%player%", p.getName())));
                }
            }
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        FileConfiguration messages = plugin.getMessages();
        if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            if(Freeze.ss_players.contains(e.getEntity())) {
                Player t = (Player) e.getDamager();
                Player p = (Player) e.getEntity();
                e.setCancelled(true);
                String damager = PlaceholderAPI.setPlaceholders(p, messages.getString("Freeze.player_frozen"));
                t.sendMessage(ChatColor.translateAlternateColorCodes('&', damager
                        .replaceAll("%player%", p.getName())));
            }

            if(Freeze.ss_players.contains(e.getDamager())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            if(Freeze.ss_players.contains(e.getEntity())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onChangeFoodLeven(FoodLevelChangeEvent e) {
        if(e.getEntity() instanceof Player) {
            if(Freeze.ss_players.contains(e.getEntity())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if(Freeze.ss_players.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if(Freeze.ss_players.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(Freeze.ss_players.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if(Freeze.ss_players.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        if(Freeze.ss_players.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(Freeze.ss_players.contains(e.getWhoClicked())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if(Freeze.ss_players.contains(e.getPlayer())) {
            Double x = e.getFrom().getX();
            Double y = e.getFrom().getY();
            Double z = e.getFrom().getZ();
            Float yaw = e.getTo().getYaw();
            Float pitch = e.getTo().getPitch();
            Location from = new Location(e.getFrom().getWorld() ,x, y, z, yaw, pitch);
            e.setTo(from);
        }
    }

}
