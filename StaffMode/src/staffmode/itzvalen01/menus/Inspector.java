package staffmode.itzvalen01.menus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import staffmode.itzvalen01.commands.Freeze;

public class Inspector {

    public static void Inspect(Player p, Entity t, FileConfiguration menu) {
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', menu.getString("Inspector.name")));

        double healthLevel = ((Damageable) t).getHealth();
        int foodLevel = ((Player) t).getFoodLevel();
        int expLevel = ((HumanEntity) t).getExpToLevel();
        PlayerInventory ti = ((HumanEntity) t).getInventory();

        ItemStack health = new ItemStack(Material.valueOf(menu.getString("Inspector.Items.Health.material")),1,
                (short) menu.getInt("Inspector.Items.Health.value"));
        ItemMeta healMeta = health.getItemMeta();
        healMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', menu.getString("Inspector.Items.Health.name")+healthLevel));
        health.setItemMeta(healMeta);

        ItemStack food = new ItemStack(Material.valueOf(menu.getString("Inspector.Items.Food.material")),1,
                (short) menu.getInt("Inspector.Items.Food.value"));
        ItemMeta foodMeta = food.getItemMeta();
        foodMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', menu.getString("Inspector.Items.Food.name")+foodLevel));
        food.setItemMeta(foodMeta);

        ItemStack exp = new ItemStack(Material.valueOf(menu.getString("Inspector.Items.EXP.material")),1,
                (short) menu.getInt("Inspector.Items.value"));
        ItemMeta expMeta = exp.getItemMeta();
        expMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', menu.getString("Inspector.Items.EXP.name")+expLevel));
        exp.setItemMeta(expMeta);

        ItemStack ss = new ItemStack(Material.valueOf(menu.getString("Inspector.Items.Freeze.material")),1,
                (short) menu.getInt("Inspector.Items.Freeze.value"));
        ItemMeta ssMeta = ss.getItemMeta();
        if(!Freeze.ss_players.contains(t)) {
            ssMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', menu.getString("Inspector.Items.Freeze.NoFrozen")));
        }else {
            ssMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', menu.getString("Inspector.Items.Freeze.Frozen")));
        }
        ss.setItemMeta(ssMeta);

        ItemStack dec = new ItemStack(Material.valueOf(menu.getString("Inspector.Items.Decorative.material")),1,
                (short) menu.getInt("Inspector.Items.Decorative.value"));
        ItemMeta decMeta = dec.getItemMeta();
        decMeta.setDisplayName(" ");
        dec.setItemMeta(decMeta);

        ItemStack[] items = ti.getContents();

        ItemStack helmet = ti.getHelmet();
        ItemStack chestplate = ti.getChestplate();
        ItemStack leggins = ti.getLeggings();
        ItemStack boots = ti.getBoots();

        ItemStack itemHand = ti.getItemInHand();



        //Set Player Inventory Items
        inv.setContents(items);


        // Items
        inv.setItem(50, health);
        inv.setItem(51, food);
        inv.setItem(52, exp);
        inv.setItem(53, ss);

        inv.setItem(40, itemHand);
        inv.setItem(39, helmet);
        inv.setItem(38, chestplate);
        inv.setItem(37, leggins);
        inv.setItem(36, boots);

        // Decorative Items Setteds
        inv.setItem(49, dec);
        inv.setItem(48, dec);
        inv.setItem(47, dec);
        inv.setItem(46, dec);
        inv.setItem(45, dec);

        inv.setItem(44, dec);
        inv.setItem(43, dec);
        inv.setItem(42, dec);
        inv.setItem(41, dec);

        p.openInventory(inv);
    }

}
