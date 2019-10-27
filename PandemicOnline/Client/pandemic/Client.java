package pandemic;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;

public class Client extends JFrame{ 
	static Socket gsocket = null;
	static Socket socket2 = null;
	
	public static void main(String[] args) {
		System.out.println("hi");
		
		// ?ÜåÏº? ?Éù?Ñ±
		try {
			gsocket = new Socket("39.127.8.134", 9002);
			socket2 = new Socket("39.127.8.134", 9003);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		new Client(gsocket, socket2);
	}
	// Ïπ¥Îìú ?†à?ù¥?ïÑ?õÉ?úºÎ°? ?îÑ?†à?ûÑ Î≥??ôò
	private CardLayout cards = new CardLayout();
	
	public Client(Socket gsocket, Socket socket2) { 
		setSize(1920,1080);
		setPreferredSize(new Dimension(1920,1080));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	setLayout(cards);
		add(new Login(gsocket, socket2));//±‘¡¯
		setResizable(false); 
		setVisible(true); 
		//getContentPane().add("One", new Login(this, socket)); 
	} 
	public void changePanel() { 
		cards.next(getContentPane());
		
	}
}


