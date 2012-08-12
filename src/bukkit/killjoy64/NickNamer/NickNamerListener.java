package bukkit.killjoy64.NickNamer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

import bukkit.killjoy64.NickNamer.Events.NickChangeEvent;
import bukkit.killjoy64.NickNamer.config.Config;

public class NickNamerListener implements Listener {

	NickNamer nick;
	
	public NickNamerListener(NickNamer instance){
		nick = instance;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		
		Player player = event.getPlayer();
		
		if(nick.getNameConfig().getNickNames().contains("Players." + player.getName())){
			
			player.setDisplayName(nick.getNickMsger().getColor(nick.getNameConfig().getNickNames().getString("Players." + player.getName())));
			player.setPlayerListName(nick.getNickMsger().stripColor(player.getDisplayName()));
			
			nick.getNickedPlayers().remove(player.getDisplayName());
			nick.getNickedPlayers().put(nick.getNickMsger().stripColor(player.getDisplayName()), player.getName());
			
			if(Config.SPOUT_ENABLED == true){
				SpoutPlayer splayer = SpoutManager.getPlayer(player);
				
				splayer.setTitle(nick.getNickMsger().stripColor(nick.getNameConfig().getNickNames().getString("Players." + player.getName())));
			}
			
		} else {
			nick.getNickedPlayers().put(nick.getNickMsger().stripColor(player.getDisplayName()), player.getName());
			nick.getNameConfig().getNickNames().set("Players." + player.getName(), player.getDisplayName());
			nick.getNameConfig().saveNickNames();
		}
		
		if(Config.SECRECY_ENABLED){
			event.setJoinMessage(nick.getNickMsger().getColor(Config.SECRECY_JOIN.replace("+nick", player.getDisplayName()).replace("+name", player.getName())));
		}
		
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event){
		
		Player player = event.getPlayer();
		
		if(Config.SECRECY_ENABLED){
			event.setQuitMessage(nick.getNickMsger().getColor(Config.SECRECY_LEAVE.replace("+nick", player.getDisplayName()).replace("+name", player.getName())));
		}
		
	}

	@EventHandler
	public void onKick(PlayerKickEvent event){
		
		Player player = event.getPlayer();
		
		if(Config.SECRECY_ENABLED){
			event.setLeaveMessage(nick.getNickMsger().getColor(Config.SECRECY_KICK.replace("+nick", player.getDisplayName()).replace("+name", player.getName())));
		}

	}
	
	@EventHandler
	public void onNickChange(NickChangeEvent event){
	
		//Player player = event.getPlayer();
		Player target = event.getTarget();
		String name = event.getNick();
		
		/*boolean found = false;
		
		if(!Config.ALLOW_IMPERSONATION){
			for(String value : nick.getNickedPlayers().values()){
				if(nick.getNickMsger().stripColor(name).equalsIgnoreCase(value)){
					found = true;
					event.setCancelled(true);
				}
			}
			
			if(found){
				nick.getNickMsger().send(player, "&eYou may not impersonate &c" + nick.getNickMsger().stripColor(name));
			}
			
			found = false;
			
		} else if(!Config.ALLOW_DOUBLE_NAMES){
			for(String key : nick.getNickedPlayers().keySet()){
				if(nick.getNickMsger().stripColor(name).equalsIgnoreCase(key)){
					found = true;
					event.setCancelled(true);
					nick.getConfig().getStringList("");
				}
			}
			
			if(found){
				nick.getNickMsger().send(player, "&eSomeone already has the nick &c" + nick.getNickMsger().stripColor(name));
			}
			
			found = false;
			
		}*/
			
		if(nick.getNickedPlayers().containsKey(nick.getNickMsger().stripColor(name))){
			nick.getNickedPlayers().remove(name);
			nick.getNickedPlayers().put(nick.getNickMsger().stripColor(name), target.getName());
		} else {
			nick.getNickedPlayers().put(nick.getNickMsger().stripColor(name), target.getName());
		}
	}
	
}
