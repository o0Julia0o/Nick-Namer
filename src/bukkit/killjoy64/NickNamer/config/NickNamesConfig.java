package bukkit.killjoy64.NickNamer.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import bukkit.killjoy64.NickNamer.NickNamer;

public class NickNamesConfig {
	
	private FileConfiguration nickConfig = null;
	private File nickConfigFile = null;
	private NickNamer nick;
	
	public NickNamesConfig(NickNamer instance){
		nick = instance;
	}
	
	public void reloadNickNames() {
	    if (nickConfigFile == null) {
	    nickConfigFile = new File(nick.getDataFolder(), "Nick Names.yml");
	    }
	    nickConfig = YamlConfiguration.loadConfiguration(nickConfigFile);
	 
	    InputStream defConfigStream = nick.getResource("Nick Names.yml");
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        nickConfig.setDefaults(defConfig);
	    }
	}
	
	public FileConfiguration getNickNames() {
	    if (nickConfig == null) {
	        reloadNickNames();
	    }
	    return nickConfig;
	}
	
	public void saveNickNames() {
	    if (nickConfig == null || nickConfigFile == null) {
	    return;
	    }
	    try {
	        getNickNames().save(nickConfigFile);
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	}
	
}
