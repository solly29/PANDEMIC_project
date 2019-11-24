package Server;

import java.io.DataInputStream;

/*
 * 게임을 제어하고 채팅을 할수있게 한다.
 */

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;

public class GameRoom{
	private String roomName = "";
	private Hashtable<String, DataOutputStream> list = null;//유저 리스트 보내기용
	private DataInputStream input = null;
	private DataOutputStream output = null;
	private Socket gsocket;
	private String name; 
	private ChatServer ChatRun;
	private Room MyRoom;
	
	public GameRoom() {
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * 여기서는 룸의 번호와 닉네임을 받고
	 * 전체 룸 리스트에서 룸 번호에 대한 룸 객체를 가지고 오고
	 * 전체 유저 리스트 객체에서 닉네임에 대한 소켓을 가지고 온다.
	 */
	
	public GameRoom(String num, String name, ChatServer Chat) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.ChatRun = Chat;
		ChatRun.ChangeRoomNumber(num);
		//나의 소켓과 입력스트림을 받는다.
		gsocket = LobbyServer.userList.get(name)[0];
		try {
			input = new DataInputStream(gsocket.getInputStream());
			output = new DataOutputStream(gsocket.getOutputStream());
			output.writeUTF("true");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//이건 방장을 클라가 표시를 할수있으면 주석 풀기~!!
		//sendAll("[제어]방장:"+MainServer.roomList.get(roomName).GetKing()); 방장이 누구인지 모든 플레이어에게 보낸다.
		
		//해당 방 번호에 대한 룸객체안에 유저들의 리스트를 가지고온다.
		roomName = num;
		MyRoom = MainServer.roomList.get(roomName);
		list = MyRoom.getUserListGame();
		
		/*게임 서버를 만들경우 채팅은 스레드로 처리한다.
		 * 나중에는 주석처리 지우기*/
		
		System.out.println("방장은 : "+MyRoom.GetKing());
		/*
		 * 이 부분부터 서비스 시작이다.
		 * 일단 채팅만 구현함! 
		 */
		try {
			while(true) {
				
				String str = input.readUTF();   // 클라이언트로부터 채팅 받아옴
				System.out.println(str);
				//sendAll(str);//테스트용  sendAll이다. 나중에는 지울것
				if(str.equals("Ready")) {
					System.out.println("준비");
					sendAll("["+name+"]Ready");
				}
				else if(str.equals("[방장]Ready")) {
					System.out.println("방장준비");
					sendAll("Start");
				}
				else if(str.equals("unReady")) {
					System.out.println("준비 취소");
					sendAll("["+name+"]unReady");
				}
				else if(str.equals("Start")) {
					System.out.println("시작");
					MyRoom.setStart(true);
					output.writeUTF("[목룍]"+MyRoom.getUserListGame().keySet().toString());
					//게임이 시작이 된다.
					if(name.equals(MyRoom.GetKing())) {
						//현재 유저가 방장일경우
						//초기 감염상황 발생
						//나중에 주석풀기
						/*for(int i=0;i<3;i++) {
							String cardList = Arrays.toString(MyRoom.cardList.infCardHandling(3));
							sendAll("[감염]"+cardList.substring(1,cardList.length()-1));
						}*/
					}
					String cardList = Arrays.toString(MyRoom.cardList.cityCardHamdling());
					output.writeUTF("[도시]"+cardList.substring(1,cardList.length()-1));
					while(true) {
						str = input.readUTF();   // 클라이언트로부터 채팅 받아옴
						System.out.println(str);
						if(input == null || str == null) break;   // 입력이 아무것도 들어오지 않으면 탈출
						if(str.substring(0,4).equals("[제어]")) {
							str = str.substring(4);
							//게임에서 서버가 관여 함
							//예를들어 승패 여부
						}
						System.out.println("asdasd");
						sendAll(str);//레디 기능을 구현 할경우 여기서 게임제어 문자를 보낸다.
					}
				}else {
					//break;
				}
				
				/*
				 * 카드 배부
				 * 시작 플레이어 선정
				 * */
			}
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
			if(name.equals(MyRoom.GetKing())) {//만약에 방장이 나일경우 바뀐 방장이 누구인지 모든 플레이어에게 보낸다.
				MyRoom.RoomUserListDel(name);//방에 유저 삭제
				//sendAll("[제어]방장:"+MainServer.roomList.get(roomName).GetKing()); 방장이 누구인지 모든 플레이어에게 보낸다.
			}else {
				MyRoom.RoomUserListDel(name);//방에 유저 삭제
			}
			
			System.out.println("방장은 : "+MyRoom.GetKing());
			System.out.println(MyRoom.getUserListChat());
			
			if(MyRoom.getUserListChat().isEmpty()) {
				System.out.println("삭제함");
				MainServer.roomList.remove(roomName);
			}
		}
	}
	
	//유저 리스트에 받은 입력 정보를 보낸다.
	public void sendAll(String str) {
		for(DataOutputStream out : list.values()) {
			try {
				System.out.println(str);
				out.writeUTF(str);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
