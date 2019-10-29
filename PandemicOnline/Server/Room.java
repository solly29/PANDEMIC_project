  
package Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;

/*
 * 룸에 있는 모든 유저를 담고있다. 그리고 그 유저의 정보, 제어권을 가지고있다
 */

public class UserList{
	//dataOutputStream은 다른 스트림과는 다르게 자료형 그대로 가지고온다.
	//ex) int형으로 데이터를 보내면 int형으로 받을수 있다.
	private Hashtable<String, DataOutputStream> user = new Hashtable<String, DataOutputStream>();
	private Hashtable<String, DataOutputStream> userChat = new Hashtable<String, DataOutputStream>();
	
	public UserList() {
		// TODO Auto-generated constructor stub
	}
	
	//가장 처음 방을 만들때 해당 닉네임에 대한 유저의 출력 스트림을 hashmap에 모아둔다.(키는 닉네임 벨류는 출력스트림)
	public UserList(String name) {
		DataOutputStream output;
		try {
			output = new DataOutputStream(LobbyServer.userList.get(name)[0].getOutputStream());
			user.put(name, output);
			output = new DataOutputStream(LobbyServer.userList.get(name)[1].getOutputStream());
			userChat.put(name, output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//기존의 방에 유저를 추가할때
	public void userAdd(String name) {
		DataOutputStream output;
		try {
			output = new DataOutputStream(LobbyServer.userList.get(name)[0].getOutputStream());
			user.put(name, output);
			output = new DataOutputStream(LobbyServer.userList.get(name)[1].getOutputStream());
			userChat.put(name, output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void userDel(String name) {
		user.remove(name);
		userChat.remove(name);
	}
	
	//이건 쓸지는 모르겠지만 해당 유저의 출력스트림을 반환한다.
	/*public DataOutputStream getUserList(String name){
		return user.get(name);
	}*/
	
	//방의 유저의 리스트를 반환한다.
	public Hashtable<String, DataOutputStream> getUserList(){
		return user;
	}
	
	public Hashtable<String, DataOutputStream> getUserListChat(){
		return userChat;
	}
}
