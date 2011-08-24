package com.minederp.superPlacement;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.minederp.superPlacement.util.BlockReprintItem;
import com.minederp.superPlacement.util.BlockReprinter;

public class TrackedBlockManager {

	List<Animation> animations = new ArrayList<Animation>();


	public void sendCurrent(Block block, boolean on) {

		if (block.getType() == Material.WALL_SIGN) {
			Sign d = (Sign) block.getState();
			if (d.getLine(0).equals("SUPERPLACE")) {
				Location loc = block.getLocation();
				for (Animation animation : animations) {
					if (animation.CurrentModifyingFrameIndex > -1)
						continue;

					int frameIndex = 0;
					for (AnimationFrame frame : animation.Frames) {
						if (frame.FrameTrigger.equals(loc)) {

							if (!on
									&& frameIndex == animation.CurrentFrameIndex) {

								BlockReprinter
										.clearReprint(animation.AnimationName);
								return;
							}

							updateDrawing(
									animation.CurrentFrameIndex > -1 ? animation.Frames.get(animation.CurrentFrameIndex)
											: null, frame);
							animation.CurrentFrameIndex = frameIndex;
							return;
						}
						frameIndex++;
					}
				}

			}
		}
	}

	public void createAnimation(Player creator, String name) {
		Animation am = new Animation(creator, name);
		animations.add(am);
	}

	private void updateDrawing(AnimationFrame previousFrame,
			AnimationFrame currentFrame) {
		if (previousFrame != null)
			BlockReprinter
					.clearReprint(previousFrame.parentAnimation.AnimationName);

		int index = 0;
		for (Location loc : currentFrame.Pieces) {

			BlockReprinter.addBlockForReprint(new BlockReprintItem(loc
					.getWorld(), loc.getBlockX(), loc.getBlockY(), loc
					.getBlockZ(), currentFrame.Types.get(index),
					currentFrame.Datas.get(index),
					previousFrame.parentAnimation.AnimationName));

			loc.getBlock().setTypeIdAndData(currentFrame.Types.get(index),
					currentFrame.Datas.get(index), false);
		}

	}
}