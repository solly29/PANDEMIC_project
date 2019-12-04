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
	public static String name = "";
	public static boolean CardPrint = true;
	
	public static void main(String[] args) {
		System.out.println("hi");
		
		try {
			gsocket = new Socket("127.0.0.1", 9002);
			socket2 = new Socket("127.0.0.1", 9003);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		new Client(gsocket, socket2);
	}
	private CardLayout cards = new CardLayout();
	
	public Client(Socket gsocket, Socket socket2) { 
		setSize(1920,1080);
		setPreferredSize(new Dimension(1920,1080));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); // 전체화면 -김영근
		setUndecorated(true); // 작업표시줄 없애기 - 김영근
	//	setLayout(cards);
		add(new Login(gsocket, socket2));//규진
		setResizable(false); 
		setVisible(true); 
		//getContentPane().add("One", new Login(this, socket)); 
	} 
	public void changePanel() { 
		cards.next(getContentPane());
		
	}
}
