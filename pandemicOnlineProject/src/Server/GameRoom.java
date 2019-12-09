package Server;

import java.io.DataInputStream;

/*
 * 게임을 제어하고 채팅을 할수있게 한다.
 */

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

	public GameRoom() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * 여기서는 룸의 번호와 닉네임을 받고 전체 룸 리스트에서 룸 번호에 대한 룸 객체를 가지고 오고 전체 유저 리스트 객체에서 닉네임에 대한
	 * 소켓을 가지고 온다.
	 */

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
					//2명이상이고 모든 방에 있는 모든 유저가 레디를 하면 게임스타트를 모든유저에게 보냅니다.
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

	public void gameStart() {
		try {
			System.out.println("시작");
			MyRoom.setStart(true);// 게임이 시작됬다는걸 나타낸다.

			String Myjob = MyRoom.getUserJob(usernumber);
			if (Myjob.equals("traffic")) {
				maxTurnNumber = 5;
			}

			// System.out.println(usernumber+" 시작시 나의 직업은 : "+Myjob);

			// 현제 방에있는 모든 유저들의 이름을 목록으로 보내준다.
			// 왜냐 그 이름을 가지고 클라이언트는 케릭터 클래스를 map에 넣는다.
			output.writeUTF("[목록]" + MyRoom.getUserListGame().keySet());
			String cardList;
			// 도시카드를 받는다.(동기화를 시켜야한다.)
			synchronized (this) {
				if (MyRoom.getRoomSize() == 2)
					cardList = Arrays.toString(MyRoom.cardList.cityCardHamdling(4));
				else if (MyRoom.getRoomSize() == 3)
					cardList = Arrays.toString(MyRoom.cardList.cityCardHamdling(3));
				else
					cardList = Arrays.toString(MyRoom.cardList.cityCardHamdling(2));
				output.writeUTF("[도시]" + cardList.substring(1, cardList.length() - 1));
			}

			input.readUTF(); // 클라가 도시카드를 받았으면 전염 또는 공백을 보낸다.
								// 여기서는 가장 처음 손패를 받는 과정이기 때문에 전염 이벤트가 발생하지 않는다.
								// 이벤트 발생은 고민중..

			if (name.equals(MyRoom.GetKing())) {
				// 현제 유저가 방장일경우 밑의 문장을 수행한다. 나머지는 밑에 반복문에서 기다리게 된다.
				// 초기 감염상황 발생
				Thread.sleep(2000);
				ChatRun.sendAll("감염카드 배부 중입니다.");// 감염카드를 배부 중이라고 채팅으로 뿌려준다.
				for (int i = 3; i >= 1; i--) {// 초기 감염상황은 3장씩 총 3번 일어나기 때문에 반복문을 썼다.
					cardList = Arrays.toString(MyRoom.cardList.infCardHandling(3));// 감염카드를 인수(3)만큼 받는다.
					sendAll("[감염]" + cardList.substring(1, cardList.length() - 1) + ", " + i);// 받은 감염카드를 클라에게 뿌려준다.
					// 이때 뒤에 i(숫자)는 해당 도시에 놓이는 토큰의 개수이다.
				}
				// 처음 시작유저를 불러온다.(일단은 방장이 처음 시작 유저이다.)
				String startuser = MyRoom.getNextStartUser();
				sendAll("[제어]turnStart:" + startuser);// 턴 시작 유저가 누구인지 보낸다.
				ChatRun.sendAll(startuser + "님의 턴입니다.");// 채팅으로도 보냄
				MyRoom.StartUser = startuser;// room에도 시작유저가 누구인지 저장한다.
			}
			input.readUTF();
			while (true) {
				String str = input.readUTF(); // 클라이언트로부터 행동을 입력 받는다
				if (str.equals("a"))
					continue;

				if(str.equals("[Exit]")) {
					System.out.println("exit");
					output.writeUTF("[제어]stop");
					System.out.println("exit 부분");
					end=true;
					break;
				}
				
				if (MyRoom.StartUser.equals(name))// 현제유저의 턴일경우 countTurn을 증가시킨다.
					countTurn++;
				

				if (input == null || str == null)
					break; // 입력이 아무것도 들어오지 않으면 탈출
				if(str.substring(0,4).equals("[제어]")) {
					str = str.substring(4);
					if(str.equals("win")) {
						ChatRun.sendAll("승리~~~");
						sendAll("[제어]승리");
						System.out.println(" 게임 승리 1");
						output.writeUTF("[제어]stop");
						end=true;
						break;
					}else if(str.equals("fail")) {
						ChatRun.sendAll("패배~~~");
						sendAll("[제어]패배");
						System.out.println(" 게임 패배 ");
						input.readUTF();
						output.writeUTF("[제어]stop");
						end=true;
						break;
					}
					//게임에서 서버가 관여 함
					//예를들어 승패 여부
				}

				if (str.equals("[특수]평온한 하룻밤")) {
					PeaceNightCard = true;
				} else if (str.substring(0, 4).equals("[예측]")) {
					String str3 = str.substring(4);
					if (str3.equals("사용")) {
						String card = Arrays.toString(MyRoom.cardList.getPredictCardList());
						System.out.println(card);
						output.writeUTF("[예측]" + card.substring(1, card.length() - 1));
						countTurn--;
						continue;
					} else {
						str3 = str3.substring(1, str3.length() - 1);
						String[] str2 = str3.split(", ");
						MyRoom.cardList.addinfCard(str2);
					}
				} else if (str.substring(0, 4).equals("[공유]")) {
					str = str.substring(4);
					String[] str2 = str.split(":");
					System.out.println(Arrays.toString(str2));
					if (str2[2].equals("안됨")) {
						if (MyRoom.StartUser.equals(name)) {
							countTurn--;
						} else {
							System.out.println("안됨 보냄");
							list.get(str2[0]).writeUTF("[공유]" + name + ":" + str2[1] + ":안됨");
						}
					} else if (str2[2].equals("전달")) {// 카드를 받는다
						if (MyRoom.StartUser.equals(name))
							countTurn--;
						list.get(str2[0]).writeUTF("[공유]" + name + ":" + str2[1] + ":전달");
					} else if (str2[2].equals("보냄")) {
						list.get(str2[0]).writeUTF("[공유]" + name + ":" + str2[1] + ":보냄");// 카드를 보낸다.
					}
				} else
					sendAll(str);// 행동을 입력 받으면 모두에게 보내준다.

				if (countTurn == maxTurnNumber) {// 턴이 끝났을때(countTurn이 4일경우) 밑에 문장을
					output.writeUTF("[제어]turnStop:" + name);// 턴이 끝났다는걸 보낸다.(이때 클라는 모든 인터페이스를
					// 비활성화한다.)

					// 다음 턴이 누구인지 알려준다.(이거는 메소드로 만들어도 될듯?)
					String startuser = MyRoom.getNextStartUser();
					sendAll("[제어]turnStart:" + startuser);
					ChatRun.sendAll(startuser + "님의 턴입니다.");
					MyRoom.StartUser = startuser;
					System.out.println("시작:" + startuser);
					countTurn = 0;// 초기화한다.

					str = input.readUTF(); // 정상적인 도시카드인지 전염카드가 섞여있는지 판단하기위해

					if(str.equals("[제어]win")) {
						ChatRun.sendAll("승리~~~");
						sendAll("[제어]승리");
						System.out.println(" 게임 승리 1");
						continue;
					}

					//도시카드를 배부 받는다.(해당)
					String[] a = MyRoom.cardList.cityCardHamdling(2);
					
					
					if(a==null) {
						ChatRun.sendAll("패배~~~");
						sendAll("[제어]패배");
						System.out.println(" 게임 패배 ");
						continue;
					}else {
						cardList = Arrays.toString(a);
						output.writeUTF("[도시]"+cardList.substring(1,cardList.length()-1));
					}

					str = input.readUTF(); // 정상적인 도시카드인지 전염카드가 섞여있는지 판단하기위해

					if (str.equals("[제어]전염")) {// 전염이 일어나면
						sendAll("[전염]");
						sendAll("[감염]" + MyRoom.cardList.Infection() + ", 3"); // 전염 로직을 실행(마지막카드 한장, 쓴카드 섞어서 올리기)
						MyRoom.InfTrackNum();
					}

					// 감염카드를 배부한다.(모든 유저)
					if (!PeaceNightCard) {
						cardList = Arrays.toString(MyRoom.cardList.infCardHandling(MyRoom.InfTrackCount()));
						sendAll("[감염]" + cardList.substring(1, cardList.length() - 1) + ", 1");
					}
					PeaceNightCard = false;

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (MyRoom.StartUser.equals(name) && !end) {
				String startuser = MyRoom.getNextStartUser();
				sendAll("[제어]turnStart:" + startuser);
				ChatRun.sendAll(startuser + "님의 턴입니다.");
				MyRoom.StartUser = startuser;
				System.out.println("시작:" + startuser);
			}
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
