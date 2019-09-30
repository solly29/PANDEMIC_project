package testServer;

import java.io.DataInputStream;

/*
 * 게임을 제어하고 채팅을 할수있게 한다.
 */

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class GameRoom extends MainThread{
	private int roomNum = 0;
	private HashMap<String, DataOutputStream> list = null;//유저 리스트 보내기용
	private DataInputStream input = null;
	private Socket s;
	private String name; 
	
	public GameRoom() {
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * 여기서는 룸의 번호와 닉네임을 받고
	 * 전체 룸 리스트에서 룸 번호에 대한 룸 객체를 가지고 오고
	 * 전체 유저 리스트 객체에서 닉네임에 대한 소켓을 가지고 온다.
	 */
	
	public GameRoom(int num, String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
		//나의 소켓과 입력스트림을 받는다.
		s = LobbyServer.userList.get(name)[0];
		try {
			input = new DataInputStream(s.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//해당 방 번호에 대한 룸객체안에 유저들의 리스트를 가지고온다.
		roomNum = num;
		list = MainServer.roomList.get(roomNum).getUserList();
		
		/*
		 * 이 부분부터 서비스 시작이다.
		 * 일단 채팅만 구현함! 
		 */
		try {
			while(true) {
				String str = input.readUTF();   // 클라이언트로부터 채팅 받아옴
				if(input == null) break;   // 입력이 아무것도 들어오지 않으면 탈출
				sendAll(name+">"+str);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//유저 리스트에 받은 입력 정보를 보낸다.
	public void sendAll(String str) {
		for(DataOutputStream out : list.values()) {
			try {
				out.writeUTF(str);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}