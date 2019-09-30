package testServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainThread implements Runnable{
	private Socket gameSocket = null;
	private Socket chatSocket = null;
	private String name;
	private DataInputStream input = null;
	private DataOutputStream output = null;
	private Login LoginServer = null;
	private LobbyServer lobbyServer = null;
	
	public MainThread() {
		// TODO Auto-generated constructor stub
		
	}
	
	public MainThread(Socket s1, Socket s2) {
		// TODO Auto-generated constructor stub
		gameSocket = s1;
		chatSocket = s2;
		try {
			input = new DataInputStream(gameSocket.getInputStream());
			output = new DataOutputStream(gameSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void SetName(String name) {
		this.name = ""+name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			LoginServer = new Login(gameSocket, chatSocket); //로그인 객체 생성
			try {
				if(LoginServer.loginCheck()) {//로그인 체크
					//성공
					name = LoginServer.getName();
					output.writeUTF("로그인 성공");
					lobbyServer = new LobbyServer(gameSocket, chatSocket, name);//성공하면 로비 객체생성
				}else {
					System.out.println("실패");
					output.writeUTF("로그인 실패");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("실패");
				break;
			}finally {
				
			}
			
		}
	}
	
}
