package Messages;

import main.User;

public class MsgHello extends Message{

	public boolean ack;
	public boolean connect;
	
	public MsgHello(String src, String dest, boolean ack, boolean connect) {
		super("Hello.", new User(src));
		this.ack = ack;
		this.connect = connect;
	}
	

}
