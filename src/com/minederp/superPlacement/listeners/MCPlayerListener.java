package com.minederp.superPlacement.listeners;


import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.minederp.superPlacement.SuperPlacement; 

public class MCPlayerListener extends PlayerListener {

	private final SuperPlacement main;

	public MCPlayerListener(SuperPlacement main) {
		this.main = main;
	}

	@Override
	public void onPlayerMove(PlayerMoveEvent event) { 
	}

	@Override
	public void onPlayerQuit(PlayerQuitEvent event) {

		 
	}

	@Override
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
	  
	}

	public void onPlayerJoin(PlayerJoinEvent event) {
 

	} 
}
