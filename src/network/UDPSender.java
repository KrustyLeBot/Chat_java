package network;
import main.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import Messages.*;
import main.*;


public class UDPSender {
	
	private DatagramSocket socket;
	private Message lastMessage;
	
	public UDPSender(){
		try {
			this.socket = new DatagramSocket();
		} catch (SocketException e) {
			System.err.println("Socket couldn't be created.");
			e.printStackTrace();
		}
	}
	
	public UDPSender(DatagramSocket sock){
		this.socket=sock;
	}
	
	/*
	 * Method that send a given message in UDP mode
	 */
	public void sendMess(Message mes) {
		//Save the last message sent
		this.lastMessage=mes;
		
		//Arbitrary port
		int port = 1234;
		
		//Messages length & output stream
		byte[] buf = new byte[2048];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(mes);
			oos.close();
			buf=baos.toByteArray();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//datagram message creation
		DatagramPacket mestosend = new DatagramPacket(buf, buf.length, mes.getDestinataire().ip, port);
		
		//Sending the datagram
		try {
			this.socket.send(mestosend);
		} catch (IOException e) {
			System.err.println("Message failed to send");
			e.printStackTrace();
		}
	}
	
	/*
	 * Send Check to a specific person
	 */
	
	public void sendCheck(int type, User src, User dest){
		MsgCheck mes=null;
		switch(type){
		case 1: 
			//Msg_Check => question=false
			mes = new MsgCheck(src, dest,false);
			break;
		case 2: 
			//Check_Ok => answer=true
			mes = new MsgCheck(src, dest,true);
			break;
		default : 
			break;
		}
		this.sendMess(mes);
	}

	
	/*
	 * Send Check in broadcast
	 */
	public void sendCheckAll(User src){
		InetAddress addr=null;
		
		MsgCheck mes = new MsgCheck(src, Main.broadcast, false);
		this.sendMess(mes);
	}
	
	/*
	 * Send Hello in to someone => Knock Knock protocol
	 */
	public void sendHello(int type, User src, User dest){
		boolean ack=false;
		boolean connect=false;
		switch(type){
		case 1 : 
			//Message Hello
			ack=false;
			connect=true;
			break;
		case 2 : 
			//Message Hello_Ok
			ack=true;
			connect=true;
			break;
		case 3 : 
			//Message Hello_Not_Ok
			ack=true;
			connect=false;
			break;
	}
		MsgHello mes = new MsgHello(src, dest,ack, connect);
		this.sendMess(mes);
	}
	
	/*
	 * Send goodbyeMessage in broadcast => disconnect
	 */
	public void sendBye(User src){
		InetAddress addr=null;
		
		MsgGoodbye mes = new MsgGoodbye(src, Main.broadcast);
		this.sendMess(mes);
	}
	
	/*
	 * Send message Text
	 */
	public void sendText(String text, User src, User dest){
		MsgTxt mes = new MsgTxt(src, dest, text);
		this.sendMess(mes);
	}
	
	
	
	public DatagramSocket getSocket(){
		return this.socket;
	}
	
	public void closeSocket(){
		System.out.println("Closing socket at port "+this.socket.getLocalPort());
		socket.close();
	}
	
	public Message getLastMessage(){
		return this.lastMessage;
	}
	
	protected void finalize() throws Throwable
    { 
		try{
			System.out.println("Closing socket at port "+this.socket.getLocalPort());
			socket.close();
		}
		finally{
			super.finalize();
		}
    }

}
