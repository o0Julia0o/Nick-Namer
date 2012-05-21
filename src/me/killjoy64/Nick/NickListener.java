package me.killjoy64.Nick;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

public class NickListener implements Listener {

	Nick plugin;
	
	public NickListener(Nick instance){
		plugin = instance;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer(); 
		
		if(plugin.getCustomConfig().contains("Players." + player.getName())){
			if(plugin.config.getBoolean("Secrecy.Enabled") == true){
				event.setJoinMessage(plugin.ConvToStrWithColor(plugin.config.getString("Secrecy.JoinMsg").replace("+displayname", plugin.getCustomConfig().getString("Players." + player.getName()))));
				}
				player.setDisplayName(plugin.ConvToStrWithColor(plugin.getCustomConfig().getString("Players." + player.getName())));
				player.setPlayerListName(ChatColor.stripColor(player.getDisplayName()));
		} else {
			plugin.getCustomConfig().set("Players." + player.getName(), player.getDisplayName());
			plugin.saveCustomConfig();
		}
		
		if(plugin.config.getBoolean("SpoutEnabled") == true){
			SpoutPlayer splayer = SpoutManager.getPlayer(player);
			splayer.setTitle(plugin.ReplaceColorCodes(player.getDisplayName()));
		}
		
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		if(plugin.config.getBoolean("Secrecy.Enabled") == true){
			if(plugin.getCustomConfig().contains("Players." + player.getName())){
				event.setQuitMessage(plugin.ConvToStrWithColor(plugin.config.getString("Secrecy.LeaveMsg").replace("+displayname", plugin.getCustomConfig().getString("Players." + player.getName()))));
			} else {
				event.setQuitMessage(plugin.ConvToStrWithColor(plugin.config.getString("Secrecy.LeaveMsg").replace("+displayname", player.getDisplayName())));
			}
		}
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		Player player = event.getPlayer();
		
		if(plugin.config.getBoolean("Secrecy.Enabled")){
			if(plugin.getCustomConfig().contains("Players." + player.getName())){
				event.setLeaveMessage(plugin.ConvToStrWithColor(plugin.config.getString("Secrecy.KickMsg").replace("+displayname", plugin.getCustomConfig().getString("Players." + player.getName()))));
			} else {
				event.setLeaveMessage(plugin.ConvToStrWithColor(plugin.config.getString("Secrecy.KickMsg").replace("+displayname", player.getDisplayName())));
			}
		}
	}
}
