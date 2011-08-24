package com.minederp.superPlacement;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Timer;
 
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.java.JavaPlugin;

import com.minederp.superPlacement.commands.*;
import com.minederp.superPlacement.listeners.MCBlockListener;
import com.minederp.superPlacement.listeners.MCEntityListener;
import com.minederp.superPlacement.listeners.MCPlayerListener;
import com.minederp.superPlacement.util.ALH;
import com.minederp.superPlacement.util.Conf;
import com.minederp.superPlacement.util.InventoryStasher;
import com.minederp.superPlacement.util.MC;
import com.minederp.superPlacement.util.Tuple2;
import com.minederp.superPlacement.util.Tuple3;
import com.minederp.superPlacement.util.Tuple4;
import com.minederp.superPlacement.util.mysqlWrapper;

public class SuperPlacement extends JavaPlugin {

	public static mysqlWrapper mysql = new mysqlWrapper();
	 
	@Override
	public void onEnable() {
		MC.load(this);

		Conf.load(this, "config");
		try {
			mysql.connectDatabase();
		} catch (Exception e) {
			e.printStackTrace();// maybe set mysql to null and check if its null
								// before using it.
		}

		int idToTrack = 14;// 14 diamond
		System.out.print("WTF");
	  
		inventoryStasher = new InventoryStasher(this); 

		// Actions
		getCommand("wild").setExecutor(new animation(this)); 

		MCPlayerListener playerListener = new MCPlayerListener(this);
		MC.registerEvent(Type.PLAYER_MOVE, playerListener, Priority.Highest);
		MC.registerEvent(Type.PLAYER_COMMAND_PREPROCESS, playerListener,
				Priority.Lowest);

		MC.registerEvent(Type.PLAYER_JOIN, playerListener, Priority.Lowest);
		MC.registerEvent(Type.PLAYER_QUIT, playerListener, Priority.Lowest);

		MCEntityListener entityListener = new MCEntityListener(this);
		MC.registerEvent(Type.ENTITY_DAMAGE, entityListener, Priority.Highest);
		
		
		MCBlockListener blockListener = new MCBlockListener(this);
		MC.registerEvent(Type.BLOCK_SPREAD, blockListener, Priority.Highest);
		MC.registerEvent(Type.BLOCK_DAMAGE, blockListener, Priority.Highest);
		MC.registerEvent(Type.REDSTONE_CHANGE, blockListener, Priority.Highest);

		MC.log("Enabled");
 

	} 

	static int numOfTicks = 0;
	public InventoryStasher inventoryStasher;

	@Override
	public void onDisable() {

		
		MC.log("Disabled");
	}
	public static TrackedBlockManager manager;
	
}
