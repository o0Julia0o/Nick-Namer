package bukkit.killjoy64.NickNamer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bukkit.killjoy64.NickNamer.NickNamer;
import bukkit.killjoy64.NickNamer.util.NickType;

public class RealNickExecutor implements CommandExecutor {

	NickNamer nick;
	String nickname, realname;
	boolean found;
	
	public RealNickExecutor(NickNamer instance){
		nick = instance;
		nickname = "";
		realname = "";
		found = false;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		Player player = null;
		
		if(sender instanceof Player){
			
			player = (Player) sender;
			
			if(args.length == 0){
				
				if(player.hasPermission("nickname.realnick")){
					
					if(nick.getNameConfig().getNickNames().contains("Players." + player.getName())){
						
						nickname = nick.getNameConfig().getNickNames().getString("Players." + player.getName());
						realname = player.getName();
						
						nick.getNickMsger().sendNick(player, nickname, realname);
					} else {
						
						nickname = player.getDisplayName();
						realname = player.getName();
						
						nick.getNickMsger().sendNick(player, nickname, realname);
					}
					
				} else {
					nick.getNickMsger().sendError(player, NickType.NO_PERMISSION, "&c");
				}
				
			} else if(args.length == 1){
				
				if(player.hasPermission("nickname.realnick")){
					
					if(nick.getNickedPlayers().containsKey(args[0])){
						nick.getNickMsger().sendNick(player, args[0], nick.getNickedPlayers().get(args[0]));
					} else {
						nick.getNickMsger().sendError(player, NickType.INVALID_NICKNAME, "&e");
					}
					
				} else {
					nick.getNickMsger().sendError(player, NickType.NO_PERMISSION, "&c");
				}
				
			} else {
				nick.getNickMsger().sendError(player, NickType.INVALID_ARGUMENT, "&c");
			}
			
		} else {
			
			
			
		}
		
		return true;
	}
	
}
