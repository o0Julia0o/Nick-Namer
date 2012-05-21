package me.killjoy64.Nick;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadingCmdExecutor implements CommandExecutor {

	Nick plugin;
	
	public ReloadingCmdExecutor(Nick instance){
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player player = (Player) sender;
		
		if(args.length == 0){
			player.sendMessage(ChatColor.RED + "Error: No arguments!");
		} else if(args.length == 2){
			if(args[0].equalsIgnoreCase("config")){
				if(args[1].equalsIgnoreCase("reload")){
					plugin.reloadCustomConfig();
					player.sendMessage("Nick Names Config Reloaded.");
				}
			}
		}
		return true;
	}
	
}
