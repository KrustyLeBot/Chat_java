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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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



	/**
	 * Create the frame.
	 */
	public GUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 590, 381);
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
		
		btnEnvoyer = new JButton("Envoyer");
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
		
		
		
		btnConnexion = new JButton("Connexion");
		btnConnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("connexion de " + textField_1.getText());
				//TODO
				//Appeler la methode Connect
				
				btnDeconnexion.setEnabled(false);
				btnConnexion.setEnabled(false);

				Main.Connect();
				textField.setEnabled(true);
			}
		});
		
		btnConnexion.setEnabled(false);
				
		JLabel lblChoisirUnCorrespondant = new JLabel("Choisir un correspondant");
		
		btnDeconnexion = new JButton("Deconnexion");
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
		
		
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(textField)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnEnvoyer))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
									.addComponent(lblChoisirUnCorrespondant)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblPseudo)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnConnexion)))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnDeconnexion)
								.addGap(11)))
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 440, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(3)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnDeconnexion)
						.addComponent(btnConnexion)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPseudo))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblChoisirUnCorrespondant)
					.addGap(5)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnEnvoyer)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
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
				//Renvoie l'utilisateur selectionn� dans la table
				System.out.println(table.getValueAt(table.getSelectedRow(), 0));
				lblChoisirUnCorrespondant.setText("Conversation avec " + table.getValueAt(table.getSelectedRow(), 0));
				
				//Change conversation string from main window
				textPane.setText(Save_msg.conversations.get(table.getValueAt(table.getSelectedRow(), 0)));
			}
		});
		table.setModel(new DefaultTableModel(new Object[][] {},	new String[] {"Users Online"}) {
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
