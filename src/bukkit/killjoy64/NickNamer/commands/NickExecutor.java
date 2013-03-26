package bukkit.killjoy64.NickNamer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kitteh.tag.TagAPI;

import bukkit.killjoy64.NickNamer.NickNamer;
import bukkit.killjoy64.NickNamer.Events.NickChangeEvent;
import bukkit.killjoy64.NickNamer.config.Config;
import bukkit.killjoy64.NickNamer.util.NickType;

public class NickExecutor implements CommandExecutor {

	NickNamer nick;
	
	public NickExecutor(NickNamer instance){
		nick = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		Player player = null;
		
		if(sender instanceof Player){
			
			player = (Player) sender;
			NickChangeEvent event;
			
				if(args.length == 0){
					if(player.hasPermission("nickname.nick")){
						nick.getNickMsger().sendError(player, NickType.NO_ARGUMENTS, "&c");
					} else {
						nick.getNickMsger().sendError(player, NickType.NO_PERMISSION, "&c");
					}
				} else if(args.length == 1){
					
					event = new NickChangeEvent(player, args[0], null);
					
					if(player.hasPermission("nickname.nick")){
						
						player.getServer().getPluginManager().callEvent(event);
						
						if(event.isCancelled() == false){
							
							String nickname = nick.getNickMsger().getColor(args[0] + "&f");
							
							try {
								player.setDisplayName(nickname);
								if(Config.NICKNAME_TABLIST == true){	
									player.setPlayerListName(nickname);
								}
							} catch (IllegalArgumentException e){
								nick.getNickMsger().sendError(player, NickType.TOO_MANY_CHARS, "&c");
								return true;
							}
							
							if(nick.getNameConfig().getNickNames().contains("Players." + player.getName())){
								nick.getNameConfig().getNickNames().set("Players." + player.getName(), args[0]);
								nick.getNameConfig().saveNickNames();
							} else {
								nick.getNameConfig().getNickNames().set("Players." + player.getName(), args[0]);
								nick.getNameConfig().saveNickNames();
							}
							
							nick.getNickMsger().nameSelf(player, nickname);
							nick.getNickMsger().notify(player, "&c" + player.getName() + " &eis now known as &c" + nickname);
							
							if(Config.TAGAPI_ENABLED == true){
								TagAPI.refreshPlayer(player);
							}
						
						} else {
							return true;
						}
					
					} else {
						nick.getNickMsger().sendError(player, NickType.NO_PERMISSION, "&c");
					}
				
				} else if(args.length == 2){
					
					if(player.hasPermission("nickname.nick.other")){
						
						String nickname = nick.getNickMsger().getColor(args[1] + "&f");
						Player target = player.getServer().getPlayer(args[0]);
						
						if(target != null && target.isOnline()){
						
							event = new NickChangeEvent(player, args[1], target);
							
							player.getServer().getPluginManager().callEvent(event);
							
							if(event.isCancelled() == false){
							
								try {
									target.setDisplayName(nickname);
									if(Config.NICKNAME_TABLIST == true){
										target.setPlayerListName(nickname);
									}
								} catch (IllegalArgumentException e){
									nick.getNickMsger().sendError(player, NickType.TOO_MANY_CHARS, "&c");
									return true;
								}
								
								if(nick.getNameConfig().getNickNames().contains("Players." + target.getName())){
									nick.getNameConfig().getNickNames().set("Players." + target.getName(), args[1]);
									nick.getNameConfig().saveNickNames();
								} else {
									nick.getNameConfig().getNickNames().set("Players." + target.getName(), args[1]);
									nick.getNameConfig().saveNickNames();
								}
								
								nick.getNickMsger().nameOther(player, target, nickname);
								nick.getNickMsger().notify(player, "&c" + target.getName() + " &eis now known as &c" + nickname);
								
								if(Config.TAGAPI_ENABLED == true){
									TagAPI.refreshPlayer(target);
								}
						
							} else {
								return true;
							}
							
						} else {
							nick.getNickMsger().sendError(player, NickType.INVALID_PLAYER, "&e");
						}
						
					} else {
						nick.getNickMsger().sendError(player, NickType.NO_PERMISSION, "&c");
					}
					
				} else {
					nick.getNickMsger().sendError(player, NickType.INVALID_ARGUMENT, "&c");
				}
			
		} else {
			nick.getNickMsger().log(sender, "&cYou must be ingame to use this command.");
		}
		
		return true;
	}
	
}
