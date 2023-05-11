package staffmode.itzvalen01.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
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
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import staffmode.itzvalen01.Main;
import staffmode.itzvalen01.commands.VanishMode;

public class VanishListener implements Listener {

    private Main plugin;

    public VanishListener(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onDestroy(BlockBreakEvent e){
        if(VanishMode.vanishmode.contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        if(VanishMode.vanishmode.contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player){
            if(VanishMode.vanishmode.contains(e.getEntity())){
                e.setCancelled(true);
            }
        }

        if(e.getDamager() instanceof Player){
            if(VanishMode.vanishmode.contains(e.getDamager())){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onChangeFoodLevel(FoodLevelChangeEvent e){
        if(e.getEntity() instanceof Player){
            if(VanishMode.vanishmode.contains(e.getEntity())){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            if(VanishMode.vanishmode.contains(e.getEntity())){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        if(VanishMode.vanishmode.contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e){
        if(VanishMode.vanishmode.contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMoveItems(InventoryClickEvent e){
        if(VanishMode.vanishmode.contains(e.getWhoClicked())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onnInteractPreview(PlayerInteractEvent e){
        Player p = e.getPlayer();
        FileConfiguration config = plugin.getConfig();
        if(VanishMode.vanishmode.contains(e.getPlayer())){
            if(e.getAction() == Action.RIGHT_CLICK_BLOCK && p.getItemInHand().getType() == Material.AIR){
                if(e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType() == Material.TRAPPED_CHEST){
                    if(config.getString("Config.ChestPreview.enabled").equals("true")){
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Config.ChestPreview.text")));
                        e.setCancelled(true);

                        Block chest = e.getClickedBlock();

                        int slots = ((org.bukkit.block.Chest) chest.getState()).getInventory().getSize();

                        Inventory inv = Bukkit.createInventory(null, slots, ChatColor.translateAlternateColorCodes('&', "&fChest Preview"));

                        ItemStack[] contents = ((org.bukkit.block.Chest) chest.getState()).getInventory().getContents();

                        inv.setContents(contents);

                        p.openInventory(inv);

                    }else{
                        e.setCancelled(true);
                    }
                }else{
                    e.setCancelled(true);
                }
            }else{
                e.setCancelled(true);
            }
        }
    }

}
