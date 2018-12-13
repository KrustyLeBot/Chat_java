package main;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
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
	public static boolean connected = false;
	public static Scanner reader = new Scanner(System.in);  // Reading from System.in
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello World");
		
		//Creation of the connected users list
		hm_users = new HashMap<>();
		
		//Get local host ip adress
		try {local_host = getLocalAddress();} catch (SocketException e1) {e1.printStackTrace();}
		System.out.println(local_host);
		
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
		
		run();
	}
	
	
	public static void run() {
		
		while(true) {
			System.out.println("Connexion => 0");
			System.out.println("Deconnexion => 1");
			System.out.println("Envoyer un message texte => 2");
			
			System.out.println("Choose an action & enter the correct number: ");
			
			switch(reader.nextInt()) {
				case 0:
					try {Connect();} catch (IOException e) {e.printStackTrace();}
					break;
					
				case 1:
					Disconnect();
					break;
					
				case 2:
					System.out.println("Pseudo of the person");
					String dest = reader.next();
					System.out.println("Message to send");
					String msg = reader.next();
					send_msg(dest,msg);
					break;
			}
			//once finished
			try {Thread.sleep(2*1000);} catch (InterruptedException e) {e.printStackTrace();}
		}
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
		//send a check in broadcast askinf for an aswer
		msg_sender.sendCheckAll(blank, true);
		
		//Waiting 5 secondes so that everyone can respond
		try {Thread.sleep(5*1000);} catch (InterruptedException e) {e.printStackTrace();}
		
		
		//Now,Set the pseudo=>has to be unique, and then notify everyone if it is
		String str = AskPseudo();
		if(hm_users.get(str) != null) {
			connected = false;
			return 0;
		}
		set_pseudo(str);
		
		
		if(me != null) {
			//Broadcast to all you pseudo and ip without asking for and answer
			msg_sender.sendCheckAll(me, false);
			
			System.out.println("All users on the network added to the Hashmap");
			System.out.println(hm_users.toString());
			
			connected = true;
			return 1;
		}
		else {
			connected = false;
			return 0;
		}
		
	}
	public static void Disconnect() {
		//Broadcast bye to everybody
		msg_sender.sendBye(me);
		
		try {
			msg_receiver.interruption();
			msg_receiver.finalize();
			msg_sender.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}
	
	//Ask for a pseudo
	public static String AskPseudo() {
		String str = null;
		System.out.println("Enter a Pseudo: ");
		str = reader.next(); // Scans the next token of the input as an int.
		//once finished
		return str;
	}
	
	//Set the pseudo and the adress of the user
	public static void set_pseudo(String pseudo) {
		me = new User(pseudo, local_host);
	}
	
	public static void send_msg(String dest, String message) {
		if(!connected) {
			System.out.println("Connect to the network before trying to send a message");
			return;
		}
		if(hm_users.get(dest)==null) {
		System.out.println("The user is not connected to the network");
		return;
		}
		
		User des = new User(dest, hm_users.get(dest));
		
		msg_sender.sendText(message, me, des);		
	}
	
	public static InetAddress getLocalAddress() throws SocketException{
	    Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
	    while( ifaces.hasMoreElements() )
	    {
	      NetworkInterface iface = ifaces.nextElement();
	      Enumeration<InetAddress> addresses = iface.getInetAddresses();

	      while( addresses.hasMoreElements() )
	      {
	        InetAddress addr = addresses.nextElement();
	        if( addr instanceof Inet4Address && !addr.isLoopbackAddress() )
	        {
	          return addr;
	        }
	      }
	    }

	    return null;
	  }
}
