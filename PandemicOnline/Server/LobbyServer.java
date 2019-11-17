package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Hashtable;

public class LobbyServer{
	public static Hashtable<String, Socket[]> userList = new Hashtable<String, Socket[]>();//모든 유저의 리스트(아이디와 소켓이 들어간다.)
	private static int RoomNumber = 1;//처음 방번호
	private Socket gameSocket = null;
	private Socket chatSocket = null;
	private DataInputStream input = null;
	private DataOutputStream output = null;
	private String name;
	private ChatServer ChatRun;
	private Thread ChatTh;
	
	
	public LobbyServer() {
		
	}
	
	public LobbyServer(Socket s1, Socket s2, String name) {
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
		
		//로비에 접속한 모든 유저들의 소켓정보를 담는다.
		//그리고 닉네임 또는 아이디를 저장한다.
		this.name = name;
		Socket[] s = {gameSocket, chatSocket};
		userList.put(name,s);
		
		//로비 채팅을 하기 위해서 방번호 0번은 로비 채팅으로 한다.
		if(MainServer.roomList.containsKey(0)) {
			MainServer.roomList.get(0).RoomUserListAdd(name);
		}
		else {
			MainServer.roomList.put(0,new Room(name));
		}
		
		//방 번호 0번으로 채팅 스레드를 만든다.
		ChatRun = new ChatServer(0, name);
		ChatTh = new Thread(ChatRun);
		ChatTh.start();
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
		 * 
		 */
		while(true) {
			try {
				String str = input.readUTF();
				if(str.equals("Create")) {
					System.out.println("생성");
					int num = 0;
					synchronized (this) {//방 번호는 동기화해야된다.
						if(RoomNumber >= 10000) {
							RoomNumber = 1;
						}
						num = RoomNumber++;
						MainServer.roomList.get(0).RoomUserListDel(name);//방에 유저 삭제
						MainServer.roomList.put(num, new Room(name));
						System.out.println("확인2");
					}
					new GameRoom(num, name, ChatRun);
				}else if(str.equals("exit")){
					return;
				}else if(str.equals("refresh")) {
					//새로고침(방번호랑 방이름을 보내줘야한다.)
					output.writeUTF(MainServer.roomList.keySet().toString());
				}
				else {
					System.out.println("입장");
					int rNumber = Integer.parseInt(input.readUTF());
					System.out.println(rNumber);
					if(MainServer.roomList.get(rNumber).getRoomSize() <= 3) {
						MainServer.roomList.get(0).RoomUserListDel(name);//방에 유저 삭제
						MainServer.roomList.get(rNumber).RoomUserListAdd(name);
						new GameRoom(rNumber, name, ChatRun);
					}else {
						System.out.println("접속 실패");
						output.writeUTF("false");
					}
				}
				
			}catch (Exception e) {
				// TODO: handle exception
				break;
			}
		}
	}
}
