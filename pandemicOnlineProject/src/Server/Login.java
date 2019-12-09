package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import DB.DAO;

public class Login {
	private DataInputStream input = null;
	private DataOutputStream output = null;
	private Socket gameSocket = null;
	private String ID;
	private String PW;
	private String NAME;
	private String NUMBER;
	DAO db;

	public Login(Socket s1) {
		// TODO Auto-generated constructor stub
		gameSocket = s1;
		db = new DAO(gameSocket);

		try {
			input = new DataInputStream(gameSocket.getInputStream());
			output = new DataOutputStream(gameSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getName() {
		return ID;
	}

	//로그인 체크함
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

		//해당 아이디의 유저가 로그인 되어있으면 로그인 실패
		if (LobbyServer.userList.containsKey(ID)) {
			System.out.println("유저가 로그인 되어있음");
			return false;
		}

		//디비를 확인해서 id, pw가 같으면 로그인 성공
		if (db.MatchPWD(ID, PW)) {
			System.out.println("PW 일치");
			return true;

			
		}
		return false;
	}
	
	//중복확인
	public void duple() throws IOException {
		ID = null;
		try {
			ID = input.readUTF();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		//중복을 확인한다.
		if (db.MatchID(ID))
			output.writeUTF("true");
		else
			output.writeUTF("false");

	}

	// 회원가입
	public void join() throws IOException{
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

		//디비에 해당 아이디가 없으면 회원가입 가능
		if (db.MatchID(ID)) {
			//디비에 추가
			db.Insert(NAME, NUMBER, ID, PW);

			output.writeUTF("true");
		} else {
			output.writeUTF("false");
		}

	}

	//id, pw 찾기
	public void find() throws IOException {

		NAME = null;
		NUMBER = null;

		try {

			NAME = input.readUTF();
			NUMBER = input.readUTF();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		
		//해당되는 아이디가 없으면 오류창뜨게
		if (!db.FindID(NAME, NUMBER))
			output.writeUTF("false");
	}
}
