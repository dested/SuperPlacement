package com.minederp.superPlacement.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

import com.minederp.superPlacement.SuperPlacement; 

public class MCEntityListener extends EntityListener {

	private final SuperPlacement main;
    
    public MCEntityListener(SuperPlacement main) {
		this.main = main;
	}


	@Override
    public void onEntityDamage(EntityDamageEvent event) { 
    }
    
}
