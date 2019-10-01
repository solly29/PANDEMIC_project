package testServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class LobbyServer{
	public static HashMap<String, Socket[]> userList = new HashMap<String, Socket[]>();
	private static int RoomNumber = 1;
	private Socket gameSocket = null;
	private Socket chatSocket = null;
	private DataInputStream input = null;
	private String name;
	
	public LobbyServer() {
		
	}
	
	public LobbyServer(Socket s1, Socket s2, String name) {
		// TODO Auto-generated constructor stub
		gameSocket = s1;
		chatSocket = s2;
		
		try {
			input = new DataInputStream(gameSocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//로비에 접속한 모든 유저들의 소켓정보를 담는다.
		//그리고 닉네임 또는 아이디를 저장한다.
		this.name = name;
		Socket[] s = {gameSocket, chatSocket};
		userList.put(name,s);
		
		/*
		 * 로비에 접속한 유저가 방을 생성하거나 입장에 대한 반복문이다.
		 * 이건 바뀔수 있지만 일단 생성하면 create 로비에서 나가면 exit 그외 다른것이 들어오면 방 입장으로 본다.
		 * 방 번호는 1부터 순차적으로 증가하고
		 * 생성하면 roomNumber의 숫자로 메인서버에 roomList에 키는 방 번호 벨류는 룸 객체를 생성한다.
		 * 입장일경우 방의 정보(방 번호, 방이름?, 비번)을 받아서 그에 맞는 room 객체를 호출한다. 그리고 유저를 추가한다.
		 * 
		 * 위 과정이 끝나면 게임룸으로 입장하게 된다.
		 * 게임 룸에서는 룸객체안에 있는 유저들만 통신, 채팅이 가능하게된다.
		 * 
		 * +방이 삭제되었을때 클래스나 메소드도 만들어야됨!!
		 */
		while(true) {
			try {
				String str = input.readUTF();
				if(str.equals("Create")) {
					System.out.println("생성");
					int num = RoomNumber;
					RoomNumber++;
					MainServer.roomList.put(num, new Room(name));
					new GameRoom(num, name);
				}else if(str.equals("exit")){
					return;
				}else {
					System.out.println("입장");
					int rNumber = Integer.parseInt(input.readUTF());
					System.out.println(rNumber);
					MainServer.roomList.get(rNumber).RoomUserListAdd(name);
					new GameRoom(rNumber, name);
				}
				
			}catch (Exception e) {
				// TODO: handle exception
				break;
			}
		}
	}
}
