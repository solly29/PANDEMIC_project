package testServer;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;

public class Login{
	private static HashMap<String, String> IdPassword = new HashMap<String, String>();
	private DataInputStream input = null;
	private Socket gameSocket = null;
	private Socket chatSocket = null;
	private String ID;
	private String PW;
	
	public Login(Socket s1, Socket s2) {
		// TODO Auto-generated constructor stub
		gameSocket = s1;
		chatSocket = s2;
		
		//임의의 유저
		IdPassword.put("admin","1234");
		IdPassword.put("user","1234");
		IdPassword.put("user2","1234");
		IdPassword.put("user3","1234");
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
		
		
		if(IdPassword.containsKey(ID)) {
			System.out.println("ID 확인");
			/*Set set = IdPassword.entrySet();
			System.out.println(set);
			set = IdPassword.keySet();
			System.out.println(set);
			System.out.println((String)IdPassword.get(ID));*/
			if(PW.equals(IdPassword.get(ID))) {
				System.out.println("PW 일치");
				return true;
			}
			return false;
		}
		else {
			return false;
		}
	}
	
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
		
		
		if(IdPassword.containsKey(ID)) {
			return false;
		}
		else {
			IdPassword.put(ID, PW);
			System.out.println(ID+ " " + PW);
			return true;
		}
	}
}