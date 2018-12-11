package Messages;
import main.*;

public class MsgCheck extends Message{
	
	public boolean answer_to_a_check;
	public MsgCheck(String src, String dest, boolean answer_to_a_check) {
		super("Check.",new User(src));
		this.answer_to_a_check = answer_to_a_check;
	}

}
