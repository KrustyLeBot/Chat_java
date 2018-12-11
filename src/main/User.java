package main;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class User implements Serializable{
	
	public String pseudo;	
	public String ip;
	
	public User(String src) {
		this.pseudo = src;
		try {
			this.ip = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
