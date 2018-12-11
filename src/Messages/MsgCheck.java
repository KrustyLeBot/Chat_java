package Messages;
import main.*;

public class MsgCheck extends Message{
	
	public boolean answer_to_a_check;
	public MsgCheck(User src, User dest, boolean answer_to_a_check) {
		super("Check.",src, dest);
		this.answer_to_a_check = answer_to_a_check;
	}

}
