package Server;

import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainThread implements Runnable {
	private Socket gameSocket = null;
	private Socket chatSocket = null;
	private String name;
	private DataInputStream input = null;
	private DataOutputStream output = null;
	private Login LoginServer = null;

	public MainThread() {
		// TODO Auto-generated constructor stub

	}

	public MainThread(Socket s1, Socket s2) {
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
	}

	public void SetName(String name) {
		this.name = "" + name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		LoginServer = new Login(gameSocket); // 로그인 객체 생성
		while (true) {
			try {
				String str1 = input.readUTF();
				if (str1.equals("login")) {// 로그인 체크
					if (LoginServer.loginCheck()) {
						output.writeUTF("true");
						name = LoginServer.getName();
						new LobbyServer(gameSocket, chatSocket, name);// 이부분
					} else
						output.writeUTF("false");

				} else if (str1.equals("join")) {// 회원가입
					LoginServer.join();
				} else if (str1.equals("find")) {// 아이디 비번 찾기
					LoginServer.find();
				} else if (str1.contentEquals("duple")) {// 중복 체크
					LoginServer.duple();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("실패");
				try {
					gameSocket.close();
					chatSocket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			}
		}
		LobbyServer.userList.remove(name); // 총 유저 리스트에서 해당 유저 삭제
	}

}
