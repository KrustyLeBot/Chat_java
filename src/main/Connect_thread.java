package main;

public class Connect_thread  implements Runnable{

	public void run() {
		Main.connecting = true;
		//send a check in broadcast askinf for an aswer
		Main.msg_sender.sendCheckAll(Main.blank, true);
		
		//Waiting 5 secondes so that everyone can respond
		try {Thread.sleep(5*1000);} catch (InterruptedException e) {e.printStackTrace();}
		
		
		//Now,Set the pseudo=>has to be unique, and then notify everyone if it is
		if(Main.hm_users.get(Main.frame_gui.textField_1.getText()) != null) {
			Main.connected = false;
		}
		Main.set_pseudo(Main.frame_gui.textField_1.getText());
		
		
		if(Main.me != null) {
			//Broadcast to all you pseudo and ip without asking for and answer
			Main.msg_sender.sendCheckAll(Main.me, false);
			
			System.out.println("All users on the network added to the Hashmap");
			System.out.println(Main.hm_users.toString());
			
			Main.connecting = false;
			Main.connected = true;
		}
		else {
			Main.connecting = false;
			Main.connected = false;
		}
		
		
		if(Main.connected) {
			Main.frame_gui.btnDeconnexion.setEnabled(true);
			Main.frame_gui.textField_1.setEnabled(false);
			Main.frame_gui.btnEnvoyer.setEnabled(true);
		}
		else {
			System.out.println("Erreur lors de la connexion");
			Main.frame_gui.btnConnexion.setEnabled(true);
		}
	}
}
