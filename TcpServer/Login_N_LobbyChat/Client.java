import java.awt.CardLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;

public class Client extends JFrame{ 
	static Socket socket = null;
	DataInputStream dis = null;
	DataOutputStream dos = null;
	
	public static void main(String[] args) {
		try {
			socket = new Socket("127.0.0.1", 9001);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Client(socket);
	}

	private CardLayout cards = new CardLayout();
	
	public Client(Socket socket) { 
		setSize(500, 500); 
	
		getContentPane().setLayout(cards);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setResizable(false); 
	
		getContentPane().add("One", new Login(this, socket)); 
		getContentPane().add("Two", new Lobby(this, socket)); 
		setVisible(true); 
	} 
	public void changePanel() { 
		cards.next(this.getContentPane());
	}
}


