package bukkit.killjoy64.NickNamer.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bukkit.killjoy64.NickNamer.NickNamer;

public class Messenger {

	private String prefix;
	private NickNamer nick;
	
	public Messenger(NickNamer instance){
		prefix = "&f[&bNick Namer&f]: ";
		nick = instance;
	}
	
	public void sendError(Player player, NickType error, String color){
		player.sendMessage(getColor(color + error.getError().toString()));
	}
	
	public void notify(Player player, String note){
		for(Player online : player.getServer().getOnlinePlayers()){
			if(online != player){
				if(online.hasPermission("nickname.notify")){
					online.sendMessage(getColor(prefix + note));
					nick.getNickLogger().log(getColor(note));
				}
			}
		}
	}
	
	public void sendNick(CommandSender player, String nick, String realName){
		player.sendMessage(getColor("&c" + nick + " &ehas the real name of &c" + realName));
	}
	
	public void nameSelf(Player player, String name){
		player.sendMessage(getColor("&eSuccessfully changed name to &c" + name));
		nick.getNickLogger().log(getColor(player.getName() + " changed name to " + name));
	}
	
	public void nameOther(Player player, Player target, String name){
		player.sendMessage(getColor("&eSuccessfully changed &c" + target.getName() + " &eto &c" + name));
		target.sendMessage(getColor("&eYour name is now &c" + name));
		nick.getNickLogger().log(getColor(player.getName() + " changed name of " + target.getName() + " to " + name));
	}
	
	public void send(Player player, String msg){
		player.sendMessage(getColor(msg));
	}
	
	public void log(CommandSender sender, String msg){
		sender.sendMessage(getColor(msg));
	}
	
	public void displayHelp(Player p){
		p.sendMessage(getColor("--------------&bNick Namer Help&f--------------"));
		p.sendMessage(getColor("&a/nick <Nick> &f- &bChange your Name!"));
		p.sendMessage(getColor("&a/realnick <Nick> &f- &bReturn the Real Name of Nick."));
		p.sendMessage(getColor("&a/nn <arg1> <arg2>.. &f- &bNickNamer Plugin Stuff."));
		p.sendMessage(getColor("--------------&bNick Namer Help&f--------------"));
	}
	
	public String getColor(String str) {
		str = str.replaceAll("&0", "" + ChatColor.BLACK);
        str = str.replaceAll("&1", "" + ChatColor.DARK_BLUE);
        str = str.replaceAll("&2", "" + ChatColor.DARK_GREEN);
        str = str.replaceAll("&3", "" + ChatColor.DARK_AQUA);
        str = str.replaceAll("&4", "" + ChatColor.DARK_RED);
        str = str.replaceAll("&5", "" + ChatColor.DARK_PURPLE);
        str = str.replaceAll("&6", "" + ChatColor.GOLD);
        str = str.replaceAll("&7", "" + ChatColor.GRAY);
        str = str.replaceAll("&8", "" + ChatColor.DARK_GRAY);
        str = str.replaceAll("&9", "" + ChatColor.BLUE);
        str = str.replaceAll("&a", "" + ChatColor.GREEN);
        str = str.replaceAll("&b", "" + ChatColor.AQUA);
        str = str.replaceAll("&c", "" + ChatColor.RED);
        str = str.replaceAll("&d", "" + ChatColor.LIGHT_PURPLE);
        str = str.replaceAll("&e", "" + ChatColor.YELLOW);
        str = str.replaceAll("&f", "" + ChatColor.WHITE);
        
        return str;
	}

	public String stripColor(String str){
        str = str.replaceAll("&0", "");
        str = str.replaceAll("&1", "");
        str = str.replaceAll("&2", "");
        str = str.replaceAll("&3", "");
        str = str.replaceAll("&4", "");
        str = str.replaceAll("&5", "");
        str = str.replaceAll("&6", "");
        str = str.replaceAll("&7", "");
        str = str.replaceAll("&8", "");
        str = str.replaceAll("&9", "");
        str = str.replaceAll("&a", "");
        str = str.replaceAll("&b", "");
        str = str.replaceAll("&c", "");
        str = str.replaceAll("&d", "");
        str = str.replaceAll("&e", "");
        str = str.replaceAll("&f", "");
        
    	return str;
	}
	
}
