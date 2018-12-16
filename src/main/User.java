package main;

import java.io.Serializable;
import java.net.InetAddress;

public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//A user has a pseudo (unique) and an @IP
	public String pseudo;	
	public InetAddress ip;
	
	public User(String pseudo, InetAddress i) {
		this.pseudo = pseudo;
		this.ip = i;
	}
}
