package Messages;
import main.*;

public class MsgNewPseudo extends Message{
	
	public User new_me;
	public MsgNewPseudo(User src, User dest, User new_me) {
		super("new pseudo",src, dest);
		this.new_me = new_me;
	}

}