package com.minederp.superPlacement.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.avaje.ebeaninternal.server.lib.cron.HelloWorld;
import com.minederp.superPlacement.SuperPlacement;

public class animation implements CommandExecutor {

	private SuperPlacement plugin;

	public animation(SuperPlacement plugin) {
		this.plugin = plugin;
	}

	public static boolean argEquals(String string, String string2) {

		if (string.toLowerCase().equals(string2.toLowerCase()))
			return true;
		StringBuilder sb = new StringBuilder();
		for (char c : string2.toCharArray()) {
			String h = Character.toString(c);
			if (h.toUpperCase().equals(h)) {
				sb.append(h);
			}
		}
		if (sb.toString().toLowerCase().equals(string.toLowerCase()))
			return true;

		return false;

	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmnd, String string,
			String[] args) {
		if (!(cs instanceof Player))
			return false;

		if (args.length > 0) {
			//(CreateAnimation[name])(ModifyAnimation[name]->CloseAnimation <(AssignFrame|ModifyFrame[index]|EndModify[index])>)
			
			
			if (argEquals(args[0], "CreateAnimation")) {
				if(args.length==2)
					plugin.manager.createAnimation((Player) cs, args[1]);
				else
					cs.sendMessage(ChatColor.RED+ "   Missing name");
			}
			if (argEquals(args[0], "ModifyAnimation")) {
				if(args.length==2)
					plugin.manager.modifyAnimation((Player) cs, args[1]);
				else
					cs.sendMessage(ChatColor.RED+ "   Missing name");
			}
			if (argEquals(args[0], "ExitAnimation")) {
				plugin.manager.exitAnimation((Player) cs);
			}
			if (argEquals(args[0], "AssignFrame")) {
				plugin.manager.assignFrame((Player) cs);
			}
		}

		return true;
	}

}
