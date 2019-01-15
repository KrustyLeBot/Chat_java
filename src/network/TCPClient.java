package network;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;

import main.Main;

public class TCPClient implements Runnable {
	
	private Socket sock;
	private InetAddress ipToSend;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public TCPClient(String fileName, InetAddress ipToSend) {
		this.sock = new Socket();
		this.ipToSend = ipToSend;
	}

	@Override
	public void run() {
		try {
			this.sock.connect(new InetSocketAddress(this.ipToSend, 8045));
			oos = new ObjectOutputStream(sock.getOutputStream());
			ois = new ObjectInputStream(sock.getInputStream());
			
			while(Main.connected) {
				try {
					Main.user_state = (Map<InetAddress, String>) ois.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			comming_out("Deconnexion");
			
			oos.close();
			ois.close();
			this.sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void comming_out(String etat) {
		try {
			oos.writeObject(etat);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
