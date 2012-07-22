package bukkit.killjoy64.NickNamer;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

import bukkit.killjoy64.NickNamer.commands.NickExecutor;
import bukkit.killjoy64.NickNamer.commands.NickNamerExecutor;
import bukkit.killjoy64.NickNamer.commands.RealNickExecutor;
import bukkit.killjoy64.NickNamer.config.Config;
import bukkit.killjoy64.NickNamer.config.NickNamesConfig;
import bukkit.killjoy64.NickNamer.logging.NickLogger;
import bukkit.killjoy64.NickNamer.util.Messenger;

public class NickNamer extends JavaPlugin {

	private NickNamesConfig nickNames;
	private NickLogger nickLogger;
	private Messenger messenger;
	
	private NickExecutor nick;
	private RealNickExecutor realnick;
	private NickNamerListener listener;
	private NickNamerExecutor nicknamer;
	
	private HashMap<String, String> nickedPlayers;
	
	private int loaded;
	
	@Override
	public void onEnable(){
		
		nickedPlayers = new HashMap<String, String>();
		
		nickNames = new NickNamesConfig(this);
		messenger = new Messenger(this);
		nickLogger = new NickLogger();
		
		nick = new NickExecutor(this);
		realnick = new RealNickExecutor(this);
		nicknamer = new NickNamerExecutor(this);
		listener = new NickNamerListener(this);
		
		loaded = 0;
		
		getConfig().options().copyDefaults(true);
		nickNames.getNickNames().options().copyDefaults(true);
		
		saveConfig();
		nickNames.saveNickNames();
		
		Config.SPOUT_ENABLED = false;
		
		getServer().getPluginManager().registerEvents(listener, this);
		
		getCommand("nick").setExecutor(nick);
		getCommand("realnick").setExecutor(realnick);
		getCommand("nicknamer").setExecutor(nicknamer);
		
		for (Plugin p : getServer().getPluginManager().getPlugins()) {
            String name = p.getClass().getName();
            
            if(name.equals("org.getspout.spout.Spout")){
            	nickLogger.log("Found Spout, using spout for Player Titles.");
            	Config.SPOUT_ENABLED = true;
            }
            
		}
		
		nickLogger.log("Loading Configuration Nodes");
		
		loadNodes();
		
		nickLogger.log("Naming Online Players...");
		
		for(Player p : getServer().getOnlinePlayers()){
			if(getNameConfig().getNickNames().contains("Players." + p.getName())){
				p.setDisplayName(getNickMsger().getColor(getNameConfig().getNickNames().getString("Players." + p.getName())));
				p.setPlayerListName(getNickMsger().stripColor(p.getDisplayName()));
				
				if(nickedPlayers.containsKey(messenger.stripColor(getNameConfig().getNickNames().getString("Players." + p.getName())))){
					nickedPlayers.remove(messenger.stripColor(getNameConfig().getNickNames().getString("Players." + p.getName())));
					nickedPlayers.put(messenger.stripColor(getNameConfig().getNickNames().getString("Players." + p.getName())), p.getName());
				} else {
					nickedPlayers.put(messenger.stripColor(getNameConfig().getNickNames().getString("Players." + p.getName())), p.getName());
				}
				
				if(Config.SPOUT_ENABLED == true){
					SpoutPlayer splayer = SpoutManager.getPlayer(p);
					
					splayer.setTitle(getNickMsger().stripColor(getNameConfig().getNickNames().getString("Players." + p.getName())));
				}
				
				loaded++;
				
			}
		}
		
		nickLogger.log("Successfully named " + loaded + " players.");
	}
	
	@Override
	public void onDisable(){
		getNameConfig().saveNickNames();
		saveConfig();
		
		loaded = 0;
		nickedPlayers.clear();
	}
	
	public void loadNodes(){
		Config.SECRECY_ENABLED = getConfig().getBoolean("Secrecy.Enabled");
		Config.SECRECY_JOIN = getConfig().getString("Secrecy.Join");
		Config.SECRECY_LEAVE = getConfig().getString("Secrecy.Leave");
		Config.SECRECY_KICK = getConfig().getString("Secrecy.Kick");
		Config.ALLOW_DOUBLE_NAMES = getConfig().getBoolean("AllowDoubleNames");
		Config.ALLOW_IMPERSONATION = getConfig().getBoolean("AllowImpersonation");
	}
	
	public HashMap<String, String> getNickedPlayers(){
		return nickedPlayers;
	}
	
	public NickNamesConfig getNameConfig(){
		return nickNames;
	}
	
	public NickLogger getNickLogger(){
		return nickLogger;
	}
	
	public Messenger getNickMsger(){
		return messenger;
	}
	
}
