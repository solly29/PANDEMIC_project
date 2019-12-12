package Server;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;

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
	public CardList cardList = null;
	private int nextStart = -1;
	public String StartUser = "";
	private Hashtable<String, String> userSelect = new Hashtable<String, String>();
	private int[] startState = { 0, 0, 0, 0 };
	private int[] InfTrack = { 2, 2, 3, 3, 4, 4 };
	private int InfNum = 0;

	public Room() {
		// TODO Auto-generated constructor stub
	}

	public Room(String name) {
		// TODO Auto-generated constructor stub
		kingName = name;
		user = new UserList(name);// 유저 객체를 생성
		cardList = new CardList(4);// 일단 난이도는 4장으로 고정 나중에 바꿀꺼당
	}

	// 해당 닉네임 또는 아이디의 유저를 유저 클레스로 추가한다.(방을 생성을 할경우)
	public Room(String name, String rName, String rPW) {
		// TODO Auto-generated constructor stub
		kingName = name;
		roomName = rName;
		roomPass = rPW;
		user = new UserList(name);// 유저 객체를 생성
		cardList = new CardList(4);// 일단 난이도는 4장으로 고정 나중에 바꿀꺼당
	}

	public void cardReset() {
		cardList.cardSetting();
	}

	// 방에 입장할경우 이 메소드를 이용해서 유저 클래스로 추가한다.
	public void RoomUserListAdd(String name) {
		user.userAdd(name);
	}

	// 방에 해당 유저를 지운다.
	public void RoomUserListDel(String name) {
		try {
			user.userDel(name);
			kingName = user.getUserListChat().keys().nextElement();
		} catch (Exception e) {
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

	// 방 인원수 반환
	public int getRoomSize() {
		return user.getUserListChat().size();
	}

	// 내 방에 있는 유저들의 게임소켓의 스트림을 가져옴
	public LinkedHashMap<String, DataOutputStream> getUserListGame() {
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

	// 총 유저의 리스트 반환
	public ArrayList<String> getUserNameList() {
		return user.getUserNameList();
	}

	public String toString() {
		if (!getRoomPass().equals(""))
			return String.format("****");
		else
			return String.format(" ");
	}
	
	public String getNextStartUser() {
		nextStart++;
		if (nextStart == getRoomSize()) {
			nextStart = 0;
		}
		return user.getUserName(nextStart);
	}

	public void setUserSelect(String num, String job) {
		userSelect.put(num, job);
	}

	public String getUserJob(String num) {
		return userSelect.get(num);
	}

	public Hashtable<String, String> getUserSelect() {
		return userSelect;
	}
	
	// 방에 입장할 때 불려짐
	public void setStartState1(String userNumber) {
		int num = Integer.parseInt(userNumber);

		if (startState[num] == 0) {
			startState[num] = 10;
		}
	}
	
	// 레디를 누를 때 불려짐
	public void setStartState2(String userNumber) {
		int num = Integer.parseInt(userNumber);

		if (startState[num] == 10)
			startState[num] = 11;
		else if (startState[num] == 11)
			startState[num] = 10;
	}
	
	public int getStartState() {
		int sum = 0;
		for (int i = 0; i < 4; i++) {
			sum += startState[i];
		}
		return sum;
	}

	public int getStartState(String str) {
		int i = Integer.parseInt(str);
		return startState[i];
	}

	public void InfTrackNum() {
		InfNum++;
		if (InfNum == 7)
			InfNum--;
	}

	public int InfTrackCount() {
		return InfTrack[InfNum];
	}
}
