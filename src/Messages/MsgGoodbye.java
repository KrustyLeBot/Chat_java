package Messages;

import main.User;

public class MsgGoodbye extends Message{
	
	public MsgGoodbye(String src) {
		super("Goodbye.", new User(src));		
	}
}
