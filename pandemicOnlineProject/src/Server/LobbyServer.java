package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Hashtable;

public class LobbyServer {
	public static Hashtable<String, Socket[]> userList = new Hashtable<String, Socket[]>();// 모든 유저의 리스트(아이디와 소켓이 들어간다.)
	private Socket gameSocket = null;
	private Socket chatSocket = null;
	private DataInputStream input = null;
	private DataOutputStream output = null;
	private String name;
	private ChatServer ChatRun;
	private Thread ChatTh;
	private String inputText;
	private Hashtable<String, Room> rList;

	public LobbyServer() {

	}

	public LobbyServer(Socket s1, Socket s2, String name) {
		// TODO Auto-generated constructor stub
		gameSocket = s1;
		chatSocket = s2;

		rList = MainServer.roomList;

		try {
			input = new DataInputStream(gameSocket.getInputStream());
			output = new DataOutputStream(gameSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 로비에 접속한 모든 유저들의 소켓정보를 담는다.
		// 그리고 닉네임 또는 아이디를 저장한다.
		this.name = name;
		Socket[] s = { gameSocket, chatSocket };
		userList.put(name, s);

		// 로비 채팅을 하기 위해서 방번호 0번은 로비 채팅으로 한다.
		if (rList.containsKey("로비")) {
			rList.get("로비").RoomUserListAdd(name);
		} else {
			rList.put("로비", new Room(name));
		}

		// 방을 로리로 채팅 스레드를 만든다.
		ChatRun = new ChatServer("로비", name);
		ChatTh = new Thread(ChatRun);
		ChatTh.start();

		StartRun();// 루프가 시작되는 메소드이다. 여기서 방을 만들거나 입장이 가능하다.

	}

	private boolean isJoin(String rNumber) { // 방에 입장이 가능한지 여부를 확인한다.
		return rList.get(rNumber).getRoomSize() <= 3 && rList.get(rNumber).getStart() == false;
	}

	private void CreateRoom() throws IOException { // 방을 만드는 메소드
		String roomName = input.readUTF();
		String roomPassword = input.readUTF();
		synchronized (this) {// 방을 만들때 동시에 못만들도록
			rList.get("로비").RoomUserListDel(name);
			rList.put(roomName, new Room(name, roomName, roomPassword));
		}
		new GameRoom(roomName, name, ChatRun);
	}

	private void JoinRoom() throws IOException { // 방에 입장하는 메소드
		String rNumber = input.readUTF();
		synchronized (this) {// 방에 들어갈때도 순서대로
			if (isJoin(rNumber)) {
				rList.get("로비").RoomUserListDel(name);// 방에 유저 삭제
				rList.get(rNumber).RoomUserListAdd(name);
				if (input.readUTF().equals(rList.get(rNumber).getRoomPass())) {
					new GameRoom(rNumber, name, ChatRun);
				} else {
					System.out.println("접속 실패");
					output.writeUTF("false");
				}
			} else {
				System.out.println("접속 실패");
				output.writeUTF("false");
			}
		}
	}

	private void StartRun() {
		try {
			while (true) {
				inputText = input.readUTF();

				if (inputText.equals("Create")) { // 방만들기
					System.out.println("방생성중...");
					CreateRoom();
				} else if (inputText.equals("exit")) { // 나가기
					break;
				} else if (inputText.equals("refresh")) { // 새로고침
					output.writeUTF(rList.toString());
				} else if (inputText.equals("logout")) { // 로그아웃
					System.out.println("로그아웃시도");
					userList.remove(name);
					break;
				} else { // 방 입장
					System.out.println("입장");
					JoinRoom();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			rList.get("로비").RoomUserListDel(name);// 방에 유저 삭제
		}
	}
}