import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.*;

public class Lobby extends JPanel { 
	private JLabel lblNewLabel; 
	private JButton btnNewButton; 
	private Client F; 
	private TextField ID_TextField;
	
	public Lobby(Client f, Socket socket) { 
		setSize(500, 500); 
		ID_TextField = new TextField(50);
		add(ID_TextField);
		F = f; 
		
		setVisible(true); 
	} 
}
