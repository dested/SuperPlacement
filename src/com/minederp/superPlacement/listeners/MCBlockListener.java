/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minederp.superPlacement.listeners;

import java.util.Random;


import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.BlockSpreadEvent;

import com.minederp.superPlacement.SuperPlacement;


public class MCBlockListener extends BlockListener {
	int MushroomSpreadRate = 75;
	private final SuperPlacement main;

	public MCBlockListener(SuperPlacement main) {
		this.main = main;
	}

	public void onBlockDamage(BlockDamageEvent event) {


	}


    public void onBlockRedstoneChange(BlockRedstoneEvent event) {
    	
    		SuperPlacement.manager.sendCurrent(event.getBlock(),event.getNewCurrent()>0);
    }

	@Override
	public void onBlockSpread(BlockSpreadEvent event) {


	}

}