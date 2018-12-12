package Messages;
import main.*;

public class MsgCheck extends Message{
	
	public boolean need_an_answer;
	public MsgCheck(User src, User dest, boolean need_an_answer) {
		super("Check.",src, dest);
		this.need_an_answer = need_an_answer;
	}

}
