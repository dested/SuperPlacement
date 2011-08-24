//MC
//v1.0.0

package com.minederp.superPlacement.util;

import org.bukkit.plugin.PluginDescriptionFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MC {
    public static final Logger logger = Logger.getLogger("minecraft");
    public static JavaPlugin jp;
    public static PluginManager pm;
    public static Server server;
    public static PluginDescriptionFile pdf;
	public static ChatColor ccText = ChatColor.GOLD;
	public static ChatColor ccLit = ChatColor.YELLOW;
	public static ChatColor ccWarn = ChatColor.RED;
    
    public static void load(JavaPlugin jp) {
        pdf = jp.getDescription();
        MC.jp = jp;
        MC.server = jp.getServer();
        MC.pm = jp.getServer().getPluginManager();
        jp.getDataFolder().mkdirs();
    }
    
    static void save() {
    }

//Logs
    public static void log(String msg) {
        logger.log(Level.INFO, "["+pdf.getName()+"] "+msg);
    }
    public static void log(Level lvl, String msg) {
        logger.log(lvl, "["+pdf.getName()+"] "+msg);
    }
    
//Event Register
    public static void registerEvent(Event.Type type, Listener listener, Priority priority) {
        jp.getServer().getPluginManager().registerEvent(type, listener, priority, jp);
    }
    public static void registerEvent(Event.Type type, Listener listener) {
        jp.getServer().getPluginManager().registerEvent(type, listener, Priority.Normal, jp);
    }    
    
//Message Handler
    public static void send(CommandSender target, String msg) {
        if ((target instanceof Player)) {
            target.sendMessage(ccText+msg);
            return;
        }
        target.sendMessage(ChatColor.stripColor(msg));
    } 
	
	public static String lit(String input) {
		return ccLit+input+ccText;
	}
    
	public static String warn(String input) {
		return ccWarn+input+ccText;
	}
	
}
