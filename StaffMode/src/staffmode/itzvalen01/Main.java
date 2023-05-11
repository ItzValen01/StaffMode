package staffmode.itzvalen01;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import staffmode.itzvalen01.commands.*;
import staffmode.itzvalen01.events.*;

public class Main extends JavaPlugin{
    public String rutaConfig;
    private FileConfiguration messages = null;
    private File messagesFile = null;
    private FileConfiguration items = null;
    private File itemsFile = null;
    private FileConfiguration menu = null;
    private File menuFile = null;

    PluginDescriptionFile description = getDescription();

    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b----------------------------------------"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "		&bStaffMode"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Status: &a&lActivated"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Version: &b"+description.getVersion()));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Author: &bItzValen01"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b----------------------------------------"));
        registerCommands();
        registerEvents();
        registerConfig();
        registerMessages();
        registerItems();
        registerMenu();

        // ScoreBoard
        StaffBoard staffboard = new StaffBoard(this);
        staffboard.createBoard(Integer.valueOf(getConfig().getInt("StaffBoard.ticks")));

        // Variables
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Variables(this).register();
        }
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b----------------------------------------"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "		&bStaffMode"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Status: &c&lDesactivated"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Version: &b"+description.getVersion()));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Author: &bItzValen01"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b----------------------------------------"));
    }

    public void registerCommands() {
        this.getCommand("mod").setExecutor(new ModMode(this));
        this.getCommand("staffchat").setExecutor(new StaffChat(this));
        this.getCommand("adminchat").setExecutor(new AdminChat(this));
        this.getCommand("vanish").setExecutor(new VanishMode(this));
        this.getCommand("freeze").setExecutor(new Freeze(this));
        this.getCommand("unfreeze").setExecutor(new Unfreeze(this));
        this.getCommand("ss").setExecutor(new SS(this));
        this.getCommand("tp").setExecutor(new Teleport(this));
        this.getCommand("tphere").setExecutor(new TPHere(this));
        this.getCommand("tphereall").setExecutor(new TPHereAll(this));
        this.getCommand("request").setExecutor(new Request(this));
        this.getCommand("report").setExecutor(new Report(this));
        this.getCommand("invsee").setExecutor(new InvSee(this));
    }

    public void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ModListener(this), this);
        pm.registerEvents(new FreezeListener(this), this);
        pm.registerEvents(new StaffJoin(this), this);
        pm.registerEvents(new VanishListener(this), this);
    }

    // Config.yml
    public void registerConfig() {
        File config = new File(this.getDataFolder(),"config.yml");
        rutaConfig = config.getPath();
        if(!config.exists()) {
            this.getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }

    // Messages.yml
    public FileConfiguration getMessages() {
        if(messages == null) {
            reloadMessages();
        }
        return messages;
    }

    public void reloadMessages() {
        if(messages == null) {
            messagesFile = new File(getDataFolder(),"messages.yml");

        }
        messages = YamlConfiguration.loadConfiguration(messagesFile);
        Reader defConfigStream;
        try {
            defConfigStream = new InputStreamReader(this.getResource("messages.yml"),"UTF8");
            if(defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                messages.setDefaults(defConfig);
            }
        }catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void saveMessages() {
        try {
            messages.save(messagesFile);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void registerMessages() {
        messagesFile = new File(this.getDataFolder(),"messages.yml");
        if(!messagesFile.exists()) {
            this.getMessages().options().copyDefaults(true);
            saveMessages();
        }
    }

    // items.yml
    public FileConfiguration getItems() {
        if(items == null) {
            reloadItems();
        }
        return items;
    }

    public void reloadItems() {
        if(items == null) {
            itemsFile = new File(getDataFolder(),"items.yml");

        }
        items = YamlConfiguration.loadConfiguration(itemsFile);
        Reader defConfigStream;
        try {
            defConfigStream = new InputStreamReader(this.getResource("items.yml"),"UTF8");
            if(defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                items.setDefaults(defConfig);
            }
        }catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void saveItems() {
        try {
            items.save(itemsFile);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void registerItems() {
        itemsFile = new File(this.getDataFolder(),"items.yml");
        if(!itemsFile.exists()) {
            this.getItems().options().copyDefaults(true);
            saveItems();
        }
    }

    // menu.yml
    public FileConfiguration getMenu() {
        if(menu == null) {
            reloadMenu();
        }
        return menu;
    }

    public void reloadMenu() {
        if(menu == null) {
            menuFile = new File(getDataFolder(),"menus.yml");

        }
        menu = YamlConfiguration.loadConfiguration(menuFile);
        Reader defConfigStream;
        try {
            defConfigStream = new InputStreamReader(this.getResource("menus.yml"),"UTF8");
            if(defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                menu.setDefaults(defConfig);
            }
        }catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void saveMenu() {
        try {
            menu.save(menuFile);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void registerMenu() {
        menuFile = new File(this.getDataFolder(),"menus.yml");
        if(!menuFile.exists()) {
            this.getMenu().options().copyDefaults(true);
            saveMenu();
        }
    }

}
