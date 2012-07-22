package bukkit.killjoy64.NickNamer.logging;

import java.util.logging.Logger;

public class NickLogger {

	private String prefix;
	private Logger logger;
	
	public NickLogger(){
		prefix = "[NickNamer] ";
		logger = Logger.getLogger("Minecraft");
	}
	
	public void log(String log){
		logger.info(prefix + log);
	}
	
	public void warn(String log){
		logger.warning(prefix + log);
	}
	
}
