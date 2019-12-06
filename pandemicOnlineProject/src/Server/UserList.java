  
package Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;

/*
 * 룸에 있는 모든 유저를 담고있다. 그리고 그 유저의 정보, 제어권을 가지고있다
 */

public class UserList{
	//dataOutputStream은 다른 스트림과는 다르게 자료형 그대로 가지고온다.
	//ex) int형으로 데이터를 보내면 int형으로 받을수 있다.
	private LinkedHashMap<String, DataOutputStream> userGameList = new LinkedHashMap<String, DataOutputStream>();//게임 스트림
	private Hashtable<String, DataOutputStream> userChatList = new Hashtable<String, DataOutputStream>();//채팅 스트림
	private Hashtable<String, Socket[]> totalUserList = LobbyServer.userList;
	private ArrayList<String> userName = new ArrayList<String>();
	
	public UserList() {
		// TODO Auto-generated constructor stub
	}
	
	//가장 처음 방을 만들때 해당 닉네임에 대한 유저의 출력 스트림을 hashmap에 모아둔다.(키는 닉네임 벨류는 출력스트림)
	public UserList(String name) {
		DataOutputStream output;
		try {
			output = new DataOutputStream(totalUserList.get(name)[0].getOutputStream());
			userGameList.put(name, output);
			output = new DataOutputStream(totalUserList.get(name)[1].getOutputStream());
			userChatList.put(name, output);
			userName.add(name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//기존의 방에 유저를 추가할때
	public void userAdd(String name) {
		DataOutputStream output;
		try {
			output = new DataOutputStream(totalUserList.get(name)[0].getOutputStream());
			userGameList.put(name, output);
			output = new DataOutputStream(totalUserList.get(name)[1].getOutputStream());
			userChatList.put(name, output);
			userName.add(name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//유저 삭제
	public void userDel(String name) {
		userGameList.remove(name);
		userChatList.remove(name);
		userName.remove(name);
	}
	
	//이건 쓸지는 모르겠지만 해당 유저의 출력스트림을 반환한다.
	public DataOutputStream getUserListGame(String name){
		return userGameList.get(name);
	}
	//이건 쓸지는 모르겠지만 해당 유저의 출력스트림을 반환한다.
	public DataOutputStream getUserListChat(String name){
		return userChatList.get(name);
	}
	
	//방의 유저의 게임 스트림을 반환
	public LinkedHashMap<String, DataOutputStream> getUserListGame(){
		return userGameList;
	}
	
	//방의 유저의 채팅 스트름을 반환
	public Hashtable<String, DataOutputStream> getUserListChat(){
		return userChatList;
	}
	
	public String getUserName(int i) {
		return userName.get(i);
	}
	
	public ArrayList<String> getUserNameList(){
		return userName;
	}
}
