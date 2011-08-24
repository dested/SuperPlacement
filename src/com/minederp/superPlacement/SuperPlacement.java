package com.minederp.superPlacement;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.java.JavaPlugin;

import com.minederp.superPlacement.commands.*;
import com.minederp.superPlacement.listeners.MCBlockListener;
import com.minederp.superPlacement.listeners.MCEntityListener;
import com.minederp.superPlacement.listeners.MCPlayerListener;
import com.minederp.superPlacement.util.Conf;
import com.minederp.superPlacement.util.InventoryStasher;
import com.minederp.superPlacement.util.MC;
import com.minederp.superPlacement.util.mysqlWrapper;

public class SuperPlacement extends JavaPlugin {

	public static mysqlWrapper mysql = new mysqlWrapper();

	public TrackedBlockManager manager;
	public InventoryStasher inventoryStasher;

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

		inventoryStasher = new InventoryStasher(this);

		getCommand("animation").setExecutor(new animation(this));

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

	@Override
	public void onDisable() {

		MC.log("Disabled");
	}

}
