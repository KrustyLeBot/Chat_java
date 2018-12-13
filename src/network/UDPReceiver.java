package network;
import main.*;
import Messages.*;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import javax.swing.event.EventListenerList;


public class UDPReceiver implements Runnable {

	private DatagramSocket socket;
	private boolean receptionMessage = false;
	private Message messageRecu;
	private Message[] messagewithip;
	
    private final EventListenerList listeners = new EventListenerList();

    //Add a listener
	public void addNewMessageListener(NewMessageListener listener) {
        this.listeners.add(NewMessageListener.class, listener);
    }

	//Remove a listener
    public void removeNewMessageListener(NewMessageListener listener) {
        this.listeners.remove(NewMessageListener.class, listener);
    }

    //SAve the list of listeners
    public NewMessageListener[] getNewMessageListeners() {
        return listeners.getListeners(NewMessageListener.class);
    }

    //Notify listeners of a new message
    protected void newMessageReceived(Message msg_event) {
		for(NewMessageListener listener : getNewMessageListeners()) {
            listener.aMessageHasBeenReceived(msg_event);
        }
	}

	public UDPReceiver() {
		try {
			this.socket = new DatagramSocket(1234);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		this.messagewithip = new Message[10];
	}
	
	public UDPReceiver(DatagramSocket sock) {
		this.socket = sock;
		this.messagewithip = new Message[10];
	}

	public boolean isReceptionMessage() {
		return receptionMessage;
	}

	public void setReceptionMessage(boolean receptionMessage) {
		this.receptionMessage = receptionMessage;
	}

	public Message getMessageRecu() {
		this.receptionMessage = false;
		return this.messageRecu;
	}

	public Message getMessagewithip(int index) {
		return messagewithip[index];
	}
	
	public void setMessagewithip(int index, Message message) {
		messagewithip[index]=message;
	}
	
	public void interruption(){
		Thread.currentThread().interrupt();
	}

	@Override
	public void run() {
		byte[] buf = new byte[8192];
		Message message=null;
		InetAddress address = null;
		int index = 0; 
		//index of message in the MessageReceived array
		try {
			DatagramPacket messageReceived = new DatagramPacket(buf, buf.length);
			while (!Thread.currentThread().isInterrupted()) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
				//Put the socket in reception mode
				try{
					this.socket.receive(messageReceived);
				}
				catch (IOException e1) {
					return;
				}
				try{
					//Deserialize the object and create a Message object from the bytes read
					ByteArrayInputStream bais = new ByteArrayInputStream(messageReceived.getData());
					ObjectInputStream ois =  new ObjectInputStream(bais) ;
					address = messageReceived.getAddress();
					try {
						message = (Message) ois.readObject() ;
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				this.messageRecu=message;
				this.messagewithip[index] = this.messageRecu;
				index = (index+1)%10;
				this.setReceptionMessage(true);
				this.newMessageReceived(message);
			}
		}
		finally {
			//this.socket.close();
			//Already called by closeSocket() method
		}

	}
	
	public DatagramSocket getSocket(){
		return this.socket;
	}
	
	public void closeSocket(){
		System.out.println("Closing socket from port "+this.socket.getLocalPort());
		socket.close();
	}
	
	public void finalize() throws Throwable
    { 
		try{
			System.out.println("Closing socket from port "+this.socket.getLocalPort());
			socket.close();
		}
		finally{
			super.finalize();
		}
    }

}
