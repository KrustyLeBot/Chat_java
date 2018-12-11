package Messages;
import java.io.Serializable;

import main.*;

public class MsgTxt extends Message implements Serializable{
	
	public MsgTxt(User src, User dest, String msg) {
		super(msg, src, dest);		
	}

}
