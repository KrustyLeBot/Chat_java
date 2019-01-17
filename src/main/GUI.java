package main;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class GUI extends JFrame {

	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;
	public JPanel contentPane;
	public JTable table;
	public JTextField textField;
	public JButton btnConnexion;
	public JButton btnEnvoyer;
	public JButton btnDeconnexion;
	public JTextField textField_1;
	public JScrollPane scrollPane_1;
	public JTextPane textPane;
	private JButton button;
	public JLabel lblChoisirUnCorrespondant;
	private JTextField textField_2;
	private JButton btnNewButton;



	/**
	 * Create the frame.
	 */
	public GUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 590, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		//Closed window process
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e) {
				System.out.println("Closed");
				
				if(Main.connected | Main.connecting) Main.Disconnect();
				
				e.getWindow().dispose();
			}
		});
		
		
		JScrollPane scrollPane = new JScrollPane();
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Send message when enter is pressed while editing message textfield
				System.out.println("Appui sur envoyer");
				//Try pour verifier qu'un utilisateur est bien selectionne
				try {
					//On verifie qu'un message est bien ecrit
					if (!textField.getText().equals("")) { 
						System.out.println("Envoi du message " + textField.getText() + " : " + table.getValueAt(table.getSelectedRow(), 0));
						//TODO
						//Appeler la methode send_msg
						
						Main.send_msg((String) table.getValueAt(table.getSelectedRow(), 0),textField.getText());
						textField.setText("");
					}
					else {
						System.out.println("Rentrer du texte avant d'envoyer le message");
					}
				}catch (Exception e1) {
					System.out.println("pas d'utilisateur selectionne");
				}	
			}
		});
		textField.setColumns(10);
		
		btnEnvoyer = new JButton("Send");
		btnEnvoyer.setEnabled(false);
		btnEnvoyer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Appui sur envoyer");
				//Try pour verifier qu'un utilisateur est bien selectionne
				try {
					//On verifie qu'un message est bien ecrit
					if (!textField.getText().equals("")) { 
						System.out.println("Envoi du message " + textField.getText() + " : " + table.getValueAt(table.getSelectedRow(), 0));
						//TODO
						//Appeler la methode send_msg
						
						Main.send_msg((String) table.getValueAt(table.getSelectedRow(), 0),textField.getText());
						textField.setText("");
					}
					else {
						System.out.println("Rentrer du texte avant d'envoyer le message");
					}
				}catch (Exception e) {
					System.out.println("pas d'utilisateur selectionn�");
				}				
			}
		});
		
		
		
		btnConnexion = new JButton("Connect");
		btnConnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("connexion de " + textField_1.getText());
				//TODO
				//Appeler la methode Connect
				
				btnDeconnexion.setEnabled(false);
				btnConnexion.setEnabled(false);

				textField.setEnabled(true);
				Main.Connect();
			}
		});
		
		btnConnexion.setEnabled(false);
				
		lblChoisirUnCorrespondant = new JLabel("Choose a recipient");
		
		btnDeconnexion = new JButton("Disconnect");
		btnDeconnexion.setEnabled(false);
		btnDeconnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("deconnexion");
				//TODO
				//Appeler la m�thode Disconnect
				//Disconnect();
				
				if(Main.connected) {
					Main.Disconnect();
					btnConnexion.setEnabled(true);
					btnDeconnexion.setEnabled(false);
					textField_1.setEnabled(true);
					btnEnvoyer.setEnabled(false);
					textField.setEnabled(false);
					
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					for (int i = model.getRowCount() - 1; i >= 0; --i) {
						model.removeRow(i);
					}
				}
			}
		});
				
		
		textField_1 = new JTextField();
		textField_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!textField_1.getText().equals("")) {
					btnConnexion.setEnabled(true);
				}
				else {
					btnConnexion.setEnabled(false);
				}
			}
		});
		
		textField_1.setColumns(10);
		
		scrollPane_1 = new JScrollPane();
		
		JLabel lblPseudo = new JLabel("Pseudo :");
		
		JButton btnFlushMessages = new JButton("Flush");
		btnFlushMessages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Flush all communication displayed & saved
				Save_msg.conversations.clear();
				Save_msg.Save_messages();
				Main.frame_gui.textPane.setText("");
			}
		});
		
		btnFlushMessages.setEnabled(true);
		
		button = new JButton("<HTML><BODY>Change<BR>pseudo</BODY></HTML>");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				do {
					JOptionPane jop = new JOptionPane();
					String nom = JOptionPane.showInputDialog(null, "Veuillez indiquer votre nouveau pseudo : ", "Changement de pseudo", JOptionPane.QUESTION_MESSAGE);
					System.out.println("Truc rentré : " + nom);
					if (nom ==null) { 	//Si on clique sur annuler dans la boite de dialogue
						return;
					}
					
					if(!Main.hm_users.containsKey(nom)) {
						//procedure de changement de pseudo
						textField_1.setText(nom);
						Main.msg_sender.sendNewPseudo(Main.me, new User(nom, Main.local_host));
						Main.me.pseudo = nom;
						return;
					}
					
				}while(false); //Mettre en condition tant que le pseudo saisi est déjà dans la liste des pseudos
			}
		});
		
		JLabel lblIpserver = new JLabel("IpServer :");
		
		textField_2 = new JTextField();
		textField_2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		textField_2.setColumns(10);
		
		JLabel lblEtat = new JLabel("State : ");
		
		JComboBox comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Online", "Offline"}));
		
		btnNewButton = new JButton("Go");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblPseudo)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnConnexion))
								.addComponent(lblChoisirUnCorrespondant))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnDeconnexion)
							.addGap(11))
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 440, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblIpserver)
									.addGap(4)
									.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnNewButton)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lblEtat, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
									.addGap(12))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnEnvoyer)
								.addComponent(comboBox, 0, 84, Short.MAX_VALUE))))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(button)
								.addComponent(btnFlushMessages))
							.addContainerGap())))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(scrollPane, 0, 0, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnDeconnexion)
								.addComponent(btnConnexion)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPseudo))
							.addGap(16)
							.addComponent(lblChoisirUnCorrespondant)
							.addGap(5)
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnEnvoyer)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblIpserver)
								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblEtat)
								.addComponent(btnNewButton)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnFlushMessages, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button)))
					.addGap(34))
		);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane_1.setViewportView(textPane);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		textPane.setLayout(new BorderLayout());
		table = new JTable();
		//Action � effectuer quand on clique sur la table
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//gestion des utilisateur avec "** " -> supprimer la notification quand on clique dessus
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int j = table.getSelectedRow();
				model.setValueAt(((String) model.getValueAt(j, 0)).replace("** ", ""), j, 0);
				
				//Renvoie l'utilisateur selectionne dans la table
				lblChoisirUnCorrespondant.setText("Conversation with " + table.getValueAt(table.getSelectedRow(), 0));
				
				//Change conversation string from main window
				textPane.setText(Save_msg.conversations.get(table.getValueAt(table.getSelectedRow(), 0)));
				
			}
		});
		table.setModel(new DefaultTableModel(new Object[][] {},	new String[] {"Users Online","State"}) {
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
	}
}
