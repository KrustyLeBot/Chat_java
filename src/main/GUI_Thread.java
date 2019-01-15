package main;

import javax.swing.table.DefaultTableModel;

import Messages.*;
import network.*;

public class GUI_Thread implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		Main.msg_receiver.addNewMessageListener(new NewMessageListener () {
			@Override public void aMessageHasBeenReceived(Message msg) {
				
				if(!(Main.connecting | Main.connected)) return;
				
				System.out.println("messaged recu de : " + msg.getEmetteur().pseudo);
				
				//If the message is from the user itself(like a broadcast) exclude it
				if(msg.getEmetteur().ip.equals(Main.local_host))return;	
				DefaultTableModel model = (DefaultTableModel) Main.frame_gui.table.getModel();
				
				//First, message paring and casting to the correct type
				//If the message is a check
				if(msg instanceof MsgCheck) {
					
					System.out.println("Check recu");
					MsgCheck message = (MsgCheck) msg;	
					
					//If it is a message from blank_user and asking an answer
					if(message.need_an_answer & message.getEmetteur().pseudo.equals(Main.blank.pseudo)) {
						//Then send a check back without asking for an answer
						System.out.println("Check avec demande de reponse");
						Main.msg_sender.sendCheck(false, Main.me, message.getEmetteur());
					}

					//If it is check not asking an answer
					else if(!message.need_an_answer) {
						//Then add the user to the list of connected people
						System.out.println("Check sans demande de reponse");
						
						if(!Main.hm_users.containsKey(message.getEmetteur().pseudo)) {
							Main.hm_users.put(message.getEmetteur().pseudo, message.getEmetteur().ip);
							System.out.println("User added: " + message.getEmetteur().pseudo);
								
							model.addRow(new Object[]{message.getEmetteur().pseudo});
						}
					}
				}
				
				else if(msg instanceof MsgNewPseudo) {
					System.out.println("Message new pseudo recu");
					//Remplacer l'ancien user par le nouveau dans les liste d'utilisateurs
					//connectés et dans la sauvegarde des messages
					MsgNewPseudo message = (MsgNewPseudo) msg;
					
					if(Save_msg.open_conection.containsKey(message.getEmetteur())) {
						Save_msg.open_conection.remove(message.getEmetteur());
						Save_msg.open_conection.put(message.new_me, true);
					}
					
					if(Save_msg.conversations.containsKey(message.getEmetteur().pseudo)){
						String str = Save_msg.conversations.get(message.getEmetteur().pseudo);
						Save_msg.conversations.remove(message.getEmetteur().pseudo);
						
						str = str + "=== " + message.getEmetteur().pseudo + " became " + message.new_me.pseudo + " ===\n\n";
						
						Save_msg.conversations.put(message.new_me.pseudo, str);
						
						//actualisation du texte si on est entrain de discuter avec la personne et de l'entete
						int i = Main.frame_gui.table.getSelectedRow();
						if(i != -1) {
							if(Main.frame_gui.table.getValueAt(Main.frame_gui.table.getSelectedRow(), 0).equals(msg.getEmetteur().pseudo) | Main.frame_gui.table.getValueAt(Main.frame_gui.table.getSelectedRow(), 0).equals("** " + msg.getEmetteur().pseudo)) {
								Main.frame_gui.textPane.setText(str);
								Main.frame_gui.lblChoisirUnCorrespondant.setText("Conversation with " + message.new_me.pseudo);
								
							}
						}
						
						//modification du pseudo dans la liste des utilisateurs
						for (int i2 = Main.frame_gui.table.getRowCount() - 1; i2 >= 0; --i2) {
							//Notification
							String str2 = (String) model.getValueAt(i2, 0);
							if(str2.equals(msg.getEmetteur().pseudo)) model.setValueAt(message.new_me.pseudo, i2, 0);
						}
						Main.hm_users.remove(message.getEmetteur().pseudo);
						Main.hm_users.put(message.new_me.pseudo, message.new_me.ip);
						System.out.println(Main.hm_users);
					}
					
					Save_msg.Save_messages();
				}
				
				
				//If the message is a bye, remove the sender from the list of connected users
				else if(msg instanceof MsgGoodbye) {
					System.out.println("Message goodbye recu");
					System.out.println("User removed: " + msg.getEmetteur().pseudo);
					Main.hm_users.remove(msg.getEmetteur().pseudo);
					
					
					//Pour supprimer un utilisateur de la table 
					for (int i = model.getRowCount() - 1; i >= 0; --i) {
						if (model.getValueAt(i, 0).equals(msg.getEmetteur().pseudo) | model.getValueAt(i, 0).equals("** "+ msg.getEmetteur().pseudo)) {
							model.removeRow(i);
			            }
					}
				}
				
				//If the message is a text message
				else if(msg instanceof MsgTxt) {
					
					if(!Main.hm_users.containsKey(msg.getEmetteur().pseudo)) {
						System.out.println(Main.hm_users);
						Main.hm_users.put(msg.getEmetteur().pseudo, msg.getEmetteur().ip);						
						model.addRow(new Object[]{msg.getEmetteur().pseudo});
					}
					
					System.out.println("Message receive from "+ msg.getEmetteur().pseudo);
					System.out.println(msg.toTxt());
					
					String entete = null;
					if(!Save_msg.conversations.containsKey(msg.getEmetteur().pseudo)) entete="";
					else entete = Save_msg.conversations.get(msg.getEmetteur().pseudo);
					
					String str = entete + (msg.getHorodatation() + " : " + msg.getEmetteur().pseudo + " -> Moi : \n" + msg.toTxt() + "\n\n");
					Save_msg.conversations.remove(msg.getEmetteur().pseudo);
					Save_msg.conversations.put(msg.getEmetteur().pseudo, str);
					
					int i = Main.frame_gui.table.getSelectedRow();
					if(i != -1) {
						if(Main.frame_gui.table.getValueAt(Main.frame_gui.table.getSelectedRow(), 0).equals(msg.getEmetteur().pseudo) | Main.frame_gui.table.getValueAt(Main.frame_gui.table.getSelectedRow(), 0).equals("** " + msg.getEmetteur().pseudo)) {
							Main.frame_gui.textPane.setText(str);
						}
					}	
					
					//Notify the user from who he get a new message
					for (int i1 = Main.frame_gui.table.getRowCount() - 1; i1 >= 0; --i1) {
						//Notification
						System.out.println("On Notifie");
						String str1 = (String) model.getValueAt(i1, 0);
						if(i1==i & i != -1) return;
						if(str1.equals(msg.getEmetteur().pseudo)) model.setValueAt("** " + str1, i1, 0);
					}
					
					Save_msg.Save_messages();
				}
			}
		});
	}
}
