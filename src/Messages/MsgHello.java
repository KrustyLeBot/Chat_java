package Messages;

import main.User;

public class MsgHello extends Message{

	public boolean ack;
	public boolean connect;
	
	public MsgHello(User src, User dest, boolean ack, boolean connect) {
		super("Hello.", src, dest);
		this.ack = ack;
		this.connect = connect;
	}
	

}
