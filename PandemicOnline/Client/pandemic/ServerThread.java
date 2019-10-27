package pandemic;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.*;

public class ServerThread extends Thread {
	private Socket Csocket = null;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	// ���� ������ ���� MAP �ڷᱸ��
	private Map<String, DataOutputStream> clientMap;
	private Map<String, String> JoinMap;
	
	public ServerThread(Socket Csocket, Map<String, DataOutputStream> clientMap, Map<String, String> JoinMap) {
		this.Csocket = Csocket;
		this.clientMap = clientMap;
		this.JoinMap = JoinMap;
		
	}
	
	
	public void run() {
		// Ŭ���̾�Ʈ�κ��� ���� ID PW ���� Ȯ���ϴ� �����α��� ����
		new ServerLogin(Csocket, JoinMap);
		
		// �κ�ä���� �ϱ����� ������ ����
		LobbyChat lobbychat = new LobbyChat(Csocket, clientMap, JoinMap);
		lobbychat.start();
	}
}
