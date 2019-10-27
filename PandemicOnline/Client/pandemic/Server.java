package pandemic;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class Server {
	public static int PORT = 9001;
	
	public static void main(String[] args) {
		ServerSocket Ssocket = null;
		
		try {
			
			Ssocket = new ServerSocket(PORT);
			Map<String, String> Join = new HashMap<String, String>(); 
			Map<String, DataOutputStream> clientMap = new HashMap<>();
			Collections.synchronizedMap(clientMap);
			// hashmap占쏙옙 占싱글쏙옙占쏙옙占썲에占쏙옙 占쏙옙占싣곤옙占쏙옙 占쌘료구占쏙옙占쏙옙
			// 占쏙옙티占쏙옙占쏙옙占쏙옙환占썸에占쏙옙占쏙옙 占쏙옙占쏙옙占싶손쏙옙占쏙옙 占싹어날 占쏙옙 占쌍댐옙
			// 占쌓뤄옙占썩에 占쏙옙占쏙옙화占쏙옙 占쏙옙占쏙옙占쏙옙占싸쏙옙 占쏙옙占쏙옙占쏙옙 占쌌쏙옙占쏙옙 占쏙옙占쏙옙占싼댐옙.
			
			// 占쏙옙占싼뤄옙占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙臼占� 클占쏙옙占싱억옙트占싸븝옙占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占싼댐옙.
			// 占쏙옙占쏙옙占썲를 占쏙옙占쏙옙占싹울옙 占쏙옙티占쏙옙占쏙옙占쏙옙 占싱뤄옙占쏙옙占쏙옙占쏙옙占� 占싼댐옙.
			while(true) {
				System.out.println("Client wait..");
				Socket Csocekt = Ssocket.accept();
				System.out.println("Client connect");
				ServerThread serverThread = new ServerThread(Csocekt,clientMap,Join);
				System.out.println("Thread access");
				System.out.println(Csocekt);
				serverThread.start();	//run() 占쏙옙占쏙옙
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}