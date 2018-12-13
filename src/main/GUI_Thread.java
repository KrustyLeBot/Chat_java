package main;

import Messages.*;
import network.*;

public class GUI_Thread implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		Main.msg_receiver.addNewMessageListener(new NewMessageListener () {
			@Override public void aMessageHasBeenReceived(Message msg) {
				
				
				System.out.println("J'ai reçu un message de "+ msg.getEmetteur().pseudo);
				
				//If the message is from the user itself(like a broadcast) exclude it
				if(msg.getEmetteur().ip.equals(Main.local_host)) {
					System.out.println("messaged reçu de localhost: " + msg.getEmetteur().ip.toString());
					return;	
				}
				
				
				//First, message paring and casting to the correct type
				//If the message is a check
				if(msg instanceof MsgCheck) {
					MsgCheck message = (MsgCheck) msg;	
					System.out.println("J'ai reçu un message de "+ message.getEmetteur().pseudo+message.need_an_answer);
					
					//If it is a message from blank_user and asking an answer
					if(message.need_an_answer & message.getEmetteur().pseudo.equals(Main.blank.pseudo)) {
						//Then send a check back without asking for an answer
						Main.msg_sender.sendCheck(false, Main.me, message.getEmetteur());
					}

					//If it is check not asking an answer
					else if(!message.need_an_answer) {
						//Then add the user to the list of connected people
						Main.hm_users.put(message.getEmetteur().pseudo, message.getEmetteur().ip);
						System.out.println("User added: " + message.getEmetteur().pseudo);
					}
					
					//It can't be a check asking an answer without the sender being blank
				}
				
				
				//If the message is a bye, remove the sender from the list of connected users
				else if(msg instanceof MsgGoodbye) {
					Main.hm_users.remove(msg.getEmetteur().pseudo);
				}
				
				
				//If the message is a text message
				else if(msg instanceof MsgTxt) {
					System.out.println("J'ai reçu un message de "+ msg.getEmetteur().ip.toString());
					System.out.println(msg.toTxt());
				}
			}
		});
	}
}
