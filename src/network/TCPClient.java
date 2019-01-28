package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

import main.Main;

public class TCPClient implements Runnable {
	
	private Socket sock;
	private InetAddress ipToSend;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	public int z;

	public TCPClient(String ipToSend) {
		try {
			this.ipToSend = InetAddress.getByName(ipToSend);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("TCPClient cree " + ipToSend);
	}

	@Override
	public void run() {
		try {
			System.out.println("Co serveur lancée");
			this.sock = new Socket(this.ipToSend, 1235);
			oos = new ObjectOutputStream(sock.getOutputStream());
			ois = new ObjectInputStream(sock.getInputStream());
			this.z = 0;
					
			comming_out("Online");
			
			while(z == 0) {
				try {
					Main.user_state = (Map<InetAddress, String>) ois.readObject();
					System.out.println(Main.user_state);
				} catch (ClassNotFoundException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void deco() {
		System.out.println("On sort du while de TCPClient");
		
		try {
			oos.close();
			ois.close();
			this.sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void comming_out(String etat) {
		System.out.println("Envoi etat : " + etat);
		try {
			oos.writeObject(etat);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
