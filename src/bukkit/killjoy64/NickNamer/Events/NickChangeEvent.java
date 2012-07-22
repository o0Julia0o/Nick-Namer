package bukkit.killjoy64.NickNamer.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NickChangeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player, target;
    private String nick;
    private boolean cancelled;
    
    public NickChangeEvent(Player player, String nick, Player target) {
      	this.player = player;
      	this.target = target;
      	this.nick = nick;
      	cancelled = false;
    }
    
    public String getNick(){
    	return nick;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public Player getTarget(){
    	if(target == null){
        	return player;  	
    	} else {
    		return target;
    	}
    }
    
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    public boolean isCancelled(){
    	return cancelled;
    }
    
    public void setCancelled(boolean cancel){
    	this.cancelled = cancel;
    }
}
