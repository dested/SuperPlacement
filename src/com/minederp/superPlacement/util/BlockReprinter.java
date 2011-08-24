package com.minederp.superPlacement.util;

import java.util.ArrayList;
import java.util.List;
 
import org.bukkit.Material;
import org.bukkit.World; 
 

public class BlockReprinter {

	private static List<BlockReprintItem> blocksForReprint = new ArrayList<BlockReprintItem>();


	public static void clearReprint(String key) {
		if (blocksForReprint.size() > 0) {

			for (int i = blocksForReprint.size() - 1; i >= 0; i--) {
				BlockReprintItem it = blocksForReprint.get(i);
				if (it.Key.equals(key)) {
					blocksForReprint.remove(i);
					if (it.drop) {
						it.World.getBlockAt(it.X, it.Y, it.Z).setType(Material.AIR);

					 
					} else {
						it.World.getBlockAt(it.X, it.Y, it.Z).setTypeIdAndData(it.Type, it.Data, false);

					}
				}
			}
		}
	}

	public static  void addBlockForReprint(BlockReprintItem item) {
		if (blocksForReprint.size() > 0) {
			for (int i = blocksForReprint.size() - 1; i >= 0; i--) {
				BlockReprintItem it = blocksForReprint.get(i);
				if (item.X == it.X && item.Y == it.Y && item.Z == it.Z) {
					return;
				}
			}
		}
		blocksForReprint.add(item);
	}

}
