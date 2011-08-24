package com.minederp.superPlacement;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
 
import com.minederp.superPlacement.util.BlockReprintItem;
import com.minederp.superPlacement.util.BlockReprinter;

public class Animation {
	public Animation(Player creator, String name) {

		Frames = new ArrayList<AnimationFrame>();
		AccessiblePlayers = new ArrayList<String>();
		AccessiblePlayers.add(creator.getName());
		AnimationName = name;

	}

	public boolean CanAccess(Player p) {
		return AccessiblePlayers.contains(p.getName());
	}

	public void AssignFrame(Sign sign) {
		sign.setLine(1, "Frame Index:" + Frames.size());
		Frames.add(new AnimationFrame(sign.getBlock().getLocation(), this));
	}

	public void BeginModifyFrame(int index) {
		CurrentModifyingFrameIndex = index;

		BlockReprinter.clearReprint(AnimationName);

		AnimationFrame currentFrame = Frames.get(index);
		for (Location loc : currentFrame.Pieces) {

			BlockReprinter.addBlockForReprint(new BlockReprintItem(loc
					.getWorld(), loc.getBlockX(), loc.getBlockY(), loc
					.getBlockZ(), currentFrame.Types.get(index),
					currentFrame.Datas.get(index), AnimationName));

			loc.getBlock().setTypeIdAndData(currentFrame.Types.get(index),
					currentFrame.Datas.get(index), false);
		}

	}

	public void AddBlock(Location loc, int type, byte data) {

		loc.getBlock().setTypeId(0);

		AnimationFrame currentFrame = Frames
				.get(CurrentModifyingFrameIndex);

		currentFrame.Pieces.add(loc);
		currentFrame.Datas.add(data);
		currentFrame.Types.add(type);

		BlockReprinter.addBlockForReprint(new BlockReprintItem(loc
				.getWorld(), loc.getBlockX(), loc.getBlockY(), loc
				.getBlockZ(), type, data, AnimationName));

		loc.getBlock().setTypeIdAndData(type, data, false);

	}

	public void RemoveBlock(Location loc) {

		loc.getBlock().setTypeId(0);

		AnimationFrame currentFrame = Frames
				.get(CurrentModifyingFrameIndex);

		int ind = currentFrame.Pieces.indexOf(CurrentModifyingFrameIndex);

		currentFrame.Pieces.remove(ind);
		currentFrame.Datas.remove(ind);
		currentFrame.Types.remove(ind);

	}

	public void EndModifyFrame(int index) {
		CurrentModifyingFrameIndex = -1;
		BlockReprinter.clearReprint(AnimationName);
	}

	public List<AnimationFrame> Frames;
	public List<String> AccessiblePlayers;
	public int CurrentFrameIndex = -1;
	public int CurrentModifyingFrameIndex = -1;
	public String AnimationName;
}
