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
	private LobbyServer lobbyServer = null;

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
		while (true) {
			System.out.println("확인");
			LoginServer = new Login(gameSocket, chatSocket); // 로그인 객체 생성
			try {
				String str1 = input.readUTF();
				System.out.println(str1);
				if (str1.equals("login")) {// 로그인 체크
					// 성공
					if (LoginServer.loginCheck()) {
						output.writeUTF("true");
						name = LoginServer.getName();
						lobbyServer = new LobbyServer(gameSocket, chatSocket, name);
					} else
						output.writeUTF("false");// 성공하면 로비 객체생성
				} else if (str1.equals("join")) {// 회원가입
					if (LoginServer.join()) {
						output.writeUTF("true");
					} else
						output.writeUTF("false");
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("실패");
				break;
			} finally {
			}

		}
	}

}
