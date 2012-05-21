package me.killjoy64.Nick;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;

public class NickConfig {
	
	public Nick plugin;
	
	private YamlConfiguration config;
	public HashMap<String, Object> configDefaults = new HashMap<String, Object>();
	
	public NickConfig(File configFile){
		config = new YamlConfiguration();
		
		configDefaults.put("SpoutEnabled", false);
		configDefaults.put("OpEnabled", true);
		configDefaults.put("Secrecy.Enabled", true);
		configDefaults.put("Secrecy.JoinMsg", "&e+displayname &ejoined the game");
		configDefaults.put("Secrecy.LeaveMsg", "&e+displayname &eleft the game");
		configDefaults.put("Secrecy.KickMsg", "&e+displayname &ewas kicked");
		
		if(configFile.exists() == false){
			for(String key : configDefaults.keySet()){
				config.set(key, configDefaults.get(key));
			}
			try {
				config.save(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				config.load(configFile);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public int getInt(String key){
		if(configDefaults.containsKey(key) == false){
			return 0;
		}
		return config.getInt(key, Integer.parseInt(configDefaults.get(key).toString()));
	}
	
	public boolean getBoolean(String key){
		if(configDefaults.containsKey(key) == false){
			return false;
		}
		return config.getBoolean(key, (Boolean)configDefaults.get(key));
	}
	
	public String getString(String key){
		if(configDefaults.containsKey(key) == false){
			return "";
		}
		return config.getString(key, (String)configDefaults.get(key));
	}
}