package me.killjoy64.Nick;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Nick extends JavaPlugin {
	
	public String PluginDirPath;
	public File configFile;
	public NickConfig config;
	
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;
	
	private renameCmdExecutor renameEx = new renameCmdExecutor(this);
	private nickCmdExecutor nickEx = new nickCmdExecutor(this);
	private oldCmdExecutor oldEx = new oldCmdExecutor(this);
	private realnickCmdExecutor realEx = new realnickCmdExecutor(this);
	public NickListener listener = new NickListener(this);
	
	@Override
	public void onDisable() {
		saveCustomConfig();
	}

	@Override
	public void onEnable() {
		
		getCommand("rename").setExecutor(renameEx);
		getCommand("nick").setExecutor(nickEx);
		getCommand("old").setExecutor(oldEx);
		getCommand("realnick").setExecutor(realEx);
		
		getServer().getPluginManager().registerEvents(listener, this);
		
		PluginDirPath = getDataFolder().getAbsolutePath();
		configFile = new File(PluginDirPath + File.separator +  "config.yml");
		
        config = new NickConfig(configFile);
     
        getCustomConfig();
        saveCustomConfig();
        
        if(config.getBoolean("SpoutEnabled") == false){
        	System.out.println("[Nick] Nick is not using Spout Features. If you have Spout enabled, check the config.yml");
        } else {
        	System.out.println("[Nick] Nick is using Spout Features! If you do not have Spout, change this proprty in the config.yml");
        }
        	
	}
	
	public void reloadCustomConfig() {
	    if (customConfigFile == null) {
	    customConfigFile = new File(getDataFolder(), "Nick Names.yml");
	    }
	    customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
	 
	    // Look for defaults in the jar
	    InputStream defConfigStream = getResource("customConfig.yml");
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        customConfig.setDefaults(defConfig);
	    }
	}
	
	public FileConfiguration getCustomConfig() {
	    if (customConfig == null) {
	        reloadCustomConfig();
	    }
	    return customConfig;
	}
	
	public void saveCustomConfig() {
	    if (customConfig == null || customConfigFile == null) {
	    return;
	    }
	    try {
	        customConfig.save(customConfigFile);
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	}
	
	public void info(String m){
		String v = " v0.5 ";
		System.out.println("NickName" + v + m);
	}
	
    public String ConvToStrWithColor (String str) {
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
    
    public String ReplaceColorCodes(String str){
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
