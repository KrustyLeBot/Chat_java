package Messages;
import java.io.Serializable;

import main.*;

public class MsgTxt extends Message implements Serializable{
	
	public MsgTxt(String src, String dest, String msg) {
		super(msg, new User(src));		
	}

}
