package staffmode.itzvalen01.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import staffmode.itzvalen01.Main;
import staffmode.itzvalen01.commands.AdminChat;
import staffmode.itzvalen01.commands.Freeze;
import staffmode.itzvalen01.commands.ModMode;
import staffmode.itzvalen01.commands.StaffChat;
import staffmode.itzvalen01.commands.VanishMode;
import staffmode.itzvalen01.menus.Inspector;

public class ModListener implements Listener{

    private Main plugin;

    public ModListener(Main plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onStaffChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage();
        FileConfiguration config = plugin.getConfig();
        FileConfiguration messages = plugin.getMessages();
        if(config.getString("Config.Modules.StaffChat").equals("true")) {
            if(StaffChat.staffchat.contains(p)) {
                e.setCancelled(true);
                String format = PlaceholderAPI.setPlaceholders(p, messages.getString("Chats.Staff.format"));
                for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                    if(all.hasPermission("staffmode.chat.staff")) {
                        all.sendMessage(ChatColor.translateAlternateColorCodes('&', format
                                .replaceAll("%staff%", p.getName())
                                .replaceAll("%message%", msg)));
                    }
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onAdminChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage();
        FileConfiguration config = plugin.getConfig();
        FileConfiguration messages = plugin.getMessages();
        if(config.getString("Config.Modules.AdminChat").equals("true")) {
            if(AdminChat.adminchat.contains(p)) {
                e.setCancelled(true);
                String format = PlaceholderAPI.setPlaceholders(p, messages.getString("Chats.Admin.format"));
                for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                    if(all.hasPermission("staffmode.chat.admin")) {
                        all.sendMessage(ChatColor.translateAlternateColorCodes('&', format
                                .replaceAll("%staff%", p.getName())
                                .replaceAll("%message%", msg)));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if(ModMode.modmode.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if(ModMode.modmode.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(ModMode.modmode.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player) {
            if(ModMode.modmode.contains(e.getEntity())) {
                e.setCancelled(true);
            }
        }

        if(e.getDamager() instanceof Player) {
            if(ModMode.modmode.contains(e.getDamager())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void ChangeFoodLevent(FoodLevelChangeEvent e) {
        if(e.getEntity() instanceof Player) {
            if(ModMode.modmode.contains(e.getEntity())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            if(ModMode.modmode.contains(e.getEntity())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {
        if(ModMode.modmode.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickUpItem(PlayerPickupItemEvent e) {
        if(ModMode.modmode.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMoveItems(InventoryClickEvent e) {
        if(ModMode.modmode.contains(e.getWhoClicked())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void inspectInteract(InventoryClickEvent e) {
        FileConfiguration menu = plugin.getMenu();
        if(e.getInventory().getName().equals(ChatColor.translateAlternateColorCodes('&', menu.getString("Inspector.name")))) {
            e.setCancelled(true);
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onSS(PlayerInteractEntityEvent e) {
        FileConfiguration messages = plugin.getMessages();
        FileConfiguration items = plugin.getItems();
        if(e.getRightClicked() instanceof Player) {
            if(ModMode.modmode.contains(e.getPlayer())) {
                if(e.getPlayer().getItemInHand().getType() == Material.valueOf(items.getString("ModMode.Freeze.material"))) {
                    if(!ModMode.modmode.contains(e.getRightClicked())) {
                        if(!Freeze.ss_players.contains(e.getRightClicked())) {
                            Freeze.ss_players.add((Player) e.getRightClicked());
                            for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                                if(all.hasPermission("staffmode.freeze")) {
                                    all.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Freeze.frozen")
                                            .replaceAll("%player%", ((Player) e.getRightClicked()).getName())
                                            .replaceAll("%staff%", e.getPlayer().getName())));
                                }
                            }
                            List<String> text = messages.getStringList("Freeze.frozen_alert");
                            BukkitScheduler schedule = Bukkit.getServer().getScheduler();
                            schedule.scheduleSyncRepeatingTask(plugin, new Runnable() {
                                public void run() {
                                    if(Freeze.ss_players.contains((Player) e.getRightClicked())){
                                        for(int i=0;i<text.size();i++) {
                                            ((Player) e.getRightClicked()).sendMessage(ChatColor.translateAlternateColorCodes('&', text.get(i)));
                                        }
                                    }
                                }
                            }, 0, 300);
                        }else if(Freeze.ss_players.contains((Player) e.getRightClicked())) {
                            Freeze.ss_players.remove((Player) e.getRightClicked());
                            for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                                if(all.hasPermission("staffmode.freeze")) {
                                    all.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Freeze.unfrozen")
                                            .replaceAll("%player%", ((Player) e.getRightClicked()).getName())
                                            .replaceAll("%staff%", e.getPlayer().getName())));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInspect(PlayerInteractEntityEvent e) {
        FileConfiguration items = plugin.getItems();
        FileConfiguration menu = plugin.getMenu();
        if(e.getRightClicked() instanceof Player) {
            if(ModMode.modmode.contains(e.getPlayer())) {
                if(e.getPlayer().getItemInHand().getType() == Material.valueOf(items.getString("ModMode.Inspector.material"))) {
                    Inspector.Inspect(e.getPlayer(), e.getRightClicked(), menu);
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onRandomTP(PlayerInteractEvent e) {
        FileConfiguration items = plugin.getItems();
        FileConfiguration messages = plugin.getMessages();
        Random r = new Random();
        ArrayList<Player> all = new ArrayList<Player>();
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(e.getPlayer().getItemInHand().getType() == Material.valueOf(items.getString("ModMode.RandomTP.material"))) {
                if(ModMode.modmode.contains(e.getPlayer())) {
                    for(Player online : Bukkit.getServer().getOnlinePlayers()) {
                        all.add(online);
                        if(all.contains(e.getPlayer())) {
                            all.remove(e.getPlayer());
                        }
                    }
                    if(all.size() == 0) {
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Messages.not_enought_players")));
                    }else {
                        int index = r.nextInt(all.size());
                        Player loc = (Player) all.get(index);
                        e.getPlayer().teleport(loc);
                        String msg = PlaceholderAPI.setPlaceholders(loc, messages.getString("Teleport.tp_player"));
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', msg
                                .replaceAll("%player%", loc.getName())));
                    }
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onVanish(PlayerInteractEvent e) {
        FileConfiguration items = plugin.getItems();
        FileConfiguration messages = plugin.getMessages();
        if(e.getAction() == Action.RIGHT_CLICK_AIR ||e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(ModMode.modmode.contains(e.getPlayer())) {
                if(e.getPlayer().getItemInHand().getType() == Material.valueOf(items.getString("ModMode.Vanish.material"))) {
                    if(VanishMode.vanishmode.contains(e.getPlayer())) {
                        ItemStack visible = new ItemStack(Material.valueOf(items.getString("ModMode.Vanish.material")),1,
                                (short) items.getInt("ModMode.Vanish.Visible.value"));
                        ItemMeta visibleMeta = visible.getItemMeta();
                        visibleMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', items.getString("ModMode.Vanish.Visible.name")));
                        ArrayList<String> visibleLore = new ArrayList<String>();
                        for(String output : items.getStringList("ModMode.Vanish.Visible.lore")) {
                            visibleLore.add(ChatColor.translateAlternateColorCodes('&', output));
                        }
                        visibleMeta.setLore(visibleLore);
                        visible.setItemMeta(visibleMeta);

                        e.getPlayer().getInventory().setItem(items.getInt("ModMode.Vanish.slot"), visible);

                        VanishMode.vanishmode.remove(e.getPlayer());
                        for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                            all.showPlayer(e.getPlayer());
                        }
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("VanishMode.desactivated")));
                    }else if(!VanishMode.vanishmode.contains(e.getPlayer())) {
                        ItemStack hidden = new ItemStack(Material.valueOf(items.getString("ModMode.Vanish.material")),1,
                                (short) items.getInt("ModMode.Vanish.Hidden.value"));
                        ItemMeta hiddenMeta = hidden.getItemMeta();
                        hiddenMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', items.getString("ModMode.Vanish.Hidden.name")));
                        ArrayList<String> hiddenLore = new ArrayList<String>();
                        for(String output : items.getStringList("ModMode.Vanish.Hidden.lore")) {
                            hiddenLore.add(ChatColor.translateAlternateColorCodes('&', output));
                        }
                        hiddenMeta.setLore(hiddenLore);
                        hidden.setItemMeta(hiddenMeta);

                        e.getPlayer().getInventory().setItem(items.getInt("ModMode.Vanish.slot"), hidden);

                        VanishMode.vanishmode.add(e.getPlayer());
                        for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                            if(!all.hasPermission("staffmode.vanish")) {
                                all.hidePlayer(e.getPlayer());
                            }else if(all.hasPermission("staffmode.vanish")) {
                                all.showPlayer(e.getPlayer());
                            }
                        }
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("VanishMode.activated")));
                    }
                }
            }
        }
    }

}
