package me.killjoy64.Nick;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;

public class renameCmdExecutor implements CommandExecutor {
	
	Nick plugin;
	
	public renameCmdExecutor(Nick instance){
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player player = (Player) sender;
		
		if(player.hasPermission("nickname.rename") || player.isOp() == plugin.config.getBoolean("OpEnabled")){
		
		if(args.length <= 1){
			player.sendMessage(plugin.ConvToStrWithColor("&eCorrect Usage is /rename <Player> <Nick>"));
		} else if(args.length ==2){
			if(Bukkit.getServer().getPlayer(args[0]) != null){
				if(Bukkit.getServer().getPlayer(args[0]).isOnline()){
				Bukkit.getServer().getPlayer(args[0]).setDisplayName(plugin.ConvToStrWithColor(args[1] + "&f"));
				Bukkit.getServer().getPlayer(args[0]).setPlayerListName(plugin.ReplaceColorCodes(args[1]));
				
				if(plugin.getCustomConfig().contains("Players." + Bukkit.getServer().getPlayer(args[0]).getName())){
					plugin.getCustomConfig().set("Players." + Bukkit.getServer().getPlayer(args[0]).getName(), args[1]);
					plugin.saveCustomConfig();
				} else {
					plugin.getCustomConfig().set("Players." + Bukkit.getServer().getPlayer(args[0]).getName(), args[1]);
					plugin.saveCustomConfig();
				}
				
				sender.sendMessage(plugin.ConvToStrWithColor("&aYou renamed " + Bukkit.getServer().getPlayer(args[0]).getName() + " &ato " + args[1]));
				Bukkit.getServer().getPlayer(args[0]).sendMessage(plugin.ConvToStrWithColor("&eYou were renamed to " + args[1]));
				
				for(Player onlinePlayer : player.getServer().getOnlinePlayers()){
					if(!(onlinePlayer.getName() == player.getName())){
						if(onlinePlayer.hasPermission("nickname.notify") || player.isOp()){
							onlinePlayer.sendMessage(plugin.ConvToStrWithColor("&f[&bNick Namer&f] " + Bukkit.getServer().getPlayer(args[0]).getName() + " is now labeled as " + Bukkit.getServer().getPlayer(args[0]).getDisplayName()));
						}
					}
				}
				
					if(plugin.config.getBoolean("SpoutEnabled") == true){
						SpoutManager.getPlayer(Bukkit.getServer().getPlayer(args[0])).setTitle(plugin.ReplaceColorCodes(args[1]));
					}
				
					} else {
						sender.sendMessage(plugin.ConvToStrWithColor("&ePlayer " + args[0] + " &eis not online or is the incorrect username"));
					}
				} else {
					sender.sendMessage(plugin.ConvToStrWithColor("&ePlayer " + args[0] + " &eis not online or is the incorrect username"));
				}
			}
		} else {
			sender.sendMessage(plugin.ConvToStrWithColor("&cYou do not have permission to do this!"));
		}
		return true;
	}
}
