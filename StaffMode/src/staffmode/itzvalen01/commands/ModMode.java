package staffmode.itzvalen01.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;

import staffmode.itzvalen01.Main;

public class ModMode implements CommandExecutor{

    private Main plugin;

    public ModMode(Main plugin) {
        this.plugin = plugin;
    }

    public static ArrayList<Player> modmode = new ArrayList<Player>();
    public static Map<Player, ItemStack[]> parmor = new HashMap<Player, ItemStack[]>();
    public static Map<Player, ItemStack[]> pitems = new HashMap<Player, ItemStack[]>();

    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou cannot execute this command from the console."));
            return true;
        }else {
            Player p = (Player) sender;
            FileConfiguration messages = plugin.getMessages();
            FileConfiguration items = plugin.getItems();
            if(p.hasPermission("staffmode.mod")) {
                if(args.length == 0) {
                    if(modmode.contains(p)) {
                        modmode.remove(p);
                        p.getInventory().clear();
                        p.setGameMode(GameMode.SURVIVAL);
                        VanishMode.vanishmode.remove(p);
                        for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                            all.showPlayer(p);
                        }
                        if(pitems.containsKey(p)) {
                            p.getInventory().setContents(pitems.get(p));
                        }
                        if(parmor.containsKey(p)) {
                            p.getInventory().setArmorContents(parmor.get(p));
                        }
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("ModMode.desactivated")));
                        return true;
                    }else if(!modmode.contains(p)) {
                        modmode.add(p);
                        parmor.put(p, p.getInventory().getArmorContents());
                        pitems.put(p, p.getInventory().getContents());
                        p.getInventory().clear();
                        StaffItems(p,items);
                        VanishMode.vanishmode.add(p);
                        p.setGameMode(GameMode.CREATIVE);
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
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("ModMode.activated")));
                        return true;
                    }
                }

                if(args.length >= 1) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("CorrectUsages.modmode")));
                    return true;
                }
            }else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Messages.no_permissions")));
                return true;
            }
        }
        return false;
    }


    public void StaffItems(Player p, FileConfiguration items) {

        ItemStack compass = new ItemStack(Material.valueOf(items.getString("ModMode.Compass.material")),1,
                (short) items.getInt("ModMode.Compass.value"));
        ItemMeta comMeta = compass.getItemMeta();
        comMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', items.getString("ModMode.Compass.name")));
        ArrayList<String> comLore = new ArrayList<String>();
        for(String output : items.getStringList("ModMode.Compass.lore")) {
            comLore.add(ChatColor.translateAlternateColorCodes('&', output));
        }
        comMeta.setLore(comLore);
        compass.setItemMeta(comMeta);

        ItemStack inspector = new ItemStack(Material.valueOf(items.getString("ModMode.Inspector.material")),1,
                (short) items.getInt("ModMode.Inspector.value"));
        ItemMeta insMeta = inspector.getItemMeta();
        insMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', items.getString("ModMode.Inspector.name")));
        ArrayList<String> insLore = new ArrayList<String>();
        for(String output : items.getStringList("ModMode.Inspector.lore")) {
            insLore.add(ChatColor.translateAlternateColorCodes('&', output));
        }
        insMeta.setLore(insLore);
        inspector.setItemMeta(insMeta);

        ItemStack wand = new ItemStack(Material.valueOf(items.getString("ModMode.WorldEdit_Wand.material")),1,
                (short) items.getInt("ModMode.WorldEdit_Wand.value"));
        ItemMeta wdMeta = wand.getItemMeta();
        wdMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', items.getString("ModMode.WorldEdit_Wand.name")));
        ArrayList<String> wdLore = new ArrayList<String>();
        for(String output : items.getStringList("ModMode.WorldEdit_Wand.lore")) {
            wdLore.add(ChatColor.translateAlternateColorCodes('&', output));
        }
        wdMeta.setLore(wdLore);
        wand.setItemMeta(wdMeta);

        ItemStack freeze = new ItemStack(Material.valueOf(items.getString("ModMode.Freeze.material")),1,
                (short) items.getInt("ModMode.Freeze.value"));
        ItemMeta frMeta = freeze.getItemMeta();
        frMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', items.getString("ModMode.Freeze.name")));
        ArrayList<String> frLore = new ArrayList<String>();
        for(String output : items.getStringList("ModMode.Freeze.lore")) {
            frLore.add(ChatColor.translateAlternateColorCodes('&', output));
        }
        frMeta.setLore(frLore);
        freeze.setItemMeta(frMeta);

        ItemStack randomtp = new ItemStack(Material.valueOf(items.getString("ModMode.RandomTP.material")),1,
                (short) items.getInt("ModMode.RandomTP.value"));
        ItemMeta tpMeta = randomtp.getItemMeta();
        tpMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', items.getString("ModMode.RandomTP.name")));
        ArrayList<String> tpLore = new ArrayList<String>();
        for(String output : items.getStringList("ModMode.RandomTP.lore")) {
            tpLore.add(ChatColor.translateAlternateColorCodes('&', output));
        }
        tpMeta.setLore(tpLore);
        randomtp.setItemMeta(tpMeta);

        ItemStack vanish = new ItemStack(Material.valueOf(items.getString("ModMode.Vanish.material")),1,
                (short) items.getInt("ModMode.Vanish.Hidden.value"));
        ItemMeta vanMeta = vanish.getItemMeta();
        vanMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', items.getString("ModMode.Vanish.Hidden.name")));
        ArrayList<String> vanLore = new ArrayList<String>();
        for(String output : items.getStringList("ModMode.Vanish.Hidden.lore")) {
            vanLore.add(ChatColor.translateAlternateColorCodes('&', output));
        }
        vanMeta.setLore(vanLore);
        vanish.setItemMeta(vanMeta);


        p.getInventory().setItem(items.getInt("ModMode.Compass.slot"), compass);
        p.getInventory().setItem(items.getInt("ModMode.Inspector.slot"), inspector);
        p.getInventory().setItem(items.getInt("ModMode.WorldEdit_Wand.slot"), wand);
        p.getInventory().setItem(items.getInt("ModMode.Freeze.slot"), freeze);
        p.getInventory().setItem(items.getInt("ModMode.RandomTP.slot"), randomtp);
        p.getInventory().setItem(items.getInt("ModMode.Vanish.slot"), vanish);
    }

}
