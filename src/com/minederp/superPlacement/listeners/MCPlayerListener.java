package com.minederp.superPlacement.listeners;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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

	public void onPlayerInteract(PlayerInteractEvent event) {

		switch (event.getAction()) {
		case LEFT_CLICK_AIR:
			break;
		case LEFT_CLICK_BLOCK:
			if (event.getClickedBlock().getType() == Material.WALL_SIGN) {
				main.manager.clickedSign(event.getPlayer(), (Sign) event
						.getClickedBlock().getState());
			}
			break;
		case PHYSICAL:
			break;
		case RIGHT_CLICK_AIR:
			break;
		case RIGHT_CLICK_BLOCK:
			break;

		}

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
