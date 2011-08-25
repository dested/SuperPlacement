package com.minederp.superPlacement;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
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
			if (d.getLine(0).endsWith("SUPERFRAME")) {
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
							if (!on)
								continue;

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

	public boolean blockBreak(Player breaker, Block block) {

		if (block.getType() == Material.WALL_SIGN) {
			return clickedSign(breaker, (Sign) block.getState());

		}
		for (Animation animation : animations) {
			if (animation.CurrentModifyingFrameIndex == -1
					&& animation.CurrentFrameIndex == -1)
				continue;
			Location loc = block.getLocation();

			int frameIndex = 0;
			for (AnimationFrame frame : animation.Frames) {
				if (animation.CurrentModifyingFrameIndex > -1
						&& breaker.equals(animation.CurrentEditor)) {

					AnimationFrame f = animation.Frames
							.get(animation.CurrentModifyingFrameIndex);

					int ind = 0;
					for (Location l : f.Pieces) {
						if (l.equals(loc)) {
							break;
						}
						ind++;
					}

					f.Datas.remove(ind);
					f.Types.remove(ind);
					f.Pieces.remove(ind);

					breaker.sendMessage(ChatColor.AQUA + "  Block number "
							+ ind + " has been removed from "
							+ f.parentAnimation.AnimationName + ".");
					return false;
				}

				if (frameIndex == animation.CurrentFrameIndex) {

					for (Location ploc : frame.Pieces) {
						if (ploc.equals(loc)) {
							return true;
						}
					}
				}
				frameIndex++;
			}
		}
		return false;
	}

	public boolean blockPlace(Player breaker, Block block) {
		for (Animation animation : animations) {
			if (animation.CurrentModifyingFrameIndex == -1
					|| !breaker.equals(animation.CurrentEditor))
				continue;
			Location loc = block.getLocation();

			AnimationFrame f = animation.Frames
					.get(animation.CurrentModifyingFrameIndex);
			f.Datas.add(block.getData());
			f.Types.add(block.getTypeId());
			f.Pieces.add(loc);

			BlockReprinter.addBlockForReprint(new BlockReprintItem(loc
					.getWorld(), loc.getBlockX(), loc.getBlockY(), loc
					.getBlockZ(), block.getTypeId(), block.getData(),
					animation.AnimationName, false));

			breaker.sendMessage(ChatColor.AQUA + "  Block number "
					+ f.Pieces.size() + " has been added to "
					+ f.parentAnimation.AnimationName + ".");
			return false;
		}
		return false;
	}

	public void createAnimation(Player creator, String name) {
		Animation am = new Animation(creator, name);
		animations.add(am);
		creator.sendMessage(ChatColor.BLUE + "  Animation '" + name
				+ "' Created.");
	}

	private void updateDrawing(AnimationFrame previousFrame,
			AnimationFrame currentFrame) {
		if (previousFrame != null)
			BlockReprinter
					.clearReprint(previousFrame.parentAnimation.AnimationName);

		if (currentFrame == null) {
			BlockReprinter
					.clearReprint(previousFrame.parentAnimation.AnimationName);
			return;
		}

		int index = 0;
		for (Location loc : currentFrame.Pieces) {

			BlockReprinter.addBlockForReprint(new BlockReprintItem(loc
					.getWorld(), loc.getBlockX(), loc.getBlockY(), loc
					.getBlockZ(), currentFrame.Types.get(index),
					currentFrame.Datas.get(index),
					currentFrame.parentAnimation.AnimationName, false));

			loc.getBlock().setTypeIdAndData(currentFrame.Types.get(index),
					currentFrame.Datas.get(index), true);
			index++;
		}

	}

	public void modifyAnimation(Player cs, String name) {

		for (Animation a : animations) {
			if (a.AnimationName.equals(name)) {
				if (a.CurrentEditor == null) {
					a.CurrentEditor = cs;
					cs.sendMessage(ChatColor.AQUA + "  Modifying Animation '"
							+ name + "'.");
					cs.sendMessage(ChatColor.AQUA + "    It contains '"
							+ a.Frames.size() + "' Frames.");
					return;
				}
				cs.sendMessage(ChatColor.AQUA
						+ "  Animation is being modified by '"
						+ a.CurrentEditor.getName() + "'.");
				return;

			}
		}
		cs.sendMessage(ChatColor.AQUA + "  Animation '" + name
				+ "' does not exist.");
	}

	public void assignFrame(Player cs) {
		for (Animation a : animations) {
			if (cs.equals(a.CurrentEditor)) {
				a.WaitingToAddFrame = true;
				cs.sendMessage(ChatColor.AQUA + "  The next Sign you click ");
				cs.sendMessage(ChatColor.AQUA
						+ "    whos top line is 'SUPERFRAME'");
				cs.sendMessage(ChatColor.AQUA
						+ "    will be assigned as Frame " + a.Frames.size()
						+ ".");
				return;
			}
		}
	}

	public void exitAnimation(Player cs) {
		for (Animation a : animations) {
			if (cs.equals(a.CurrentEditor)) {
				a.CurrentEditor = null;
				cs.sendMessage(ChatColor.AQUA + "  Ending Modifcation '"
						+ a.AnimationName + "'.");
				return;
			}
		}
	}

	public boolean clickedSign(Player player, Sign state) {
		for (Animation a : animations) {
			if (player.equals(a.CurrentEditor)
					&& state.getLine(0).endsWith("SUPERFRAME")) {
				if (a.WaitingToAddFrame) {
					a.WaitingToAddFrame = false;
					state.setLine(0, ChatColor.DARK_AQUA + "SUPERFRAME");
					state.setLine(1,
							ChatColor.AQUA + "Frame " + a.Frames.size());
					a.AddFrame(state.getBlock().getLocation());
					state.update(true);
					return true;
				}

				if (!state.getLine(1).startsWith(ChatColor.AQUA + "Frame "))
					return false;

				int frameInd = Integer.parseInt(ChatColor.stripColor(state
						.getLine(1).replace(ChatColor.AQUA + "Frame ", "")));
				if (a.CurrentModifyingFrameIndex == frameInd) {

					player.sendMessage(ChatColor.AQUA
							+ "  You have completed modifying frame "
							+ a.CurrentModifyingFrameIndex);
					a.CurrentModifyingFrameIndex = -1;
					updateDrawing(a.Frames.get(frameInd), a.CurrentFrameIndex>-1?a.Frames.get(a.CurrentFrameIndex):null);
					return true;
				}

				a.CurrentModifyingFrameIndex = frameInd;
				player.sendMessage(ChatColor.AQUA
						+ "  You are now modifying frame "
						+ a.CurrentModifyingFrameIndex);
				updateDrawing(a.Frames.get(frameInd), a.Frames.get(frameInd));
				return true;
			}
		}
		return false;

	}
}