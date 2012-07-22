package bukkit.killjoy64.NickNamer.util;

public enum NickType {
	
	TAKEN_NICKNAME("Your NickName has already been taken."),
	NOT_ENOUGH_ARGUMENTS("Not enough arguments for this command."),
	NO_ARGUMENTS("There are no arguments. /nicknamer help for more info"),
	NO_PERMISSION("You don't have permision to do this!"),
	CONSOLE_SENDER("Command must be used from in-game!"),
	INVALID_PLAYER("The target player is either offline or does not exist."),
	INVALID_NICKNAME("Found no player with that nickname."),
	INVALID_COMMAND("Command not found, invalid."),
	INVALID_ARGUMENT("Invalid Argument. Type /nicknamer help for more info.");
	
	NickType(String error){
		this.error = error;
	}
	
	private String error;
	
	public String getError(){
		return error;
	}
	
}
