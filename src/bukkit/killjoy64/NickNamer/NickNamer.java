package bukkit.killjoy64.NickNamer;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.kitteh.tag.TagAPI;

import bukkit.killjoy64.NickNamer.commands.NickExecutor;
import bukkit.killjoy64.NickNamer.commands.NickNamerExecutor;
import bukkit.killjoy64.NickNamer.commands.RealNickExecutor;
import bukkit.killjoy64.NickNamer.config.Config;
import bukkit.killjoy64.NickNamer.config.NickNamesBlacklist;
import bukkit.killjoy64.NickNamer.config.NickNamesConfig;
import bukkit.killjoy64.NickNamer.logging.NickLogger;
import bukkit.killjoy64.NickNamer.util.Messenger;

public class NickNamer extends JavaPlugin {

	private NickNamesConfig nickNames;
	private NickNamesBlacklist blacklist;
	private NickLogger nickLogger;
	private Messenger messenger;
	
	private NickExecutor nick;
	private RealNickExecutor realnick;
	private NickNamerListener listener;
	private TagApiListener tagListener;
	private NickNamerExecutor nicknamer;
	
	private HashMap<String, String> nickedPlayers;
	
	private int loaded;
	
	@Override
	public void onEnable(){
		
		nickedPlayers = new HashMap<String, String>();
		
		nickNames = new NickNamesConfig(this);
		blacklist = new NickNamesBlacklist(this);
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
		
		Config.TAGAPI_ENABLED = false;
		
		getServer().getPluginManager().registerEvents(listener, this);
		
		getCommand("nick").setExecutor(nick);
		getCommand("realnick").setExecutor(realnick);
		getCommand("nicknamer").setExecutor(nicknamer);
		
        for (Plugin p : getServer().getPluginManager().getPlugins()) {
            String name = p.getClass().getName();
            
            if(name.equals("org.kitteh.tag.TagAPI")){
            	tagListener = new TagApiListener(this);
            	getServer().getPluginManager().registerEvents(tagListener, this);
            	
            	nickLogger.log("Found TagAPI, using TagAPI for Name Changes!");
                Config.TAGAPI_ENABLED = true;
            }
            
		}
        
		nickLogger.log("Loading Configuration Nodes");
		
		loadNodes();
		
		if(Config.USE_BLACKLIST) {
			blacklist.create();
		}
		
		nickLogger.log("Naming Online Players...");
		
		namePlayers();
		
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
		Config.COLORED_TAGS = getConfig().getBoolean("Colored Tags");
		Config.NICKNAME_TABLIST = getConfig().getBoolean("NickName TabList");
		Config.USE_BLACKLIST = getConfig().getBoolean("Blacklist");
		Config.SECRECY_ENABLED = getConfig().getBoolean("Secrecy.Enabled");
		Config.SECRECY_JOIN = getConfig().getString("Secrecy.Join");
		Config.SECRECY_LEAVE = getConfig().getString("Secrecy.Leave");
		Config.SECRECY_KICK = getConfig().getString("Secrecy.Kick");
		//Config.ALLOW_DOUBLE_NAMES = getConfig().getBoolean("AllowDoubleNames");
		//Config.ALLOW_IMPERSONATION = getConfig().getBoolean("AllowImpersonation");
	}
	
	public void namePlayers() {
		for(Player p : getServer().getOnlinePlayers()){
			if(getNameConfig().getNickNames().contains("Players." + p.getName())){
				if(Config.USE_BLACKLIST && blacklist.blacklisted(getNameConfig().getNickNames().getString("Players." + p.getName())) && p.hasPermission("nickname.blacklist.bypass") == false) {
					p.setDisplayName(p.getName());
					getNameConfig().getNickNames().set("Players." + p.getName(), p.getName());
					getNameConfig().saveNickNames();
				} else {
					p.setDisplayName(getNickMsger().getColor(getNameConfig().getNickNames().getString("Players." + p.getName()) + "&f"));
					
					if(Config.NICKNAME_TABLIST == true){
						p.setPlayerListName(getNickMsger().stripColor(p.getDisplayName()));
					}
						
					if(nickedPlayers.containsKey(messenger.stripColor(getNameConfig().getNickNames().getString("Players." + p.getName())))){
						nickedPlayers.remove(messenger.stripColor(getNameConfig().getNickNames().getString("Players." + p.getName())));
						nickedPlayers.put(messenger.stripColor(getNameConfig().getNickNames().getString("Players." + p.getName())), p.getName());
					} else {
						nickedPlayers.put(messenger.stripColor(getNameConfig().getNickNames().getString("Players." + p.getName())), p.getName());
					}
					
					if(Config.TAGAPI_ENABLED == true){
						TagAPI.refreshPlayer(p);
					}
					
					loaded++;
				}
			}
		}
	}
	
	public HashMap<String, String> getNickedPlayers(){
		return nickedPlayers;
	}
	
	public NickNamesConfig getNameConfig(){
		return nickNames;
	}
	
	public NickNamesBlacklist getBlacklist() {
		return blacklist;
	}
	
	public NickLogger getNickLogger(){
		return nickLogger;
	}
	
	public Messenger getNickMsger(){
		return messenger;
	}
	
}
