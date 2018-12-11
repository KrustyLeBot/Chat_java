package Messages;

import main.User;

public class MsgGoodbye extends Message{
	
	public MsgGoodbye(User src, User dest) {
		super("Goodbye.", src, dest);		
	}
}
