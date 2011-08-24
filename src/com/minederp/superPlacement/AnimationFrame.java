package com.minederp.superPlacement;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class AnimationFrame {
	public AnimationFrame(Location location, Animation animation) {
		FrameTrigger = location;
		Pieces = new ArrayList<Location>();
		Types = new ArrayList<Integer>();
		Datas = new ArrayList<Byte>();
		parentAnimation = animation;
	}

	public List<Location> Pieces;
	public List<Integer> Types;
	public List<Byte> Datas;
	public Location FrameTrigger;
	public Animation parentAnimation;

}