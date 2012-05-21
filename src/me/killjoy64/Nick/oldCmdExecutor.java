package me.killjoy64.Nick;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

public class oldCmdExecutor implements CommandExecutor {
	
	Nick plugin;
	
	public oldCmdExecutor(Nick instance){
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player player = (Player) sender;
		
		if(player.hasPermission("nickname.oldname") || player.isOp() == plugin.config.getBoolean("OpEnabled")){
		
		player.setDisplayName(plugin.ConvToStrWithColor(player.getName() + ChatColor.WHITE));
		player.setPlayerListName(plugin.ReplaceColorCodes(player.getName()));
		
		if(plugin.getCustomConfig().contains("Players." + player.getName())){
			plugin.getCustomConfig().set("Players." + player.getName(), player.getName());
			plugin.saveCustomConfig();
		} else {
			plugin.getCustomConfig().set("Players." + player.getName(), player.getName());
			plugin.saveCustomConfig();
		}
		
		player.sendMessage(plugin.ConvToStrWithColor(ChatColor.YELLOW + "Your name is back to " + player.getDisplayName()));
		
		for(Player onlinePlayer : player.getServer().getOnlinePlayers()){
			if(!(onlinePlayer.getName() == player.getName())){
				if(onlinePlayer.hasPermission("nickname.notify") || player.isOp()){
					onlinePlayer.sendMessage(plugin.ConvToStrWithColor("&f[&bNick Namer&f] " + player.getDisplayName() + " is now labeled as " + player.getDisplayName()));
				}
			}
		}
		
		if(plugin.config.getBoolean("SpoutEnabled") == true){
			SpoutPlayer splayer = SpoutManager.getPlayer(player);
			splayer.setTitle(plugin.ReplaceColorCodes(player.getName()));
		}
		
		} else {
			sender.sendMessage(plugin.ConvToStrWithColor("&cYou do not have permission to do this!"));
		}
		return true;
	}
}
