package bukkit.killjoy64.NickNamer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.kitteh.tag.TagAPI;

import bukkit.killjoy64.NickNamer.Events.NickChangeEvent;
import bukkit.killjoy64.NickNamer.config.Config;
import bukkit.killjoy64.NickNamer.util.NickType;

public class NickNamerListener implements Listener {

	NickNamer nick;
	
	public NickNamerListener(NickNamer instance){
		nick = instance;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		
		Player player = event.getPlayer();
		
		if(nick.getNameConfig().getNickNames().contains("Players." + player.getName())){
			
			if(Config.USE_BLACKLIST && nick.getBlacklist().blacklisted(nick.getNameConfig().getNickNames().getString("Players." + player.getName()))) {
				player.setDisplayName(player.getName());
				nick.getNameConfig().getNickNames().set("Players." + player.getName(), player.getName());
				nick.getNameConfig().saveNickNames();
			} else {
				player.setDisplayName(nick.getNickMsger().getColor(nick.getNameConfig().getNickNames().getString("Players." + player.getName()) + "&f"));
			}
			
			if(Config.NICKNAME_TABLIST == true){
				player.setPlayerListName(nick.getNickMsger().stripColor(player.getDisplayName()));
			}
			
			nick.getNickedPlayers().remove(player.getDisplayName());
			nick.getNickedPlayers().put(nick.getNickMsger().stripColor(player.getDisplayName()), player.getName());
			
			if(Config.TAGAPI_ENABLED == true){
				TagAPI.refreshPlayer(player);
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
			
		
		// if name contains certain characters don't allow
		// else nickname the player
		
		if(Config.USE_BLACKLIST) {
			if(nick.getBlacklist().blacklisted(name)) {
				if(!event.getPlayer().hasPermission("nickname.blacklist.bypass")) {
					nick.getNickMsger().sendError(event.getPlayer(), NickType.BLACKLISTED_NAME, "&c");
					event.setCancelled(true);
				}
			}
		}
		
		if(nick.getNickedPlayers().containsKey(nick.getNickMsger().stripColor(name))){
			nick.getNickedPlayers().remove(name);
			nick.getNickedPlayers().put(nick.getNickMsger().stripColor(name), target.getName());
		} else {
			nick.getNickedPlayers().put(nick.getNickMsger().stripColor(name), target.getName());
		}
	
	}
	
}
