package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedHashMap;

public class GameRoom {
	private String roomName = "";
	private LinkedHashMap<String, DataOutputStream> list = null;// 유저 리스트 보내기용
	private DataInputStream input = null;
	private DataOutputStream output = null;
	private Socket gsocket;
	private String name;
	private ChatServer ChatRun;
	private Room MyRoom;
	public String usernumber = "";
	private int countTurn = 0;
	private int maxTurnNumber = 4;
	private boolean PeaceNightCard = false;
	private boolean end = false;
	private Hashtable<String, String> userSelect = new Hashtable<String, String>();
	private String cardList;

	public GameRoom() {
		// TODO Auto-generated constructor stub
	}

	public GameRoom(String num, String name, ChatServer Chat) {
		// TODO Auto-generated constructor stub
		String[] job = { "emergency", "traffic", "soilder", "builder", "random", "quarantine", "researcher",
				"scientist", "empty" };

		this.name = name;
		this.ChatRun = Chat;
		ChatRun.ChangeRoomNumber(num);
		// 나의 소켓과 입력스트림을 받는다.
		gsocket = LobbyServer.userList.get(name)[0];
		try {
			input = new DataInputStream(gsocket.getInputStream());
			output = new DataOutputStream(gsocket.getOutputStream());
			output.writeUTF("true");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 이건 방장을 클라가 표시를 할수있으면 주석 풀기~!!
		// sendAll("[제어]방장:"+MainServer.roomList.get(roomName).GetKing()); 방장이 누구인지 모든
		// 플레이어에게 보낸다.

		// 해당 방 번호에 대한 룸객체안에 유저들의 리스트를 가지고온다.
		roomName = num;
		MyRoom = MainServer.roomList.get(roomName);
		list = MyRoom.getUserListGame();

		/*
		 * 게임 서버를 만들경우 채팅은 스레드로 처리한다. 나중에는 주석처리 지우기
		 */

		userSelect = MainServer.roomList.get(roomName).getUserSelect();

		for (int i = 0; i < userSelect.size(); i++) {
			String usernumber = i + "";
			String userjob = userSelect.get(usernumber);
			sendAll(usernumber + userjob);
		}

		System.out.println("방장은 : " + MyRoom.GetKing());

		ArrayList<String> username = MyRoom.getUserNameList();

		String nickname = "";
		String userjob = "";
		try {
			nickname = input.readUTF();
			System.out.println(nickname);
			userjob = input.readUTF();
			System.out.println(userjob);
			System.out.println("게임 룸에서 아이디 직업 받음");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (int i = 0; i < username.size(); i++) {
			if (nickname.equals(username.get(i))) {
				usernumber = Integer.toString(i);
				MyRoom.setStartState1(usernumber);

				break;
			}
		}

		try {
			while (true) {

				nickname = input.readUTF();
				System.out.println(nickname);
				userjob = input.readUTF();
				System.out.println(userjob);
				System.out.println("게임 룸에서 아이디 직업 받음");

				if (nickname.equals("Enter")) {
					for (int i = 0; i < userSelect.size(); i++) {
						String usernumber_send = i + "";
						String id = username.get(i);
						sendAll("[ID]" + usernumber_send + id);
					}

				}

				if (nickname.equals("[Exit]")) {
					System.out.println("exit");
					output.writeUTF("[제어]stop");
					break;
				}

				// 직업 확인 후 샌드올
				for (int i = 0; i < 9; i++) {
					if (userjob.equals(job[i])) {
						System.out.println(usernumber + userjob);
						sendAll(usernumber + userjob);
						MyRoom.setUserSelect(usernumber, userjob);
					}
				}

				if (nickname.equals("Join")) {
					System.out.println(" C - Join" + MyRoom.getRoomSize());
					for (int i = 0; i < MyRoom.getRoomSize(); i++) {
						usernumber = Integer.toString(i);
						if (MyRoom.getStartState(usernumber) % 10 == 0) {
							System.out.println("RDOF" + usernumber + username.get(Integer.parseInt(usernumber)));
							sendAll("RDOF" + usernumber + username.get(Integer.parseInt(usernumber)));
						} else if (MyRoom.getStartState(usernumber) % 10 == 1) {
							System.out.println("RDON" + usernumber + username.get(Integer.parseInt(usernumber)));
							sendAll("RDON" + usernumber + username.get(Integer.parseInt(usernumber)));
						}
					}
				}

				if (nickname.equals("[Ready]")) {
					System.out.println(nickname + "Ready");
					MyRoom.setStartState2(usernumber);
					int sum = MyRoom.getStartState();
					// MyRoom.getStartState(usernumber) 의 10의 자리 수는 총 유저의 수를
					// MyRoom.getStartState(usernumber) 의 1의 자리 수는 레디를 한 유저의 수입니다.
					// MyRoom.getStartState(usernumber) % 10 가 0이면 Ready를 하지 않은 상태이며
					// MyRoom.getStartState(usernumber) % 10 가 1이면 Ready를 한 상태입니다.
					if (MyRoom.getStartState(usernumber) % 10 == 0) {
						System.out.println("RDOF" + usernumber + username.get(Integer.parseInt(usernumber)));
						sendAll("RDOF" + usernumber + username.get(Integer.parseInt(usernumber)));
					} else if (MyRoom.getStartState(usernumber) % 10 == 1) {
						System.out.println("RDON" + usernumber + username.get(Integer.parseInt(usernumber)));
						sendAll("RDON" + usernumber + username.get(Integer.parseInt(usernumber)));
					}

					System.out.println("레디 " + sum);
					// 2명이상이고 모든 방에 있는 모든 유저가 레디를 하면 게임스타트를 모든유저에게 보냅니다.
					if (sum / 10 >= 2 && sum / 10 == sum % 10) {
						sendAll("gameStart");
					}
				}

				if (nickname.equals("gameStart")) {
					gameStart();
					break;
				}

				/*
				 * 카드 배부 시작 플레이어 선정
				 */
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (name.equals(MyRoom.GetKing())) {// 만약에 방장이 나일경우 바뀐 방장이 누구인지 모든 플레이어에게 보낸다.
				MyRoom.RoomUserListDel(name);// 방에 유저 삭제
				// sendAll("[제어]방장:"+MainServer.roomList.get(roomName).GetKing()); 방장이 누구인지 모든
				// 플레이어에게 보낸다.
			} else {
				MyRoom.RoomUserListDel(name);// 방에 유저 삭제
			}

			System.out.println("방장은 : " + MyRoom.GetKing());
			System.out.println(MyRoom.getUserListChat());

			if (MyRoom.getUserListChat().isEmpty()) {
				System.out.println("삭제함");
				MainServer.roomList.remove(roomName);
			}
		}
	}

	private void cityCardHamdling() throws IOException { // 초반 도시카드를 배부한다.
		synchronized (this) {
			if (MyRoom.getRoomSize() == 2)
				cardList = Arrays.toString(MyRoom.cardList.cityCardHamdling(4));
			else if (MyRoom.getRoomSize() == 3)
				cardList = Arrays.toString(MyRoom.cardList.cityCardHamdling(3));
			else
				cardList = Arrays.toString(MyRoom.cardList.cityCardHamdling(2));
			output.writeUTF("[도시]" + cardList.substring(1, cardList.length() - 1));
		}
	}

	public void nextTurn() { // 턴을 다음 사람에게 넘긴다.
		String startuser = MyRoom.getNextStartUser();
		sendAll("[제어]turnStart:" + startuser);// 턴 시작 유저가 누구인지 보낸다.
		ChatRun.sendAll(startuser + "님의 턴입니다.");// 채팅으로도 보냄
		MyRoom.StartUser = startuser;// room에도 시작유저가 누구인지 저장한다.
	}

	public void gameSetting() throws Exception {
		String Myjob = MyRoom.getUserJob(usernumber);

		// 직업이 traffic이면 행동 5개이다.
		if (Myjob.equals("traffic")) {
			maxTurnNumber = 5;
		}

		// 현제 방에있는 모든 유저들의 이름을 목록으로 보내준다.
		// 왜냐 그 이름을 가지고 클라이언트는 케릭터 클래스를 map에 넣는다.
		output.writeUTF("[목록]" + MyRoom.getUserListGame().keySet());

		cityCardHamdling();

		input.readUTF();

		if (name.equals(MyRoom.GetKing())) {
			// 초기 감염상황 발생
			Thread.sleep(2000);
			ChatRun.sendAll("감염카드 배부 중입니다.");
			for (int i = 3; i >= 1; i--) { // 초기 감염상황은 3장씩 총 3번 일어나기 때문에 반복문을 썼다.
				cardList = Arrays.toString(MyRoom.cardList.infCardHandling(3)); // 감염카드를 인수(3)만큼 받는다.
				sendAll("[감염]" + cardList.substring(1, cardList.length() - 1) + ", " + i);// 받은 감염카드를 클라에게 뿌려준다.
				// 이때 뒤에 i(숫자)는 해당 도시에 놓이는 토큰의 개수이다.
			}

			nextTurn(); // 처음 시작유저를 불러온다.(일단은 방장이 처음 시작 유저이다.)
		}
		input.readUTF();
	}

	// 게임이 시작되는 부분이다.
	public void gameStart() {
		try {
			System.out.println("게임시작");
			MyRoom.setStart(true);// 게임이 시작됬다는걸 나타낸다.

			// 초반 게임 셋팅을 한다.
			gameSetting();

			while (true) {
				String str = input.readUTF(); // 클라이언트로부터 행동을 입력 받는다
				if (str.equals("a"))
					continue;

				if (str.equals("[Exit]")) { // 게임이 끝났을때 [Exit]를 받는다.
					output.writeUTF("[제어]stop");
					end = true;
					break;
				}

				if (MyRoom.StartUser.equals(name))// 현제유저의 턴일경우 countTurn을 증가시킨다.
					countTurn++;

				if (input == null || str == null)
					break; // 입력이 아무것도 들어오지 않으면 탈출
				if (str.substring(0, 4).equals("[제어]")) { // 승패여부
					str = str.substring(4);
					if (str.equals("win")) {
						ChatRun.sendAll("승리~~~");
						sendAll("[제어]승리");
						output.writeUTF("[제어]stop");
						end = true;
						break;
					} else if (str.equals("fail")) {
						ChatRun.sendAll("패배~~~");
						sendAll("[제어]패배");
						input.readUTF();
						output.writeUTF("[제어]stop");
						end = true;
						break;
					}
				}

				// 이벤트카드 사용
				if (str.equals("[특수]평온한 하룻밤")) {
					PeaceNightCard = true;
				} else if (str.substring(0, 4).equals("[예측]")) {
					PredictEvent(str);
				} else if (str.substring(0, 4).equals("[공유]")) {
					ShareEvent(str);
				} else
					sendAll(str);// 행동을 입력 받으면 모두에게 보내준다.

				if (countTurn == maxTurnNumber) {// 턴이 끝났을때(countTurn이 4일경우)
					if (TurnEnd())
						continue;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (MyRoom.StartUser.equals(name) && !end) {
				nextTurn();
			}
		}
	}

	// 턴이 끝나면 해당 메소드를 호출한다.
	public boolean TurnEnd() throws IOException {
		output.writeUTF("[제어]turnStop:" + name);// 턴이 끝났다는걸 보낸다.(이때 클라는 모든 인터페이스를 비활성화한다.)

		nextTurn();
		countTurn = 0;// 초기화한다.

		String str = input.readUTF(); // 정상적인 도시카드인지 전염카드가 섞여있는지 판단하기위해

		if (str.equals("[제어]win")) {
			ChatRun.sendAll("승리~~~");
			sendAll("[제어]승리");
			return true;
		}

		// 도시카드를 배부 받는다.
		String[] card = MyRoom.cardList.cityCardHamdling(2);

		if (card == null) {
			ChatRun.sendAll("패배~~~");
			sendAll("[제어]패배");
			return true;
		} else {
			cardList = Arrays.toString(card);
			output.writeUTF("[도시]" + cardList.substring(1, cardList.length() - 1));
		}

		str = input.readUTF(); // 정상적인 도시카드인지 전염카드가 섞여있는지 판단하기위해

		if (str.equals("[제어]전염")) {// 전염이 일어나면
			sendAll("[전염]");
			sendAll("[감염]" + MyRoom.cardList.Infection() + ", 3"); // 전염 로직을 실행(마지막카드 한장, 쓴카드 섞어서 올리기)
			MyRoom.InfTrackNum();
		}

		// 감염카드를 배부한다.(모든 유저)
		if (!PeaceNightCard) { // 평온한 하룻밤 이벤트카드를 쓰지 않으면 배부한다.
			cardList = Arrays.toString(MyRoom.cardList.infCardHandling(MyRoom.InfTrackCount()));
			sendAll("[감염]" + cardList.substring(1, cardList.length() - 1) + ", 1");
		}
		PeaceNightCard = false;

		return false;
	}

	// 예측 이벤트카드 사용 메소드
	public void PredictEvent(String str) throws IOException {
		str = str.substring(4);
		if (str.equals("사용")) { // 이벤트 카드를 사용했을때
			String card = Arrays.toString(MyRoom.cardList.getPredictCardList()); // 감염카드 6장을 받아온다.
			output.writeUTF("[예측]" + card.substring(1, card.length() - 1)); // 카드 리스트를 보낸다.
			countTurn--; // 이 과정에서는 턴을 카운트 하지 않는다.
		} else { // 순서대로 감염 카드 덱에 올린다.
			str = str.substring(1, str.length() - 1);
			String[] str2 = str.split(", ");
			MyRoom.cardList.addinfCard(str2);
		}
	}

	// 공유 메소드 턴인 유저가 카드를 받을때는 "전달", 카드를 보낼때는 "보냄", 만약 보내는 사람이 거절하거나 카드가 없으면 "안됨"
	// "전달"로 보내면 상대방이 해당 카드 "보냄"으로 다시 보낸다.
	public void ShareEvent(String str) throws IOException {
		str = str.substring(4);
		String[] str2 = str.split(":");
		// 거절했거나 카드가 없으면 안됨을 보냄
		if (str2[2].equals("안됨")) {
			if (MyRoom.StartUser.equals(name))
				countTurn--;
			else
				list.get(str2[0]).writeUTF("[공유]" + name + ":" + str2[1] + ":안됨");
		} else if (str2[2].equals("전달")) {// 카드를 받는다
			if (MyRoom.StartUser.equals(name))
				countTurn--;
			list.get(str2[0]).writeUTF("[공유]" + name + ":" + str2[1] + ":전달");
		} else if (str2[2].equals("보냄")) {// 카드를 보낸다.
			list.get(str2[0]).writeUTF("[공유]" + name + ":" + str2[1] + ":보냄");
		}
	}

	// 유저 리스트에 받은 입력 정보를 보낸다.
	public void sendAll(String str) {
		for (DataOutputStream out : list.values()) {
			try {
				out.writeUTF(str);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
