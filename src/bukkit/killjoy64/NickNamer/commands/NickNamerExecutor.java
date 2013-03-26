package bukkit.killjoy64.NickNamer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bukkit.killjoy64.NickNamer.NickNamer;
import bukkit.killjoy64.NickNamer.util.NickType;

public class NickNamerExecutor implements CommandExecutor {

	private NickNamer nick;
	
	public NickNamerExecutor(NickNamer instance){
		nick = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		Player player = null;
		
		if(sender instanceof Player){
			
			player = (Player) sender;
			
			if(args.length == 0){
				if(player.hasPermission("nickname.nick")){
					nick.getNickMsger().sendError(player, NickType.NO_ARGUMENTS, "&c");
				} else {
					nick.getNickMsger().sendError(player, NickType.NO_PERMISSION, "&c");
				}
			} else if(args.length == 1){
				
				if(args[0].equalsIgnoreCase("help")){
					if(player.hasPermission("nickname.help")){
						nick.getNickMsger().displayHelp(player);
					} else {
						nick.getNickMsger().sendError(player, NickType.NO_PERMISSION, "&c");
					}
				} else if(args[0].equalsIgnoreCase("reload")){
					if(player.hasPermission("nickname.reload")){
						try {	
							nick.reloadConfig();
							nick.saveConfig();
							nick.getNameConfig().reloadNickNames();
							nick.getNameConfig().saveNickNames();
							nick.loadNodes();
							nick.namePlayers();
							nick.getNickMsger().send(player, "&eSuccesfully Reloaded &cNick Namer &eFiles");
						} catch(Exception e){
							nick.getNickMsger().send(player, "&cError Reloading Files. Report to an Administrator");
							e.printStackTrace();
						}
					} else {
						nick.getNickMsger().sendError(player, NickType.NO_PERMISSION, "&c");
					}
				} else {
					nick.getNickMsger().sendError(player, NickType.INVALID_ARGUMENT, "&c");
				}
				
			} else {
				nick.getNickMsger().sendError(player, NickType.INVALID_ARGUMENT, "&c");
			}
			
		} else {
			
			if(args.length == 0){
				nick.getNickMsger().sendError(player, NickType.NO_ARGUMENTS, "&c");
			} else if(args.length == 1){
				if(args[0].equalsIgnoreCase("reload")){
						try {	
							nick.reloadConfig();
							nick.saveConfig();
							nick.getNameConfig().reloadNickNames();
							nick.getNameConfig().saveNickNames();
							nick.namePlayers();
							nick.loadNodes();
							nick.getNickLogger().log("Succesfully Reloaded Nick Namer Files");
						} catch(Exception e){
							nick.getNickLogger().warn("Error Reloading Files.");
							e.printStackTrace();
						}
					} else {
						nick.getNickLogger().log("Invalid Argument, /nicknamer <args>");
					}
				} else {
					nick.getNickLogger().log("Invalid Argument, /nicknamer <args>");
				}
			}	
			return true;
		}
	}
