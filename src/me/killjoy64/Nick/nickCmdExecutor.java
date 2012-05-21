package me.killjoy64.Nick;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

public class nickCmdExecutor implements CommandExecutor {
	
	Nick plugin;
	
	public nickCmdExecutor(Nick instance){
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player player = (Player) sender;
		
		if(player.hasPermission("nickname.nick") || player.isOp() == plugin.config.getBoolean("OpEnabled")){
		
		if(args.length == 0){
			
			player.sendMessage(ChatColor.YELLOW + "Correct usage is /nick <NickName>");
		
			} else if(args.length == 1){
				
				player.setDisplayName(plugin.ConvToStrWithColor(args[0] + ChatColor.WHITE));
				player.setPlayerListName(plugin.ReplaceColorCodes(args[0]));
				
				if(plugin.getCustomConfig().contains("Players." + player.getName())){
					plugin.getCustomConfig().set("Players." + player.getName(), args[0]);
					plugin.saveCustomConfig();
				} else {
					plugin.getCustomConfig().set("Players." + player.getName(), args[0]);
					plugin.saveCustomConfig();
				}
				
				player.sendMessage(plugin.ConvToStrWithColor(ChatColor.YELLOW + "Your new name is " + args[0]));
				
				for(Player onlinePlayer : player.getServer().getOnlinePlayers()){
					if(!(onlinePlayer.getName() == player.getName())){
						if(onlinePlayer.hasPermission("nickname.notify") || player.isOp()){
							onlinePlayer.sendMessage(plugin.ConvToStrWithColor("&f[&bNick Namer&f] " + player.getName() + " is now labeled as " + player.getDisplayName()));
						}
					}
				}
				
				if(plugin.config.getBoolean("SpoutEnabled") == true){
					SpoutPlayer splayer = SpoutManager.getPlayer(player);
					splayer.setTitle(plugin.ReplaceColorCodes(args[0]));
				}
				
			}
		} else {
			sender.sendMessage(plugin.ConvToStrWithColor("&cYou do not have permission to do this!"));
		}
		return true;
	}
}
