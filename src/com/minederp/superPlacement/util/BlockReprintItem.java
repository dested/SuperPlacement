package com.minederp.superPlacement.util;

import org.bukkit.World;

public class BlockReprintItem {
	String Key;
	final boolean drop;
	final World World;

	public BlockReprintItem(World w,int x2, int y2, int z2, int typeId, byte data, String key) {
		World = w;
		X = x2;
		Y = y2;
		Z = z2;
		Data = data;
		Type = typeId;
		Key = key;
		this.drop = false;
	}

	public BlockReprintItem(World w,int x2, int y2, int z2, int typeId, byte data, String key, boolean drop) {
		World=w;
		X = x2;
		Y = y2;
		Z = z2;
		Data = data;
		Type = typeId;
		Key = key;
		this.drop = drop;
	}

	int Type;
	byte Data;
	int X;
	int Y;
	int Z;
}