

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static int PORT = 9001;
	
	public static void main(String[] args) {
		ServerSocket Ssocket = null;
		
		try {
			Ssocket = new ServerSocket(PORT);
			System.out.println("Client wait..");
			Socket Csocekt = Ssocket.accept();
			System.out.println("Client connect");
			ServerThread serverThread = new ServerThread(Csocekt);
			System.out.println("Thread access");
			System.out.println(Csocekt);
			serverThread.start();	//run() ½ÇÇà
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
