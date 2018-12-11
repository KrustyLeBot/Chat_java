package main;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class User implements Serializable{
	
	//A user has a pseudo (unique) and an @IP
	public String pseudo;	
	public InetAddress ip;
	
	public User(String pseudo, InetAddress i) {
		this.pseudo = pseudo;
		this.ip = i;
	}
}
