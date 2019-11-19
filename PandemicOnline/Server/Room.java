package Server;

import java.io.DataOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;

/*
 * 방에 대한 클래스이다.
 * 각 방마다 이 room클래스를 생성을 하고
 * 여기서는 방의 모든 정보, 제어권?을 갖는다.
 */

public class Room {
	private UserList user = null;
	private String roomName = "";
	private String kingName = "";
	private String roomPass = "";
	private boolean start = false;

	public Room() {
		// TODO Auto-generated constructor stub
	}

	// 해당 닉네임 또는 아이디의 유저를 유저 클레스로 추가한다.(방을 생성을 할경우)
	public Room(String name) {
		// TODO Auto-generated constructor stub
		System.out.println(name);
		kingName = name;
		user = new UserList(name);// 유저 객체를 생성
	}

	// 방에 입장할경우 이 메소드를 이용해서 유저 클래스로 추가한다.
	public void RoomUserListAdd(String name) {
		System.out.println(name);
		user.userAdd(name);
	}

	// 방에 해당 유저를 지운다.
	public void RoomUserListDel(String name) {
		try {
			user.userDel(name);
			kingName = user.getUserListChat().keys().nextElement();
		}catch (Exception e) {
			// TODO: handle exception
			kingName = "";
			System.out.println("유저 없음");
		}
	}
	
	public void setStart(boolean start) {
		this.start = start;
	}
	
	public boolean getStart() {
		return start;
	}

	// 방이름이 있으면 반환
	public void setRoomName(String str) {
		roomName = str;
	}

	// 이름 설정
	public String getRoomName() {
		return roomName;
	}
	
	public void setRoomPass(String str) {
		roomPass = str;
	}

	// 이름 설정
	public String getRoomPass() {
		return roomPass;
	}

	// 방장 시스템
	public String GetKing() {
		return kingName;
	}
	
	//방 인원수 반환
	public int getRoomSize() {
		return user.getUserListChat().size();
	}

	// 내 방에 있는 유저들의 게임소켓의 스트림을 가져옴
	public Hashtable<String, DataOutputStream> getUserListGame() {
		return user.getUserListGame();
	}

	// 방에 있는 유저들의 채팅소켓의 스트림을 가져옴
	public Hashtable<String, DataOutputStream> getUserListChat() {
		return user.getUserListChat();
	}

	// 내 방에 있는 해당유저 게임소켓의 스트림을 가져옴
	public DataOutputStream getUserListGame(String name) {
		return user.getUserListGame(name);
	}

	// 방에 있는 해당유저 채팅소켓의 스트림을 가져옴
	public DataOutputStream getUserListChat(String name) {
		return user.getUserListChat(name);
	}
	
	//총 유저의 리스트 반환
	public Enumeration<String> getUserNameList(){
		return user.getUserListChat().keys();
	}
}
