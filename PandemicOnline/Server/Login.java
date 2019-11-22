package Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;

import DB.DOA;

public class Login{
	private DataInputStream input = null;
	private Socket gameSocket = null;
	private Socket chatSocket = null;
	private String ID;
	private String PW;
	DOA db = new DOA();
	
	public Login(Socket s1, Socket s2) {
		// TODO Auto-generated constructor stub
		gameSocket = s1;
		chatSocket = s2;
		
		//임의의 유저
		/*MainServer.IdPassword.put("admin","1234");
		MainServer.IdPassword.put("user","1234");
		MainServer.IdPassword.put("user2","1234");
		MainServer.IdPassword.put("user3","1234");*/
		try {
			input = new DataInputStream(gameSocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String getName() {
		return ID;
	}
	
	public boolean loginCheck() {
		
		ID = null;
		PW = null;
		
		try {
			ID = input.readUTF();
			PW = input.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		System.out.println("login");
		
		if(LobbyServer.userList.containsKey(ID)) {
			System.out.println("유저가 로그인 되어있음");
			return false;
		}
	
			if(db.MatchPWD(ID,PW)) {
				System.out.println("PW 일치");
				return true;
			}
			
		return false;
	}
	//회원가입
	public boolean join() {
		System.out.println("확인2");
		ID = null;
		PW = null;
		
		try {
			ID = input.readUTF();
			PW = input.readUTF();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		
		if(!db.MatchPWD(ID,PW)) {
			return false;
		}
		else {
			MainServer.IdPassword.put(ID, PW);
			System.out.println(ID+ " " + PW);
			return true;
		}
	}
}
