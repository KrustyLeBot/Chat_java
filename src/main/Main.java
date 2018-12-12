package main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import network.*;

public class Main {

	public static UDPSender msg_sender;
	public static UDPReceiver msg_receiver;
	public static Map<String,InetAddress> hm_users;
	public static User me = null;
	public static User blank = null;
	public static User broadcast = null;
	public static GUI_Thread graphic_thread;
	public static InetAddress local_host;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello World");
		
		//Creation of the connected users list
		hm_users = new HashMap<>();
		
		//Get local host ip adress
		try {
			local_host = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Creation of the user blank => get all people connected without pseudo
		blank = new User("BLANK_USER", local_host);
				
		//Creation of the user broadcast with represent everyone on the network and add it to the list of user
		InetAddress addr=null;
		try {
			addr = InetAddress.getByName("255.255.255.255");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		broadcast = new User("BROADCAST", addr);
		hm_users.put(broadcast.pseudo, broadcast.ip);
		
		
		//Creation of UDP interfaces & GUI thread
		msg_receiver = new UDPReceiver();
		msg_sender = new UDPSender();
		graphic_thread = new GUI_Thread();
		
		//Start the receiver and the GUI
		StartReceiver();
		StartGUI_Thread();

		//Try to engage the connection with the network
		try {
			Connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Ask for a pseudo
	public static String AskPseudo() {
		String str = null;
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("Enter a Pseudo: ");
		str = reader.next(); // Scans the next token of the input as an int.
		//once finished
		reader.close();
		return str;
	}
		
	//Start The receiver in another thread
	public static void StartReceiver() {
		Thread t = new Thread(msg_receiver);
		t.start();
	}
	
	
	//Start The GUI_Thread in another thread
		public static void StartGUI_Thread() {
			Thread t = new Thread(graphic_thread);
			t.start();
		}
	
		
	public static int Connect() throws IOException {
		//send a check in broadcast
		msg_sender.sendCheckAll(blank, true);
		
		//WAiting 10 secondes so that everyone can respond
		try {
			Thread.sleep(10*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Now,Set the pseudo=>has to be unique, and then notify everyone
		set_pseudo(AskPseudo());
		
		if(me != null) {
			//Broadcast to all you pseudo and ip without asking for and answer==false
			msg_sender.sendCheckAll(me, false);
			System.out.println("All users on the network added to the Hashmap");
			System.out.println(hm_users.toString());
			return 1;
		}
		else return 0;
		
	}
	
	
	public void Disconnect() {
		//Broadcast bye to everybody
		msg_sender.sendBye(me);
	}
	
	
	//Set the pseudo and the adress of the user
	public static void set_pseudo(String pseudo) {
		me = new User(pseudo, local_host);
	}
}
