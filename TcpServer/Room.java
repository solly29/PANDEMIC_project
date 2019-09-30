package testServer;

import java.io.DataOutputStream;
import java.util.HashMap;

/*
 * 방에 대한 클래스이다.
 * 각 방마다 이 room클래스를 생성을 하고
 * 여기서는 방의 모든 정보, 제어권?을 갖는다.
 */

public class Room{
	private UserList user = null;
	public Room() {
		// TODO Auto-generated constructor stub
	}
	//해당 닉네임 또는 아이디의 유저를 유저 클레스로 추가한다.(방을 생성을 할경우)
	public Room(String name) {
		// TODO Auto-generated constructor stub
		System.out.println(name);
		user = new UserList(name);//유저 객체를 생성
	}
	//방에 입장할경우 이 메소드를 이용해서 유저 클래스로 추가한다.
	public void RoomUserListAdd(String name) {
		System.out.println(name);
		user.userAdd(name);
	}
	//내 방에 있는 유저들의 정보를 가져온다.
	public HashMap<String, DataOutputStream> getUserList(){
		return user.getUserList();
	}
}
