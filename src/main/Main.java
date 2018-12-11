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
	public static Map<String,String> hm_users;
	public static User me;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hellow World");
		
		//Creer un thread pour UDPReceiver qui ecoute en permanence et qui possède un buffer de 10 msg
		//Creer un thread qui gère l'interface graphique
		
		//Creation of UDP interfaces
		msg_receiver = new UDPReceiver();
		msg_sender = new UDPSender();
		
		//Creation of the connected users list
		hm_users = new HashMap<>();
		
		//Set the ip of the user
		try {
			me.ip = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void Connect() throws IOException {
		//The user wants to connect to the network
		//get all users connected
		//Broadcast a check
		msg_sender.sendCheckAll(me.pseudo);
		//Get the response from everyone and add them to the hasmap
		int i = 0;
		while(msg_receiver.getMessagewithip(i) != null) {
			//While there is messages in the buffer
			MsgCheck message = (MsgCheck) msg_receiver.getMessagewithip(i);
			if(message.answer_to_a_check) {
				hm_users.put(message.getEmetteur().pseudo, message.getEmetteur().ip);
				
			}
			i=(i+1)%10;
		}
		System.out.println("All users on the network added to the Hashmap");
	}
	
	public void Disconnect() {
		msg_sender.sendBye(me.pseudo);
	}
	
	public void set_pseudo(String pseudo) {
		this.me.pseudo = pseudo;
	}
	
	public int Talk_with(String dest) {
		//Knock Knock method with only syn and syn/ack
		//Send a SYN
		
		//Wait for a SYN/ACK
		
		
		return 0;
	}

}
