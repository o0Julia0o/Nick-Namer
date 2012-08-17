package bukkit.killjoy64.NickNamer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

import bukkit.killjoy64.NickNamer.config.Config;

public class TagApiListener implements Listener {

	NickNamer nick;
	
	public TagApiListener(NickNamer instance){
		nick = instance;
	}
	
	@EventHandler
	public void onTagReceive(PlayerReceiveNameTagEvent event){
		
		Player namedPlayer = event.getNamedPlayer();
		
		if(nick.getNameConfig().getNickNames().contains("Players." + namedPlayer.getName())){
			if(Config.COLORED_TAGS == true){
				event.setTag(nick.getNickMsger().getColor(nick.getNameConfig().getNickNames().getString("Players." + namedPlayer.getName())));
			} else {
				event.setTag(nick.getNickMsger().stripColor(nick.getNameConfig().getNickNames().getString("Players." + namedPlayer.getName())));
			}
		}
		
	}
	
}
