package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;

public class MainServer {
	private ServerSocket sSocket1 = null;
	private ServerSocket sSocket2 = null;
	private Runnable runThread = null;
	private Thread mainThread = null;
	public static Hashtable<String, Room> roomList = new Hashtable<String, Room>();// 룸 리스트

	public MainServer() {

		try {
			System.out.println("실행");
			sSocket1 = new ServerSocket(9002);
			sSocket2 = new ServerSocket(9003);

			while (true) {
				Socket gameSocket = sSocket1.accept();
				Socket chatSocket = sSocket2.accept();
				System.out.println("서버 접속");

				// 다중 통신, 접속을 위해 스레드를 씀
				runThread = new MainThread(gameSocket, chatSocket);
				mainThread = new Thread(runThread);
				mainThread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("접속 실패");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainServer();
	}

}
