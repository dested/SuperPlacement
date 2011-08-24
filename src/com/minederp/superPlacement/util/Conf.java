//Config
//v1.1.0
package com.minederp.superPlacement.util;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class Conf {
	public static Configuration config;
	public static Map<String,String> sql = new HashMap<String,String>();
	public static Map<String,Map<String,Integer>> kits = new HashMap<String,Map<String,Integer>>();
	
	//Example Default
	static public void loadDefault() {
		sql.put("user","minecore");
		sql.put("pass","default");
		sql.put("host","localhost");
		sql.put("port", "3306");
		Map<String,Integer> defaultKit = new HashMap<String, Integer>();
		defaultKit.put("time",24);
		defaultKit.put("dirt",16);
		defaultKit.put("sand",16);
		kits.put("default",defaultKit);
	}
	
	static public void load(JavaPlugin jp, String filename) {
		File file = new File(jp.getDataFolder(),filename+".yml");
		config = new Configuration(file);
		if (! file.exists()) {
			MC.log(filename+".yml doesn't exist - creating new file.");
			loadDefault();
			config.setHeader(filename);
			save();
			return;
		}
		config.load();
		List<String> keys = config.getKeys();
		Field[] list = Conf.class.getDeclaredFields();
		for (int i=1;i<list.length;i++) {
			if (keys.contains(list[i].getName())) {
				try {
					list[i].set(null, config.getProperty(list[i].getName()));
				} catch (Exception ex) {
					MC.log("Error loading '"+list[i].getName()+"' in "+filename+".yml - default used.");
				}
			} else {
				MC.log("'"+list[i].getName() +"' doesn't exist in "+filename+".yml - default used.");
			}
		}
		save();
	}

	public static void reload() {
		config.load();
		List<String> keys = config.getKeys();
		Field[] list = Conf.class.getDeclaredFields();
		for (int i=1;i<list.length;i++) {
			if (keys.contains(list[i].getName())) {
				try {
					list[i].set(null, config.getProperty(list[i].getName()));
				} catch (Exception ex) {
					MC.log("Error loading '"+config.getHeader()+"' in "+config.getHeader()+".yml - default used.");
				}
			} else {
				MC.log("'"+list[i].getName() +"' doesn't exist in "+config.getHeader()+".yml - default used.");
			}
		}
		save();
 	}
	

	static public void save() {
		Field[] list = Conf.class.getDeclaredFields();
		for (int i=1;i<list.length;i++) {
			try {
				config.setProperty(list[i].getName(), list[i].get(null));
			} catch (Exception ex) {
				MC.log("Error saving to "+config.getHeader()+".yml");
			}
		}
		config.save();
	}


}
