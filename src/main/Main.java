package main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import Messages.*;
import network.*;

public class Main {

	public static UDPSender msg_sender;
	public static UDPReceiver msg_receiver;
	public static Map<String,InetAddress> hm_users;
	public static User me = null;
	public static User broadcast = null;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello World");
		
		//Creation of the connected users list
		hm_users = new HashMap<>();
		
		//Creation of the user itself
				
		//Creation of the user broadcast with represent everyone on the network and add it to the list of user
		InetAddress addr=null;
		try {
			addr = InetAddress.getByName("255.255.255.255");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		broadcast = new User("BROADCAST", addr);
		hm_users.put(broadcast.pseudo, broadcast.ip);
		
		
		//Creation of UDP interfaces
		msg_receiver = new UDPReceiver();
		msg_sender = new UDPSender();
		
		StartReceiver();
		
		set_pseudo("Jerome");
		
		
		//test message send in broadcast
		msg_sender.sendText("yop c'est jerome", me, broadcast);
		
	}
	
	//Start The receiver in another thread
	public static void StartReceiver() {
		Thread t = new Thread(msg_receiver);
		t.start();
	}
	
	public int Connect() throws IOException {
		if(me != null) {
			//The user wants to connect to the network and get all users connected
			//Broadcast a check
			msg_sender.sendCheckAll(me);
			//Get the response from everyone and add them to the hasmap
			int i = 0;
			while(msg_receiver.getMessagewithip(i) != null) {
				//While there is messages in the buffer AND THEY ARE AN INSTANCE OF MsgCheck
				Message msg = msg_receiver.getMessagewithip(i);
				if(msg instanceof MsgCheck) {
					MsgCheck message = (MsgCheck) msg;
					//If they are a response to the check broadcast
					if(message.answer_to_a_check) {
						//Add them to the list of users
						hm_users.put(message.getEmetteur().pseudo, message.getEmetteur().ip);
					}
				}
				i=(i+1)%10;
			}
			System.out.println("All users on the network added to the Hashmap");
			return 1;
		}
		else return 0;
		
	}
	
	public void Disconnect() {
		msg_sender.sendBye(me);
	}
	
	//Set the pseudo and the adress of the user
	public static void set_pseudo(String pseudo) {
		InetAddress i = null;
		try {
			i = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		me = new User(pseudo, i);
	}
	
	public int Talk_with(String dest) {
		//Knock Knock method with only syn and syn/ack
		//Send a SYN
		
		//Wait for a SYN/ACK
		
		
		return 0;
	}

}
