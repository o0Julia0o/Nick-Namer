package me.killjoy64.Nick;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class realnickCmdExecutor implements CommandExecutor {

	private Nick plugin;
	
	public realnickCmdExecutor(Nick instance){
		plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player player = (Player) sender;
		
		if(player.hasPermission("nickname.realnick")){
		
		if(args.length == 0){
			player.sendMessage(ChatColor.RED + "Not enough Arguments! /realnick <Player>");
		} else if(args.length == 1){
			for(Player onlinePlayer : player.getServer().getOnlinePlayers()){
				if(args[0].equalsIgnoreCase(onlinePlayer.getDisplayName().toString())){
					player.sendMessage(ChatColor.AQUA + onlinePlayer.getDisplayName() + "'s " + ChatColor.YELLOW + "Real Name is " + ChatColor.GREEN + onlinePlayer.getName());
				} else {
					player.sendMessage(ChatColor.YELLOW + args[0] + " does not exist or is an incorrect username.");
				}
			}
		}
	} else {
		sender.sendMessage(plugin.ConvToStrWithColor("&cYou do not have permission to do this!"));
	}
		return true;
	}
	
}
