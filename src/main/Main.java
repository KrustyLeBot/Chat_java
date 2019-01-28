package main;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import network.*;

public class Main {

	public static TCPClient co_tcp;
	public static UDPSender msg_sender = new UDPSender();
	public static UDPReceiver msg_receiver;
	public static Map<String,InetAddress> hm_users;
	public static Map<InetAddress, String> user_state;
	public static User me = null;
	public static User blank = null;
	public static User broadcast = null;
	public static GUI_Thread graphic_thread = new GUI_Thread();
	public static InetAddress local_host;
	public static boolean connected = false;
	public static boolean connecting = false;
	public static Scanner reader = new Scanner(System.in);  // Reading from System.in
	public static GUI frame_gui = new GUI();
	public static Connect_thread connect = new Connect_thread();
	public static Save_msg save;
	
	
	public static void main(String[] args) {		
		//Creation of the connected users list
		hm_users = new HashMap<>();
		
		//Get local host ip adress
		try {local_host = getLocalAddress();} catch (SocketException e1) {e1.printStackTrace();}
		
		//Creation of the user blank => get all people connected without pseudo
		blank = new User("BLANK_USER", local_host);
				
		//Creation of the user broadcast with represent everyone on the network and add it to the list of user
		InetAddress addr=null;
		try {addr = InetAddress.getByName("255.255.255.255");} catch (UnknownHostException e) {e.printStackTrace();
		}
		broadcast = new User("BROADCAST", addr);
		hm_users.put(broadcast.pseudo, broadcast.ip);
		
		save = new Save_msg();
		
		try {
			frame_gui.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
		
	//Start The receiver in another thread
	public static void StartReceiver() {
		msg_receiver = new UDPReceiver();
		Thread t = new Thread(msg_receiver);
		t.start();
	}
	
	//Start The GUI_Thread in another thread
	public static void StartGUI_Thread() {
		Thread t = new Thread(graphic_thread);
		t.start();
	}
	
	
	public static void Connect() {
		Thread t = new Thread(connect);
		t.start();
	}
	
 	public static void Disconnect() {
		//Broadcast bye to everybody
		msg_sender.sendBye(me);
		hm_users.clear();
		hm_users.put(broadcast.pseudo, broadcast.ip);
		connecting = false;
		connected = false;
		try {msg_receiver.interruption();} catch (Throwable e) {e.printStackTrace();}
		msg_receiver.closeSocket();
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
		
		
		String entete = null;
		if(!Save_msg.conversations.containsKey(dest)) entete="";
		else entete = Save_msg.conversations.get(dest);
		
		String msg = (new Date()).toString() + " : " + "Moi -> " + frame_gui.table.getValueAt(frame_gui.table.getSelectedRow(), 0) + " : \n" + frame_gui.textField.getText() + "\n\n";
		frame_gui.textPane.setText(entete+msg);
		
		Save_msg.conversations.remove(dest);
		Save_msg.conversations.put(dest, entete+msg);	
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
