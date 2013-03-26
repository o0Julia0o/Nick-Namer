package bukkit.killjoy64.NickNamer.config;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import bukkit.killjoy64.NickNamer.NickNamer;

public class NickNamesBlacklist {

	private NickNamer nick;
	private File blacklist;
	
	public NickNamesBlacklist(NickNamer instance) {
		nick = instance;
		
		blacklist = new File(nick.getDataFolder(), "Nick Names Blacklist.txt");
	}
	
	public void create() {
		if(!blacklist.exists()) {
			try {
				blacklist.createNewFile();
				nick.getNickLogger().log("Successfully created and loaded Blacklist");
			} catch (IOException e) {
				nick.getNickLogger().warn("Failed to create Blacklist");
			}
		} else {
			nick.getNickLogger().log("Successfully loaded Blacklist");
		}
	}
	
	public boolean blacklisted(String name) {
		DataInputStream input;
		
		try {
			input = new DataInputStream(new FileInputStream(blacklist));
			BufferedReader br = new BufferedReader(new InputStreamReader(input));
			
			String line;
			
			while((line = br.readLine()) != null){
				if(name.equalsIgnoreCase(line)) {
					return true;
				}
			}
			
		} catch(Exception e) {
			nick.getNickLogger().warn("Failed to see/check blacklist for name");
			return false;
		}
		
		return false;
	}
	
}
