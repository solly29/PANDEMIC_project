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
	private String NAME;
	private String NUMBER;
	//DOA db = new DOA(); 지금은 디비 서버를 다시 만들어야되서 주석처리함
	
	public Login(Socket s1, Socket s2) {
		// TODO Auto-generated constructor stub
		gameSocket = s1;
		chatSocket = s2;
		
		//임의의 유저
		MainServer.IdPassword.put("admin","1234");
		MainServer.IdPassword.put("user","1234");
		MainServer.IdPassword.put("user2","1234");
		MainServer.IdPassword.put("user3","1234");
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
		
		if(MainServer.IdPassword.get(ID).equals(PW)) {
			System.out.println("PW 일치");
			return true;
		}
		
		//디비서버를 다시 만들어야 되서 주석처리함
		/*
		if (db.MatchPWD(ID, PW)) {
			System.out.println("PW 일치");
			return true;
		}*/

		return false;
	}
	//회원가입
	public boolean join() {
		ID = null;
		PW = null;
		NAME = null;
		NUMBER = null;
		
		try {
			ID = input.readUTF();
			PW = input.readUTF();
			NAME = input.readUTF();
			NUMBER = input.readUTF();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		/* 디비가 원상복귀되면 주석 지우기
		if (db.MatchID(ID)) {

			db.Insert(NAME, NUMBER, ID, PW);

			return true;
		} else {
			return false;
		}*/
		return false;//이거도 복구되면 지우기
	}
}
